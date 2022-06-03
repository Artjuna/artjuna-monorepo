import os
import sys

os.environ["TF_CPP_MIN_LOG_LEVEL"] = "3"
sys.path.append("..")
import base64
import io
import shutil
import urllib.request

import numpy as np
import tensorflow as tf
from PIL import Image

from model.style_transfer.adain.img_preprocess import central_crop, scale_image
from model.style_transfer.adain.model import adain


class StyleTransferModel:
    def load_model(self, model_path):
        try:
            self.model = tf.saved_model.load(model_path)
        except:
            print("Model not Found, Downloading File")
            urllib.request.urlretrieve(
                r"https://drive.google.com/uc?export=download&id=1RNHWRUXKDASKElmxQu-70bfkc93EqIyp&confirm=t",
                "adain_300.zip",
            )
            print("Make directory")
            os.mkdir("adain_300")
            print("Unzip")
            shutil.unpack_archive("adain_300.zip", "adain_300")
            print("Delete zip file")
            os.remove("adain_300.zip")
            print("Load model")
            self.model = tf.saved_model.load(model_path)

    def style_transfer(self, prepared_style, prepared_content):
        style_encoded = self.model.encoder(prepared_style)
        content_encoded = self.model.encoder(prepared_content)
        mapped = adain(style=style_encoded, content=content_encoded)
        reconstructed_image = self.model.decoder(mapped)
        reconstructed_image = reconstructed_image * 255.0
        reconstructed_image = np.uint8(reconstructed_image[0])
        im = Image.fromarray(reconstructed_image)

        with io.BytesIO() as buf:
            im.save(buf, format="PNG")
            im_bytes = buf.getvalue()

        return im_bytes

    def image_decoder(self, image_b64):
        # Turn base64 byte string to raw bytes
        image_b64_raw = base64.b64decode(image_b64)
        # Turn raw bytes to image
        image = tf.image.decode_image(image_b64_raw, channels=3)
        return image

    def image_preprocessing(self, image):
        image = np.asarray(image)
        image = central_crop(image)
        image = scale_image(image, 224)
        image = image / 255
        image = np.expand_dims(image, axis=0)
        image = tf.cast(image, tf.float32)
        return image

    def image_converter(self, image_b64):
        image = self.image_decoder(image_b64)
        image = self.image_preprocessing(image)
        return image

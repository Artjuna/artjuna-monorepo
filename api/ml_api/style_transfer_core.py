import os
import sys

os.environ["TF_CPP_MIN_LOG_LEVEL"] = "3"
sys.path.append("..")
import base64
import io
import shutil
import urllib.request
import base64

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

        base64_image = base64.b64encode(im_bytes).decode("utf-8")

        return base64_image

    def image_preprocessing(self, image):
        image = np.asarray(image)
        image = central_crop(image)
        image = scale_image(image, 224)
        image = image / 255
        image = np.expand_dims(image, axis=0)
        image = tf.cast(image, tf.float32)
        return image

    def get_image(self, path):
        return Image.open(path).convert("RGB")

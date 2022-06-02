import os

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3' 
import base64

import tensorflow as tf


class StyleTransferModel:
    
    def image_decoder(self, image_b64):
        # Turn base64 byte string to raw bytes
        image_b64_raw = base64.b64decode(image_b64)
        # Turn raw bytes to image
        image = tf.image.decode_image(image_b64_raw, channels=3)
        return image

    def image_preprocessing(self,image):
        # Casting image to float32
        image = tf.cast(image,tf.float32)
        # Normalizing image to [0,1]
        image = image/255.0
        # Resizing image to 224x224
        image = tf.image.resize(image,(224,224))
        return image

    def image_converter(self, image_b64):
        image = self.image_decoder(image_b64)
        image = self.image_preprocessing(image)
        return image

    # def load_model(self,model_path):
        
    

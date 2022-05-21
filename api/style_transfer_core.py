import tensorflow as tf
import base64

class StyleTransferModel:
    
    def image_decoder(self, image_b64):
        # Turn base64 byte string to raw bytes
        image_b64_raw = base64.b64decode(image_b64)
        # Turn raw bytes to image
        image = tf.image.decode_image(image_b64_raw, channels=3)
        return image

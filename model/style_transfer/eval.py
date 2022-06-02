import tensorflow as tf
import os
import numpy as np
from PIL import Image

from adain.model import adain, mean_std
from adain.img_preprocess import scale_image, central_crop, prepare_image

# Fill in saved model folder path 
saved_model_dir = 'Adain 300 Epochs'
# Load saved_model model
model = tf.saved_model.load(saved_model_dir)


content_folder = r'\eval_images\content'
style_folder = r'\eval_images\style'
result_folder = r'\style_transfer_images'

content_list = os.listdir(content_folder)
style_list = os.listdir(style_folder)

content_full_path_list = [os.path.join(content_folder, img_pth) for img_pth in content_list]
style_full_path_list = [os.path.join(style_folder, img_pth) for img_pth in style_list]

# Save reconstructed image to result folder
for content in content_full_path_list:
    for style in style_full_path_list:
        
        # Preparing content and style format
        prepared_content = prepare_image(content, 224)
        prepared_style = prepare_image(style, 224)
        
        # Process array to model
        style_encoded = model.encoder(prepared_style)
        content_encoded = model.encoder(prepared_content)
        mapped = adain(style=style_encoded, content=content_encoded)
        reconstructed_image = model.decoder(mapped)
        reconstructed_image = reconstructed_image*255.0
        
        file_name = os.path.split(content)[1].split('.')[0] + '_' + os.path.split(style)[1].split('.')[0] + '.jpg'
        save_name = os.path.join(result_folder, file_name)
        Image.fromarray(np.uint8(reconstructed_image[0])).save(save_name)

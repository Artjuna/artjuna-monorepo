# AdaIn saved model can be downloaded from this link: 

 #### https://drive.google.com/drive/folders/1HvqzHQcjTnKz-NA9-7h-7j3i2Cl8AYdJ?usp=sharing

## This link containing trained model using configurations:
<li> batch_size = 6
<li> image_size = (224,224)
<li> steps_per_epochs = 50
<li> validation_steps = 50
<li> epochs = 300
<li> content dataset : (VOC Pascal(main) + COCO Validation Set)
<li> style dataset : (Best Artworks of All Time (main) + 5000 Wikiart images)
 
  -----
# Testing

### Place downloaded saved_model folder to "saved_model" folder 
### First, put content images on 'eval_images/content' and style images on 'eval_images/content'
### Run eval.py and transfered image will be saved on 'style_transfer_images' folder
  
  -----
  
# Train from scratch
  ### Download all required dataset with steps provided on folder 'load_dataset'
  ### Open prepare_dataset.py, change directory of each dataset where corresponding images located then run it
  ### By running that file, 'content' and 'style' folder will be filled with .tfrecords file with split size setting from prepare_dataset.py
  ### Open run.py, set training configuration, run it and it will train AdaIn model, and save this model on 'saved_model' folder
  ### Put content images on 'eval_images/content' and style images on 'eval_images/content'
  ### Run eval.py and transfered image will be saved on 'style_transfer_images' folder
 

# AdaIn saved model can be downloaded from this link: 

 #### https://drive.google.com/file/d/1dELEG_0w8NWFSeHXipr3gMEGuzXFn9O3/view?usp=sharing
 

## This link containing trained model using configurations:
<li> batch_size = 6
<li> image_size = (224,224)
<li> style weight factor = 0.6
<li> steps_per_epochs = 50
<li> validation_steps = 50
<li> epochs = 500
<li> content dataset : (VOC Pascal(main) + COCO Validation Set)
<li> style dataset : (Best Artworks of All Time (main) + 5000 Wikiart images)
 
## Styled image example from test dataset: 
 ![adain1](https://user-images.githubusercontent.com/92104520/171184421-273fd9af-5b63-44e1-b58c-a55e084ee4a6.JPG)
 ![adain2](https://user-images.githubusercontent.com/92104520/171184430-14d84d01-8422-4697-8f27-696980780d3d.JPG)
 ![adain3](https://user-images.githubusercontent.com/92104520/171184448-5943f618-2a11-459d-b96d-1fba2bdc9a45.JPG)
 ![adain4](https://user-images.githubusercontent.com/92104520/171184384-c69cf1c6-3171-483d-9186-e2a3118383c8.JPG)

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
 

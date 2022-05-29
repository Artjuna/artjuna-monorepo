# Import library
import tensorflow as tf
from pathlib import Path
import os
import shutil
from adain.pipeline import full_path_list, parse_tfrecord, convert_to_tfrecords, image_feature, split_move_folder

# Define Global Variables
# Define resized image size (use 224x224 because VGG19 trained with this size)
IMAGE_SIZE = (224,224)

# Define number of samples in each tfrecords file
NUM_SAMPLES = 1000
AUTOTUNE = tf.data.AUTOTUNE

# Change this path to path where corresponding images located
old_wikiart_path_img = r"\Small Set Wikiart\style"
old_coco_path_img = r"\validation\data"
old_best_art_path_img = r"\resized\resized"

# For VOC images please define each split path when its downloaded
voc_train_path_img = r"\voc\train"
voc_val_path_img = r"\voc\val"
voc_test_path_img = r"\voc\test"

# Don't Change!!!
# Don't Change!
# ------------------------------------------------------------------------------------------------

# Define coco wikiart & best_art path to variables
coco_train_path_img = r"\coco\train"
coco_val_path_img = r"\coco\val"
coco_test_path_img = r"\coco\test"

wikiart_train_path_img = r"\wikiart\train"
wikiart_val_path_img = r"\wikiart\val"
wikiart_test_path_img = r"\wikiart\test"

best_art_train_path_img = r"\best_art\train"
best_art_val_path_img = r"\best_art\val"
best_art_test_path_img = r"\best_art\test"

# tfrecords output folder path
voc_train_path_tfrecs = r"\content\train"
voc_val_path_tfrecs = r"\content\val"
voc_test_path_tfrecs = r"\content\test"

coco_train_path_tfrecs = r"\content\train"
coco_val_path_tfrecs = r"\content\val"
coco_test_path_tfrecs = r"\content\test"

best_art_train_path_tfrecs = r"\style\train"
best_art_val_path_tfrecs = r"\style\val"
best_art_test_path_tfrecs = r"\style\test"

wikiart_train_path_tfrecs = r"\style\train"
wikiart_val_path_tfrecs = r"\style\val"
wikiart_test_path_tfrecs = r"\style\test"

# Define list to iterate
DATASET_NAME_LIST = ["VOC", "COCO", "BestArtwork", "Wikiart"]

# Image path list
TRAIN_PATH_IMG_LIST = [voc_train_path_img, coco_train_path_img, best_art_train_path_img, wikiart_train_path_img]
VAL_PATH_IMG_LIST = [voc_val_path_img, coco_val_path_img, best_art_val_path_img, wikiart_val_path_img]
TEST_PATH_IMG_LIST = [voc_test_path_img, coco_test_path_img, best_art_test_path_img, wikiart_test_path_img]

# tfrecords output path list
TRAIN_PATH_TFRECS_LIST = [voc_train_path_tfrecs, coco_train_path_tfrecs, best_art_train_path_tfrecs, wikiart_train_path_tfrecs]
VAL_PATH_TFRECS_LIST = [voc_val_path_tfrecs, coco_val_path_tfrecs, best_art_val_path_tfrecs, wikiart_val_path_tfrecs]
TEST_PATH_TFRECS_LIST = [voc_test_path_tfrecs, coco_test_path_tfrecs, best_art_test_path_tfrecs, wikiart_test_path_tfrecs]


# Create directory of splitted 
for i in range(len(TRAIN_PATH_IMG_LIST)):
    if os.path.exists(Path(TRAIN_PATH_IMG_LIST[i])):
        pass
    else:
        os.makedirs(Path(TRAIN_PATH_IMG_LIST[i]))
        
    if os.path.exists(Path(VAL_PATH_IMG_LIST[i])):
        pass
    else:
        os.makedirs(Path(VAL_PATH_IMG_LIST[i]))
        
    if os.path.exists(Path(TEST_PATH_IMG_LIST[i])):
        pass
    else:
        os.makedirs(Path(TEST_PATH_IMG_LIST[i]))

# Splitting Section
all_wikiart = os.listdir(old_wikiart_path_img)
all_coco = os.listdir(old_coco_path_img)
all_best_art = os.listdir(old_best_art_path_img)
num_best_art = len(all_best_art)

# Define Split Size
wikiart_train = all_wikiart[:3000]
wikiart_val = all_wikiart[3000:4000]
wikiart_test = all_wikiart[4000:]

coco_train = all_coco[:4000]
coco_val = all_coco[4000:4500]
coco_test = all_coco[4500:]

best_art_train = all_best_art[:int(0.8*num_best_art)]
best_art_val = all_best_art[int(0.8*num_best_art):int(0.9*num_best_art)]
best_art_test = all_best_art[int(0.9*num_best_art):]
     
# Move coco image to splitted folder
split_move_folder(old_coco_path_img, coco_train_path_img, coco_train)
split_move_folder(old_coco_path_img, coco_val_path_img, coco_val)
split_move_folder(old_coco_path_img, coco_test_path_img, coco_test)

# Move wikiart image to splitted folder
split_move_folder(old_wikiart_path_img, wikiart_train_path_img, wikiart_train)
split_move_folder(old_wikiart_path_img, wikiart_val_path_img, wikiart_val)
split_move_folder(old_wikiart_path_img, wikiart_test_path_img, wikiart_test)

# Move best_art image to splitted folder
split_move_folder(old_best_art_path_img, best_art_train_path_img, best_art_train)
split_move_folder(old_best_art_path_img, best_art_val_path_img, best_art_val)
split_move_folder(old_best_art_path_img, best_art_test_path_img, best_art_test)   

# Converting all images to .tfrecords and place it to content and style directory
for i in range(len(TRAIN_PATH_TFRECS_LIST)):
    if len(os.listdir(TRAIN_PATH_IMG_LIST[i]))!= 0:
        
        # Converting train image set
        if os.path.exists(Path(TRAIN_PATH_TFRECS_LIST[i])):
            convert_to_tfrecords((TRAIN_PATH_IMG_LIST[i]),(TRAIN_PATH_TFRECS_LIST[i]), NUM_SAMPLES,DATASET_NAME_LIST[i])
        else:
            os.makedirs(Path(TRAIN_PATH_TFRECS_LIST[i]))
            convert_to_tfrecords((TRAIN_PATH_IMG_LIST[i]),(TRAIN_PATH_TFRECS_LIST[i]), NUM_SAMPLES,DATASET_NAME_LIST[i])
            
        # Converting validation image set
        if os.path.exists(Path(VAL_PATH_TFRECS_LIST[i])):
            convert_to_tfrecords((VAL_PATH_IMG_LIST[i]),(VAL_PATH_TFRECS_LIST[i]), NUM_SAMPLES,DATASET_NAME_LIST[i])
        else:
            os.makedirs(Path(VAL_PATH_TFRECS_LIST[i]))
            convert_to_tfrecords((VAL_PATH_IMG_LIST[i]),(VAL_PATH_TFRECS_LIST[i]), NUM_SAMPLES,DATASET_NAME_LIST[i])
            
        # Converting test image set
        if os.path.exists(Path(TEST_PATH_TFRECS_LIST[i])):
            convert_to_tfrecords((TEST_PATH_IMG_LIST[i]),(TEST_PATH_TFRECS_LIST[i]), NUM_SAMPLES,DATASET_NAME_LIST[i])
        else:
            os.makedirs(Path(TEST_PATH_TFRECS_LIST[i]))
            convert_to_tfrecords((TEST_PATH_IMG_LIST[i]),(TEST_PATH_TFRECS_LIST[i]), NUM_SAMPLES,DATASET_NAME_LIST[i])

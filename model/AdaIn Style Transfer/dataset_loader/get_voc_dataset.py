import tensorflow_datasets as tfds

''' VOC dataset will be used as main content datasset,
which containing arround 10,000 images with a set of objects, out of 20 different classes.''' 

train_content_dataset = tfds.load("voc", split="train")
val_content_dataset = tfds.load("voc", split="val")
test_content_dataset = tfds.load("voc", split="test")

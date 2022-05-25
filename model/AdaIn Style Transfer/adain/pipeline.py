import os
import tensorflow as tf

def full_path_list(folder):
    '''Return full path of file that have .tfrecords extension'''
    
    full_path_list = []
    extension = ['.tfrecords']
    
    for dir_path, _ , file_names in os.walk(folder, topdown = True):
        for file_name in file_names:
            if os.path.splitext(file_name)[1] in extension:
                full_path_list.append(os.path.join(dir_path, file_name))
                
    return full_path_list
    
    
def parse_tfrecord(example):
    '''Parsing, decode, convert data type,
    normalize and resize image from .tfrecrds file'''
    
    feature_desc = {"image": tf.io.FixedLenFeature((), tf.string, "")}
    example = tf.io.parse_single_example(example, feature_desc)
    example["image"] = tf.io.decode_jpeg(example["image"], channels=3)
    example["image"] = tf.cast(example["image"], tf.float32)
    
    # Normalize image
    example["image"] = example["image"]/255.0
    
    # Resize image to (224,224)
    example["image"] = tf.image.resize(example["image"], [224,224])
    
    return example['image']
  
    
def convert_to_tfrecords(input_folder_path, output_folder_path, num_samples, dataset_name):
    '''Convert images from input folder to certain amount of .tfrecords file
    based on num_shards value and write it at output folder path'''
    
    num_tfrecords = len(input_folder_path) // num_samples
    if len(input_folder_path) % num_samples:
        num_tfrecords += 1 
    
    for tfrecords_n in range(num_tfrecords):
        samples = input_folder_path[(tfrecords_n * num_samples) : ((tfrecords_n + 1) * num_samples)]
        
        with tf.io.TFRecordWriter(output_folder_path + "/" + dataset_name + "_%.2i-%i.tfrecords" % (tfrecords_n, len(samples))) as writer:
            for sample in samples:
                image = tf.io.decode_jpeg(tf.io.read_file(sample))
                feature = {"image": image_feature(image)}
                example = tf.train.Example(features=tf.train.Features(feature=feature))
                writer.write(example.SerializeToString())

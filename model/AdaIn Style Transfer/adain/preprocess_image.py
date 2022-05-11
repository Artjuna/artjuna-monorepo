from imageio import imread, imwrite
from PIL import Image
import numpy as np

import tensorflow as tf

def load_image(filename, size, crop):
    image = imread(filename)
    if crop:
        image = central_crop(image)
    if size:
        image = scale_image(image, size)
    return image

def prepare_image(image, normalize=True, data_format='channels_first'):
    if normalize:
        image = image.astype(np.float32)
        image /= 255
    if data_format == 'channels_first':
        image = np.transpose(image, [2, 0, 1]) # HWC --> CHW
    return image

def scale_image(image, size):
    "size specifies the minimum height or width of the output"
    h, w, _ = image.shape
    if h > w:
        image = np.array(Image.fromarray(image).resize((int(h*size//w),
                                                        int(size)),
                                                        Image.Resampling.BICUBIC))
    else:
        image = np.array(Image.fromarray(image).resize((int(size),
                                                        int(w*size//h)),
                                                        Image.Resampling.BICUBIC))
    return image

def central_crop(image):
    h, w, _ = image.shape
    minsize = min(h, w)
    h_pad, w_pad = (h - minsize) // 2, (w - minsize) // 2
    image = image[h_pad:h_pad+minsize,w_pad:w_pad+minsize]
    return image

def save_image(filename, image, data_format='channels_first'):
    if data_format == 'channels_first':
        image = np.transpose(image, [1, 2, 0]) # CHW --> HWC
    image *= 255
    image = np.clip(image, 0, 255)
    imwrite(filename, image.astype(np.uint8))


def load_mask(filename, h, w):
    mask = imread(filename)
    mask = np.array(Image.fromarray(mask).resize((int(h),int(w)), Image.Resampling.NEAREST))
    mask = imresize(mask, (h, w), interp='nearest')
    mask = mask.astype(np.uint8)
    mask[mask == 255] = 1
    return mask

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
    '''Apply central crop to image'''
    h, w, _ = image.shape
    minsize = min(h, w)
    h_pad, w_pad = (h - minsize) // 2, (w - minsize) // 2
    image = image[h_pad:h_pad+minsize, w_pad:w_pad+minsize]
    return image

def prepare_image(file_dir, size):
    '''Prepare image to be feed to AdaIn model'''
    image = Image.open(file_dir)
    image = np.asarray(image)
    image = central_crop(image)
    image = scale_image(image,size)
    image = image /255
    image = np.expand_dims(image, axis=0)
    image = tf.cast(image, tf.float32)
    return image

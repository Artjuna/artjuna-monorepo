import tensorflow as tf
from tensorflow.keras import layers

def create_encoder():
    '''Create encoder model from VGG19 model
    until "block4_conv1" layer'''
    
    vgg19 = tf.keras.applications.vgg19.VGG19(
        include_top=False,
        weights="imagenet",
        input_shape=(224, 224, 3))
    
    vgg19.trainable = False
    
    encoder = tf.keras.Model(vgg19.input, vgg19.get_layer("block4_conv1").output)
    inputs = layers.Input([224, 224, 3], name='image')
    encoder_out = encoder(inputs)
    
    return tf.keras.Model(inputs, encoder_out, name="encoder")

def create_decoder():
    '''Create decoder model based on inverted encoder structure'''
    
    config = {"kernel_size": 3, "strides": 1, "padding": "same", "activation": "relu"}
    decoder = tf.keras.Sequential(
        [layers.InputLayer((None, None, 512)),
        layers.Conv2D(512, **config),
        layers.UpSampling2D(),
        layers.Conv2D(256, **config),
        layers.Conv2D(256, **config),
        layers.Conv2D(256, **config),
        layers.Conv2D(256, **config),
        layers.UpSampling2D(),
        layers.Conv2D(128, **config),
        layers.Conv2D(128, **config),
        layers.UpSampling2D(),
        layers.Conv2D(64, **config),
        layers.Conv2D(3,kernel_size=3,
                      strides=1,padding="same",activation="sigmoid")
        ])
    
    return decoder

def create_loss_model():
    '''Create loss model from VGG19 similar to encoder model
    to evalute mean & deviation each layer'''
    
    vgg19 = tf.keras.applications.vgg19.VGG19(
        include_top=False,
        weights="imagenet",
        input_shape=(224, 224, 3))
    
    vgg19.trainable = False
    
    layer_names = ["block1_conv1", "block2_conv1", "block3_conv1", "block4_conv1"]
    
    outputs = [vgg19.get_layer(name).output for name in layer_names]
    loss_model = tf.keras.Model(vgg19.input, outputs)

    inputs = layers.Input([224, 224, 3], name='image')
    loss_model_out = loss_model(inputs)
    
    return tf.keras.Model(inputs, loss_model_out, name="loss_net")

def mean_std(x, epsilon=1e-5):
    '''Calculate mean and standard deviation'''
    
    axes = [1, 2]
    
    mean, variance = tf.nn.moments(x, axes=axes, keepdims=True)
    standard_deviation = tf.sqrt(variance + epsilon)
    return mean, standard_deviation


def adain(style, content):
    '''Computes the AdaIn feature map'''
    
    content_mean, content_std = mean_std(content)
    style_mean, style_std = get_mean_std(style)
    t = style_std * (content - content_mean) / content_std + style_mean
    
    return adain

class NeuralStyleTransfer(tf.keras.Model):
    '''Combine encoder decoder and loss model and calculate total loss'''
    
    def __init__(self, encoder, decoder, loss_net, style_weight, **kwargs):
        super().__init__(**kwargs)
        self.encoder = encoder
        self.decoder = decoder
        self.loss_net = loss_net
        self.style_weight = style_weight

    def compile(self, optimizer, loss_fn):
        super().compile()
        self.optimizer = optimizer
        self.loss_fn = loss_fn
        self.style_loss_tracker = tf.keras.metrics.Mean(name="style_loss")
        self.content_loss_tracker = tf.keras.metrics.Mean(name="content_loss")
        self.total_loss_tracker = tf.keras.metrics.Mean(name="total_loss")

    def train_step(self, inputs):
        style, content = inputs

        # Initialize content and style loss.
        loss_content = 0.0
        loss_style = 0.0

        with tf.GradientTape() as tape:
            style_encoded = self.encoder(style)
            content_encoded = self.encoder(content)
            adain = adain(style=style_encoded, content=content_encoded)
            reconstructed_image = self.decoder(adain)

            # Compute losses.
            reconstructed_vgg_features = self.loss_net(reconstructed_image)
            style_vgg_features = self.loss_net(style)
            loss_content = self.loss_fn(t, reconstructed_vgg_features[-1])
            
            for inp, out in zip(style_vgg_features, reconstructed_vgg_features):
                mean_inp, std_inp = mean_std(inp)
                mean_out, std_out = mean_std(out)
                loss_style += self.loss_fn(mean_inp, mean_out) + self.loss_fn(
                    std_inp, std_out)
                
            loss_style = self.style_weight * loss_style
            total_loss = loss_content + loss_style

        # Compute gradients and optimize the decoder.
        trainable_vars = self.decoder.trainable_variables
        gradients = tape.gradient(total_loss, trainable_vars)
        self.optimizer.apply_gradients(zip(gradients, trainable_vars))

        self.style_loss_tracker.update_state(loss_style)
        self.content_loss_tracker.update_state(loss_content)
        self.total_loss_tracker.update_state(total_loss)
        
        return {
            "style_loss": self.style_loss_tracker.result(),
            "content_loss": self.content_loss_tracker.result(),
            "total_loss": self.total_loss_tracker.result(),
        }

    def test_step(self, inputs):
        style, content = inputs

        # Initialize the content and style loss.
        loss_content = 0.0
        loss_style = 0.0

        style_encoded = self.encoder(style)
        content_encoded = self.encoder(content)
        adain = adain(style=style_encoded, content=content_encoded)
        reconstructed_image = self.decoder(adain)

        # Compute losses.
        recons_vgg_features = self.loss_net(reconstructed_image)
        style_vgg_features = self.loss_net(style)
        loss_content = self.loss_fn(t, recons_vgg_features[-1])
        
        for inp, out in zip(style_vgg_features, recons_vgg_features):
            mean_inp, std_inp = mean_std(inp)
            mean_out, std_out = mean_std(out)
            loss_style += self.loss_fn(mean_inp, mean_out) + self.loss_fn(
                std_inp, std_out)
            
        loss_style = self.style_weight * loss_style
        total_loss = loss_content + loss_style

        self.style_loss_tracker.update_state(loss_style)
        self.content_loss_tracker.update_state(loss_content)
        self.total_loss_tracker.update_state(total_loss)
        
        return {
            "style_loss": self.style_loss_tracker.result(),
            "content_loss": self.content_loss_tracker.result(),
            "total_loss": self.total_loss_tracker.result(),
        }

    @property
    def metrics(self):
        return [
            self.style_loss_tracker,
            self.content_loss_tracker,
            self.total_loss_tracker,
        ]


class PlotCallback(tf.keras.callbacks.Callback):
    '''Plot content, style, and NST image from test set each epoch'''
    
    def on_epoch_end(self, epoch, logs=None):
        test_style_encoded = self.model.encoder(test_style)
        test_content_encoded = self.model.encoder(test_content)

        adain = adain(style=test_style_encoded, content=test_content_encoded)
        test_reconstructed_image = self.model.decoder(adain)

        fig, ax = plt.subplots(nrows=1, ncols=3, figsize=(20, 5))
        
        ax[0].imshow(tf.keras.preprocessing.image.array_to_img(test_content[0]))
        ax[0].set_title(f"Content: {epoch:03d}")
        
        ax[1].imshow(tf.keras.preprocessing.image.array_to_img(test_style[0]))
        ax[1].set_title(f"Style: {epoch:03d}")

        ax[2].imshow(
            tf.keras.preprocessing.image.array_to_img(test_reconstructed_image[0])
        )
        ax[2].set_title(f"NST: {epoch:03d}")

        plt.show()
        plt.close()

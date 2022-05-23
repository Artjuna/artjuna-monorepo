# Import library
import tensorflow as tf
import os
from adain.model import create_encoder, create_decoder, create_loss_model, mean_std, adain, NeuralStyleTransfer
from adain.pipeline import full_path_list, parse_tfrecord
from pathlib import Path

# Define global variable
EPOCHS = 500
BATCH_SIZE = 6
SAVE_PERIOD = 10
STEPS_PER_EPOCH = 50

checkpoint_path = Path(
    r'Checkpoint\cp-{epoch:02d}')
checkpoint_dir = os.path.dirname(checkpoint_path)

# Define content path where tfrecords data placed
out_train_content = Path(r'content\train')
out_val_content = Path(r'content\val')
out_test_content = Path(r'content\test')

# Define style path where tfrecords data placed
out_train_style = Path(r'style\train')
out_val_style = Path(r'style\val')
out_test_style = Path(r'style\test')

# Extract tfrecords files full path in content folder
train_content_list = full_path_list(out_train_content)
val_content_list = full_path_list(out_val_content)
test_content_list =full_path_list(out_test_content)

# Extract tfrecords files full path in style folder
train_style_list = full_path_list(out_train_style)
val_style_list = full_path_list(out_val_style)
test_style_list =full_path_list(out_test_style)

# Build the style and content tf.data datasets.
tf.autograph.set_verbosity(0)

train_style_ds = tf.data.TFRecordDataset(train_style_list).map(parse_tfrecord, num_parallel_calls=tf.data.AUTOTUNE).repeat()
val_style_ds = tf.data.TFRecordDataset(val_style_list).map(parse_tfrecord, num_parallel_calls=tf.data.AUTOTUNE).repeat()
test_style_ds = tf.data.TFRecordDataset(test_style_list).map(parse_tfrecord, num_parallel_calls=tf.data.AUTOTUNE).repeat()

train_content_ds = tf.data.TFRecordDataset(train_content_list).map(parse_tfrecord).repeat()
val_content_ds = tf.data.TFRecordDataset(val_content_list).map(parse_tfrecord).repeat()
test_content_ds = tf.data.TFRecordDataset(test_content_list).map(parse_tfrecord, num_parallel_calls=tf.data.AUTOTUNE).repeat()

# Zipping the style and content datasets.
train_ds = (tf.data.Dataset.zip((train_style_ds, train_content_ds))
            .shuffle(BATCH_SIZE * 4)
            .batch(BATCH_SIZE)
            .prefetch(tf.data.AUTOTUNE))

val_ds = (tf.data.Dataset.zip((val_style_ds, val_content_ds))
          .shuffle(BATCH_SIZE * 4)
          .batch(BATCH_SIZE)
          .prefetch(tf.data.AUTOTUNE))

test_ds = (tf.data.Dataset.zip((test_style_ds, test_content_ds))
           .shuffle(BATCH_SIZE * 4)
           .batch(BATCH_SIZE)
           .prefetch(tf.data.AUTOTUNE))

# Checkpoints callback
cp_callback = tf.keras.callbacks.ModelCheckpoint(filepath=checkpoint_path, 
                                                 verbose=1, 
                                                 save_weights_only=True,
                                                 save_freq= int(SAVE_PERIOD * STEPS_PER_EPOCH))

# Compiling model
optimizer = tf.keras.optimizers.Adam(learning_rate=1e-5)
loss_fn = tf.keras.losses.MeanSquaredError()

encoder = create_encoder()
decoder = create_decoder()
loss_model = create_loss_model()

model = NeuralStyleTransfer(encoder=encoder, decoder=decoder,
                            loss_net=loss_model, style_weight=4.0)

model.compile(optimizer=optimizer, loss_fn=loss_fn)

# Fitting model
history = model.fit(train_ds,
                    epochs= EPOCHS,
                    steps_per_epoch= STEPS_PER_EPOCH,
                    validation_data= val_ds,
                    validation_steps= STEPS_PER_EPOCH,
                    callbacks = [cp_callback])

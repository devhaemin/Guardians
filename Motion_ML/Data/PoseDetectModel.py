from keras.applications.mobilenet_v2 import MobileNetV2

from keras import models
from keras import layers

from keras.preprocessing import image
from keras.preprocessing.image import ImageDataGenerator
from keras import optimizers

import matplotlib.pyplot as plt

import numpy as np


flag = ''   # determine if trainable
h5_path = ''

def smooth_curve(points, factor=0.8):
    smoothed_points = []
    for point in points:
        if smoothed_points:
            previous = smoothed_points[-1]
            smoothed_points.append(previous * factor + point * (1 - factor))
        else:
            smoothed_points.append(point)
    return smoothed_points


class MobileNetV2Train:

    def __init__(self):
        self.model = models.Sequential()


        ########################################
        conv_base = MobileNetV2(weights='imagenet',
                                include_top=False,
                                input_shape=(224, 224, 3))

        # set trainable
        conv_base.trainable = True
        set_trainable = False
        for layer in conv_base.layers:
            if layer.name == flag:
                set_trainable = True
            if set_trainable:
                layer.trainable = True
            else:
                layer.trainable = False

        self.model.add(conv_base)
        self.model.add(layers.GlobalAveragePooling2D())
        self.model.add(layers.Dense(5, activation='sigmoid'))

        self.model.compile(loss='binary_crossentropy',
                           optimizer=optimizers.RMSprop(lr=1e-4),
                           metrics=['acc'])
        ########################################

        self.train_dir = ''
        self.test_dir = ''
        self.validation_dir = ''

        self.train_datagen = ImageDataGenerator(
            rescale=1. / 255,
            rotation_range=10,
            width_shift_range=0.1,
            height_shift_range=0.1,
            shear_range=0.1,
            zoom_range=0.1,
            horizontal_flip=False,
            fill_mode='nearest')
        self.test_datagen = ImageDataGenerator(rescale=1. / 255)

        self.train_generator = self.train_datagen.flow_from_directory(
            self.train_dir,
            target_size=(224, 224), #
            batch_size=20,
            class_mode='binary')
        self.validation_generator = self.test_datagen.flow_from_directory(
            self.validation_dir,
            target_size=(224, 224), #
            batch_size=20,
            class_mode='binary')
        self.test_generator = self.test_datagen.flow_from_directory(
            self.test_dir,
            target_size=(224, 224), #
            batch_size=20,
            class_mode='binary')

        self.history


    def train(self):
        self.history = self.model.fit_generator(
            self.train_generator,
            steps_per_epoch=100,
            epochs=100,
            validation_data=self.validation_generator,
            validation_steps=50,
            verbose=2)


    def show_history(self):
        acc = smooth_curve(self.history.history['acc'])
        val_acc = smooth_curve(self.history.history['val_acc'])
        loss = smooth_curve(self.history.history['loss'])
        val_loss = smooth_curve(self.history.history['val_loss'])

        epochs = range(1, len(acc) + 1)

        plt.plot(epochs, acc, 'bo', label='Training acc')
        plt.plot(epochs, val_acc, 'b', label='Validation acc')
        plt.title("Training and Validation accuracy")
        plt.legend()

        plt.figure()

        plt.plot(epochs, loss, 'bo', label='Training loss')
        plt.plot(epochs, val_loss, 'b', label='Validation loss')
        plt.title("Training and Validation loss")
        plt.legend()

        plt.show()


    def load_model(self, path):
        self.model = models.load_model(path)


    def save_model(self, path):
        self.model.save_weights(path, save_format='.h5')


class MobileNetV2:

    def __init__(self):
        self.model = models.Sequential()

        conv_base = MobileNetV2(weights='imagenet',
                                include_top=False,
                                input_shape=(224, 224, 3))

        # set trainable
        conv_base.trainable = True
        set_trainable = False
        for layer in conv_base.layers:
            if layer.name == flag:
                set_trainable = True
            if set_trainable:
                layer.trainable = True
            else:
                layer.trainable = False

        self.model.add(conv_base)
        self.model.add(layers.GlobalAveragePooling2D())
        self.model.add(layers.Dense(1, activation='sigmoid'))

        self.model.compile(loss='binary_crossentropy',
                           optimizer=optimizers.RMSprop(lr=1e-4),
                           metrics=['acc'])

        self.model = models.load_model(h5_path)

    def run(self, img_path):
        img = image.load_img(img_path, target_size=(224, 224))
        img_tensor = image.img_to_array(img)
        img_tensor = np.expand_dims(img_tensor, axis=0)
        img_tensor /= 255.

        activation = self.model.predict(img_tensor)
        return activation


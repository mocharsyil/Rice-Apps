# How to replicate our research

## 1. Download the datasets
Our dataset is a set of images we get from combining [1] and [2] and preprocessing the dataset by resizing all the images in the dataset. We resized all the images into 256 x 256 pixels by using Python Image Library (PIL). Our combined dataset contains 6 different classes (Blast, Blight, Brownspot, Hispa, Healthy, Tungro) and we have 80 images for each class. The datasets for Hispa, Brownspot, and Healthy classes are from [1] and the datasets for Blast, Blight, Tungro classes are from [2]. For each class we have, we used 75 % of the total number of images for training and 25 % to be our validation set. 

### References:
* [1] Rhiyas, S. (2019, July). Rice Leafs, Version 1. Retrieved May 18, 2021 from https://www.kaggle.com/shayanriyaz/riceleafs.
* [2] Setiady, T. (2021, May). Leaf Rice Disease, Version 1. Retrieved May 19, 2021 from https://www.kaggle.com/tedisetiady/leaf-rice-disease-indonesia.

## 2. Create a Deep Learning model using TensorFlow
We did't build the model from scratch, we used transfer learning method instead. Our model is based on VGG19 architecture that has been trained on ImageNet dataset. In our model, we add some layers on top of the base model and then train the model using the dataset we get from the first step. And the last part of this step is to export the model.

For the better explanation, you can visit this notebook: https://colab.research.google.com/drive/1cVeFRl9KXwEUfDWlQiV0DKiTLQMAmOSB?usp=sharing

## 3. Deploy the model on the cloud server

## 4. Create an API to connect our mobile App to our cloud server

## 5. Create the mobile App for Android



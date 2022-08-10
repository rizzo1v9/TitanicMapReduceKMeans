#!/usr/bin/env python
# coding: utf-8

# In[1]:


import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from matplotlib import style
style.use("ggplot")
from sklearn.cluster import KMeans
from mpl_toolkits.mplot3d import Axes3D

X = pd.read_csv("C:/Users/ctr33/OneDrive/Documents/data.csv")

X.head()

km = KMeans(n_clusters=3,n_init=1)


# In[2]:


y1 = km.fit_predict(X[['Survived', 'Sex', 'Age']])

mycentroids = np.array( [[0.42424242, 0.58441558, 14.64575758], [0.37162162, 0.67567568, 51.20945946], [0.36914062, 0.66796875, 30.27293716]])
km1 = KMeans(n_clusters=3, random_state=0, init=mycentroids )
y1 = km1.fit_predict(X[['Survived', 'Sex', 'Age']])


# In[42]:


centroids = km.cluster_centers_
centroids2 = km1.cluster_centers_
mycentroids2 = np.array([[ 0.42424242,  0.58441558, 14.64575758], [ 0.37162162,  0.67567568, 51.20945946],[ 0.36914062,  0.66796875, 30.27293716] ])
print(centroids2)
print(mycentroids2)
print(mycentroids)


# In[43]:






fig = plt.figure()

ax = fig.add_subplot(111, projection='3d')

ax.set_xlim((X['Survived']).min(),(X['Survived']).max())

ax.set_ylim((X['Sex']).min(),(X['Sex']).max())

ax.set_zlim((X['Age']).min(),(X['Age']).max())

ax.scatter(mycentroids[0][0], mycentroids[0][1], mycentroids[0][2], marker="x", c='red')
ax.scatter(mycentroids[1][0], mycentroids[1][1], mycentroids[1][2], marker="x", c='red')
ax.scatter(mycentroids[2][0], mycentroids[2][1], mycentroids[2][2], marker="x", c='red')
ax.scatter(mycentroids2[0][0], mycentroids2[0][1], mycentroids2[0][2], c='blue')
ax.scatter(mycentroids2[1][0], mycentroids2[1][1], mycentroids2[1][2], c='blue')
ax.scatter(mycentroids2[2][0], mycentroids2[2][1], mycentroids2[2][2], c='blue')

ax.set_title("Survival Rate Based on Sex and Age")

ax.set_xlabel("Survived(0 = died, 1 = lived)")

ax.set_ylabel("Sex(0 = male, 1 = female)")

ax.set_zlabel("Age")

plt.show()


# In[ ]:





# In[ ]:





# In[ ]:





# In[ ]:





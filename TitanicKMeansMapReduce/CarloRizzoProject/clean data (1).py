#!/usr/bin/env python
# coding: utf-8

# In[1]:


import pandas as pd


# In[2]:


train = pd.read_csv("C:/Users/ctr33/Downloads/train.csv")
test = pd.read_csv("C:/Users/ctr33/Downloads/test.csv")

def dropUseless(train):
    train = train.drop(["Ticket", "Cabin", "Name", "PassengerId"], axis=1)
    
    cols = ["SibSp", "Parch", "Fare", "Age"]
    for col in cols:
        train[col].fillna(train[col].mean(), inplace=True)
        
    train.Embarked.fillna("U", inplace=True)
    return train

train = dropUseless(train)
test = dropUseless(test)


# In[ ]:





# In[3]:


from sklearn import preprocessing 
le = preprocessing.LabelEncoder()

cols = ["Sex", "Embarked"]

for col in cols:
    train[col] = le.fit_transform(train[col])
    test[col] = le.transform(test[col])
    print(le.classes_)
    
print(train)
print(test)
train.to_csv('C:/Users/ctr33/OneDrive/Documents/data.csv')
test.to_csv('C:/Users/ctr33/OneDrive/Documents/test.csv')


# In[4]:



#from sklearn.linear_model import LogisticRegression
#from sklearn.model_selection import train_test_split

#y = train["Survived"]
#x = train.drop("Survived", axis=1)

#x_train, x_val, y_train, y_val = train_test_split(x, y, test_size=0.2, random_state=42)


# In[5]:


#clf = LogisticRegression(random_state=0, max_iter=1000).fit(x_train, y_train)


# In[ ]:





# 数据
# data 包含{
    # action action_Id -- 确定不同的决策树
    # user -- user_Id age height weight history sex
    # action_description -- 重量 数量
# }
# data list<data>


# actionId userId age height weight history sex   action_weight action_cnt
import os

import joblib
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from sklearn.model_selection import train_test_split
from sklearn.model_selection import GridSearchCV
from sklearn.tree import DecisionTreeClassifier,export_graphviz
import warnings
warnings.filterwarnings('ignore')
"""
    加载数据
"""
# 加载训练集
print(os.path.abspath(os.curdir))

train_data = pd.read_csv('./data/train.csv')
test_data = pd.read_csv('.//data/test.csv')

# 查看数据形状
print("train_data.shape: ", train_data.shape)   # (981, 12)
print(train_data.head())    # 查看数据前5行

# 数据信息总览
print(train_data.info())

"""
    数据预处理
"""
# 指定第一列作为行索引
train_data = pd.read_csv("./data/train.csv", index_col = 0)
# 处理缺失数据：这里用最简单的0值填充
# train_data = train_data.fillna(0)

"""
    拆分数据集
"""
y = train_data["score"].values
X = train_data.drop("score", axis=1).values

# print(y)
# print(X)
# print("X.shape: ", X.shape)
# print(X)
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2)
print("X_train shape:", X_train.shape, "X_test shape:", X_test.shape)

"""
    模型训练
"""

clf = DecisionTreeClassifier()
clf.fit(X_train, y_train)
train_score = clf.score(X_train, y_train)
test_score = clf.score(X_test, y_test)
print("train score:{0:.3f}; test score:{1:.3f}".format(train_score, test_score))

"""
    模型参数调优
"""
# 优化模型参数：max_depth
def cv_score(d):
    """
    在不同depth值下，train_score和test_score的值
    :param d: max_depth值
    :return: (train_score, test_score)
    """
    clf = DecisionTreeClassifier(max_depth=d)
    clf.fit(X_train, y_train)
    train_score = clf.score(X_train, y_train)
    test_score = clf.score(X_test, y_test)
    return (train_score, test_score)


# 指定参数的范围，训练模型计算得分
depths = range(2, 15)
scores = [cv_score(d) for d in depths]
train_scores = [s[0] for s in scores]
cv_scores = [s[1] for s in scores]

# 找出交叉验证集评分最高的模型参数
best_score_index = np.argmax(cv_scores)
best_score = cv_scores[best_score_index]
best_param = depths[best_score_index]   # 找出对应的参数
print("best param: {0}; best score: {1:.3f}".format(best_param, best_score))

print(cv_scores)
clf = DecisionTreeClassifier(max_depth=10)
clf.fit(X_train, y_train)

with open("titanic.dot", 'w') as f:
    f = export_graphviz(clf, out_file=f)

joblib.dump(clf, '../decision_trees/decision_tree_model_1.pkl')
print("模型已保存到 ../decision_trees/decision_tree_model_1.pkl")
"""
    参数调优可视化
"""
plt.figure(figsize=(6, 4), dpi=200)
plt.grid()
plt.xlabel("Max depth of Decision Tree")
plt.ylabel("score")
plt.plot(depths, cv_scores, ".g--", label="cross validation score")
plt.plot(depths, train_scores, ".r--", label="training score")
plt.legend()
plt.show()

# 优化模型参数：在criterion="gini"下的min_impurity_split
def cv_score(val):
    """
    在不同depth值下，train_score和test_score的值
    :param d: max_depth值
    :return: (train_score, test_score)
    """
    clf = DecisionTreeClassifier(criterion="gini", min_impurity_decrease=val)
    clf.fit(X_train, y_train)
    train_score = clf.score(X_train, y_train)
    test_score = clf.score(X_test, y_test)
    return (train_score, test_score)


# 指定参数的范围，训练模型计算得分
values = np.linspace(0, 0.5, 50)
scores = [cv_score(v) for v in values]
train_scores = [s[0] for s in scores]
cv_scores = [s[1] for s in scores]

# 找出交叉验证集评分最高的模型参数
best_score_index = np.argmax(cv_scores)
best_score = cv_scores[best_score_index]
best_param = values[best_score_index]   # 找出对应的参数
print("best param: {0}; best score: {1:.3f}".format(best_param, best_score))

# 画出模型参数与模型评分的关系
plt.figure(figsize=(6, 4), dpi=200)
plt.grid()
plt.xlabel("Min_impurity_split of Decision Tree")
plt.ylabel("score")
plt.plot(values, cv_scores, ".g--", label="cross validation score")
plt.plot(values, train_scores, ".r--", label="training score")
plt.legend()
plt.show()


# """
#     模型参数选择工具包
# """
# thresholds = np.linspace(0, 0.5, 50)
# # 设置参数矩阵
# param_grid = {"min_impurity_split": thresholds}
# clf = GridSearchCV(DecisionTreeClassifier(), param_grid, cv=5)
# clf.fit(X, y)
# print("best param: {0} \nbest score: {1}".format(clf.best_params_, clf.best_score_))

# 参数
entropy_thresholds = np.linspace(0, 1, 50)
gini_thresholds = np.linspace(0, 0.5, 50)

# 设置参数矩阵
param_grid = [{"criterion": ["entropy"], "min_impurity_decrease": entropy_thresholds},
              {"criterion": ["gini"], "min_impurity_decrease": gini_thresholds},
              {"max_depth": range(2, 10)},
              {"min_samples_split": range(2, 30, 2)}]

clf = GridSearchCV(DecisionTreeClassifier(), param_grid, cv=5)
clf.fit(X, y)
print("best param: {0} \nbest score: {1}".format(clf.best_params_, clf.best_score_))

"""
    生成决策树图形
"""
# clf = DecisionTreeClassifier(criterion='entropy', min_impurity_decrease=0.5306122448979591)
# clf.fit(X_train, y_train)
train_score = clf.score(X_train, y_train)
test_score = clf.score(X_test, y_test)
print('train score: {0:.3f}; test score: {1:.3f}'.format(train_score, test_score))



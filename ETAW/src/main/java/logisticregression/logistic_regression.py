import numpy as np
import math
from logisticregression import metrics

class LogisticRegression:
    def __init__(self):
        """
        初始化LogisticRegression模型
        """
        self.coef_ = None#斜率
        self.interception_ = None#截距
        self._theta = None

    def _sigmoid(self, t):
        """
        sigmoid函数求解概率，值限制在0或1来进行二分类.概率以0.5为界划分
        :param t:
        :return:
        """
        return  1./(1.+np.exp(-t))

    def fit(self, X_train, y_train, eta =0.01, n_iters = 1e4):
        """
        根据训练数据集x_train,y_train,使用梯度下降法训练logisticregression模型
        :param X_train:
        :param y_train:
        :param eta:
        :param n_iters:
        :return:
        """
        assert X_train.shape[0] == y_train.shape[0], \
            "the size of X_train must be equal to the size of y_train"
        def J(theta, X_b, y):
            """
            多个样本时的损失函数
            :param theta:
            :param X_b:
            :param y:
            :return:
            """
            y_hat = self._sigmoid(X_b.dot(theta))
            try:
                return -math.sum(y*math.log(y_hat) + (1-y)*math.log(1-y_hat))/len(y)
            # 两种情况下的表示,一定要使用math中的函数，不要用numpy
            # （numpy的同名函数针对的是矩阵而非理想的结果）
            except:
                return float('inf')

        def dJ(theta, X_b, y):
            """
            求解梯度，即对损失函数求偏导，化简为以下公式
            :param theta:
            :param X_b:
            :param y:
            :return:
            """
            return X_b.T.dot(self._sigmoid(X_b.dot(theta)) - y)/len(X_b)

        def gradient_descent(X_b, y,initial_theta, eta, n_iters = 1e4, epsilon=1e-8):
            theta = initial_theta
            cur_iter = 0
            while cur_iter<n_iters:
                gradient = dJ(theta, X_b,y)
                last_theta = theta
                theta = theta - eta*gradient
                if(abs(J(theta, X_b, y)-J(last_theta, X_b, y))<epsilon):
                    break
                cur_iter+=1
            return theta
        X_b = np.hstack([np.ones((len(X_train),1)), X_train])
        initial_theta = np.zeros(X_b.shape[1])
        self._theta = gradient_descent(X_b, y_train, initial_theta,eta, n_iters)
        self.interception_ = self._theta[0]
        self.coef_ = self._theta[1:]

        return self

    def predict(self, X_predict):
        """
        给定待预测数据集X_predict, 返回表示X_predict的结果向量
        :param X_predict:
        :return:
        """
        assert self.interception_ is not None and self.coef_ is not None, \
        "must fit before predict!"
        assert X_predict.shape[1] == len(self.coef_), \
        "the feature number of X_predict must be equal to X_train"
        proba = self.predict_proba(X_predict)

        return np.array(proba>=0.5, dtype='int')

    def predict_proba(self, X_predict):
        """
        给定待预测数据集X_predict, 返回表示X_predict的结果概率向量
        :param X_predict:
        :return:
        """
        assert self.interception_ is not None and self.coef_ is not None, \
        "must fit before predict!"
        assert X_predict.shape[1] == len(self.coef_), \
        "the feature number of X_predict must be equal to X_train"

        X_b = np.hstack([np.ones((len(X_predict), 1)), X_predict])
        return self._sigmoid(X_b.dot(self._theta))

    def score(self, X_test, y_test):
        y_predict = self.predict(X_test)
        return metrics.accuracy_score(y_test, y_predict)

    def __repr__(self):
        return "LogisticRegression()"









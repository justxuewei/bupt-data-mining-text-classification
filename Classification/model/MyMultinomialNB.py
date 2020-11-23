#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import numpy as np
from scipy.sparse import csr_matrix
from sklearn.preprocessing import LabelBinarizer

class MyMultinomialNB(object):
    '''

            
    '''
    
    def __init__(self):
        # 一维数组，存储各分类的 p(c)
        self.priors = None
        self.log_priors = None # 以及 log 后的值

        # 二维 Numpy 数组。shape=(分类数, 词表大小)
        # 存储各分类的词频/ tfidf 值的总和
        # 
        self.feature_count = None

        # 二维 Numpy 数组。shape=(分类数, 词表大小)
        # 存储 p(w|c)
        # self.feature_prob = None
        self.feature_log_prob = None # log 后的值

        # 一维数组
        # 存储各分类 [0, 1, 2, ...]
        self.classes = None

        # 一维数组
        # 存储各分类的文章数
        self.class_count = None
    
    def fit(self, X, y, alpha=1.0):
        '''
            拟合多项式朴素贝叶斯模型
            计算 self.feature_log_prob 及 self.priors 
            
            参数:
                X: 二维数组，每行=文章，每列=单词
                y: 一维数组，y_i 代表文章 x_i 的类别
                alpha: 平滑参数，取值 [0.0, 1.0]
            返回:
                self
        '''
        
        assert ((alpha <= 1.0) and (alpha > 0.0)), "alpha 取值 (0.0, 1.0]"
        
        self.classes = np.unique(y)
        n_features = X.shape[1]
        n_classes = len(self.classes)

        # 初始化矩阵
        self.priors = np.zeros(shape = (n_classes,))
        feature_prob = csr_matrix( (n_classes, n_features ), dtype=np.float64)
        self.feature_log_prob = csr_matrix( (n_classes, n_features ), dtype=np.float64)
        self.feature_count = csr_matrix( (n_classes, n_features ), dtype=np.float64)


        for cat_i, cat in enumerate(self.classes): # 遍历各分类
            # 用来选择所有属于 cat 类的文章
            mask = (y == cat) # eg: mask = [True, False, False, True, ...]
            
            token_counts_in_cat = (np.sum(X[mask, :], axis=0) + alpha) # 将同属cat类的文章选择出来，对每列(单词)求权重和
            total_tokens_in_cat = np.sum(X[mask, :]) + n_classes * alpha # 将同属cat类的文章选择出来，求单词权重总和

            self.feature_count[cat_i, :] = token_counts_in_cat
            self.feature_log_prob[cat_i, :] = np.log(token_counts_in_cat) - np.log(total_tokens_in_cat)
            self.priors[cat_i] = np.sum(mask)/len(y) # p(c) = 该分类文章数 / 文章总数
        
        self.log_priors = np.log(self.priors)
        return self

    def fit2(self, X, y, alpha=1.0):
        
        assert ((alpha <= 1.0) and (alpha > 0.0)), "alpha 取值 (0.0, 1.0]"
        
        # 词汇表大小
        n_features = X.shape[1]

        # 统计分类
        # 使用 LabelBinarizer 得到文章对应的分类，每篇文章对应一行，在某一列取值为1代表属于该类。例如
        # Y = |文章\分类| 分类1 | 分类2 | ...
        #     |  文章1  |   1   |   0  | ...
        #     |  文章2  |   0   |   1  |
        labelbin = LabelBinarizer()
        Y = labelbin.fit_transform(y)
        self.classes = labelbin.classes_  # 获取各分类 [0, 1, 2, ...]
        n_classes = Y.shape[1] # 获取分类个数

        self.class_count = np.zeros(n_classes, dtype=np.int32)

        self.class_count += Y.sum(axis=0) # 统计各分类的文章数

        self.priors = Y.sum(axis=0) / Y.sum() # p(c) = 该分类文章数 / 文章总数
        self.log_priors = np.log(self.priors)
###
        # 计算各分类中文章的词频/tfidf值总和

        self.feature_count = np.zeros((n_classes, n_features),
                                       dtype=np.float64)
        Y_ = csr_matrix(Y)                        
        self.feature_count += Y_.T.dot(X) # Y_.T.shape = (分类数, 文章数)  X.shape = (文章数, 词表大小)
        
###

###
        smoothed_fc = self.feature_count + alpha # p(w|c) + 1
        smoothed_cc = smoothed_fc.sum(axis=1) # p(w) + 1*词表大小
        self.feature_log_prob = (np.log(smoothed_fc) -
                                  np.log(smoothed_cc.reshape(-1, 1)))



    def calc_log_likelihood(self, X):
        '''
            计算给定测试集 X 属于各分类的概率
            
            参数:
                X: 二维数组，文章。行=文章，列=单词
            返回:
                log_likelihoods: 二维数组，行=文章，列=分类。
                                 存储文章 x_i 属于分类 c_i 的 log 概率
        '''

        # print("X: %s\nfeature_log_prob: %s\n" % (X.shape, self.feature_log_prob.shape))
        X = csr_matrix(X)
        
        log_priors_ = np.tile(self.log_priors, (X.shape[0], 1)) # 扩展到 shape=(文章数, 1)
        log_likelihoods = X.dot(self.feature_log_prob.T) + log_priors_
        return log_likelihoods
            
    def predict(self, X):
        '''
            预测测试集 X 中文章的分类
            
            参数:
                X: 二维数组，文章。行=文章，列=单词
            返回:
                一维list，测试集中对应文章的分类
        '''
        
        log_likelihoods = self.calc_log_likelihood(X)
        index_to_label = np.argmax(log_likelihoods, axis=1) # 按每一行（即文章）查找列（分类）最大的列号
        return index_to_label.T.tolist()[0]
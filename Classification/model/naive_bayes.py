import numpy as np


class NaiveBayes(object):
    def __init__(self):
        self.__X_train = None
        self.__Y_train = None

    #
    # @property
    # def training_data(self):
    #     return self.__training_data
    #
    # @training_data.setter
    # def training_data(self, v):
    #     self.__training_data = v
    #     self.__separated = separate_by_class(v)
    #
    # @property
    # def classes(self):
    #     if self.__separated is not None:
    #         return self.__separated.keys()
    #     return None
    #
    # def predict(self, data):
    #     summaries = summarize_by_class(self.training_data)
    #     total_rows = sum([summaries[label][0][2] for label in summaries])
    #     probabilities = dict()
    #     for class_value, class_summaries in summaries.items():
    #         # p(class)
    #         probability = class_summaries[0][2] / float(total_rows)
    #         for i in range(len(class_summaries)):
    #             mean, stdev, _ = class_summaries[i]
    #             # p(x_i|class)
    #             probability *= calculate_probability(data[i], mean, stdev)
    #         probabilities[class_value] = probability
    #     return probabilities

    @property
    def corpus_num(self):
        return self.__X_train.shape[1]

    @property
    def classes(self):
        classes = set()
        for y in self.__Y_train:
            classes.add(y)
        return classes

    def separate_by_class(self):
        separated = dict()
        for i in range(len(self.classes)):
            class_value = self.__Y_train[i]
            document = self.__X_train[i]
            if class_value not in separated:
                separated[class_value] = np.zeros((self.__X_train.shape[1],))
            separated[class_value][i] = document
        return separated

    def summarize_by_class(self, separated):
        summarise = dict()
        for class_value, rows in separated.items():
            s = list()

        return summarise

    def train(self, X, Y):
        """
        train data
        :param X: X should be a matrix with (d * t) shape
        :param Y: Y should be a vector with (d * 1) shape
        :return:
        """
        X_shape = X.shape
        Y_shape = Y.shape
        assert (X_shape[0] == Y_shape[0])
        self.__X_train = X
        self.__Y_train = Y
        separated = self.separate_by_class()
        # summarise = self.summarize_by_class(separated)
        print(separated)

    def predict(self, x):
        pass

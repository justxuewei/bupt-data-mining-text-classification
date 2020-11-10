import numpy as np


class NaiveBayes(object):
    def __init__(self):
        self.X_train = None
        self.Y_train = None
        self.separated = None
        # sum of columns by class
        self.soc_by_class = None
        # sum of columns
        self.soc = None

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
    def document_num(self):
        return self.X_train.shape[0]

    @property
    def corpus_num(self):
        return self.X_train.shape[1]

    @property
    def classes(self):
        classes = set()
        for y in self.Y_train:
            classes.add(y)
        return classes

    def separate_by_class(self):
        separated = dict()
        for i in range(self.X_train.shape[0]):
            class_value = self.Y_train[i]
            document = self.X_train[i]
            if class_value not in separated:
                separated[class_value] = list()
            separated[class_value].append(document)
        for i, row in separated.items():
            separated[i] = np.array(row)
        return separated

    def sum_of_column_by_class(self):
        summarise = dict()
        # (sum_x_in_the_column)
        for class_value, rows in self.separated.items():
            summaries_for_the_class = list()
            rows_sum_by_column = np.sum(rows, axis=0)
            summarise[class_value] = rows_sum_by_column
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
        self.X_train = X
        self.Y_train = Y
        self.separated = self.separate_by_class()
        self.soc_by_class = self.sum_of_column_by_class()
        self.soc = np.sum(self.X_train, axis=0)

    def predict(self, x):
        pass

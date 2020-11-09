from model.base import separate_by_class, summarize_by_class, calculate_probability


class NaiveBayes(object):
    def __init__(self, td=None):
        """
        init
        :param td: vectors that includes the class at the end of columns, the type if DataFrame
        """
        self.__training_data = td
        if self.__training_data is not None:
            self.__separated = None

    @property
    def training_data(self):
        return self.__training_data

    @training_data.setter
    def training_data(self, v):
        self.__training_data = v
        self.__separated = separate_by_class(v)

    @property
    def classes(self):
        if self.__separated is not None:
            return self.__separated.keys()
        return None

    def predict(self, data):
        summaries = summarize_by_class(self.training_data)
        total_rows = sum([summaries[label][0][2] for label in summaries])
        probabilities = dict()
        for class_value, class_summaries in summaries.items():
            # p(class)
            probability = class_summaries[0][2] / float(total_rows)
            for i in range(len(class_summaries)):
                mean, stdev, _ = class_summaries[i]
                # p(x_i|class)
                probability *= calculate_probability(data[i], mean, stdev)
            probabilities[class_value] = probability
        return probabilities

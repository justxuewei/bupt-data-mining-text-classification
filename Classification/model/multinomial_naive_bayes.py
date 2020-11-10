from model import NaiveBayes


class MultinomialNaiveBayes(NaiveBayes):

    def calculate_probability(self, t, sum_t, v_count):
        return (t + 1) / (sum_t + v_count)

    def predict(self, x):
        probabilities = dict()
        for class_value, soc_by_class in self.soc_by_class.items():
            p = 
            for i in range(self.corpus_num):
                t = soc_by_class[i]
                sum_t = self.soc[i]
                p_x_class = self.calculate_probability(t, sum_t, self.corpus_num)
                p * p_x_class

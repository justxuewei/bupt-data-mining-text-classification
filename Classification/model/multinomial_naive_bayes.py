from model import NaiveBayes


class MultinomialNaiveBayes(NaiveBayes):

    def calculate_probability(self, t, sum_t, v_count):
        return (t + 1/v_count) / sum_t

    def predict(self, x):
        probabilities = dict()
        for class_value, soc_by_class in self.soc_by_class.items():
            p = float(self.separated[class_value].shape[0]) / float(self.document_num)
            for i in range(self.corpus_num):
                t = soc_by_class[i]
                if t == 0:
                    continue
                sum_t = self.soc[i]
                p_x_class = self.calculate_probability(t, sum_t, self.corpus_num)
                p *= p_x_class
            probabilities[class_value] = p
        max_cat = max(probabilities, key=probabilities.get)
        return max_cat

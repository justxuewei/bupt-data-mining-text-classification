import json
import pandas as pd
from sklearn.model_selection import train_test_split


def load_category_mapper(file="./category_mapper.json"):
    with open(file, "r") as file:
        return json.loads(file.read())


def sample(dspath, n, sampledspath, traindspath, testdspath, testsize):
    data = pd.read_csv(dspath).sample(n)
    data.to_csv(sampledspath)
    train, test = train_test_split(data, test_size=testsize)
    train.to_csv(traindspath, index=False)
    test.to_csv(testdspath, index=False)


def load_stop_words():
    sw = list()
    with open("stopwords.txt", "r") as stopf:
        for line in stopf.readlines():
            w = line[:-1]
            if w == "":
                continue
            sw.append(w)
    return sw

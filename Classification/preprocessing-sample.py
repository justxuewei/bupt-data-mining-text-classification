import json
from sklearn.model_selection import train_test_split
import pandas as pd
import preprocessing
from model import MultinomialNaiveBayes
import numpy as np


max_features = 500


def load_dataset_stat():
    with open("dataset_stat.json", "r") as file:
        return json.loads(file.read())


def category_stat(sd):
    """
    category statistics, left hand side is the stat of the dataset you passed from params
    right hand side is of original dataset
    :param sd: dataset
    """
    dataset_stat = load_dataset_stat()
    category_statistics = sd.groupby("category")["id"].count()
    for i, v in category_statistics.items():
        sample_category_percentage = v / 500.0
        category_percentage = (dataset_stat[i] / dataset_stat["total"]) * 100
        print("%-15s %-10.2f %-10.2f" % (i, sample_category_percentage, category_percentage))


def tf_idf_dataset(dataset, tf_idf_params):
    """
    generate vectors for model by tf-idf

    :param dataset: dataset, include a column named "category"
    :param tf_idf_params: parameters for tf_idf
    :return: vectors, which the last column is for category and the rest are vector
    """
    cat_mapper = preprocessing.load_category_mapper()
    sws = preprocessing.load_stop_words()
    c = preprocessing.corpus(dataset, sws)
    tf_idf = preprocessing.tf_idf(c, tf_idf_params)
    cats = np.zeros((tf_idf.shape[0],))
    count = 0
    for _, r in dataset.iterrows():
        cats[count] = cat_mapper[r["category"]]
        count += 1
    # tf_idf.insert(loc=len(tf_idf.columns), column="category", value=cats)
    return tf_idf.to_numpy(), cats


if __name__ == "__main__":
    sample_dataset = pd.read_csv("dataminingnews-sample.csv").sample(100)
    train, test = train_test_split(sample_dataset, test_size=0.3)
    X_train, Y_train = tf_idf_dataset(train, {
        "max_features": max_features
    })
    X_test, Y_test = tf_idf_dataset(test, {
        "max_features": max_features
    })
    m_naive_bayes = MultinomialNaiveBayes()
    m_naive_bayes.train(X_train, Y_train)
    m_naive_bayes.predict(X_test[0])

    # category_stat(sample_dataset)
    # stop_words = preprocessing.load_stop_words()
    # sample_corpus = preprocessing.corpus(sample_dataset, stop_words)
    # tf_idf = preprocessing.tf_idf(sample_corpus, tf_idf_params={
    #     "max_features": 1000
    # })
    # tf_idf.to_csv("sample_tf_idf.csv")

    # merge "sample_tf_idf" into "dataminingnews-sample.csv"


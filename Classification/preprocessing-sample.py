import json
from collections import Counter

from bidi.algorithm import get_display
from sklearn.cluster import KMeans
from sklearn.model_selection import train_test_split
import pandas as pd
from wordcloud import WordCloud

import preprocessing
from model import MultinomialNaiveBayes
import numpy as np
from sklearn.naive_bayes import MultinomialNB
from sklearn.metrics import accuracy_score, classification_report, silhouette_score
from sklearn.feature_extraction.text import TfidfVectorizer
from create_sample import sample_train_path, sample_test_path
# from sklearn.decomposition import TruncatedSVD
from sklearn.decomposition import NMF
import matplotlib.pyplot as plt
from sklearn.feature_extraction.text import CountVectorizer, TfidfTransformer

max_features = 5000


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


def corpus_count(corpus):
    corpus_split = [item for sublist in [i.split(" ") for i in corpus] for item in sublist]
    count_words = dict(Counter(corpus_split))
    outputwords_sorted = sorted(count_words.items(), key=lambda x: x[1], reverse=True)[:100]
    print(outputwords_sorted)


def tf_idf_dataset(dataset, tf_idf_params=None):
    """
    generate vectors for model by tf-idf

    :param dataset: dataset, include a column named "category"
    :param tf_idf_params: parameters for tf_idf
    :return: vectors, which the last column is for category and the rest are vector
    """
    if tf_idf_params is None:
        tf_idf_params = {}
    cat_mapper = preprocessing.load_category_mapper()
    sws = preprocessing.load_stop_words()
    c = preprocessing.corpus(dataset, sws)
    corpus_count(c)
    tf_idf = preprocessing.tf_idf(c, tf_idf_params)
    cats = np.zeros((tf_idf.shape[0], 1))
    for i in range(len(dataset)):
        cats[i] = cat_mapper[dataset.iloc[i, :]["category"]]
    # tf_idf.insert(loc=len(tf_idf.columns), column="category", value=cats)
    return tf_idf.to_numpy(), cats


def counter_and_tfidf(dataset, params=None):
    if params is None:
        params = {}
    category_mapper = preprocessing.load_category_mapper()
    stop_words = preprocessing.load_stop_words()
    corpus = preprocessing.corpus(dataset, stop_words)
    cv = CountVectorizer()
    counts = cv.fit_transform(corpus)
    tfidf_transformer = TfidfTransformer()
    X_tfidf = tfidf_transformer.fit_transform(counts)
    cats = np.zeros((counts.shape[0], 1))
    for i in range(len(dataset)):
        cats[i] = category_mapper[dataset.iloc[i, :]["category"]]
    return X_tfidf, cats, cv


def counter(dataset, count_vectorizer):
    category_mapper = preprocessing.load_category_mapper()
    stop_words = preprocessing.load_stop_words()
    corpus = preprocessing.corpus(dataset, stop_words)
    counts = count_vectorizer.transform(corpus)
    cats = np.zeros((counts.shape[0], 1))
    for i in range(len(dataset)):
        cats[i] = category_mapper[dataset.iloc[i, :]["category"]]
    return counts, cats


# def generate_wordclouds(X, in_X_tfidf, k, in_word_positions):
#     in_model = KMeans(n_clusters=k, random_state=42, n_jobs=-1)
#     in_y_pred = in_model.fit_predict(X)
#     in_cluster_ids = set(in_y_pred)
#     silhouette_avg = silhouette_score(X, in_y_pred)
#     print("For n_clusters =", k, "The average silhouette_score is :", silhouette_avg)
#     # Number of words with highest tfidf score to display
#     top_count = 100
#
#     for in_cluster_id in in_cluster_ids:
#         # compute the total tfidf for each term in the cluster
#         in_tfidf = in_X_tfidf[in_y_pred == in_cluster_id]
#         # numpy.matrix
#         tfidf_sum = np.sum(in_tfidf, axis=0)
#         # numpy.array of shape (1, X.shape[1])
#         tfidf_sum = np.asarray(tfidf_sum).reshape(-1)
#         top_indices = tfidf_sum.argsort()[-top_count:]
#         term_weights = {in_word_positions[in_idx]: tfidf_sum[in_idx] for in_idx in top_indices}
#         wc = WordCloud(font_path="/Users/xavier/Library/Fonts/Fangsong.ttf", width=1200, height=800, background_color="white")
#         wordcloud = wc.generate_from_frequencies(term_weights)
#         fig, ax = plt.subplots(figsize=(10, 6), dpi=100)
#         ax.imshow(wordcloud, interpolation='bilinear')
#         ax.axis("off")
#         fig.suptitle(f"Cluster {in_cluster_id}")
#         plt.show()
#
#     return in_cluster_ids


if __name__ == "__main__":
    cat_mapper = preprocessing.load_category_mapper()

    sample_train_data = pd.read_csv(sample_train_path)
    sample_test_data = pd.read_csv(sample_test_path)
    # sample_data = pd.read_csv("dataminingnews-participle-n.csv")
    train_X, train_y, count_vectorizer = counter_and_tfidf(sample_train_data)
    test_X, test_y = counter(sample_test_data, count_vectorizer)
    # test_X, test_y = counter_vectorizer(sample_test_data, {"max_features": max_features})
    # cat_mapper = preprocessing.load_category_mapper()
    # stop_words = preprocessing.load_stop_words()
    # corpus = preprocessing.corpus(sample_train_data, stop_words)
    # vectorizer = TfidfVectorizer(smooth_idf=True, max_features=10000)
    # _train_corpus = sample_train_data[['content']].values.tolist()
    # train_corpus = list()
    # for i in _train_corpus:
    #     if type(i[0]) != str:
    #         train_corpus.append("")
    #         continue
    #     train_corpus.append(i[0])
    # train_category = sample_train_data[['category']]
    # tf_idf_train_X = vectorizer.fit_transform(train_corpus)
    # train_X = np.array(tf_idf_train_X.toarray())
    # train_y = np.zeros((train_category.shape[0], 1))
    # for i in range(len(train_category)):
    #     train_y[i] = cat_mapper[train_category.iloc[i, :]["category"]]
    #
    # vectorizer_test = TfidfVectorizer(smooth_idf=True, max_features=10000)
    # _test_corpus = sample_test_data[['content']].values.tolist()
    # test_corpus = list()
    # for i in _test_corpus:
    #     if type(i[0]) != str:
    #         test_corpus.append("")
    #         continue
    #     test_corpus.append(i[0])
    # test_category = sample_test_data[['category']]
    # test_X = np.array(vectorizer_test.fit_transform(test_corpus).toarray())
    # test_y = np.zeros((test_category.shape[0], 1))
    # for i in range(len(test_category)):
    #     test_y[i] = cat_mapper[test_category.iloc[i, :]["category"]]
    # print(test_X, test_y)

    # train_X = pd.DataFrame(tf_idf_train.toarray(), columns=vectorizer.get_feature_names())
    # train_Y = np.zeros((tf_idf_train.shape[0], 1))
    # for i in range(len(sample_train_data)):
    #     train_Y[i] = wordscat_mapper[sample_train_data.iloc[i, :]["category"]]

    # nmf = NMF(n_components=60)
    # train_X_NMF = nmf.fit_transform(train_X)
    # test_X_NMF = nmf.fit_transform(test_X)
    # print(f"Total variance explained: {np.sum(svd.explained_variance_ratio_):.2f}")
    # cum_variance = np.cumsum(svd.explained_variance_ratio_)
    # idx = np.argmax(cum_variance > .8)
    # svd = TruncatedSVD(n_components=idx, random_state=42)
    # train_X_SVD = svd.fit_transform(train_X)
    # print(f"Total variance explained: {np.sum(svd.explained_variance_ratio_):.2f}")

    # word_positions = {v: k for k, v in vectorizer.vocabulary_.items()}
    # cluster_ids = generate_wordclouds(train_X_SVD, train_X, 2, word_positions)
    # cluster_ids = generate_wordclouds(train_X_SVD, train_X, 3, word_positions)
    # cluster_ids = generate_wordclouds(train_X_SVD, train_X, 4, word_positions)
    # cluster_ids = generate_wordclouds(train_X_SVD, train_X, 5, word_positions)
    # cluster_ids = generate_wordclouds(train_X_SVD, train_X, 6, word_positions)
    # cluster_ids = generate_wordclouds(train_X_SVD, train_X, 7, word_positions)
    # cluster_ids = generate_wordclouds(train_X_SVD, train_X, 29, word_positions)

    # X, Y = tf_idf_dataset(sample_data, {"max_features": max_features, "min_df": 5, "max_df": 0.7})
    # X_n_Y = np.hstack((X, Y))
    # train_X_n_Y, test_X_n_Y = train_test_split(X_n_Y, test_size=0.3)
    # X_n_Y = None
    # train_X = train_X_n_Y[:, :-1]
    # train_Y = train_X_n_Y[:, -1]
    # train_X_n_Y = None
    # test_X = test_X_n_Y[:, :-1]
    # test_Y = test_X_n_Y[:, -1]
    # test_X_n_Y = None
    naive_bayes = MultinomialNB()
    naive_bayes.fit(train_X, train_y)
    pred_y = naive_bayes.predict(test_X)
    print(accuracy_score(test_y, pred_y))

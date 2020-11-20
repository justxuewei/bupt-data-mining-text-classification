import pandas as pd
import numpy as np

import pickle

from sklearn.model_selection import train_test_split
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn import svm
from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score

from foundation_win import generate_corpus, stop_words, generate_y


should_vectorize = False
sample_num = 0
dataset_path = "news.csv"
test_size = 0.5
# tfidfvectorizer
max_features = 5000
# vectors' path
train_X_path = "news-train-X.pkl"
train_y_path = "news-train-y.pkl"
test_X_path = "news-test-X.pkl"
test_y_path = "news-test-y.pkl"
# svm
svm_C = 30.0
svm_class_weight = None
svm_tol = 0.001


if __name__ == "__main__":
    if should_vectorize:
        print(">>> Load dataset.")
        data = pd.read_csv(dataset_path)
        if sample_num != 0:
            data = data.sample(sample_num)
        print(">>> Split dataset.")
        train, test = train_test_split(data, test_size=test_size)
        print(f"train.shape: {train.shape}")
        print(f"test.shape: {test.shape}")
        print(">>> Generate corpus.")
        print(">>> Task 1/2: Train corpus")
        train_corpus = generate_corpus(train, stop_words)
        print(f"train_corpus len: {len(train_corpus)}")
        print(">>> Task 2/2: Test corpus")
        test_corpus = generate_corpus(test, stop_words)
        print(f"test_corpus len: {len(test_corpus)}")
        print(">>> Vectorize.")
        tfidf_vectorizer = TfidfVectorizer(max_features=max_features)
        train_X = tfidf_vectorizer.fit_transform(train_corpus)
        test_X = tfidf_vectorizer.transform(test_corpus)
        train_y = generate_y(train)
        test_y = generate_y(test)
        print(">>> Write vectors to files.")
        print(">>> Task 1/4: train_X")
        with open(train_X_path, "wb") as f:
            pickle.dump(train_X, f)
        print(">>> Task 2/4: train_y")
        np.save(file=train_y_path, arr=train_y)
        print(">>> Task 3/4: test_X")
        with open(test_X_path, "wb") as f:
            pickle.dump(test_X, f)
        print(">>> Task 4/4: test_y")
        np.save(file=test_y_path, arr=test_y)
    else:
        print(">>> Load vectors from files.")
        print(">>> Task 1/4: train_X")
        with open(train_X_path, "rb") as f:
            train_X = pickle.load(f)
        print(">>> Task 2/4: train_y")
        train_y = np.load(file=train_y_path+".npy")
        print(">>> Task 3/4: test_X")
        with open(test_X_path, "rb") as f:
            test_X = pickle.load(f)
        print(">>> Task 4/4: test_y")
        test_y = np.load(file=test_y_path+".npy")
    print(f"train_X.shape: {train_X.shape}", f"train_y.shape: {train_y.shape}",
          f"test_X.shape: {test_X.shape}", f"test_y.shape: {test_y.shape}")
    print(">>> SVM")
    model = svm.LinearSVC(C=svm_C, class_weight=svm_class_weight, tol=svm_tol)
    model.fit(train_X, train_y)
    pred_y = model.predict(test_X)
    print(">>> Assess")
    print(f"accuracy_score: {accuracy_score(test_y, pred_y)}")
    print(f"precision_score: {precision_score(test_y, pred_y, average='weighted')}")
    print(f"recall_score: {recall_score(test_y, pred_y, average='weighted')}")
    print(f"f1_score: {f1_score(test_y, pred_y, average='weighted')}")


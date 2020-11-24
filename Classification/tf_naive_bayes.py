import numpy as np
import pickle

from model import MyMultinomialNB

from sklearn.metrics import confusion_matrix, classification_report

# vectors' path
train_X_path = "news-train-X.pkl"
train_y_path = "news-train-y.pkl"
test_X_path = "news-test-X.pkl"
test_y_path = "news-test-y.pkl"


if __name__ == "__main__":
    print(">>> Load vectors from files.")
    print(">>> Task 1/4: train_X")
    with open(train_X_path, "rb") as f:
        train_X = pickle.load(f)
    print(">>> Task 2/4: train_y")
    train_y = np.load(file=train_y_path + ".npy")
    print(">>> Task 3/4: test_X")
    with open(test_X_path, "rb") as f:
        test_X = pickle.load(f)
    print(">>> Task 4/4: test_y")
    test_y = np.load(file=test_y_path + ".npy")
    print(f"train_X.shape: {train_X.shape}", f"train_y.shape: {train_y.shape}",
          f"test_X.shape: {test_X.shape}", f"test_y.shape: {test_y.shape}")

    my_clf = MyMultinomialNB()
    my_clf.fit(train_X, train_y)

    pred_y = my_clf.predict(test_X)

    print(">>> Classification Report")
    print(classification_report(test_y, pred_y))
    print(">>> Confusion Matrix")
    print(confusion_matrix(test_y, pred_y))

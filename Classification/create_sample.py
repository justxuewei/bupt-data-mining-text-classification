import pandas as pd
from sklearn.model_selection import train_test_split


sample_size = 10000
sample_path = "dataminingnews-sample.csv"
sample_train_path = "dataminingnews-sample-train.csv"
sample_test_path = "dataminingnews-sample-test.csv"
test_size = 0.3

if __name__ == "__main__":
    data = pd.read_csv("news.csv").sample(sample_size)
    data.to_csv(sample_path)
    train, test = train_test_split(data, test_size=test_size)
    train.to_csv(sample_train_path, encoding='utf-8', index=False)
    test.to_csv(sample_test_path, encoding='utf-8', index=False)
    # data = pd.read_csv("dataminingnews-participle-n.csv", engine='python')
    # train, test = train_test_split(data, test_size=test_size)
    # train.to_csv(sample_train_path, index=False)
    # test.to_csv(sample_test_path, index=False)

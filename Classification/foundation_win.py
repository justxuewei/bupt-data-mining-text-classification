import json
import jieba
import numpy as np
import pandas as pd
from collections import Counter
from sklearn.model_selection import train_test_split


def load_category_mapper(file="./category_mapper.json"):
    with open(file, "r", encoding='utf-8-sig') as file:
        return json.loads(file.read())


cat_mapper = load_category_mapper()


def sample(dspath, n, sampledspath, traindspath, testdspath, traincorpuspath, testcorpuspath, testsize):
    print(f">>> Loading dataset from \"{dspath}\".")
    data = pd.read_csv(dspath).sample(n)
    print(">>> Saving samples.")
    data.to_csv(sampledspath)
    print(">>> Spliting dataset.")
    train, test = train_test_split(data, test_size=testsize)
    print(">>> Saving the train and the test dataset.")
    print(">>> Task 1/2: Train dataset")
    train.to_csv(traindspath, index=False)
    print(">>> Task 2/2: Test dataset")
    test.to_csv(testdspath, index=False)
    print(">>> Generating corpus.")
    print(">>> Task 1/2: Train corpus")
    train_corpus = generate_corpus(train, stop_words)
    print(">>> Task 2/2: Test corpus")
    test_corpus = generate_corpus(test, stop_words)
    print(">>> Saving the train and the test corpus.")
    print(">>> Task 1/2: Train corpus")
    pd.DataFrame(train_corpus).to_csv(traincorpuspath, index=False)
    print(">>> Task 2/2: Test corpus")
    pd.DataFrame(test_corpus).to_csv(testcorpuspath, index=False)


def load_stop_words():
    sw = list()
    with open("stopwords.txt", "r", encoding='utf-8-sig') as stopf:
        for line in stopf.readlines():
            w = line[:-1]
            if w == "":
                continue
            sw.append(w)
    return sw


stop_words = load_stop_words()


def __is_chinese(uchar):
    if u'\u4e00' <= uchar <= u'\u9fa5':
        return True
    else:
        return False


def __format_str(string):
    content_str = ''
    for i in string:
        if __is_chinese(i):
            content_str = content_str + i
        else:
            content_str += ","
    return content_str


def generate_corpus(dataset, stopwords):
    corpus = list()
    count = 0
    for idx, row in dataset.iterrows():
        if count % 1000 == 0:
            print(f"The progress of generating corpus is {count * 100 / len(dataset)}% ({count}/{len(dataset)}).")
        count += 1
        text = __format_str(row["title"] + row["content"])
        words = jieba.cut(text)
        pwords = list()
        for word in words:
            if word not in stopwords:
                pwords.append(word)
        if len(pwords) == 0:
            print("WARNING: THERE IS NOT CONTENT IN CURRENT NEWS.")
            text = ""
        else:
            text = " ".join(pwords)
        corpus.append(text)
    print(f"The progress of generating corpus is 100% ({len(corpus)}/{len(corpus)}).")
    return corpus


def corpus_count(corpus, size=100):
    corpus_split = [item for sublist in [i.split(" ") for i in corpus] for item in sublist]
    count_words = dict(Counter(corpus_split))
    outputwords_sorted = sorted(count_words.items(), key=lambda x: x[1], reverse=True)[:size]
    print(outputwords_sorted)


def generate_y(dataset):
    y = np.zeros((len(dataset),))
    for i in range(len(dataset)):
        y[i] = cat_mapper[dataset.iloc[i, :]["category"]]
    return y

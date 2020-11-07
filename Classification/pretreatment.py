import jieba
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer


def is_chinese(uchar):
    if u'\u4e00' <= uchar <= u'\u9fa5':
        return True
    else:
        return False


def format_str(string):
    content_str = ''
    for i in string:
        if is_chinese(i):
            content_str = content_str + i
        else:
            content_str += ","
    return content_str


if __name__ == "__main__":
    # load stop words
    stop_words = list()
    with open("stopwords.txt", "r") as stopf:
        for line in stopf.readlines():
            word = line[:-1]
            if word == "":
                continue
            stop_words.append(word)

    # participle
    dataset = pd.read_csv("dataset/dataminingnews.csv")
    corpus = list()
    tfidfvector = TfidfVectorizer()
    for index, row in dataset.iterrows():
        if index % 100 == 0:
            print("->>> %d" % index)
        content = format_str(row["content"])
        seg_list = jieba.cut(content, cut_all=False)
        words = list()
        for word in seg_list:
            if word not in stop_words and word != ",":
                words.append(word)
        seg_str = " ".join(words)
        corpus.append(seg_str)
    tfidf = tfidfvector.fit_transform(corpus)
    tfidf_df = pd.DataFrame(tfidf.toarray(), columns=tfidfvector.get_feature_names()).to_csv("tfidf.csv")

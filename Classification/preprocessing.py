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


def load_stop_words():
    sw = list()
    with open("stopwords.txt", "r") as stopf:
        for line in stopf.readlines():
            w = line[:-1]
            if w == "":
                continue
            sw.append(w)
    return sw


def corpus(sw, limit=-1):
    """
    generate corpus
    :param sw: stop word list
    :param limit: limit the amount of news
    :return: corpus list
    """
    dataset = pd.read_csv("dataminingnews.csv")
    c = list()
    for index, row in dataset.iterrows():
        if index % 100 == 0:
            print("->>> %d" % index)
        if limit != -1 and index > limit:
            break
        # Combine title & content,
        # in some cases the news haven't the content.
        content = format_str(row["title"] + row["content"])
        seg_list = jieba.cut(content, cut_all=False)
        words = list()
        for word in seg_list:
            if word not in sw and word != ",":
                words.append(word)
        seg_str = " ".join(words)
        c.append(seg_str)
    return c


def tf_idf(c, tf_idf_params=None):
    """
    calculate tf-idf
    :param c: corpus list
    :param tf_idf_params: parameters for TfidfVectorizer
    :return: data frame
    """
    if tf_idf_params is None:
        tf_idf_params = {}
    vector = TfidfVectorizer(**tf_idf_params)
    tf = vector.fit_transform(c)
    return pd.DataFrame(tf.toarray(), columns=vector.get_feature_names())


if __name__ == "__main__":
    stop_words = load_stop_words()
    # participle
    corpus = corpus(stop_words)
    tf_idf = tf_idf(corpus, tf_idf_params={
        "max_features": 80
    })
    tf_idf.to_csv("/Volumes/Data/tf_idf.csv")

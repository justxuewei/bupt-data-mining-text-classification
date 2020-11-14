import json

import jieba
import jieba.posseg as pseg
# import jieba
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer, CountVectorizer


def load_category_mapper():
    with open("./category_mapper.json", "r") as file:
        return json.loads(file.read())


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


def load_stop_words():
    sw = list()
    with open("stopwords.txt", "r") as stopf:
        for line in stopf.readlines():
            w = line[:-1]
            if w == "":
                continue
            sw.append(w)
    return sw


def corpus(ds, sw, limit=-1):
    """
    generate corpus
    :param ds: dataframe for dataset
    :param sw: stop word list
    :param limit: limit the amount of news
    :return: corpus list
    """
    print("->>> Generating Corpus...")
    c = list()
    count = 1
    for index, row in ds.iterrows():
        if count == 1:
            print("->>> %d" % count)
        if count % 100 == 0:
            print("->>> %d" % count)
        count += 1
        if limit != -1 and index > limit:
            break
        # Combine title & content,
        # in some cases the news haven't the content.
        content = __format_str(row["title"] + row["content"])
        # seg_list = pseg.cut(content)
        seg_list = jieba.cut(content)
        words = list()
        # Only append noun & verb into `words`
        # tags = ['n', 'v']
        # tags = ['n']
        for word in seg_list:
            if word not in sw and word != ",":
                # if s.word not in sw and s.word != "," and s.flag in tags:
                #     words.append(s.word)
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
    print("->>> TF-IDF is going to start...")
    if tf_idf_params is None:
        tf_idf_params = {}
    vector = TfidfVectorizer(smooth_idf=True, **tf_idf_params)
    # vector = CountVectorizer(**tf_idf_params)
    tf = vector.fit_transform(c)
    return pd.DataFrame(tf.toarray(), columns=vector.get_feature_names())


if __name__ == "__main__":
    dataset = pd.read_csv("dataminingnews.csv")
    stop_words = load_stop_words()
    # participle
    corpus = corpus(dataset, stop_words)
    tf_idf = tf_idf(corpus, tf_idf_params={
        "max_features": 1000,
    })
    tf_idf.to_csv("/Volumes/Data/tf_idf.csv")

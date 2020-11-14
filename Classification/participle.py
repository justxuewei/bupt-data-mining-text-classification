from datetime import datetime
from math import ceil

import jieba
import jieba.posseg as pseg
import pandas as pd
from multiprocessing import Pool, Lock

import preprocessing


def __is_chinese(uchar):
    if u'\u4e00' <= uchar <= u'\u9fa5':
        return True
    else:
        return False


def __format_str(string):
    content_str = ''
    # last_str = ""
    for i in string:
        if __is_chinese(i):
            content_str = content_str + i
            # last_str = i
        else:
            # if last_str != " ":
            #     content_str += " "
            #     last_str = " "
            pass
    return content_str


d_index = 0


def read_and_set(lock, ds, ilen):
    global d_index
    with lock:
        _d_index = d_index
        if d_index + ilen > len(ds):
            end_index = len(ds)
            elms = ds.iloc[d_index:, :]
        else:
            end_index = d_index + ilen
            elms = ds.iloc[d_index:end_index, :]
        d_index = end_index
        print(f"read_and_set()", f"d_index: {_d_index}", f"end_index: {end_index}", f"current_d_index: {d_index}")
        return elms


def write_to_file(lock, nf, nnvf, nt, nnvt):
    with lock:
        nf.write("".join(nt))
        nnvf.write("".join(nnvt))


def split_cut(lock, ds, spsize, nf, nnvf):
    print("split_cut")
    # rows = read_and_set(lock, ds, spsize)
    # n_text_list = list()
    # n_and_v_text_list = list()
    # for idx, row in rows.itterrows():
    #     content = __format_str(row["title"] + row["content"])
    #     seg_list = pseg.lcut(content)
    #     n_and_v_words = list()
    #     n_words = list()
    #     # Only append noun & verb into `words`
    #     tags_n_and_v = ['n', 'v']
    #     tags_n = ['n']
    #     for s in seg_list:
    #         if s not in stop_words and s.word != ",":
    #             if s.flag in tags_n_and_v:
    #                 n_and_v_words.append(s.word)
    #             if s.flag in tags_n:
    #                 n_words.append(s.word)
    #     n_and_v_content = " ".join(n_and_v_words).replace(",", "\",\"")
    #     n_content = " ".join(n_words).replace(",", "\",\"")
    #     n_text_list.append(f"{row['id']},{n_content},{row['category']}\n")
    #     n_and_v_text_list.append(f"{row['id']},{n_and_v_content},{row['category']}\n")
    # write_to_file(flock, nf, nnvf, n_text_list, n_and_v_text_list)


if __name__ == "__main__":
    dataset = pd.read_csv("dataminingnews-sample.csv")
    stop_words = preprocessing.load_stop_words()
    print("->>> Generating Corpus...")
    with open("dataminingnews-participle-n.csv", "w") as dp_n_file:
        with open("dataminingnews-participle-n-and-v.csv", "w") as dp_n_and_v_file:
            dp_n_file.write("id,content,category\n")
            dp_n_and_v_file.write("id,content,category\n")
            jieba.enable_parallel(6)
            jieba.enable_paddle()
            start = datetime.now()
            for idx, row in dataset.iterrows():
                if idx % 100 == 0:
                    now_time = datetime.now()
                    print(f"->>> CURRENT PROGRESS {idx*100/len(dataset)}% ({idx}/{len(dataset)}) - "
                          f"RUN TIME {(now_time-start).seconds}s")
                n_text_list = list()
                n_and_v_text_list = list()
                content = __format_str(row["title"] + row["content"])
                # print(content)
                seg_list = pseg.cut(content, use_paddle=True)
                n_and_v_words = list()
                n_words = list()
                # Only append noun & verb into `words`
                tags_n_and_v = ['n', 'f', 's', 'nr', 'ns', 'nt', 'nw', 'nz', 'v']
                tags_n = ['n', 'f', 's', 'nr', 'ns', 'nt', 'nw', 'nz']
                for s in seg_list:
                    if s not in stop_words and s.word != "," and s.word != "":
                        if s.flag in tags_n_and_v:
                            # print(s.word, s.flag)
                            n_and_v_words.append(s.word)
                        if s.flag in tags_n:
                            n_words.append(s.word)
                n_and_v_content = " ".join(n_and_v_words).replace(",", "\",\"")
                n_content = " ".join(n_words).replace(",", "\",\"")
                dp_n_file.write(f"{row['id']},{n_content},{row['category']}\n")
                dp_n_and_v_file.write(f"{row['id']},{n_and_v_content},{row['category']}\n")

import jieba.posseg as pseg


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


def split_cut(dilock, flock, ds, spsize, sws, nf, nnvf):
    print("split_cut")
    rows = read_and_set(dilock, ds, spsize)
    n_text_list = list()
    n_and_v_text_list = list()
    for idx, row in rows.itterrows():
        content = __format_str(row["title"] + row["content"])
        seg_list = pseg.lcut(content)
        n_and_v_words = list()
        n_words = list()
        # Only append noun & verb into `words`
        tags_n_and_v = ['n', 'v']
        tags_n = ['n']
        for s in seg_list:
            if s not in sws and s.word != ",":
                if s.flag in tags_n_and_v:
                    n_and_v_words.append(s.word)
                if s.flag in tags_n:
                    n_words.append(s.word)
        n_and_v_content = " ".join(n_and_v_words).replace(",", "\",\"")
        n_content = " ".join(n_words).replace(",", "\",\"")
        n_text_list.append(f"{row['id']},{n_content},{row['category']}\n")
        n_and_v_text_list.append(f"{row['id']},{n_and_v_content},{row['category']}\n")
    write_to_file(flock, nf, nnvf, n_text_list, n_and_v_text_list)

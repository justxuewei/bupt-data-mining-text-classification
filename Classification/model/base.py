from math import sqrt, exp, pi


def separate_by_class(dataset):
    """
    separate data by class
    :param dataset: dataset that includes the class at the end of columns, the type is DataFrame
    :return: a dict which keys are classes and values are vectors
    """
    separated = dict()
    for i, row in dataset.iterrows():
        vector = row.head(len(dataset.columns) - 1).to_numpy()
        category = row["category"]
        if category not in separated:
            separated[category] = list()
        separated[category].append(vector)
    return separated


def mean(numbers):
    """
    calculate the mean of a list of numbers
    :param numbers: a list
    :return: mean with float type
    """
    return sum(numbers) / float(len(numbers))


def stdev(numbers):
    """
    calculate the standard deviation of a list of numbers
    :param numbers: a list
    :return: the standard deviation with float type
    """
    # if len(numbers) <= 1:
    #     return 0
    avg = mean(numbers)
    variance = sum([(x - avg) ** 2 for x in numbers]) / float(len(numbers) - 1)
    return sqrt(variance)


def summarize(vectors):
    """
    calculate the mean, stdev and count for each column in the vectors
    :param vectors: a list of vectors
    :return: a list of tuples with three elements: mean, stdev and count
    """
    summaries = [(mean(column), stdev(column), len(column)) for column in zip(*vectors)]
    return summaries


def summarize_by_class(dataset):
    """
    calculate statistics for each row in dataset
    :param dataset: dataset that includes the class at the end of columns, the type is DataFrame
    :return: a dict with keys are categories and values are return values from summarize()
    """
    separated = separate_by_class(dataset)
    summarise = dict()
    for class_value, rows in separated.items():
        summarise[class_value] = summarize(rows)
    return summarise


def calculate_probability(x, mean, stdev):
    """
    Calculate the Gaussian probability distribution function for x
    :param x: the data
    :param mean: the mean for x
    :param stdev: the standard deviation for x
    :return: probability with float type
    """
    exponent = exp(-((x-mean)**2)/(2*stdev**2))
    return (1/(sqrt(2*pi)*stdev)) * exponent

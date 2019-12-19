def average(values):
    """Computes the arithmetic mean of a list of numbers.

    >>> print(average([20, 30, 70]))
    40.0
    """
    return sum(values) / len(values)

# doctest将基于文档中包含的单元测试进行验证
import doctest
r = doctest.testmod()   # automatically validate the embedded tests

print(r)

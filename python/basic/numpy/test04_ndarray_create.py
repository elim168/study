# 其它创建ndarray的方式

import numpy

ndarray = numpy.zeros((2, 6), dtype=int)    # 创建一个二维的两行6列的填充值都是整数0的数组
print(ndarray)  # [[0 0 0 0 0 0][0 0 0 0 0 0]]

ndarray = numpy.ones((2, 6), dtype=int) # 创建一个填充值都是整数1的两行6列的二维数组
print(ndarray)  # [[1 1 1 1 1 1][1 1 1 1 1 1]]

ndarray = numpy.empty((2, 5))   # 创建一个两行5列的空的二维数组，只负责内存分配，不填充值。此时元素的值将是内存未分配以前写的值（具体内容未知）
print(ndarray)  # 输出如下
""" [[6.94884256e-310 1.22618002e-316 5.39246171e-317 5.39246171e-317
  6.94884134e-310]
 [6.94884134e-310 5.39246171e-317 5.39246171e-317 5.39247752e-317
  6.94884134e-310]]"""

ndarray = numpy.linspace(1, 100, 10, dtype=int)  # 生成从1到100的等差数组，整数类型，共10个元素
print(ndarray)  # [  1  12  23  34  45  56  67  78  89 100]


ndarray = numpy.logspace(1, 5, 5, dtype=int)    # 生成1-5的以10为底的log值，共5个，整数类型。底数也可以通过base指定。
print(ndarray)  # [    10    100   1000  10000 100000]


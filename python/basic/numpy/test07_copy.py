# 测试numpy的copy

import numpy

ndarray = numpy.arange(1, 11)
ndarray[:5][0] = 100
print(ndarray)  # 这说明切片的结果只是原来数组的一个视图。[100   2   3   4   5   6   7   8   9  10]

copied_ndarray = numpy.copy(ndarray)
copied_ndarray[0] = 10
print(ndarray)  # 没有改变ndarray的值。[100   2   3   4   5   6   7   8   9  10]
print(copied_ndarray)   # [10  2  3  4  5  6  7  8  9 10]


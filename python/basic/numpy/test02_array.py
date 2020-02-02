import numpy

# 创建一维的数组，基于一维的列表
ndarray = numpy.array([1, 2, 3, 4, 5, 6])
print(ndarray)
print(type(ndarray))    # <class 'numpy.ndarray'>

# 创建二维的数组，基于二维的列表。三维/四维等也是类似的道理
ndarray = numpy.array([[1, 2, 3, 4, 5], [2, 3, 4, 5, 6], [3, 4, 5, 6, 7]])
print(ndarray)


# 通过ndmin指定最小维度
ndarray = numpy.array([1, 2, 3], ndmin=5)   # 指定创建的数组最小为5维
print(ndarray)  # [[[[[1 2 3]]]]]


# 测试数组的拼接，主要有hstack/vstack和concatenate三种方式

import numpy

a = numpy.arange(10).reshape((2, 5))
b = numpy.arange(10, 20).reshape((2, 5))

print(numpy.hstack((a, b))) # 进行水平拼接，拼接后的数组是2行10列
print(numpy.vstack((a, b))) # 进行垂直拼接，拼接后的数组是4行5列

# concatenate默认按第一个维度合并
print(numpy.concatenate((a, b)))    # 拼接后的数组是4行5列
print(numpy.concatenate((a, b), axis=1))    # 通过axis指定拼接维度，1表示第2个。拼接后的数组是2行10列

c = numpy.arange(12).reshape((2, 2, 3))
d = numpy.arange(12, 24).reshape((2, 2, 3))
print(numpy.concatenate((c, d), axis=2))    # 按第三个维度拼接，拼接后的shape 是(2, 2, 6)


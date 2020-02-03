# 测试对ndarray的切割

import numpy

a = numpy.arange(10)
b = numpy.split(a, 5)    # 平均分为5份
print(b)    # [array([0, 1]), array([2, 3]), array([4, 5]), array([6, 7]), array([8, 9])]
print(b[0], b[1], b[2])     # [0 1] [2 3] [4 5]

b = numpy.split(a, [3])     # 第2个参数传递一个列表，列出需要作为分隔点的索引。这里将分隔为两个数组，第一个是0-2,第2个是3之后的
print(b)
b1, b2 = numpy.split(a, [3])    # 也可以直接用两个变量接收
print(b1, b2)   # [0 1 2] [3 4 5 6 7 8 9]

b1, b2, b3, b4 = numpy.split(a, [3, 5, 8])  # 分成了4段

print(b1, b2, b3, b4)   # [0 1 2] [3 4] [5 6 7] [8 9]

a = numpy.arange(48).reshape((4, 6, 2)) # 生成一个三维的数组
b1, b2 = numpy.split(a, 2)   # 默认按第一个维度切分，平均为2份
print(b1.shape, b2.shape)   # (2, 6, 2) (2, 6, 2)

b1, b2, b3 = numpy.split(a, [2, 5], axis=1)  # 按第2个维度分为3份
print(b1.shape, b2.shape, b3.shape) # (4, 2, 2) (4, 3, 2) (4, 1, 2)



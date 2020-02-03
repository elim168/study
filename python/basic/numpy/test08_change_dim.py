# 修改数组的维度
import numpy

a = numpy.arange(30)
print(a)
print(a.reshape((3, 10)))   # 将一维数组转换为3行10列的二维数组
print(a.reshape((3, 10)).reshape((2, 3, 5)))   # 将一维数组转换为二维数组，再转换为三维数组
print(numpy.reshape(a, (5, 6))) # 通过numpy的reshape转换数组的维度

b = a.reshape((2, 3, 5))
print(b.reshape(-1))    # 将多维数组转换为一维数组
print(b.reshape(b.size))    # 将多维数组转换为一维数组

print(b.ravel())    # 将多维数组转换为一维数组
print(b.flatten())  # 将多维数组转换为一维数组




# 测试数组的转换，可以将维度进行转换
import numpy

a = numpy.arange(12).reshape((2, 6))    # 生成一个两行6列的数组
print(a)
print(a.transpose())    # 进行行列转换，变成一个6行2列的数组

# 也可以使用numpy.transpose()
print(numpy.transpose(a))   # 进行行列转换，变成一个6行2列的数组

a = numpy.arange(48).reshape((2, 6, 4)) # 生成一个三维数组
b = numpy.transpose(a)  # 默认会将第1维和第3维进行交换，变成(4, 6, 2)
print(b, b.shape)

b = numpy.transpose(a, (2, 0, 1))   # 将原来的第3维放在第1维，原来的第1维放在第2维，原来的第2维放在第3维，变成(4, 2, 6)
print(b, b.shape, a.shape)




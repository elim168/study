# 测试numpy中的一些函数

import numpy

# 生成一个三行三列的二维数组
a = numpy.arange(1, 10).reshape((3, 3))
b = numpy.arange(11, 20).reshape((3, 3))
print(a)
print(b)
print(numpy.add(a, b))  # 将数组相同位置的数相加生成新的数组
print(numpy.add(a, 10)) # 将数组a的每个位置的值加上10
print(numpy.add(a, numpy.arange(1, 4))) # 将数组a的每一行的元素都加上[1,2,3]
print(a + b)    # 等同于numpy.add(a, b)

print(numpy.multiply(a, b)) # 将数组相同位置的数相乘

print(numpy.sum(a)) # 将数组的所有元素相加

print(numpy.power(a, b))    # 将数组a的每个元素的值取数组b对应位置的值的幂
print(numpy.power(a, 3))    # 取数组a的每个元素的3次方
c = numpy.empty((3, 3), dtype=int)
print(numpy.power(a, 3, out=c))    # 取数组a的每个元素的3次方，并把结果写到数组c中
print(c)

c = numpy.ones(10, dtype=int)
d = numpy.arange(6)
numpy.power(2, d, out=c[:6])    # 依次取2的对应数组d的每个元素值的次方，把结果放到数组c的前六个元素中
print(c)

print(numpy.sin(numpy.array((0, 30, 45, 60, 90, 120, 150, 180))))
print(numpy.cos(numpy.array((0, 30, 45, 60, 90, 120, 150, 180))))





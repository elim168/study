# 对二维的ndarray进行索引访问和切片

import numpy

ndarray = numpy.arange(1, 31)  # 1-30的共30个元素的一维数组
print(ndarray)
ndarray = numpy.reshape(ndarray, (5, 6))  # 把30个元素的一维数组，重新变成5行6列的二维数组
print(ndarray)

# -----根据索引下标访问元素
print(ndarray[0])   # 访问第一行
print(ndarray[2][3])   # 访问第3行，4列（通过两层索引访问）
print(ndarray[2, 3])   # 访问第3行，4列
print(ndarray[-1][-2])  # 访问倒数第1行的倒数第2列
print(ndarray[-1, -2])  # 访问倒数第1行的倒数第2列

# ------切片--------
'''二维数组的切片的语法是[行切片规则,列切片规则]，其中行和列的切片规则又同一维数组的切片规则，即[start:end:step]
'''

print(ndarray[:,:]) # 对行和列都同时取所有的元素
print(ndarray[1:3,2:4]) # 取第2-3行的第3-4列。
print(ndarray[-3:,2:4]) # 取倒数第3行/倒数第2行/倒数第1行的第3-4列。
print(ndarray[::-1,2:4]) # 按照逆序取                                               第3-4列。


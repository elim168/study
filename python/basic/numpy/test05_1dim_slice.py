# 对一维的ndarray进行索引下标访问和切片
import numpy

ndarray = numpy.arange(10)
print(ndarray) # [0 1 2 3 4 5 6 7 8 9]

# 通过索引访问ndarray的元素
print(ndarray[5])   # 索引为5的元素，即5
print(ndarray[-3])  # 倒数第3个元素，即7


# 切片。ndarray的切片和list的切片是一样的，语法都是[start:end:step]
print(ndarray[:])   # 输出整个数组
print(ndarray[5:])   # 输出从索引5开始之后的元素组成的数组。[5 6 7 8 9]
print(ndarray[2:8])   # 输出从索引2开始到索引7的元素组成的数组，不包括end指定的索引。[2 3 4 5 6 7]
print(ndarray[2:8:2])   # 输出从索引2开始到索引7，步长为2的元素组成的数组。[2 4 6]
print(ndarray[::-1])   # 按反向的顺序输出。[9 8 7 6 5 4 3 2 1 0]
print(ndarray[-1:-6:-1])   # 按反向的顺序获取倒数第一个到倒数第5个元素。[9 8 7 6 5]
print(ndarray[-6:-2])   # 获取倒数第6个到倒数第3个元素，左开右闭。[4 5 6 7]


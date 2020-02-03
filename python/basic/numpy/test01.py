# numpy是进行科学计算的一个库，官网是https://numpy.org/

import numpy as np

# numpy.arange()的用法类似于range()
print(np.arange(10))    # 生成0-9的数组
print(np.arange(10, 20))    # 生成10-19的数组
print(np.arange(1, 20, 3))  # 步长是3,每隔3位取一个数字，然后生成数组

array = np.arange(30, 36)
print(type(array)) # <class 'numpy.ndarray'> ，ndarray表示多维数组
print(array)
print(np.sqrt(80))
print(np.sqrt(array))   # numpy也可以直接对一个数组进行开方


# ---ndarray的属性，更多属性可以参考相应的文档或源码------
print(array.dtype)  # 数据类型/元素的了；类型
print(array.itemsize)   # 每个元素的大小
print(array.nbytes) # 总大小，以字节为单位
print(array.ndim)   # 维度
print(array.shape)  # 形状，元组，(6,)，如果是一个两行三列的二维数组则会输出(2,3)
print(array.size)   # 元素总个数

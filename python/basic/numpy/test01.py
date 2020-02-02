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

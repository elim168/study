# 测试partial函数
# functools的partial函数可以把一个函数的某些参数固定住，然后返回新的函数。比如下面把int函数的base参数固定为2返回new_int函数，之后new_int都以二进制对字符串进行整数转换。

import functools

new_int = functools.partial(int, base=2)
print(new_int('111'))  # 7
print(new_int('1110'))  # 14
print(new_int('1110101'))  # 117

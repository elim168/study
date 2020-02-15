# reduce test
# 测试reduce
import functools


def multi(x, y):
    return x * y


a = list(range(1, 6))
# reduce函数可以基于可迭代对象a中的所有元素应用multi函数，先把1×2,再把1×2的结果乘3,再把结果6乘4，依次类推。即结果是：(((1*2)*3)*4)*5
result = functools.reduce(multi, a)
print(result)  # 120

result = functools.reduce(multi, a, 10)  # 最后一个参数是初始值，即初始会把它赋值给x，即结果是((((10*1)*2)*3)*4)*5
print(result)  # 1200


# 函数这里也可以直接使用lambda表达式
result = functools.reduce(lambda x, y: x + y, a)  # (((1+2)+3)+4)+5
print(result)  # 15

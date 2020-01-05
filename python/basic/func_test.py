def add(a, b):
    '''把两个数字相加并返回'''
    return a + b


print(add(10, 20))

print('*' * 20)
print(add.__doc__)  # 输出函数add的文档字符串
print('*' * 20)
help(add)  # 输出函数add的帮助信息，即文档字符串

a = 100  # 全局变量


def test1():
    b = 10  # 局部变量
    global a
    a = 50  # 局部需要对全局变量进行赋值时，需要通过global声明它是一个全局变量
    print(a)  # 局部变量
    # global a
    # print('全局变量a =', a)


test1()
print(a)

import time
import math


def test2():
    t1 = time.time()
    sqrt = math.sqrt
    for i in range(10000000):
        sqrt(30)
    t2 = time.time()
    print('耗时：', t2 - t1)


def test3():
    t1 = time.time()
    for i in range(10000000):
        math.sqrt(30)
    t2 = time.time()
    print('耗时：', t2 - t1)


test3()
test2()


# c和d是默认值参数，不传递时将使用默认值
def test4(a, b, c=10, d=20):
    print(a, b, c, d)


test4(1, 5, 15)  # 1 5 15 20

# 除了通过参数位置传递参数外，还可以通过参数名传递参数
test4(b=100, a=200)


# 测试可变参数，下面*c表示的就是可变参数，接收后c是元组，通过位置传递对应的参数给到c
def test5(a, b, *c):
    print(a, b, c)


test5(1, 2, 3, 4, 5, 6)  # 1 2 (3, 4, 5, 6)


# 下面的**c表示可变参数，必须通过字典形式传递，c对应的也是字典形式
def test6(a, b, **c):
    print(a, b, c)


test6(1, 2, a1=10, b1=20, c1=30)  # 1 2 {'a1': 10, 'b1': 20, 'c1': 30}

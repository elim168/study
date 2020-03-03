# 当一个函数中有yield时，该函数的返回结果将是一个Generator对象，第一次调用__next__()会从函数开始执行到yield语句结束，第2次会从前一次yield语句之后开始执行，直到遇到yield语句，依次类推


def A():
    for i in range(5):
        print(i)
        yield i
        # yield   # yield 也可以不返回值


a = A()
print(type(a))  # <class 'generator'>

for b in range(5):
    print('B')
    # a.__next__()
    next(a)

# 输出如下
'''
B
0
B
1
B
2
B
3
B
4
'''

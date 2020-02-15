# 一个函数上可以应用多个装饰器函数
# 当应用多个装饰器函数时会按照从下到上的顺序依次装饰，所以下面的用法会先装饰decorate1，再装饰decorate2。


def decorate1(func):
    def func_in():
        print('hello, decorate1')
        return func()
    return func_in


def decorate2(func):
    def func_in():
        print('hello, decorate2')
        return func()
    return func_in


@decorate2
@decorate1
def func1():
    print('hello func1')


func1()


# 输出如下
'''
hello, decorate2
hello, decorate1
hello func1
'''

# 测试装饰器包含参数的场景
# 当需要装饰的函数是有参数时，装饰器的内部函数也需要有同样的参数定义。当一个装饰器需要应用到多个函数时，我们可以利用下面的方式定义通用的参数定义


def decorate1(func):
    def func_in(*args, **kwargs):  # *args指定所有的位置参数，**kwargs指定所有的字典参数
        result = func(*args, **kwargs)
        print('decorate1----result-{} with args-{} kwargs-{} is {}'.format(func.__name__, args, kwargs, result))
        return result

    return func_in


@decorate1
def add1(a, b):
    return a + b


@decorate1
def add2(a, b, c):
    return a + b + c


print(add1(10, 20))
print(add2(10, 20, 30))

# 输出如下
'''
decorate1----result-add1 with args-(10, 20) kwargs-{} is 30
30
decorate1----result-add2 with args-(10, 20, 30) kwargs-{} is 60
60
'''

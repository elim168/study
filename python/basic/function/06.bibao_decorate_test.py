# 测试使用装饰器
# 定义一个装饰器函数，它的入参是一个函数，在内部函数里调用入参函数。可以在内部函数中添加额外的逻辑
# 然后在需要应用该装饰器的函数上加上"@装饰器函数名"，如下面的func1上的@write_log则表示需要在func1上应用write_log函数。在调用func1时就相当于
# 调用了write_log的func_in函数。
import time


def write_log(func):
    def func_in():
        print('execute %s at %s' % (func.__name__, time.asctime()))
        return func()

    return func_in


@write_log
def func1():
    print('func1')


func1()
# 输出如下
'''
execute func1 at Sat Feb 15 16:42:06 2020
func1
'''

# test _thread
# 线程有两种实现方式，一种是_thread.start_new_thread()，一种是threading.Thread()
# _thread是为了兼容python2的，目前使用的多的是threading

import _thread
import time


def action1():
    print('action1 start-----')
    time.sleep(3)
    print('action1 end-----')


_thread.start_new_thread(action1, ())   # 开启一个新的线程，线程方法没有参数
time.sleep(4)
print('main thread end')


def action2(name, seconds):
    print('hello %s' % name)
    time.sleep(seconds)
    print('-------action2 end-----')


_thread.start_new_thread(action2, ('zhangsan', 2))   # 开启一个新的线程，线程方法有参数
time.sleep(3)

print('main thread end')

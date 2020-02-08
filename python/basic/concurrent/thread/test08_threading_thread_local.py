# 测试ThreadLocal

import threading


local_data = threading.local()	# 类似于Java中的ThreadLocal，多个方法共享同一个ThreadLocal对象，基于该ThreadLocal对象赋予的值将区别于每个线程。


def func1(name):
    local_data.name = name
    func2()


def func2():
    print('thread-%s  get name from thread local is %s' % (threading.currentThread().getName(), local_data.name))


print('main thread name is', threading.currentThread().getName())

t1 = threading.Thread(target=func1, args=('zhangsan',))
t2 = threading.Thread(target=func1, args=('lisi',))
t1.start()
t2.start()


# 测试结果输出如下
'''
main thread name is MainThread
thread-Thread-1  get name from thread local is zhangsan
thread-Thread-2  get name from thread local is lisi
'''

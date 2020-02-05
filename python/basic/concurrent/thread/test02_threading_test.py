# 通过threading.Thread()创建线程

import threading
import time


def func1():
    print('func1 start')
    time.sleep(3)
    print('func1 end')


def func2():
    print('func2 start')
    time.sleep(3)
    print('func2 end')


t1 = threading.Thread(target=func1)
t2 = threading.Thread(target=func2, name='thread-name-func2')    # 通过name指定线程的名字

t1.start()
t2.start()

print(t1.getName(), t2.getName())   # Thread-1 thread-name-func2
print(t1.is_alive())

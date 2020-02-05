# 测试给线程方法传参


import threading
import time


def func1(name, seconds, **kwargs):
    print('hello {}'.format(name))
    time.sleep(seconds)
    print('kwargs is', kwargs)


print('main thread starting')
# 位置参数通过args传递，字典参数通过kwargs传递
t1 = threading.Thread(target=func1, args=('zhangsan', 3), kwargs={'score': 100})
t1.start()
print('waiting for thread t1 to end')
t1.join()
# t1.join(10)
print('main thread end.')


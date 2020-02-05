# 测试互斥锁

import threading


n = 0   # 全局的变量n


# 累加，不使用锁的方式
def num_plus_without_lock(times):
    global n
    for i in range(times):
        n += 1


# 创建一个互斥锁
lock = threading.Lock()
print(lock.locked())    # True or False

def num_plus_with_lock(times):
    global n
    for i in range(times):
        lock.acquire()    # 获取锁，没有获取到就等待
        # lock.acquire(blocking=True, timeout=1)    # 获取锁指定是否阻塞和超时时间，默认是阻塞的
        n += 1
        lock.release()    # 释放锁


times = 1000000


def test_without_lock():
    t1 = threading.Thread(target=num_plus_without_lock, args=(times,))
    t2 = threading.Thread(target=num_plus_without_lock, args=(times,))
    t1.start()
    t2.start()
    t1.join()
    t2.join()
    print('without lock the result n is', n)


def test_with_lock():
    global n
    n = 0
    t1 = threading.Thread(target=num_plus_with_lock, args=(times,))
    t2 = threading.Thread(target=num_plus_with_lock, args=(times,))
    t1.start()
    t2.start()
    t1.join()
    t2.join()
    print('with lock the result n is', n)


test_without_lock()
test_with_lock()

# 测试结果如下
'''
without lock the result n is 1332115
with lock the result n is 2000000
'''


# 生产者消费者测试

import multiprocessing
import time



def producer(q):
    for i in range(10):
        q.put(i)
        print('produce one item %d' % i)
        time.sleep(1)


def consumer(q):
    for i in range(10):
        print('consume one item %d' % q.get())


q = multiprocessing.Queue(maxsize=3)
p1 = multiprocessing.Process(target=producer, args=(q,))
p2 = multiprocessing.Process(target=consumer, args=(q,))

p1.start()
p2.start()

p2.join()
print('===============')


# 使用进程池时，Queue不能使用multiprocessing.Queue()，需要使用multiprocessing.Manager().Queue()才有效。
q = multiprocessing.Manager().Queue(maxsize=3)
pool = multiprocessing.Pool(processes=2)
pool.apply_async(producer, args=(q,))
pool.apply_async(consumer, args=(q,))
pool.close()
pool.join()

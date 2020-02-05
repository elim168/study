# 测试队列
import multiprocessing


q = multiprocessing.Queue(maxsize=11)   # 通过maxsize指定队列的最大元素数，不指定maxsize属性时将不限元素数
for i in range(10):
    q.put(i)
if not q.full():
    q.put(10, block=True, timeout=1)    # 当队列已经满了之后可以指定block=True，此时将阻塞，默认是True。timeout指定阻塞的超时时间，单位是秒，默认是不限制


print('q.qsize is ', q.qsize())

for i in range(q.qsize()):
    print(q.get(), 'remaining', q.qsize())



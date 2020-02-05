# 测试进程池


import multiprocessing
import time


def action1(i, seconds):
    print('id=%d start' % i)
    time.sleep(seconds)
    print('id=%d end' % i)


pool = multiprocessing.Pool(processes=2)  # 通过processes指定最大的进程数。不指定时默认取CPU核心数
for i in range(6):
    pool.apply_async(action1, (i, 2))  # 异步提交，提交后立即返回

for i in range(6):
    pool.apply(action1, (i+10, 2))  # 同步提交，提交后需要等任务执行完成后才返回
print('main process end 2!')
pool.close()

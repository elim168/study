# test process
# 测试多进程

from multiprocessing import Process
import time


def action1():
    for i in range(5):
        print('action1----%d' % i)
        time.sleep(1)


def action2():
    for i in range(5):
        print('action2----%d' % i)
        time.sleep(1)


# 创建一个进程，target指定进程启动需要执行的方法
p1 = Process(target=action1)
p2 = Process(target=action2, name='Process-Name')	# name指定进程的名称，不指定时将使用自动生成的名称，其它属性参考源码或API文档

# 启动进程
p1.start()
p2.start()

print(p1.name, p2.name)	# 输出进程名称
print(p1.pid, p2.pid)	# 输出进程ID
print(p1.is_alive(), p2.is_alive())	# 判断进程是否还存活

# test process


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


#
p1 = Process(target=action1)
p2 = Process(target=action2, name='Process-Name')

p1.start()
p2.start()

print(p1.name, p2.name)
print(p1.pid, p2.pid)
print(p1.is_alive(), p2.is_alive())

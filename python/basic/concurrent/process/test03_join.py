
from multiprocessing import Process
import time


def action1():
    print('hello action1')
    time.sleep(3)
    print('action1 end')


p1 = Process(target=action1)
p1.start()
print('main process starting')
p1.join()
# p1.join(3)    # join时也可以指定超时时间，单位是秒
print('main process end!')

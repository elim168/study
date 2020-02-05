
from multiprocessing import Process
import time


# 自定义的Class继承自Process类
class MyProcess(Process):

    def __init__(self, seconds):
        Process.__init__(self)  # 构造方法里面需要手动调用父类中的构造方法
        self.seconds = seconds

    def run(self):# 重写Process的run方法
        for i in range(self.seconds):
            print('now is', time.ctime())
            time.sleep(1)


p = MyProcess(10)
p.start()

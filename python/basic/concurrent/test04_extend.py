
from multiprocessing import Process
import time


class MyProcess(Process):

    def __init__(self, seconds):
        Process.__init__(self)  # call parent class construct method
        self.seconds = seconds

    def run(self):
        for i in range(self.seconds):
            print('now is', time.ctime())
            time.sleep(1)


p = MyProcess(10)
p.start()

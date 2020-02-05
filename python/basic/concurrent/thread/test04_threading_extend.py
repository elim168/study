# 测试通过继承threading.Thread实现线程
import threading


class MyThread(threading.Thread):

    def __init__(self, name):
        # threading.Thread.__init__(self)
        super().__init__() # 调用父类的构造方法
        self.name = name

    def run(self): # 重写run方法
        print('hello {}'.format(self.name))


t = MyThread('zhangsan')
t.start()
t.join()

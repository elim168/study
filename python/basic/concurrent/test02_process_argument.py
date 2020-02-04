# test process target with argument

from multiprocessing import Process

def action1(name):
    print('Hello %s' % name)


# can shu tong guo args chuan di
p1 = Process(target=action1, args=('zhangsan',))
p1.start()


def action2(name, **kwargs):
    print('hello %s' % name)
    print('dict args is ', kwargs)


p2 = Process(target=action2, args=('lisi',), kwargs={'age': 18, 'score': 100})
p2.start()




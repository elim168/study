# test process target with argument
# 测试给进程执行的方法传递参数

from multiprocessing import Process

def action1(name):
    print('Hello %s' % name)


# 进程方法上的非字典参数通过args属性传递，类型是元组
p1 = Process(target=action1, args=('zhangsan',))
p1.start()


def action2(name, **kwargs):
    print('hello %s' % name)
    print('dict args is ', kwargs)

# 进程方法上的字典参数通过kwargs参数传递
p2 = Process(target=action2, args=('lisi',), kwargs={'age': 18, 'score': 100})
p2.start()




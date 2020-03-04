# yield的函数会当作一个Generator，调用其send()方法可以把控制权转到包含yield的函数，send的值将作为yield的返回值


def consumer():
    while True:
        data = yield    # 没有调用send()时这里返回的就是None
        print('消费者消费了{}'.format(data))


c = consumer()
# 第一次调用next()会开始调用consumer()，程序运行到第一次yield交出控制权
next(c)
for i in range(10):
    print('生产者生产了{}'.format(i))
    c.send(i)   # 每次调用send会把值发送给consumer，此时生产者会停止运行，consumer接着运行，yield的返回值就是生产者send的值。


# 输出如下
'''
生产者生产了0
消费者消费了0
生产者生产了1
消费者消费了1
生产者生产了2
消费者消费了2
生产者生产了3
消费者消费了3
生产者生产了4
消费者消费了4
生产者生产了5
消费者消费了5
生产者生产了6
消费者消费了6
生产者生产了7
消费者消费了7
生产者生产了8
消费者消费了8
生产者生产了9
消费者消费了9
'''


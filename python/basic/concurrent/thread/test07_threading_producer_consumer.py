# 验证生产者和消费者模式，基于线程的

import threading
import queue
import time


class Producer(threading.Thread):
    def __init__(self, _queue, total):
        super().__init__()
        self.queue = _queue
        self.total = total

    def run(self):
        count = 0
        while count < self.total:
            count += 1
            product = 'product-%d' % count
            print('%s produce %s' % (self.getName(), product))
            self.queue.put(product)
            time.sleep(0.2)  # sleep 0.2 second
        print('produce finish')


class Consumer(threading.Thread):
    def __init__(self, _queue, total):
        super().__init__()
        self.queue = _queue
        self.total = total

    def run(self):
        count = 0
        while count < self.total:
            count += 1
            print('%s consume %s' % (self.getName(), self.queue.get()))
            time.sleep(0.3)
        print('consume finish')


# 这里的对列跟进程那里的队列是不一样的，但是用法是相同的。
_queue = queue.Queue()
total = 100
producer = Producer(_queue, total)
consumer = Consumer(_queue, total)

producer.start()
consumer.start()



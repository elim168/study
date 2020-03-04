import asyncio
import time
import threading


# 加上async表示是异步执行的，表示对该函数的调用不会立即执行，而是将会返回一个协程对象。该协程对象需要通过事件循环对象调用。
# 所有的内容都是在一个线程里面完成的
async def func(num):
    print('start-{}'.format(num), threading.current_thread().getName())
    asyncio.sleep(1)
    # time.sleep(1)
    print('end-{}'.format(num), threading.current_thread().getName())


# 创建事件循环对象
loop = asyncio.get_event_loop()
start = time.time()
for i in range(5):
    # 返回的是协程对象
    coroutine_obj = func(i)
    print(type(coroutine_obj))    # <class 'coroutine'>
    # 通过事件循环对象来运行该协程对象，进而运行定义的方法
    loop.run_until_complete(coroutine_obj)
end = time.time()
print(end-start, 's')


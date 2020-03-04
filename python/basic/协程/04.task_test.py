# 协程对象在通过事件循环对象调用时，内部会把它包装为一个Task对象，task是一个future的子类
# 所以我们也可以直接基于协程对象创建对应的Task对象，再通过事件循环对象进行调用。

import asyncio
import threading


# 加上async表示一个协程对象。
async def func(num):
    print('start-{}'.format(num))


# 创建事件循环对象
loop = asyncio.get_event_loop()
# 返回的是协程对象
coroutine_obj = func(5)

# 创建事件循环对象
task = asyncio.ensure_future(coroutine_obj)

# 也可以通过下面这样的方式创建Task对象
# task = loop.create_task(coroutine_obj)

print(type(task))  # <class '_asyncio.Task'>

print(isinstance(task, asyncio.Task))   # True
# 通过事件循环对象来运行Task
loop.run_until_complete(task)

import asyncio


# 协程对象
async def func():
    return 'hello python'


def callback(future: asyncio.Task):
    print('函数func()的运行结果是：{}'.format(future.result()))


# 协程对象
coroutine = func()
loop = asyncio.get_event_loop()
# 创建Task
task = loop.create_task(coroutine)
# 添加回调函数
task.add_done_callback(callback)
# 运行任务
r = loop.run_until_complete(task)
print(r)    # hello python


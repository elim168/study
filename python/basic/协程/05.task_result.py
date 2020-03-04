import asyncio


# 协程对象
async def func():
    return 'hello python'


# 协程对象
coroutine = func()
loop = asyncio.get_event_loop()
# 创建Task
task = loop.create_task(coroutine)
# 当协程对象有返回值时，其返回值可以通过loop.run_until_complete()接收
r = loop.run_until_complete(task)
print(r)
# 也可以通过如下方式获取Task运行之后的结果
result = task.result()
print(result)   # hello python

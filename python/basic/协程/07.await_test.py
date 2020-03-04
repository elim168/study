import asyncio, time


async def func(s):
    # time.sleep(s)
    # 不加这个await这句会很快执行完，下面的输出会接近0秒，而加上await后会等待睡眠时间结束，最终输出3秒。
    await asyncio.sleep(s)


t1 = time.time()
coroutine = func(3)
loop = asyncio.get_event_loop()
loop.run_until_complete(coroutine)
t2 = time.time()
print(t2 - t1)

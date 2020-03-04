import asyncio, time


async def func(s):
    print('start waiting')
    # 遇到await时会让出CPU时间给其它协程执行，所以该程序最终的输出耗时时间是最长的睡眠时间4秒。
    await asyncio.sleep(s)
    return 'waiting {} seconds'.format(s)


t1 = time.time()
tasks = [func(2), func(3), func(4)]
loop = asyncio.get_event_loop()

# dones, pendings = loop.run_until_complete(asyncio.wait(tasks))
# for r in dones:  # 输出返回结果
#     print(r.result())
result = loop.run_until_complete(asyncio.gather(*tasks))
print(result)   # ['waiting 2 seconds', 'waiting 3 seconds', 'waiting 4 seconds']
t2 = time.time()
print(t2 - t1)  # 4.002530336380005

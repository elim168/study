import multiprocessing


def action1(a):
    a.append(10)
    print('action1-------------', a)


a = [1, 2, 3]
p1 = multiprocessing.Process(target=action1, args=(a,))
p2 = multiprocessing.Process(target=action1, args=(a,))
print(a)
p1.start()
p1.join()
print(a)
p2.start()
p2.join()
print(a)

# 输出如下，说明多进程不能共享全局的变量
'''

[1, 2, 3]
action1------------- [1, 2, 3, 10]
[1, 2, 3]
action1------------- [1, 2, 3, 10]
[1, 2, 3]
'''

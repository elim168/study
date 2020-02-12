from timeit import Timer
"""用于演示执行某段代码的性能情况，即统计执行时间"""
t1 = Timer('t=a; a=b; b=t', 'a=1; b=2').timeit()
# 可以通过number指定执行次数，默认是100万次。
t2 = Timer('a,b = b,a', 'a=1; b=2').timeit(number=1000000)

print(t1, t2)


def list_append_test():
    a = []
    for i in range(10000):
        a.append(i)


def list_insert_test():
    a = []
    for i in range(10000):
        a.insert(0, i)


timer = Timer('list_append_test()', 'from __main__ import list_append_test')
print('list_append_test', timer.timeit(1000))   # list_append_test 3.3770746619998135

timer = Timer('list_insert_test()', 'from __main__ import list_insert_test')
print('list_insert_test', timer.timeit(1000))   # list_insert_test 177.45807983299983

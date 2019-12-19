from timeit import Timer
"""用于演示执行某段代码的性能情况，即统计执行时间"""
t1 = Timer('t=a; a=b; b=t', 'a=1; b=2').timeit()
t2 = Timer('a,b = b,a', 'a=1; b=2').timeit()

print(t1, t2)

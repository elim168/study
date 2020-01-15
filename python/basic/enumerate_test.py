a = [(x+1) * 10 for x in range(10)]
b = enumerate(a)

print(a)    # [10, 20, 30, 40, 50, 60, 70, 80, 90, 100]
print(b)    # <enumerate object at 0x7fa010dcae10>
# enumerate对象变为list后可以看到每个元素是一个元组，第一个值是索引，第二个值是原来的值
print(list(b))  # [(0, 10), (1, 20), (2, 30), (3, 40), (4, 50), (5, 60), (6, 70), (7, 80), (8, 90), (9, 100)]
# 也可以把一个range对象变为enumerate对象
print(list(enumerate(range(10, 20))))   # [(0, 10), (1, 11), (2, 12), (3, 13), (4, 14), (5, 15), (6, 16), (7, 17), (8, 18), (9, 19)]



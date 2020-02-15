# filter test
# filter函数用于对可迭代对象进行过滤，只筛选出满足条件的元素


a = list(range(1, 10))


def is_even(x):
    return x % 2 == 1


result = filter(is_even, a)
print(result)
print(list(result))  # [1, 3, 5, 7, 9]

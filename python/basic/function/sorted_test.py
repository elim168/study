# sorted test
# 排序函数测试
import functools


# 排序数字，按照正常的数字大小进行升序排列
a = sorted([1, 3, 9, 5, 2, 80, 72])
print(a)  # [1, 2, 3, 5, 9, 72, 80]


# 按照逆序排列
a = sorted([1, 3, 9, 5, 2, 80, 72], reverse=True)
print(a)  # [80, 72, 9, 5, 3, 2, 1]



# 通过key指定元素转换后的值进行排序，这里取每个字符串的长度进行排序。
a = sorted(['AAAAAA', 'BBB', 'CCCCC', 'DDDD', 'EE'], key=len)
print(a)  # ['EE', 'BBB', 'DDDD', 'CCCCC', 'AAAAAA']


def custom_compare(x, y):
    if len(x) > len(y):  # x放后面
        return 1
    elif len(x) < len(y):  # x放前面
        return -1
    else:
        return 0



# 使用自定义的函数进行排序，需要使用functools.cmp_to_key把自定义函数转换为key规则。
a = sorted(['AAAAAA', 'BBB', 'CCCCC', 'DDDD', 'EE', 'FFF'], key=functools.cmp_to_key(custom_compare))
print(a)  # ['EE', 'BBB', 'FFF', 'DDDD', 'CCCCC', 'AAAAAA']





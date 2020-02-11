# 归并排序
# 它的思想是把一个数组平分为两部分，再把每一份平分，直到不能平分了为止。然后开始合并，把分开的两部分按照顺序合并为一个数组，依次类推，最终得到整体的排序后的结果。

import random


def sort(lista) -> list:
    n = len(lista)
    if n == 1:  # 列表的长度为1说明已经是最小的维度了，不需要再细分了。
        return lista
    mid = n // 2  # 找出中间位置
    list_left = sort(lista[0:mid])  # 把左半部分排序
    list_right = sort(lista[mid:])  # 把右半部分排序
    left_index, right_index = 0, 0
    result = []  # 放有序的结果集
    left_len = len(list_left)
    right_len = len(list_right)
    while left_index < left_len and right_index < right_len:  # 分别从左半部分和右半部分的左边开始扫，依次把最小值附加到结果中
        if list_left[left_index] < list_right[right_index]:
            result.append(list_left[left_index])
            left_index += 1
        else:
            result.append(list_right[right_index])
            right_index += 1
    # 扫完后原来的列表中可能还剩余部分数字，需要把它们附加上。
    if left_index < left_len:
        result.extend(list_left[left_index:])
    if right_index < right_len:
        result.extend(list_right[right_index:])
    return result


def test():  # 测试函数
    a = list(range(120))
    for i in range(8):
        b = random.sample(a, 20)
        b = sort(b)
        print(b)


test()

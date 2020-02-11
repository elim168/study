# 快速排序
# 它的思想是取需要排序的数组中的第一个数作为基准，然后依次比较，把小于该基准数的都放左边，大于该基准数的都放右边。然后左右两边继续按照此方法进行，直到排序完成。

import random


def sort(lista, start, end): # 对列表中的某一段进行快排
    if start >= end: # 索引超出或相等时退出递归
        return
    low = start # 低位索引
    high = end # 高位索引
    mid = low # 基准数据的索引，初始值为地位索引
    while low < high:
        while low < high and lista[mid] > lista[low]: # 左边的数字比基准数小时将低位索引右移，直到低位索引所在的值比基准数大
            low += 1
        while low < high and lista[mid] < lista[high]: # 右边的数字比基准数大时将高位索引左移，直到高位索引所在的值比基准数小
            high -= 1
        lista[low], lista[high] = lista[high], lista[low] # 将高位索引的值与低位索引的值互换。这样可以继续进行比较。
    lista[mid], lista[low] = lista[low], lista[mid] # 上面的循环结束后需要把左边基准数据挪到低位索引，这样基准数据左边的数据都比基准数据小，右边的数据都比基准数据大。
    sort(lista, start, low-1) # 把基准数据左边的数据进行快排
    sort(lista, low+1, end) # 把基准数据右边的数据进行快排


def test(): # 测试函数
    a = list(range(120))
    for i in range(8):
        b = random.sample(a, 20)
        sort(b, 0, len(b)-1)
        print(b)


test()


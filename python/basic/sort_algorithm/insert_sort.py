# 插入排序
# 它的思想是把数组分成两部分，始终保持左边部分是有序的，然后每次拿右边部分的第一个跟左边的最后一个比较，比左边最后一个小，则换位置，再依次往前比，直到找到比它小的。
# [1   20, 3, 58, 22, 33, 87, 76, 65, 36, 54]
# [1, 20   3, 58, 22, 33, 87, 76, 65, 36, 54]
# [1, 3, 20   58, 22, 33, 87, 76, 65, 36, 54]
# [1, 3, 20, 58   22, 33, 87, 76, 65, 36, 54]
# [1, 3, 20, 22, 58   33, 87, 76, 65, 36, 54]


def sort(lista) :
    n = len(lista)
    for i in range(1, n): # 从第1个数字开始跟前面第0个数字比
        j = i
        while j > 0:
            if lista[j] < lista[j-1]: # 如果比前面的小则交换位置
                lista[j], lista[j-1] = lista[j-1], lista[j]
            else: # 推出循环，说明当前新加入的数字已经找到了它在左边应有的位置
                break
            j -= 1


a = [1, 20, 3, 58, 22, 33, 87, 76, 65, 36, 54]
sort(a)
print(a)

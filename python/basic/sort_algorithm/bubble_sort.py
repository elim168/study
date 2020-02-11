# 冒泡排序
# 它的思想是循环比较，第一次找出最大的数据放最后的位置，第二次在剩余的位置中找出最大的数字放最后的位置，依次类推


def sort(lista):
    n = len(lista)
    for i in range(n-1):
        change_flag = False
        for j in range(n-1-i):
            if lista[j] > lista[j+1]:
                lista[j], lista[j+1] = lista[j+1], lista[j]
                change_flag = True
        if not change_flag:
            break


a = [10, 20, 30, 22, 1, 86, 12, 33, 25, 67, 75]
sort(a)
print(a)

a = [1, 2, 3, 10, 30, 60, 62, 72, 75, 80]
sort(a)
print(a)

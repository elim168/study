# 选择排序
# 它的思想是第一次选择最小的数字与第一个位置的数字交换，第二次在剩余的数字中选择最小的与第二个位置交换，依次类推。
def sort(lista):
    n = len(lista)
    for i in range(n):
        min_index = i
        for j in range(i+1, n):
            if lista[min_index] > lista[j]:
                min_index = j
        if min_index != i:
            lista[i], lista[min_index] = lista[min_index], lista[i]


a = [1, 5, 8, 7, 2, 3, 10, 25, 14, 22]
sort(a)
print(a)

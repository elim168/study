'''
元组，其中的元素是不可变的，不能增/删/改
'''

a = (1, 2, 3)   #元组用小括号表示，跟列表一样，也可以认为是集合类
print(a)

#用来定义元素的小括号是可以省略的，所以下面的用法也是可以的。
a = 1, 2, 3
print(a)

#当元组只有一个元素时不能定义为a = (1)，这会把它当作数字1,必须在元素后加上逗号，如a = (1,)，或者省略小括号，为a = 1,
a = (1,)
print(a)

#省略括号
a = 1,
print(a)

#元组也可以通过tuple()把其它可迭代对象转变为一个元组
a = tuple('ABC')
print(a)

a = tuple(list(range(1, 10)))
print(a)

a = tuple(range(1, 10))
print(a)


## 可以通过索引访问元组中的内容

for i in range(len(a)):
    print(a[i], end = ' ')
print('=======================================')

#元组也是可以截取的，语法和list是一样的。
print(a[:5])    #取索引5以前的元素组成新的元组


#zip操作
a = ('A', 'B', 'C')
b = ('A', 'B', 'C')
c = ('A', 'B', 'C')
d = zip(a, b, c)    #把多个元组相同位置的元素组成一个新的元组，这样组成一个zip对象，zip通过list(zip)转换为list对象后，可以看到每个元素是元组相应位置的组合。
print(type(d))
print(list(d))  #[('A', 'A', 'A'), ('B', 'B', 'B'), ('C', 'C', 'C')]



####推导式

a = (x ** 2 for x in range(1, 10))  #元组的推导式用小括号
print(type(a))  #元组的推导式生成的是一个generator对象

print(tuple(a)) #通过tuple()可以把generator对象转换为元组
#输出的是(1, 4, 9, 16, 25, 36, 49, 64, 81)
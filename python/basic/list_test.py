####创建
#通过[]创建
a = [1, 2, 3]
a.append(4)
print(len(a))

#通过list()创建
a = list('123')
print(a)    #['1', '2', '3']

a = range(1, 100, 2)    #1,3,5,7,9,..,99
print(list(a))

a = list(range(1, 11))  #[1,2,3,4,5,6,7,8,9,10]
print(a)


#推倒式
a = [x**2 for x in range(1, 11)]    #1-10的平方组成的列表
print(a)

a = [x**2 for x in range(1, 100) if x%5==0] #1-99中能被5整除的数的平方组成的列表
print(a)

###判断元素是否存在

a = [1, 2, 3, 4, 5]
print(3 in a)   #True
print(30 not in a)   #True

###列表截取，类似于字符串截取

a = list(range(1, 100))
print(a[-10:])  #取最后的10个元素

#[31, 34, 37, 40, 43, 46, 49, 52, 55, 58, 61, 64, 67, 70, 73, 76, 79]
print(a[30:80:3])#取索引为30开始的数字，每3个索引取一次，直到索引80（不包含）

###排序
a.sort(reverse=True)
print(a) #倒序

a = sorted(a)   #新的对象，list.sort()是原对象
print(a)

a = reversed(a) #新的对象，倒序，返回的是一个迭代器对象
a = list(a)
print(a)
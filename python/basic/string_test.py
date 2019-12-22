a = 'AAA'
b = 'BBB'
c = a + b   #AAABBB
print(a, b, end='') #不换行打印，指定end，默认是换行符
print(c)

d = len(c)  #字符串长度
print(d)
e = str(123) + str(True)    #str()函数，用于把其它类型的对象转换为字符串
print(e)
for i in range(len(e)):
    print(e[i])

f = e[-1] + e[-2]   #等于eu，负索引是从右开始取，-1是最后一个字符。
print(f)

f = 'ABCDEFH'

print(f)
g = f.replace('H', 'G') #字符串替换
print(g)
print(g[1:4])   #输出索引1-4的字符，且不包含4,即输出BCD。
print(g[1:])    #输出索引1开始的所有字符，即输出BCDEFG
print(g[:4])    #输出索引4以前的所有字符，不包括4,即输出ABCD
print(g[-3:])   #输出倒数3个索引开始到最后的字符，即输出EFG。
print(g[1:5:2]) #最后一位是步长，默认是1，最后一位是2则表示每隔两位取一位数，即取BD
print(g[::-1])  #步长为负则表示反向获取，-1则是倒序。


a = 'A B C D E F G HIJK LM N'
print(a.split()) #默认按空格等分隔，['A', 'B', 'C', 'D', 'E', 'F', 'G', 'HIJK', 'LM', 'N']
print(a.split('H')) #['A B C D E F G ', 'IJK LM N']

a = ['A', 'B', 'C', 'D', 'E']
print(','.join(a)) #把数组中的每个元素用逗号拼接起来，成一个字符串

a = 'abcdefg'
print('abc' in a)   #True，abc是abcdefg的一个子字符串
print('abc' not in a) #False


a = 'abcdefg\
hijklmn'    #可以通过\换行，abcdefghijklmn
print(a)

print(a.startswith('abc'))
print(a.endswith('efg'))
print(a.find('def'))    #寻找def第一次出现的位置
print(a.rfind('def'))    #寻找def最后一次出现的位置
print(a.index('def'))   #寻找def第一次出现的位置
print(a.rindex('def'))  #寻找def最后一次出现的位置
print(a.count('def')) #统计字符串出现的位置

a = '''
三个引号包含的字符串可以包含多行，
可以随便换行
'''

print(a)

a = '    abcdEFG    '
print(a)
print(a.strip())    #去除首尾字符串
print(a.strip("abc"))   #去除首尾的abc
a.lstrip("abc") #去除左边的abc
a.lstrip(a) #去除左边的空格
a.rstrip("abc") #去除右边的abc
a.rstrip(a) #去除右边的空格

a = 'abcdeFGJLJSLJhsnsl'
print(a.capitalize())   #首字母大写

print('abc'.rjust(10, '*')) #左对齐，长度为10，少于的部分右侧填充*
print('abc'.isalpha())  #是否都由字母组成
print('123.4'.isdigit())  #False，是否都由数字组成abcdefghijklmn
print('123.4'.isnumeric())  #False，是否是数字形式

#########格式化
#参数可以用索引占位，也可以用参数名占位。
a = 'Key is {0}, Value is {1}'
print(a.format('key1', 'value1'))

a = 'Key is {key}, Value is {value}'
print(a.format(key = 'key1', value = 'value1'))

'''
填充与对齐

^、<、>分别是居中、左对齐、右对齐，后面带宽度
:号后面带填充的字符，只能是一个字符，不指定时为空格
'''

print('Key is {0:*^10}, Value is {1:*>10}'.format('key1', 'value1'))

#####################################
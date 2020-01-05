a = 10
if a > 5:
    print('a > 5, a =', a)

if 1 < a < 20:  #   相当于a > 1 and a < 20
    print('1 < a < 20')

if a :  #数字不为0在if表达式中也表示True，同样的还有非空集合/非空字符串/非None等
    print('数字不为0在if表达式中也表示True')



#### if-else
if a > 10:
    print('a > 10')
else:
    print('a <= 10')

#三元运算符，上面的逻辑相当于如下的写法
print('a > 10' if a > 10 else 'a <= 10')

#### if-elif-else
if a > 50:
    print('A')
elif a > 40:
    print('B')
elif a > 30:
    print('C')
else:
    print('D')
a = True or 30#返回为True，x or y，x为true时则返回x，否则返回y。
b = False or 30#返回30
c = 10 or 30#返回10，非False就是True。


d = True and 30#返回30,x and y，x为True时返回y，否则返回x。
e = False and 30#返回False
f = 10 and 30#返回30

print(a, b, c, d, e, f, '应该是True/30/10/30/False/30')


a, b = 3, 3.0
print(a == b, a is b, a is not b, 'should be: True/False/True')#is用于比较是否是同一个对象，==用于比较值是否相等



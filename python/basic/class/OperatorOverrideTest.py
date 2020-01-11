# 可以通过给Class增加操作符对应的处理方法，可以让Class对应的对象可以进行相关操作符运算，比如常见的加减乘除等。


class Person:
    def __init__(self, name):
        self.name = name

    def __add__(self, other):   # 对应的是+操作，其它的操作符也是类似的重写对应的方法
        if isinstance(other, Person):
            return '{0} ++++++++ {1}'.format(self.name, other.name)
        else:
            raise TypeError('不支持的操作类型')

    def __mul__(self, other):
        if isinstance(other, int):
            return self.name * other
        else:
            raise ValueError('乘数必须是整数')


p1 = Person('张三')
p2 = Person('李四')

s = p1 + p2 # 调用的是p1对象的__add__方法
print(s)

s = p1 * 10 # 调用的是p1的__mul__方法

print(s)


'''
里面列出的一些操作符相关的方法都是可以重写，让对象拥有对应的操作能力的。
['__add__', '__eq__', '__ge__', '__getattribute__', '__getitem__', '__getnewargs__', '__gt__', '__hash__', '__init__', '__init_subclass__', '__iter__', '__le__', '__len__', '__lt__', '__mod__', '__mul__', '__ne__', '__new__', '__reduce__', '__reduce_ex__', '__repr__', '__rmod__', '__rmul__', '__setattr__', '__sizeof__', '__str__', '__subclasshook__', 'capitalize', 'casefold', 'center', 'count', 'encode', 'endswith', 'expandtabs', 'find', 'format', 'format_map', 'index', 'isalnum', 'isalpha', 'isdecimal', 'isdigit', 'isidentifier', 'islower', 'isnumeric', 'isprintable', 'isspace', 'istitle', 'isupper', 'join', 'ljust', 'lower', 'lstrip', 'maketrans', 'partition', 'replace', 'rfind', 'rindex', 'rjust', 'rpartition', 'rsplit', 'rstrip', 'split', 'splitlines', 'startswith', 'strip', 'swapcase', 'title', 'translate', 'upper', 'zfill']
'''
print(dir(s))
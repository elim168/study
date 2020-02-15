class BaseClass:

    def baseFunc1(self):
        print('Base Func1')

    def baseFunc2(self):
        print('Base Func2')


class SubClass(BaseClass):
    def subFunc1(self):
        print('Sub Func1')

    def baseFunc2(self):
        print('Sub override baseFunc2')
        print('调用父类的方法------', BaseClass.baseFunc2(self))


sub = SubClass()
sub.baseFunc1()
sub.baseFunc2()
sub.subFunc1()

print('sub is instance BaseClass =', isinstance(sub, BaseClass))
print('sub is instance SubClass =', isinstance(sub, SubClass))

print('SubClass issubclass of BaseClass =', issubclass(sub.__class__, BaseClass))
print(sub.__class__)
print(type(sub))

print(SubClass.mro())  # 输出Class的继承结构  [<class '__main__.SubClass'>, <class '__main__.BaseClass'>, <class 'object'>]

obj = object()  # 新建一个object对象，o是小写的
print(obj)
print(dir(obj))  # dir(object)可以输出一个对象中所有的属性和方法


class Person:
    def __init__(self, name):
        self.name = name

    def hello(self):
        print('Hello {0}. Person'.format(self.name))


class Student(Person):
    def __init__(self, name, grade):
        Person.__init__(self, name)  # 调用父类的构造方法
        self.grade = grade

    def hello(self):  # 重写父类方法
        # Person.hello(self)  # 调用父类方法
        super().hello()  # 调用父类的方法
        print('Hello {0}. Student'.format(self.name))


s = Student('张三', '九年级')
print(s.name, s.grade)
s.hello()
print(s.__dict__)  # 输出一个对象的所有属性   {'name': '张三', 'grade': '九年级'}
print(s.__module__)

'''
多重继承
'''


class A:
    def a(self):
        print('A')

    def common(self):
        print('通用方法，来自A')


class B:
    def b(self):
        print('B')

    def common(self):
        print('通用方法，来自B')


class C(A, B):  # 多重继承，C的父类有A和B
    def c(self):
        print('C')


c = C()
c.a()
c.b()
c.c()
# 当多个父类中都有同一个方法时，会按父类声明的顺序寻找方法，定义在前面的优先级更高
c.common()  # 输出通用方法，来自A

print(C.__base__)  # 获取Class的父类，只会输出一个，多重继承时只输出第一个。<class '__main__.A'>
print(C.__bases__)  # 获取Class的所有父类，以元组的形式返回。(<class '__main__.A'>, <class '__main__.B'>)

print(A.__subclasses__())  # 输出Class的所有子类。[<class '__main__.C'>]

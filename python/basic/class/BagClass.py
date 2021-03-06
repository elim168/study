class Bag:
    static_prop1 = 10  # 静态属性，所有的对象共享，需要通过Class访问

    def __init__(self):
        self.data = []

    def add(self, x):
        self.data.append(x)

    def addTwice(self, x):
        self.add(x)
        self.add(x)

    def __str__(self):  # 重写对象的toString()方法。
        return "current data is: {0}".format(self.data)

    @classmethod
    def class_method1(cls):
        return '''
        Class级别的方法，必须通过Class调用，必须加上@classmethod，并且必须包含一个参数，表示Class对象，参数名通常定义为cls，该参数必须定义为第一个参数，
        调用的时候不需要传递该参数，其它参数可以接在cls参数之后。
        '''

    @staticmethod
    def static_method1():
        return '''
            静态方法，也是需要通过Class访问，需要加上@staticmethod标注，它和classmethod的区别就在于它不需要定义和传递Class参数，所以它通常用来定义一些
            与Class属性无关的操作。当然也可以直接在该方法内通过Class访问静态属性。
        '''

    def __del__(self):  # __del__方法会在对象被销毁时调用，一般不需要重写
        print('对象id={0}被销毁了---'.format(id(self)))

    def __call__(self, *args, **kwargs):
        print('__call__方法可以让对象像方法一样调用，调用obj()时即将调用对象的__call__()方法。该方法也可以定义需要接收的参数')


bag = Bag()
bag.add(1)
bag.add(10)
bag.addTwice(100)
print(bag.data)
print(bag)

bag.name = 'Hello'

print(bag.name)

print(dir(bag))  # dir(object)可以列出一个对象中的所有方法

print(bag.__dict__)  # 对象的__dict__用于获取对象中定义的所有属性及其值，返回的是字典。

b1 = Bag()
b1.static_prop1 = 1000

Bag.static_prop1 = 200
b2 = Bag()

print(b1.static_prop1)  # 1000
print(b2.static_prop1)  # 200
print(Bag.static_prop1)  # 200

print(Bag.class_method1())
print(Bag.static_method1())

bag()  # 调用对象的__call__()。

Bag.hello = lambda s: print('Hello -> {0}'.format(s))  # 给类增加新方法，如果原方法已经存在则会被覆盖。


def hello2(self):
    print('Hello 2 -> id = {0}'.format(id(self)))


Bag.hello2 = hello2

bag.hello()
bag.hello2()

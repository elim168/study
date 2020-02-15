class Person:
    # Class级别的私有属性，在Class内部可以通过Person.__static_private_prop调用，外部通过Person._Person__static_private_prop
    __static_private_prop = 1

    def __init__(self, name, age):
        self.name = name
        # 两个下划线开头的属性是私有属性，是不能直接被外部访问的。如果需要访问，需要以_{ClassName}__{私有属性}的形式访问。如这里的age属性可以通过对象的_Person__age访问
        self.__age = age

    def hello(self):
        print('Hello, name={0}, age={1}-------------{2}'.format(self.name, self.__age,
                                                                Person.__static_private_prop))  # 私有属性是可以在内部调用的

    def __private_method(self):
        print('我是私有方法，在内部可以随便调，在外部需要以_Person__private_method()调用')

    def call_private_method(self):
        self.__private_method()

    # 方法上加上了@property后可以直接通过把方法当作属性来访问，如obj.age访问的就是Person对象的age()，常用于私有属性的get方法上。
    @property
    def age(self):
        return self.__age


'''
    @age.getter
    def age(self):
        return self.__age

    @age.setter   # 定义这是age属性的setter方法
    def age(self, age):
        self.__age = age
'''

p = Person('张三', 30)
print(p.name)
# print(p.__age)    # 私有属性也不能这样访问
print(p._Person__age)  # 访问私有属性

p.hello()
p._Person__private_method()
p.call_private_method()

print(Person._Person__static_private_prop)

print(p.age)
p.age = 35
print(p.age)

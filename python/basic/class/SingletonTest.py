class Singleton:
    __obj = None
    __init_flag = True
    def __new__(cls, *args, **kwargs):
        if not cls.__obj:
            cls.__obj = object.__new__(cls)
        return cls.__obj

    def __init__(self):
        if Singleton.__init_flag:   # 没有初始化的时候初始化一次，之后就不再初始化了。
            Singleton.__init_flag = False
            pass

obj1 = Singleton()
obj2 = Singleton()

print(obj1)
print(obj2)

class Singleton2:
    instance = None

    @staticmethod
    def get_instance():
        if not Singleton2.instance:
            Singleton2.instance = Singleton2()
        return Singleton2.instance


print(Singleton2.get_instance())
print(Singleton2.get_instance())
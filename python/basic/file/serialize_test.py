# 序列化和反序列化使用pickle模块

import pickle


class Person:
    def __init__(self, name, age, sex):
        self.name = name
        self.age = age
        self.sex = sex

    def __str__(self):
        return 'name={0}, age={1}, sex={2}'.format(self.name, self.age, self.sex)


p1 = Person('张三', 30, '男')
p2 = Person('李四', 32, '男')
p3 = Person('王五', 35, '男')
with open('serialize_file.dat', 'wb') as f: # 序列化文件要以字节形式读写
    pickle.dump(p1, f)    # 把对象序列化到文件serialize_file.dat中
    pickle.dump(p2, f)    # 把对象序列化到文件serialize_file.dat中
    pickle.dump(p3, f)    # 把对象序列化到文件serialize_file.dat中

with open('serialize_file.dat', 'rb') as f: # 反序列化的顺序要和序列化的顺序保持一致
    _p1 = pickle.load(f)
    _p2 = pickle.load(f)
    _p3 = pickle.load(f)
    print('反序列化后的对象是：')
    print(_p1)
    print(_p2)
    print(_p3)





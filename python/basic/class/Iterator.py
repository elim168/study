class Reverse:
    """倒序遍历一个字符串"""
    def __init__(self, data):
        self.data = data
        self.index = len(data)

    def __iter__(self):
        return self

    def __next__(self):
        if self.index == 0:
            raise StopIteration
        self.index = self.index - 1
        return self.data[self.index]

s = 'Hello Python'
print('准备倒序字符串-----' + s)
rev = Reverse(s)
print('倒序后的结果是：')
for a in rev:
    print(a)

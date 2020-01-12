class MyException(Exception):
    def __init__(self):
        Exception.__init__(self)

    def __str__(self):
        return '自定义的异常'

if __name__ == '__main__':
    while True:
        try:
            s = input('请输入一个数字，输入100退出，否则抛出MyException')
            n = int(s)
            if n == 100:
                print('输入了100, 程序退出')
                break
            else:
                print('输入的是{0}'.format(n))
                raise MyException() # 手动抛出异常
        except Exception as e:
            print('抛出了一个异常：', e)


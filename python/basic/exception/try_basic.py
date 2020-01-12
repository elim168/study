# 试验基本的try-except结构

while True:
    try:
        s = input('请输入一个数字!输入100结束：')
        num = int(s)
        if num == 100:
            print('输入了100,程序结束！')
            break
        else:
            print('输入的数字是{0}'.format(num))
        result = 100 / num
    except ZeroDivisionError:
        print('捕获了0作为除数的异常')
    except Exception as e:  # 捕获了一个异常
        print('输入数字异常：', e, e.__class__)
    else:  # try语句块没有抛出异常时将执行else语句
        print('Try语句块没有抛出异常，100/{0}={1}'.format(num, result))
    finally:
        print('最终执行的内容，不管try语句块是否发生异常')
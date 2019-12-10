# -*- coding: UTF8 -*-
def test1():#主动抛出一个异常
    try:
        raise ValueError
        print('Success')
    except ValueError:
        print('发生了ValueError')
        #raise#加上raise可以把该异常重新原封不动的抛出

def test2(flag=True):#通过flag控制是否抛出异常，默认是True
    try:
        if flag:
            raise ValueError('Exception Test')
        else:
            print('不抛出异常')
    except ValueError:#捕获ValueError
        print('捕获了ValueError异常')
    else:#try语句没有抛出异常时执行
        print('Try语句没有抛出异常')


def test3():
    raise InputError('表达式', '消息')

def test4():
    try:
        raise Error('hello')
    finally:
        print('finally语句包含所有情形都必须执行的内容')


def divide(x, y):
    try:
        result = x / y
    except ZeroDivisionError:
        print('division by zero!')
    else:
        print('result is', result)
    finally:
        print('executing finally clause')


class Error(Exception):
    """定义异常类Error，它是Exception的子类"""
    pass

class InputError(Error):
    """Exception raised for errors in the input.
    Attributes:
        expression -- input expression in which the error occurred
        message -- explanation of the error
    """

    def __init__(self, expression, message):
        self.expression = expression
        self.message = message



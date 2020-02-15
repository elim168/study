# 测试闭包，
# 1.闭包是函数里面又有内部函数
# 2.内部函数可以使用外部函数的变量
# 3.外部函数的返回值是内部函数


def func_out(base):  # 外部函数

    def func_in(num):  # 内部函数
        # 求指定数字的阶乘，再乘以base的结果
        result = base
        for i in range(1, num + 1):
            result *= i
        return result

    return func_in  # 外部函数的返回值是内部函数


f = func_out(10)
result = f(5)
print(result)

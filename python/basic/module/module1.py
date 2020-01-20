"""
测试模块，这是作为一个单独的模块存在的
"""


def add(p1, p2):
    """计算两个数的和"""
    return p1 + p2


def mul(p1, p2):
    """计算两个数的乘"""
    return p1 * p2


if __name__ == '__main__':  # 单独作为主函数运行
    print(add(10, 20))
    print(mul(10, 20))
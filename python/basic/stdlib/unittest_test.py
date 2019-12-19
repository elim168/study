def average(values):
    """Computes the arithmetic mean of a list of numbers.

    >>> print(average([20, 30, 70]))
    40.0
    """
    return sum(values) / len(values)

import unittest

#创建一个Class，继承自unittest.TestCase类，里面定义了一个单元测试方法test_average
class TestStatisticalFunctions(unittest.TestCase):

    def test_average(self):
        self.assertEqual(average([20, 30, 70]), 40.0)
        self.assertEqual(round(average([1, 5, 7]), 1), 4.3)
        with self.assertRaises(ZeroDivisionError):#测试运行下面的代码会抛出ZeroDivisionError
            average([])
        with self.assertRaises(TypeError):
            average(20, 30, 70)

r = unittest.main() #运行所有的单元测试

print(r)


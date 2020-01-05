#测试浅拷贝和深拷贝

import copy


def test_copy():
    print('测试浅拷贝：')
    a = [1, 2, 3, 4, [5, 6]]
    b = copy.copy(a)
    b.append(7)
    b[4].append(8)
    print('a=', a)
    print('b=', b)


test_copy()


def test_deepcopy():
    print('测试深拷贝：')
    a = [1, 2, 3, 4, [5, 6]]
    b = copy.deepcopy(a)
    b.append(7)
    b[4].append(8)
    print('a=', a)
    print('b=', b)

test_deepcopy()
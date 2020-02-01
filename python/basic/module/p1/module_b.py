from . import module_a  # 引入同包下面的模块module_a

# 如果需要引入兄弟包下面的模块，则可以是from ..p2 import
# 顶级包是不能使用相对引入的，相对引入请参见test2.py和p1.p101.module_1.py
# from ..p2 import module_a_p2
#
#

def b_add(a, b):
    r = module_a.p1_add(a, b)
    print('调用b_add，{0} + {1} = {2}'.format(a, b, r))
    return r
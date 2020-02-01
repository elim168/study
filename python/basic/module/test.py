import module1
import importlib # 通过importlib可以实现动态的模块导入
print(module1.add(10, 50))



m = importlib.import_module('math') # 导入math模块
print(m.pi)
# importlib.reload('math') # 重新加载一个模块


import p1.module_a as module_aa

a = module_aa.p1_add(10, 30)
print(a)

import p1

a = p1.module_a.p1_add(10, 30)
print(a)


from p1 import *    # 导入p1包下的所有模块（所有模块定义在p1下面的__init__.py文件的__all__变量中）

module_b.b_add(112, 336)    # module_b就是通过import * 导入进来的
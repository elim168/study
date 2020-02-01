# package下的__init__.py文件用来作为package的标识，不能删除
# 单纯的import一个package时会执行该package下的__init__.py文件，所以可以在该文件中做一些批量操作。

print('导入了package p1')

__all__ = ['module_b']  # 定义当通过from package import *导入整个包时导入的模块
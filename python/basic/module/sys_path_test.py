import sys

print(sys.path)
print('\n'.join(sys.path))

print('*****分界线*****' * 10)
sys.path.append('/home/elim/abc')   # 附加一个寻找模块的路径，import模块时将从sys.path指定的路径中寻找模块，优先内置的模块
print('\n'.join(sys.path))

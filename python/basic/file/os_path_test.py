# os.path拥有一些操作路径的相关方法

import os

print(os.path.isdir('/home/elim'))
print(os.path.isfile('/home/elim'))
print(os.path.isabs('/home/elim'))  # 是否绝对路径
print(os.path.islink('/home/elim'))
print(os.path.exists('/home/elim'))

print(os.path.getsize('os_path_test.py')) # 获取文件大小
print(os.path.getatime('os_path_test.py')) # 获取文件的最后访问时间
print(os.path.getctime('os_path_test.py')) # 创建时间
print(os.path.getmtime('os_path_test.py')) # 最后修改时间

print(os.path.abspath('os_path_test.py'))   # 获取文件的绝对路径
abs_path = os.path.abspath('os_path_test.py')
print(os.path.split(abs_path))    # 把一个路径分隔成目录和文件名(子目录）两部分
print(os.path.splitext('os_path_test.py'))   # 分割成文件名和后缀名 ('os_path_test', '.py')

print(os.path.join('a', 'b', 'c'))  # a/b/c


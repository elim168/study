# 测试os模块，文件的基本操作，如创建目录，更改当前目录等都是通过os模块操作的

import os

os.system('ping -c 2 www.baidu.com')    # 运行系统指令
current_dir = os.getcwd() # 获取当前目录的路径
print(dir())
os.chdir('/home/elim')  # 改变当前工作的目录
os.mkdir('abc') # 创建目录
os.rmdir('abc') # 删除目录
os.makedirs('a/b/c/d/e/f/g')  # 创建多级目录
os.removedirs('a/b/c/d/e/f/g')    # 删除多级目录只能删除非空目录

print(os.getcwd(), os.curdir, os.pardir)

os.listdir(os.curdir)   # 列出目录下面的子目录/文件信息

os.chdir(current_dir)






print(os.name) # windows返回nt，linux和unix返回posix
print(os.sep)   # 分隔符，windows -> \   linux -> /
# repr用于展示字符串中的隐藏字符
print(repr(os.linesep)) # 换行符，windows -> \r\n   linux->\n

print(os.stat('os_test.py'))    # os.stat_result(st_mode=33204, st_ino=1971348, st_dev=2054, st_nlink=1, st_uid=1000, st_gid=1000, st_size=685, st_atime=1579321901, st_mtime=1579321900, st_ctime=1579321900)



# os.walk()测试，用来循环获取一个目录下的所有子目录及其子文件

files = os.walk(os.path.abspath(os.path.pardir))
for dirpath, dirnames, filenames in files:
    '''
    dirpath是每一层级的目录，dirnames是该层级下的所有子目录名称，filenames是该层级下的所有子文件名称
    '''
    for dir in dirnames:
        print('子目录', dir) # 输出的都只是相对路径，要绝对路径可以加上dirpath
        print('子目录-绝对路径', os.path.join(dirpath, dir))
    for file in filenames:
        print('子文件', file)
        print('子文件-绝对路径', os.path.join(dirpath, file))




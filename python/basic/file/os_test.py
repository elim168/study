# 测试os模块

import os

os.system('ping -c 2 www.baidu.com')
os.chdir('/home/elim')
print(os.curdir, os.pardir, os.listdir(os.curdir))
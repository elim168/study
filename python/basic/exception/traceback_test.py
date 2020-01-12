# traceback模块用于输出异常的详细信息（错误发生的位置等）

import traceback

a = 10
b = 0

try:
    a/b
except:
    traceback.print_exc()   # 输出异常的详细信息
    with open('/tmp/py/error.txt', 'w') as f:
        traceback.print_exc(file=f) # 输出错误信息到文件中
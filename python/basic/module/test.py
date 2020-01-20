import module1
import importlib # 通过importlib可以实现动态的模块导入
print(module1.add(10, 50))



m = importlib.import_module('math') # 导入math模块
print(m.pi)
# importlib.reload('math') # 重新加载一个模块
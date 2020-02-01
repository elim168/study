# 该文件试验模块发布和安装后的结果，参见publish_test文件夹，
# 包mymodule下面的module1和module2已经安装到了本地的site-packages了。

import mymodule.module1 as m1
import mymodule.module2 as m2

m1.hello_module1()
m2.hello_module2()


import matplotlib.pyplot as plt
import numpy

x = numpy.random.randn(1000)
# 绘制直方图，通过bins可以控制柱子的宽度，bins越大，柱子越窄。不指定时柱子很宽，会存在覆盖现象
plt.hist(x, bins=200)
# 可以同时绘制多个柱状图
plt.hist(numpy.random.randn(1000), bins=200)
plt.show()
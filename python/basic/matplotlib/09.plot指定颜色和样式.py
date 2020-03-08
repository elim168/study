import matplotlib.pyplot as plt
import numpy

x = numpy.linspace(0, 20, 20)

plt.plot(x, x, 'g')  # 绿色
plt.plot(x, x + 1, 'r')  # 红色
plt.plot(x, x + 2, 'b')  # 蓝色
# 默认是画的实线，--会画虚线
plt.plot(x, x + 3, '--r')  # 红色
plt.plot(x, x + 4, ':k')  # 实心点，黑色
plt.plot(x, x + 5, '.c')  # 大一点的实心点，浅蓝色
plt.plot(x, x + 6, '-.y')  # -.，黄色
plt.plot(x, x + 7, '*y')  # *号，黄色
plt.show()

import matplotlib.pyplot as plt
import numpy

x = numpy.linspace(0, 20, 20)

# label用来定义图例的内容
plt.plot(x, x, 'g', label='legend1')  # 绿色
plt.plot(x, x + 1, 'r', label='legend2')  # 红色
plt.plot(x, x + 2, 'b', label='legend3')  # 蓝色
# 默认是画的实线，--会画虚线
plt.plot(x, x + 3, '--r', label='legend4')  # 红色
plt.plot(x, x + 4, ':k', label='legend5')  # 实心点，黑色
plt.plot(x, x + 5, '.c', label='legend6')  # 大一点的实心点，浅蓝色
plt.plot(x, x + 6, '-.y', label='legend7')  # -.，黄色
plt.plot(x, x + 7, '*y', label='legend8')  # *号，黄色

# 展示图例。loc定义图例位置，默认展示的位置是左上角，即upper left，下面的lower right表示左下角
plt.legend(loc='lower right')

plt.show()

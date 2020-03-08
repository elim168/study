import matplotlib.pyplot as plt
import numpy

x = numpy.linspace(0, 20, 100)
# 把画布分为两行两列，下面将在第1个分区画图
plt.subplot(2, 2, 1)
# 将把sin曲线画到第一个分区。
plt.plot(x, numpy.sin(x))


# 把画布分为两行两列，下面将在第4个分区画图
plt.subplot(2, 2, 4)
# 将把cos曲线画到第一个分区。
plt.plot(x, numpy.cos(x))

# 设置X坐标轴的区间，下面表示只展示1-10的区间
plt.xlim(1, 10)
# plt.ylim()

plt.show()


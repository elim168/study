import matplotlib.pyplot as plt
import numpy


x = numpy.linspace(-10, 10, 100)
y = numpy.linspace(-10, 10, 100)
X, Y = numpy.meshgrid(x, y)

Z = numpy.sqrt(X**2 + Y**2)
plt.contour(X, Y, Z)
# 也可以调用下面这个方法，下面的方法会填充
# plt.contourf(X, Y, Z)
plt.show()

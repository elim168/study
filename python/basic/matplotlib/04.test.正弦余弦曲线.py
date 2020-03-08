import numpy
import matplotlib.pyplot as plt

x = numpy.linspace(0, 30, 100)
sin_y = numpy.sin(x)
plt.plot(x, sin_y)

cos_y = numpy.cos(x)
plt.plot(x, cos_y)
plt.savefig('sin_cos_result')   # 保存图片，默认是png格式
plt.savefig('sin_cos_result.jpg')   # 保存图片，并指定保存的格式是jpg
plt.show()
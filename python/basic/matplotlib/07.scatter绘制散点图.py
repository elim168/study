import matplotlib.pyplot as plt
import random

# 从0-100中随机取20个数
x = random.sample(range(100), 20)
y = random.sample(range(100), 20)
print(x, y)

# 绘制散点图
plt.scatter(x, y)

# 散点图也可以通过plot画，需要多加一个参数o。
# plt.plot(x, y, 'o')
plt.show()

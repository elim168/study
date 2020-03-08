import matplotlib.pyplot as plt
import random

# 从0-100中随机取20个数
x = random.sample(range(100), 20)
y = random.sample(range(100), 20)
# print(x, y)

# 指定颜色值，颜色值的个数必须和点的个数一致
colors = random.sample(range(1000), 20)
# 指定点的大小
sizes = random.sample(range(100), 20)
plt.scatter(x, y, c=colors, s=sizes)

plt.show()

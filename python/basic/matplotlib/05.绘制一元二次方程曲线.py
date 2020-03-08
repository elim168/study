import matplotlib.pyplot as plt

x = list(range(-100, 100))
y = [2 * i ** 2 - 100 for i in x]
plt.plot(x, y)
plt.show()

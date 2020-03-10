import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D

X = [1, 6, 8, 12, 10]
Y = [3, 10, 5, 13, 50]
Z = [5, 168, 18, 90, 10]

figure = plt.figure()
d = Axes3D(figure)
d.plot_trisurf(X, Y, Z)
plt.show()

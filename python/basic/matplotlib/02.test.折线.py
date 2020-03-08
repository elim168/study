# 安装matplotlib库。pip3 install matplotlib
# ubuntu18.10上图形显示不出来，运行sudo apt install python3-tk安装python3-tk
import matplotlib.pyplot as plt

# 第一个参数是所有点的X坐标，第二个参数是所有点的Y坐标
# 两个点画的是直线
x = [1, 2, 3, 4, 5, 6]
y = [10, 20, 15, 30, 12, 18]
print(y)
plt.plot(x, y)
plt.show()
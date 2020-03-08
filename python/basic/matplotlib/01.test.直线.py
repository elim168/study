# 安装matplotlib库。pip3 install matplotlib
# ubuntu18.10上图形显示不出来，运行sudo apt install python3-tk安装python3-tk
import matplotlib.pyplot as plt

# [x1, x2], [y1, y2]
# 两个点画的是直线
plt.plot([1, 20], [5,45])
plt.show()
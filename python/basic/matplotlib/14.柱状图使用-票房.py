import matplotlib.pyplot as plt
import numpy

movies = ['玩具总动员', '汽车总动员', '机器人总动员', '美食总动员']
movie1_sells = [6231, 8990, 10200]
movie2_sells = [2563, 6509, 9000]
movie3_sells = [5600, 6980, 7520]
movie4_sells = [7000, 8200, 5200]
x = numpy.arange(len(movie1_sells))
width = 0.2
plt.bar(x, movie1_sells, width=width, alpha=0.5, label=movies[0])
plt.bar(x + width, movie2_sells, width=width, alpha=0.5, label=movies[1])
plt.bar(x + 2 * width, movie3_sells, width=width, alpha=0.5, label=movies[2])
plt.bar(x + 3 * width, movie4_sells, width=width, alpha=0.5, label=movies[3])
# 指定字体为支持中文的字体
plt.rcParams['font.sans-serif'] = ['AR PL UMing CN']
plt.xlabel('日期')
plt.ylabel('票房（万元）')

# 指定X轴每个坐标的名称
plt.xticks(x+1.5*width, ['第{}天'.format(i + 1) for i in x])
# 展示图例
plt.legend()
plt.show()

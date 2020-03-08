import matplotlib.pyplot as plt

x = [2000, 2005, 2010, 2015, 2020]
y = [3000, 4000, 5000, 5500, 6000]
plt.bar(x, y, width=2.5)  # width用来指定柱子的宽度是原来宽度的倍数，可以是小数
# 指定字体为支持中文的字体
plt.rcParams['font.sans-serif']=['AR PL UMing CN']
plt.xlabel('年份')
plt.ylabel('利润')

# 指定X轴每个坐标的名称
plt.xticks(x, ['2000年', '2005年', '2010年', '2015年', '2020年'])
plt.show()
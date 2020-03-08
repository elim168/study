import matplotlib.pyplot as plt

y = [2000, 2005, 2010, 2015, 2020]
x = [3000, 4000, 5000, 5500, 6000]
# 水平柱状图第1个参数是Y，第2个参数是X
plt.barh(y, x, height=2)  # height用来指定柱子的宽度是原来宽度的倍数，可以是小数
# 指定字体为支持中文的字体
plt.rcParams['font.sans-serif']=['AR PL UMing CN']
plt.ylabel('年份')
plt.xlabel('利润')
# 画一根线
plt.axhline(2001, color='red')
# plt.axvline(2001, color='red')

# 指定X轴每个坐标的名称
plt.yticks(y, ['2000年', '2005年', '2010年', '2015年', '2020年'])
plt.show()
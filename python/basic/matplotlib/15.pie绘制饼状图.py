import matplotlib.pyplot as plt


movie1 = 18000
movie2 = 38000
movie3 = 88000

sum = movie1 + movie2 + movie3
movie1_percent = movie1/sum
movie2_percent = movie2/sum
movie3_percent = movie3/sum

movie_names = ['汽车总动员', '玩具总动员', '机器人总动员']
colors = ['red', 'green', 'blue']
# 指定字体为支持中文的字体
plt.rcParams['font.sans-serif'] = ['AR PL UMing CN']
# autopct显示百分比，explode可以让图形分裂
patches, texts, pct_texts = plt.pie([movie1_percent, movie2_percent, movie3_percent], labels=movie_names, autopct='%0.2f%%', colors=colors, explode=(0, 0.03, 0.03))
# 设置百分比字体的颜色
for text in pct_texts:
    text.set_color('white')
# plt.legend(loc='lower center')
plt.legend(loc='best')
plt.show()
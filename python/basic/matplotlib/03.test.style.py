# 安装matplotlib库。pip3 install matplotlib
# ubuntu18.10上图形显示不出来，运行sudo apt install python3-tk安装python3-tk


from matplotlib.font_manager import FontManager
import subprocess

fm = FontManager()
mat_fonts = {f.name for f in fm.ttflist}

output = subprocess.check_output(
    'fc-list :lang=zh -f "%{family}\n"', shell=True)
# print '*' * 10, '系统可用的中文字体', '*' * 10
# print output

print(str(output).split(r'\n'))
zh_fonts = {f.split(',', 1)[0] for f in str(output).split(r'\n')}
print(zh_fonts)
available = mat_fonts & zh_fonts

print('*' * 10, '可用的字体', '*' * 10)
for f in available:
    print(f)

import matplotlib.pyplot as plt

# 第一个参数是所有点的X坐标，第二个参数是所有点的Y坐标
# 两个点画的是直线
x = [1, 2, 3, 4, 5, 6]
y = [10, 20, 15, 30, 12, 18]
print(y)
# linewidth设置线条宽度
plt.plot(x, y, linewidth=3)
# 支持中文的字符
plt.rcParams['font.sans-serif']=['AR PL UMing CN']
plt.xlabel('设置X轴名称')
plt.ylabel('设置Y轴名称')
plt.title('设置图表标题')
plt.show()
from PIL import Image, ImageDraw, ImageFont

# 创建一个图片
image = Image.new('RGB', (500, 300), 'green')
# 获取该图片的画笔，之后都通过该画笔进行绘画
draw = ImageDraw.Draw(image)

# 画一个矩形，起点是(20, 20)，终点是(480, 280)。
draw.rectangle((20, 20, 480, 280), fill='red', outline='blue')
draw.text((50, 50), 'Hello World!', fill='blue')
# 写的字如果是中文需要指定字体
draw.text((50, 100), '你好，世界!', font=ImageFont.truetype('NotoSansCJK-Regular.ttc', size=16))

# 画一个起点是(150, 50)，终点是(350, 250)，以这条直线为直径画圆弧，圆弧的起点是0度，终点是360度，即画的是一个圆
draw.arc((150, 50, 350, 250), 0, 360, fill='yellow')

image.show()

# 也可以在一张已有的图片之上进行绘图
image = Image.open('flower_01.jpg')
w, h = image.size
draw = ImageDraw.Draw(image)
draw.line((0, h / 2, w, h / 2), fill=(123, 200, 102), width=5)
image.show()

# 绘制九宫格
image = Image.new('RGB', (300, 300))
draw = ImageDraw.Draw(image)


def get_color(x, y):
    n = x // 100 + y // 100
    colors = ['red', 'yellow', 'green', 'blue', 'orange']
    return colors[n]


for x in range(300):
    for y in range(300):
        draw.point((x, y), fill=get_color(x, y))

image.show()

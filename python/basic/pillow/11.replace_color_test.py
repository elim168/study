# 下面的代码可以把图片中的黄色都变成红色
from PIL import Image, ImageDraw

image = Image.open('flower_01.jpg')
draw = ImageDraw.Draw(image)
w, h = image.size
for x in range(w):
    for y in range(h):
        color = image.getpixel((x, y))  # 获取图片上每个点的像素值，一个像素由RGB三种颜色组成
        if color[0] > 60 and color[1] > 60:  # 红色和绿色都大于60时就为黄色，此时把绿色变为0,就为红色
            color = (color[0], 0, color[2])
            draw.point((x, y), fill=color)  # 替换对应点的颜色为红色
image.show()

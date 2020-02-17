# 通过Image.composite()可以将两张图片混合起来，然后再加上一层遮罩。它要求两张图片的大小和遮罩图片都必须一致。

from PIL import Image

image1 = Image.open('flower_01.jpg')
# 把图片2重置为和图片一样大
image2 = Image.open('flower_02.jpg').resize(image1.size)
# 将image2切分为红绿蓝三个单色的图片，可以用其中之一作为遮罩层图片
r, g, b = image2.split()
# r.show()
# g.show()
b.show()
# image3 = Image.composite(image1, image2, image1.convert('L'))
# 下面用的是图片2的红色图作为遮罩层图片。也可以选择其它图片作为遮罩层图片，但它的大小必须和图片1一致，且模式必须为1、L和RGBA中的一种，其它图片可以通过convert进行模式转换。
image3 = Image.composite(image1, image2, r)
# 展示合成的图片
image3.show()

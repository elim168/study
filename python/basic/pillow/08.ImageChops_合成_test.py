# 测试图片的合成
from PIL import Image, ImageChops

image1 = Image.open('flower_01.jpg')
image1.thumbnail((300, 200))
image2 = Image.open('flower_02.jpg').resize(image1.size)
w, h = image1.size
out_image = Image.new('RGB', (w * 3, h))
out_image.paste(image1, (0, 0))
out_image.paste(image2, (w, 0))


def show_image(imageobj):
    out_image.paste(image, (2 * w, 0))
    out_image.show()


# 将两个图片的像素累加
image = ImageChops.add(image1, image2)
# show_image(image)


# 将两图片的像素相减
image = ImageChops.subtract(image1, image2)
# show_image(image)


# 取两图片中像素较小的像素
image = ImageChops.darker(image1, image2)
# show_image(image)


# 取两图片中像素较大的像素
image = ImageChops.lighter(image1, image2)
# show_image(image)


# 取两者的像素相乘
image = ImageChops.multiply(image1, image2)
# show_image(image)


# 把两者的像素都投影到一个屏幕的效果
image = ImageChops.screen(image1, image2)
# show_image(image)


# 像素取反，255-像素
image = ImageChops.invert(image1)
# show_image(image)

# 取两者的像素差异的绝对值的效果，完全一样的图片的差异结果就是黑色
image = ImageChops.difference(image1, image2)
show_image(image)

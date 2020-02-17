# 通过Image.blend()可以将两张图片混合起来。它要求两张图片的大小必须一致。

from PIL import Image

image1 = Image.open('flower_01.jpg')
# 使用图片1的尺寸创建图片2,颜色为绿色。颜色那里也可以是元组表示RGB。
image2 = Image.new('RGB', image1.size, 'green')
image2.show()
# 将两张图片混合起来，最后一个参数是透明度，取值为0-1,它的值越大则表明image2的占比越高。image1*(1-alpha)+image2*alpha。透明度为0则完全为image1,透明度为1则完全为image2。
image3 = Image.blend(image1, image2, 0.6)
# 展示合成的图片
image3.show()

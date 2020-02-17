# 测试图片的旋转

from PIL import Image

image = Image.open('flower_01.jpg')
# 逆时针旋转45度。
image2 = image.rotate(45)
image2.show()

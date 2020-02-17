from PIL import Image

image = Image.open('flower_01.jpg')
# 上下互换
# image2 = image.transpose(Image.FLIP_TOP_BOTTOM)
# 左右互换
image2 = image.transpose(Image.FLIP_LEFT_RIGHT)
# 逆时针旋转90度
image2 = image.transpose(Image.ROTATE_90)
image2 = image.transpose(Image.ROTATE_180)
image2 = image.transpose(Image.ROTATE_270)
# 左右互换再逆时针旋转90度
image2 = image.transpose(Image.TRANSPOSE)
# 左右互换再逆时针旋转270度。
image2 = image.transpose(Image.TRANSVERSE)
image2.show()

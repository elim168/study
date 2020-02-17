# 从PIL包中引入Image模块
from PIL import Image

# 打开一张图片，返回一个Image对象
image = Image.open('flower_01.jpg')

# 输出图片的格式
print(image.format)  # JPEG
# 输出图片的尺寸，包括宽和高
print(image.size)  # (500, 375)
# 输出图片的高度
print(image.height)  # 375
# 输出图片的宽度
print(image.width)  # 500
# 展示图片
image.show()

# 测试图片的大小调整/缩略图/裁剪/复制/粘贴等功能
from PIL import Image

image1 = Image.open('c.png')
# 复制image1
image2 = image1.copy()
print(image1.size, image2.size)  # (500, 375) (500, 375)

# 调整大小为宽200,高300
image3 = image2.resize((250, 200))
image3.show()
image3.save('c_c.png')

# 缩略图。把一张图调整为缩略图将按照等比例缩小，缩小的是当前图片，不生成新图片。指定了宽和高后将计算哪个缩放的比例更大则按照那个更大的比例进行缩放。
# 比如下面指定了缩略图的宽高为(200, 300)，实际缩放出来是(200, 150)，因为宽的缩放比例更大。
image4 = image2.copy()
image4.thumbnail((200, 300))
image4.show()
print(image4.size)  # (200, 150)


# 调整像素。eval用于把一个图片的每个像素都应用指定的函数。下面的将每个像素都放大两倍。这样出来的图片将更亮。
image5 = Image.eval(image2, lambda x: x * 2)
image5.show()


# 裁剪图片。指定的参数分别是x1,y1,x2,y2，以左上角为0,0。
image6 = image2.crop((50, 50, 300, 150))
image6.show()
print(image6.size)


# 图片粘贴。下面将图片image6粘贴到image2的(50, 100)处。
image2.paste(image6, (50, 100))
image2.show()

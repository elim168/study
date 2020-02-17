# 测试调整图片的色彩/亮度/清晰度/对比度，它们分别对应于ImageEnhance中的不同class，它们的入参都是一张图片，都有一个enhance方法，该方法的参数调整值在0-2之间取值，1表示原图，大于1表示增强，小于1表示减弱。下面的示例中分别对一张图片进行了增强和减弱，然后把它们放到一起进行输出。
from PIL import Image, ImageEnhance

image1 = Image.open('flower_01.jpg')
image1.thumbnail((450, 300))
w, h = image1.size
out_image = Image.new('RGB', (w * 3, h))
out_image.paste(image1, (0, 0))


def enhance(enhance_func):
    enhance_obj = enhance_func(image1)
    enhanced_image = enhance_obj.enhance(0.5)
    out_image.paste(enhanced_image, (w, 0))
    enhanced_image = enhance_obj.enhance(1.5)
    out_image.paste(enhanced_image, (2 * w, 0))
    out_image.show()


# 调整亮度
# enhance(ImageEnhance.Brightness)
# 调整色彩
# enhance(ImageEnhance.Color)
# 调整对比度
# enhance(ImageEnhance.Contrast)
# 调整清晰度
enhance(ImageEnhance.Sharpness)




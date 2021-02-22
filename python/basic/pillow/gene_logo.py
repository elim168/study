from PIL import Image, ImageDraw, ImageFont

# 创建一个图片
image = Image.new('RGB', (500, 300), 'green')
# 获取该图片的画笔，之后都通过该画笔进行绘画
draw = ImageDraw.Draw(image)
# 写的字如果是中文需要指定字体
# draw.text((20, 85), '更上一层楼A', font=ImageFont.truetype('NotoSansCJK-Regular.ttc', size=80))
draw.text((20, 85), '2021年2月20日', font=ImageFont.truetype('NotoSansCJK-Regular.ttc', size=65))

image1 = Image.open('head.jpg')
image1.resize((198,100)).save('head1.jpg')
# image1.show()
image.show()

out_image = Image.new('RGB', (800, 300))
out_image.paste(image1, (0, 0))
out_image.paste(image, (300, 0))
out_image.show()
# out_image.resize()
out_image.save('head_logo_date.jpg')

from PIL import Image, ImageDraw, ImageFont


def addTransparency(img, factor=0.7):
    img = img.convert('RGBA')
    img_blender = Image.new('RGBA', img.size, (0, 0, 0, 0))
    img = Image.blend(img_blender, img, factor)
    return img


# 创建一个图片
image = Image.new('RGBA', (500, 200), '#F5F5F5')

# 获取该图片的画笔，之后都通过该画笔进行绘画
draw = ImageDraw.Draw(image)
font=ImageFont.truetype('NotoSansCJK-Regular.ttc', size=96, index=4)
# 写的字如果是中文需要指定字体
# draw.text((0, 0), '高清', fill='red', align='center', font=font)
text = '高清'
imwidth, imheight = image.size
font_width, font_height = draw.textsize(text, font)
draw.text(((imwidth - font_width-font.getoffset(text)[0]) / 2, (imheight - font_height-font.getoffset(text)[1]) / 2), text=text, font=font, fill=(255, 0, 0))


image = addTransparency(image)
image.show()
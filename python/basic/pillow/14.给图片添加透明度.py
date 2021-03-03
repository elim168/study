from PIL import Image


def addTransparency(img, factor=0.7):
    img = img.convert('RGBA')
    img_blender = Image.new('RGBA', img.size, (0, 0, 0, 0))
    img = Image.blend(img_blender, img, factor)
    return img


img = Image.open("flower_01.jpg")
img = addTransparency(img, factor=0.7)
img.show()

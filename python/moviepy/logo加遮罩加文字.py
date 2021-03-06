from moviepy.editor import *
from PIL import Image, ImageDraw, ImageFont
from datetime import datetime


def gene_image(width=500, height=200, factor=0.7):
    # 创建一个图片
    image = Image.new('RGBA', (width, height), '#AAAAAA')

    # 获取该图片的画笔，之后都通过该画笔进行绘画
    draw = ImageDraw.Draw(image)
    font = ImageFont.truetype('NotoSansCJK-Regular.ttc', size=96)
    # 写的字如果是中文需要指定字体
    # draw.text((0, 0), '高清', fill='red', align='center', font=font)
    text = '高清'
    imwidth, imheight = image.size
    font_width, font_height = draw.textsize(text, font)
    draw.text(
        ((imwidth - font_width - font.getoffset(text)[0]) / 2, (imheight - font_height - font.getoffset(text)[1]) / 2),
        text=text, font=font, fill=(255, 255, 255))
    image = addTransparency(image, factor)
    image_name = datetime.today().strftime('%Y%m%d%H%M%S') + '.png'
    image.save(image_name)
    return image_name


def addTransparency(img, factor=0.7):
    img = img.convert('RGBA')
    img_blender = Image.new('RGBA', img.size, (0, 0, 0, 0))
    img = Image.blend(img_blender, img, factor)
    return img


def test():
    clip:VideoFileClip = VideoFileClip('/home/elim/dev/视频录制/元宵晚会-彩排.mp4').subclip(20,-30)
    # clip.write_videofile('cctv.mp4')
    # (1584, 886)
    size = clip.size
    print(size)
    img_width = 350
    img_height = 100
    img_x = size[0] - img_width - 50
    img_y = 80
    image_clip = ImageClip(gene_image(img_width, img_height, 1), duration=clip.duration, transparent=True).set_position((img_x, img_y))
    clip2 = ImageClip('1.png').set_fps(clip.fps).set_duration(clip.duration).set_position((100, 60))
    clip3 = ImageClip('1.png').set_fps(clip.fps).set_duration(clip.duration).set_position((180, 40))
    result = CompositeVideoClip([clip, image_clip, clip2, clip3])
    result.write_videofile('logo.result.mp4')


if __name__ == '__main__':
    test()
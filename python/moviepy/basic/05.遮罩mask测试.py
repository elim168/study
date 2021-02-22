from moviepy.editor import *

"""
Some image formats like PNG support transparency with an alpha layer, which MoviePy will use as a mask:

myclip = ImageClip("image.png", transparent=True) # True is the default
myclip.mask # <- the alpha layer of the picture.

Any video clip can be turned into a mask with clip.to_mask(), and a mask can be turned to a standard RGB video clip with my_mask_clip.to_RGB().
"""

if __name__ == '__main__':
    clip = VideoFileClip('sub1.mp4')
    # clip.save_frame('mask.png')
    # mask图片会自动转换为一张黑白照片
    image_mask = ImageClip('mask.png', ismask=True)
    image_mask.set_fps(15).set_duration(30).preview()
    # clip.fx(vfx.mask_color,color=[200,200,200]).preview()
    # clip.write_gif('test.gif')
    clip.set_mask(image_mask).preview()
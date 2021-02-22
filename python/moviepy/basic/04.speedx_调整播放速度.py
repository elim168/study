from moviepy.editor import *


"""
Returns a clip playing the current clip but at a speed multiplied
by ``factor``. Instead of factor one can indicate the desired
``final_duration`` of the clip, and the factor will be automatically
computed.
The same effect is applied to the clip's audio and mask if any.
"""


# 可以参考上面的注释或speedx.py源码
if __name__ == '__main__':
    clip = VideoFileClip('sub1.mp4')
    # 调整播放速度为原来的0.1倍
    # newclip = clip.speedx(0.1)
    newclip = clip.fx(vfx.speedx, 0.1)
    newclip.preview()

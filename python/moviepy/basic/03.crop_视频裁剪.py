from moviepy.editor import *


"""
    Returns a new clip in which just a rectangular subregion of the
    original clip is conserved. x1,y1 indicates the top left corner and
    x2,y2 is the lower right corner of the croped region.
    All coordinates are in pixels. Float numbers are accepted.
    
    To crop an arbitrary rectangle:
    
    >>> crop(clip, x1=50, y1=60, x2=460, y2=275)
    
    Only remove the part above y=30:
    
    >>> crop(clip, y1=30)
    
    Crop a rectangle that starts 10 pixels left and is 200px wide
    
    >>> crop(clip, x1=10, width=200)
    
    Crop a rectangle centered in x,y=(300,400), width=50, height=150 :
    
    >>> crop(clip,  x_center=300 , y_center=400,
                        width=50, height=150)
    
    Any combination of the above should work, like for this rectangle
    centered in x=300, with explicit y-boundaries:
    
    >>> crop(x_center=300, width=400, y1=100, y2=600)
"""


if __name__ == '__main__':
    clip = VideoFileClip('../01.简介-2021-02-21_22.02.19.mp4')
    # 裁剪视频，参数的详情请参考上面的注释说明，或crop.py源码
    # newClip = clip.fx(vfx.crop, x1=200, x2=600, y1=150, y2=400)
    # 这两种用法是等价的。
    #2, 72, 609, 525
    newClip = clip.crop(x1=2, x2=611, y1=72, y2=597)
    print(newClip.size)
    newClip = newClip.resize(width=1920, height=1080)
    # newClip.preview()
    newClip.write_videofile('../同桌的她.mp4')

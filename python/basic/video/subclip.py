from moviepy.editor import *

"""
Many methods that we will see accept times as arguments.
For instance clip.subclip(t_start,t_end) which cuts the clip between two times. 
For these methods, times can be represented either in seconds (t_start=230.54), 
as a couple (minutes, seconds) (t_start=(3,50.54)), 
as a triplet (hour, min, sec) (t_start=(0,3,50.54)) or as a string (t_start='00:03:50.54')).
"""

def subclip() :
    video = VideoFileClip('航拍中国-新疆/2.mp4')
    # 截取0-10秒
    subclip1: VideoClip = video.subclip(0,38)
    subclip1.write_videofile('航拍中国-新疆/2.sub.mp4')


def merge():
    video1 = VideoFileClip('航拍中国-新疆/1.sub.mp4')
    video2 = VideoFileClip('航拍中国-新疆/2.sub.mp4')
    result = concatenate_videoclips((video1, video2))
    result.write_videofile('航拍中国-新疆/01.美丽的天山.mp4')


if __name__ == '__main__':
    # subclip()
    merge()
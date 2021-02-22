from moviepy.editor import *

"""
Many methods that we will see accept times as arguments.
For instance clip.subclip(t_start,t_end) which cuts the clip between two times. 
For these methods, times can be represented either in seconds (t_start=230.54), 
as a couple (minutes, seconds) (t_start=(3,50.54)), 
as a triplet (hour, min, sec) (t_start=(0,3,50.54)) or as a string (t_start='00:03:50.54')).
"""

def subclip() :
    video = VideoFileClip('1.result.mp4')
    # 截取0-10秒
    subclip1: VideoClip = video.subclip(0, 5)
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
    #
    subclip2 = video.subclip(5, 10)
    subclip3 = video.subclip(10, 15)
    subclip4 = video.subclip(15, 20)

    subclip1.resize(width=480).write_videofile('sub1.mp4')
    subclip2.resize(width=480).write_videofile('sub2.mp4')
    subclip3.resize(width=480).write_videofile('sub3.mp4')
    subclip4.resize(width=480).write_videofile('sub4.mp4')


def subclip2():
    video = VideoFileClip('01.简介-2021-02-21_22.49.27.mp4')
    video = video.subclip(3, -6)
    video = video.resize(width=1920, height=1080)
    # txt_clip = TextClip("《疯狂直播》", fontsize=70, color='white', font='/usr/share/fonts/truetype/arphic/ukai.ttc')\
    #     .set_position('center').set_duration(15)
    # result = CompositeVideoClip([video, txt_clip])
    video.write_videofile('2000爱笑的女孩.mp4')


def subBatch(*times):
    video = VideoFileClip('01.简介-2021-02-16_22.51.23.mp4')
    index = 1;
    for item in times:
        result = video.subclip(item[0], item[1])
        result.write_videofile('sub_01_{0}.mp4'.format(index))
        print('处理完成{0}个'.format(index))
        index = index + 1
    print('处理完成')


def subclip3():
    subBatch(('00:00:02', '00:15:32'),('00:16:08', '00:34:26'))


if __name__ == '__main__':
    # subclip()
    subclip2()
    # subclip3()

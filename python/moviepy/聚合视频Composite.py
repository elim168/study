# 聚合视频，使用CompositeVideoClip
# 如果需要聚合的是音频，请使用CompositeAudioClip。

from moviepy.editor import *


def composite1():
    """
    使用CompositeVideoClip时，可以同时指定多个Clip，本示例中使用了三个Clip。第一个Clip会放在最下面，第二个
    在第一个的上面，第三个又会在第二个的上面。
    :return:
    """
    clip1 = VideoFileClip('sub1.mp4')
    clip2 = VideoFileClip('sub2.mp4')
    clip3 = VideoFileClip('sub3.mp4')
    result = CompositeVideoClip([clip1, clip2, clip3])
    result.write_videofile('composite1_result.mp4')


def composite2():
    """
    通过指定Clip的开始时间，多个Clip组合在一起后可以指定每个Clip开始出现的时间点。
    比如本示例从0秒开始播放clip1,第8秒开始播放clip2,如果此时clip1还没有播放完成，clip2会在clip1上面，它们会有部分重合，
    第15秒开始播放clip3。

    In the example above, maybe clip2 will start before clip1 is over. In this case you can make clip2 appear with a fade-in effect of one second:

video = CompositeVideoClip([clip1, # starts at t=0
                            clip2.set_start(5).crossfadein(1),
                            clip3.set_start(9).crossfadein(1.5)])
    :return:
    """
    clip1 = VideoFileClip('sub1.mp4')
    clip2 = VideoFileClip('sub2.mp4')
    clip3 = VideoFileClip('myHolidays.mp4')
    result = CompositeVideoClip([clip1, clip2.set_start(8), clip3.set_start(15)])
    result.write_videofile('composite2_result.mp4')


def composite3():
    """
    使用CompositeVideoClip时，可以同时指定多个Clip，本示例中使用了三个Clip。第一个Clip会放在最下面，第二个
    在第一个的上面，第三个又会在第二个的上面。
    :return:
    """
    clip1 = VideoFileClip('sub1.mp4')
    clip2 = VideoFileClip('sub2.mp4')
    clip3 = VideoFileClip('sub3.mp4')
    # 还可以通过size指定聚合后的视频的幕布大小，如果指定的大小超过了视频原来的尺寸大小，则看到的视频会只有幕布的一部分。
    # 默认不指定size时以第一个视频的大小为幕布大小。
    result = CompositeVideoClip([clip1, clip2, clip3], size=(1920,1080))
    result.write_videofile('composite3_result.mp4')


def composite4():
    """
    当后面的Clip比前面的Clip小时还可以通过position指定位置

    If clip2 and clip3 are smaller than clip1, you can decide where they will appear in the composition by setting their position. Here we indicate the coordinates of the top-left pixel of the clips:

video = CompositeVideoClip([clip1,
                           clip2.set_position((45,150)),
                           clip3.set_position((90,100))])
There are many ways to specify the position:

clip2.set_position((45,150)) # x=45, y=150 , in pixels

clip2.set_position("center") # automatically centered

# clip2 is horizontally centered, and at the top of the picture
clip2.set_position(("center","top"))

# clip2 is vertically centered, at the left of the picture
clip2.set_position(("left","center"))

# clip2 is at 40% of the width, 70% of the height of the screen:
clip2.set_position((0.4,0.7), relative=True)

# clip2's position is horizontally centered, and moving down !
clip2.set_position(lambda t: ('center', 50+t) )
    :return:
    """
    # clip1 = VideoFileClip('04一醉十年.mp4').speedx(1.15).subclip(0, 20)
    clip1 = VideoFileClip('04一醉十年.mp4').subclip(0, 10)
    size = clip1.size
    # 在指定的位置加一张图片
    clip2 = ImageClip('1.png').set_fps(clip1.fps).set_duration(clip1.duration).set_position((60, 0))
    result = CompositeVideoClip([clip1, clip2])
    # result = result.resize(width=1920, height=1080)
    result.write_videofile('一醉十年.mp4')





if __name__ == '__main__':
    # composite1()
    # composite2()
    # composite3()
    composite4()
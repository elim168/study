from moviepy.editor import *


def resize():
    # width和height也可以只指定一个，另一个会自适应的调整
    clip: VideoClip = VideoFileClip('myHolidays.mp4').resize(width=1920, height=1080)
    clip.write_videofile('resize_result.mp4')


if __name__ == '__main__':
    resize()
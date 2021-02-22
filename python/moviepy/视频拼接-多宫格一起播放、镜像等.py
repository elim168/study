from moviepy.editor import *


def concat(result_file, *files):
    videos = []
    for file in files:
        video = VideoFileClip(file)
        videos.append(video)
    result_video = concatenate_videoclips(videos)
    result_video.write_videofile(result_file)


def stack():
    clip1 = VideoFileClip("sub1.mp4").margin(10)  # add 10px contour
    # 左右相反（X轴的镜像）
    clip2 = clip1.fx(vfx.mirror_x)
    # 上下相反（Y轴的镜像）
    clip3 = clip1.fx(vfx.mirror_y)
    # 缩小百分之60
    clip4 = clip1.resize(0.60)  # downsize 60%
    final_clip = clips_array([[clip1, clip2],
                              [clip3, clip4]])
    # resize相当于改变了分辨率
    final_clip.resize(width=480).write_videofile("my_stack.mp4")


def stack2():
    clip1 = VideoFileClip("sub1.mp4")
    clip2 = VideoFileClip("sub2.mp4")
    clip3 = VideoFileClip("sub3.mp4")
    clip4 = VideoFileClip("sub4.mp4")
    final_clip = clips_array([[clip1, clip2],
                              [clip3, clip4]])
    final_clip.write_videofile("my_stack.mp4")


if __name__ == '__main__':
    stack2()
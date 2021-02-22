from moviepy.editor import *


def concat(result_file, *files):
    videos = []
    for file in files:
        video = VideoFileClip(file)
        videos.append(video)
    result_video = concatenate_videoclips(videos)
    result_video.write_videofile(result_file)


if __name__ == '__main__':
    concat('concat.mp4', 'sub1.mp4', 'sub2.mp4', 'sub3.mp4', 'sub4.mp4')
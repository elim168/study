# 合并音视频
from moviepy.editor import *


def merge(video_file, audio_file, result_file):
    # 读取视频文件
    video = VideoFileClip(video_file)
    # 读取音频文件
    audio = AudioFileClip(audio_file)

    # 设置视频的音频文件
    result = video.set_audio(audio)
    # 把音视频组合起来
    # result = CompositeVideoClip([video, audio])
    # 结果文件输出
    result.write_videofile(result_file)


if __name__ == '__main__':
    video_file = 'yang.mp4'
    audio_file = 'yang.voice.mp4'
    result_file = '1.result.mp4'
    merge(video_file, audio_file, result_file)
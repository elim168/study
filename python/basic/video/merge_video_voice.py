# 合并音视频
# 可以使用下面的指令合并音视频
# ffmpeg -i video_file.mp4 -i voice_file.mp4 -strict -2 -f mp4 result.mp4

import cv2
import math
import numpy as np
from PIL import Image
from moviepy.editor import *


def getFrame(video_name, save_path):
    """
    读取视频将视频逐帧保存为图片，并返回视频的分辨率size和帧率fps
    :param video_name: 视频的名称
    :param save_path: 保存的路径
    :return: fps帧率，size分辨率
    """

# 读取视频
    video = cv2.VideoCapture(video_name)# 获取视频帧率
    fps = video.get(cv2.CAP_PROP_FPS)# 获取画面大小
    width = int(video.get(cv2.CAP_PROP_FRAME_WIDTH))
    height = int(video.get(cv2.CAP_PROP_FRAME_HEIGHT))
    size = (width, height)# ?获取帧数，用于给图片命名
    frame_num = str(video.get(7))
    name = int(math.pow(10, len(frame_num)))# 读取帧，ret为是否还有下一帧，frame为当前帧的ndarray对象
    ret, frame = video.read()
    while ret:
        cv2.imwrite(save_path + str(name) + '.jpg', frame)

        ret, frame = video.read()
        name += 1
    video.release()
    return fps, size


# 写入视频
def writeVideo(humanseg, bg_im, fps, size):
    """
    :param humanseg: png图片的路径
    :param bgname: 背景图片
    :param fps: 帧率
    :param size: 分辨率
    :return:
    """# 写入视频
    fourcc = cv2.VideoWriter_fourcc(*'mp4v')
    out = cv2.VideoWriter('green.mp4', fourcc, fps, size)# 将每一帧设置背景
    files = [humanseg + i for i in os.listdir(humanseg)]
    for file in files:# 循环合并图片
        im_array = setImageBg(file, bg_im)# 逐帧写入视频
        out.write(im_array)
    out.release()


# 读取视频的音频
def getMusic(video_name):
    """
    获取指定视频的音频
    :param video_name: 视频名称
    :return: 音频对象
    """
    # 读取视频文件
    video = VideoFileClip(video_name)
    # 返回音频
    return video.audio


# 给视频添加音频
def addMusic(video_name, audio):
    """实现混流，给video_name添加音频"""
    # 读取视频
    video = VideoFileClip(video_name)
    # 设置视频的音频
    video = video.set_audio(audio)
    # 保存新的视频文件
    video.write_videofile(output_video)


# 更改视频的背景
def changeVideoScene(video_name, bgname):
    """
    :param video_name: 视频的文件
    :param bgname: 背景图片
    :return:
    """
    # 读取视频中每一帧画面
    fps, size = getFrame(video_name, frame_path)# 批量抠图
    getHumanseg(frame_path)# 读取背景图片
    bg_im = readBg(bgname, size)# 将画面一帧帧写入视频
    writeVideo(humanseg_path, bg_im, fps, size)# 混流
    addMusic('green.mp4', getMusic(video_name))# 删除过渡文件
    deleteTransitionalFiles()


if __name__ == '__main__':# 当前项目根目录
    import subprocess
    BASE_DIR = os.path.abspath(os.path.join(os.path.dirname(__file__), "."))# 每一帧画面保存的地址
    print(BASE_DIR)
    # frame_path = BASE_DIR + '\\frames\\'# 抠好的图片位置
    humanseg_path = BASE_DIR + '\\humanseg_output\\'# 最终视频的保存路径
    output_video = BASE_DIR + '\\result.mp4'
    # if not os.path.exists(frame_path):
    #     os.makedirs(frame_path)
    try:
    # 调用函数制作视频
        print('------------------Running------------------', VideoFileClip('1voice.mp4'))
        print('AAAAAAAAAAAAA')
    #     changeVideoScene('jljt.mp4', 'bg.jpg')# 当制作完成发送邮箱
        addMusic('1.mp4', VideoFileClip('1voice.mp4'))
    #     mail.sendMail('你的视频已经制作完成')
    except Exception as e:# 当发生错误，发送错误信息
        # mail.sendMail('在制作过程中遇到了问题' + e.__str__())
        print("音视频合并失败", e.__str__())


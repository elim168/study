

import moviepy.editor as mp


def add_logo(video_file, logo_file, result_file):
    # 创建对象
    video = mp.VideoFileClip(video_file)
    # 准备log图片
    logo = (
        mp.ImageClip(logo_file)  # 图片必须是rgb解析成3个值才行，不然报错
            .set_duration(video.duration)  # 水印持续时间
            .resize(height=60)  # 水印的高度，会等比缩放
            .margin(left=0, right=0, top=0, opacity=0.5)  # 水印的边距与透明度
            .set_pos(('right', 'top')))  # 水印的位置

    txt_clip = (mp.TextClip("贾玲——一波三折", fontsize=70, color='white', font='/usr/share/fonts/truetype/arphic/ukai.ttc')
                .set_position('center')
                .set_duration(30))


    final = mp.CompositeVideoClip([video, txt_clip, logo])
    # 文件存放的路劲及文件名 mp4文件默认用libx264编码，比特率单位bps
    final.write_videofile(result_file)





if __name__ == '__main__':
    add_logo('myHolidays_edited.mp4', 'logo.png', 'logo_video.mp4')
    # from moviepy.editor import *
    # # 打印所有的字体
    # print(TextClip.list("font"))
    # 在控制台运行下面的命令也可以看系统上支持中文的字体
    # fc-list :lang=zh-cn
    # video = VideoFileClip("result.mp4").subclip(50, 60)
    #
    # # Make the text. Many more options are available.
    # txt_clip = (TextClip("贾玲——一波三折", fontsize=70, color='white', font='/usr/share/fonts/truetype/arphic/ukai.ttc')
    #             .set_position('center')
    #             .set_duration(10))
    #
    # result = CompositeVideoClip([video, txt_clip])  # Overlay text on video
    # result.write_videofile("myHolidays_edited.mp4", fps=25)  # Many options...
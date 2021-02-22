from moviepy.editor import *

txt_clip = TextClip("更上一层楼AB", fontsize=180, size=(1920, 1080), color='white', bg_color='#00A0F5', font='/usr/share/fonts/truetype/arphic/ukai.ttc')
txt_clip.save_frame('logo.png')
# txt_clip.set_fps(15).set_duration(5).preview()
from moviepy.editor import *

clip = VideoFileClip('小心肝.mp4')
clip = clip.resize(width=1920, height=1080)
clip.write_videofile('小心肝1.mp4')

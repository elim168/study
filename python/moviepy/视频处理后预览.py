from moviepy.editor import *

"""
The many ways of previewing a clip
When you are editing a video or trying to achieve an effect with MoviePy through a trial and error process, generating the video at each trial can be very long. This section presents a few tricks to go faster.

clip.save_frame
Most of the time, just having one frame of the video can tell you if you are doing the right thing. You can save just one frame of the clip to a file as follows:

my_clip.save_frame("frame.jpeg") # saves the first frame
my_clip.save_frame("frame.png", t=2) # saves the frame a t=2s
clip.show and clip.preview
The methods clip.show and clip.preview enable you to vizualise the clip in a Pygame window. They are the fastest way to preview, as the clips are generated and displayed at the same time, and they can be useful to get the coordinates or colors of pixels. These methods require to have PyGame installed, and to use the moviepy.editor module.

The method clip.show enables preview one frame of a clip without having to write it to a file: the following lines display the frame in a PyGame window

my_clip.show() # shows the first frame of the clip
my_clip.show(10.5) # shows the frame of the clip at t=10.5s
my_clip.show(10.5, interactive = True)
The last line (with interactive=True) displays the frame in an interactive way: if you click somewhere in the frame, it will print the position and color of the pixel. Press Escape to exit when you are done.

A clip can be previewed as follows

my_clip.preview() # preview with default fps=15
my_clip.preview(fps=25)
my_clip.preview(fps=15, audio=False) # don't generate/play the audio.
my_audio_clip.preview(fps=22000)
If you click somewhere in the frames of a video clip being previewed, it will print the position and color of the pixel clicked. Press Escape abort the previewing.

Note that if the clip is complex and your computer not fast enough, the preview will appear slowed down compared to the real speed of the clip. In this case you can try to lower the frame rate (for instance to 10) or reduce the size of the clip with clip.resize, it helps.
"""

video = VideoFileClip('你好.sub.result.mp4')

# 保存一帧的图片
video.save_frame('frame.png')
# 展示第20秒的图片，交互式展示，点击后会输出位置和颜色，按ESC键退出
video.show(20, interactive = True)
# 以默认fps=15的速度预览，点击后会输出位置和颜色，按ESC键退出
video.preview()
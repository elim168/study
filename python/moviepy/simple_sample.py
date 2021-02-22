# Import everything needed to edit video clips
from moviepy.editor import *

# Load myHolidays.mp4 and select the subclip 00:00:00 - 00:00:10   截取前10秒
clip: VideoFileClip = VideoFileClip("myHolidays.mp4").subclip(0,10)


# Reduce the audio volume (volume x 0.8)
clip = clip.volumex(0.8)


# Generate a text clip. You can customize the font, color, etc.
txt_clip = TextClip("My Holidays 2013",fontsize=70,color='white')

# Say that you want it to appear 10s at the center of the screen
txt_clip = txt_clip.set_pos('center').set_duration(10)

# Overlay the text clip on the first video clip
video = CompositeVideoClip([clip, txt_clip])

# Write the result to a file (many options available !)
video.write_videofile("myHolidays_edited.webm")
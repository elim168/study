from moviepy.editor import *
from moviepy.video.tools.drawing import circle

clip = VideoFileClip("../sub1.mp4", audio=False).add_mask()

w, h = clip.size
print(clip.size)
# The mask is a circle with vanishing radius r(t) = 800-200*t
clip.mask.get_frame = lambda t: circle(screensize=(clip.w, clip.h),
                                       center=(clip.w / 2, clip.h / 4),
                                       radius=max(0, int(100 - 1 * t)),
                                       col1=1, col2=0, blur=4)

the_end = TextClip("The End", font="Amiri-bold", color="white",
                   fontsize=70).set_duration(clip.duration)

final = CompositeVideoClip([the_end.set_pos('center'), clip],
                           size=clip.size)

# final.write_videofile("../../theEnd.avi")
final.preview()
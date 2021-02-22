from moviepy.editor import *
from moviepy.video.tools.segmenting import findObjects

# Load the image specifying the regions.
im = ImageClip('compo_from_image.jpeg')

# Loacate the regions, return a list of ImageClips
regions = findObjects(im)
for r in regions:
    print(r.size, r.screenpos, r.mask, 10*'==========')

# Load 7 clips from the US National Parks. Public Domain :D
clips = [VideoFileClip(n, audio=False) for n in
     [ "../sub1.mp4",
      "../sub1.mp4",
      "../sub1.mp4",
      "../sub1.mp4",
      "../sub1.mp4",
      "../sub1.mp4",
      "../sub1.mp4"]]

# fit each clip into its region
comp_clips =  [c.resize(r.size)
                .set_mask(r.mask)
                .set_pos(r.screenpos)
               for c,r in zip(clips,regions)]

cc = CompositeVideoClip(comp_clips,im.size)
# cc.resize(0.6).write_videofile("../../composition.mp4")
cc.resize(0.6).preview()
# Note that this particular composition takes quite a long time of
# rendering (about 20s on my computer for just 4s of video).


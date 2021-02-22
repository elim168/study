"""
The recipe used to make a photo look like a painting:

    Find the edges of the image with the Sobel algorithm. You obtain what looks like a black and white hand-drawing of the photo.

    Multiply the image array to make the colors flashier, and add the contours obtained at the previous step.

The final clip will be the concatenation of three part: the part before the effect, the part with the effect, and the part after the effect. The part with the effect is obtained as follows:

    Take the frame to freeze and make a “painted image” of it. Make it a clip.

    Add a text clip saying “Audrey” to the “painted image” clip.

    Overlay the painted clip over the original frame, but make it appear and disappear with a fading effect.

Here you are for the code:
"""


""" requires scikit-image installed (for vfx.painting) """

from moviepy.editor import *

# WE TAKE THE SUBCLIPS WHICH ARE 2 SECONDS BEFORE & AFTER THE FREEZE

charade = VideoFileClip("../sub1.mp4")
tfreeze = cvsecs(1.2)  # Time of the freeze, 19'21

# when using several subclips of a same clip, it can be faster
# to create 'coreaders' of the clip (=other entrance points).
clip_before = charade.coreader().subclip(tfreeze - 2, tfreeze)
clip_after = charade.coreader().subclip(tfreeze, tfreeze + 2)

# THE FRAME TO FREEZE

im_freeze = charade.to_ImageClip(tfreeze)
painting = (charade.fx(vfx.painting, saturation=1.6, black=0.006)
            .to_ImageClip(tfreeze))

txt = TextClip('Audrey', font='Amiri-regular', fontsize=35)

painting_txt = (CompositeVideoClip([painting, txt.set_pos((10, 180))])
                .add_mask()
                .set_duration(3)
                .crossfadein(0.5)
                .crossfadeout(0.5))

# FADEIN/FADEOUT EFFECT ON THE PAINTED IMAGE

painting_fading = CompositeVideoClip([im_freeze, painting_txt])

# FINAL CLIP AND RENDERING

final_clip = concatenate_videoclips([clip_before,
                                     painting_fading.set_duration(3),
                                     clip_after])

# final_clip.write_videofile('../../audrey.avi', fps=charade.fps,
#                            codec="mpeg4", audio_bitrate="3000k")
final_clip.preview()
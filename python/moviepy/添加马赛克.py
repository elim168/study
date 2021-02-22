from moviepy.editor import *


def test1():
    video = VideoFileClip("如此排练.mp4")
    size = video.size
    print(size)
    image = ImageClip('logo.png').set_opacity(1.0).set_position((size[0] - 190, 20)).set_duration(video.duration)
    print(image.size)
    result = CompositeVideoClip([video, image])
    result.write_videofile('如此排练1.mp4')


def test2():
    video = VideoFileClip('如此排练.mp4')
    size = video.size
    print(size)
    x = size[0] - 120
    y = 10
    fx = lambda t: int(x)
    fy = lambda t: int(y)
    # 使用到了moviepy/video/fx/headblur.py中的内容，有疑问可以参考对应的源码
    clip_blurred = video.fx(vfx.headblur, fx, fy, 80, 20)
    clip_blurred.write_videofile('如此排练1.mp4')


def blur(video, x, y, radis=80, r_blur=20):
    if x < 0:
        x = video.size[0] + x
    if y < 0:
        y = video.size[1] + y
    fx = lambda t: int(x)
    fy = lambda t: int(y)
    # 使用到了moviepy/video/fx/headblur.py中的内容，有疑问可以参考对应的源码
    clip_blurred = video.fx(vfx.headblur, fx, fy, radis, r_blur)
    return clip_blurred


def test3():
    video = VideoFileClip('如此排练1.mp4')
    video = blur(video, -120, 10)
    video = blur(video, 200, 40, 120, 100)
    video = blur(video, -220, -80, 150, 50)
    video.write_videofile('如此排练.mp4')


# 打上矩形马赛克
def test4():
    import numpy as np

    # ------- CHECKING DEPENDENCIES -----------------------------------------
    try:
        import cv2
        headblur_possible = True
        if cv2.__version__ >= '3.0.0':
            cv2.CV_AA = cv2.LINE_AA
    except:
        headblur_possible = False

    # -----------------------------------------------------------------------

    def my_headblur(clip, x, y, width, height, r_blur=50):
        """
        Returns a filter that will blurr a moving part (a head ?) of
        the frames. The position of the blur at time t is
        defined by (fx(t), fy(t)), the radius of the blurring
        by ``r_zone`` and the intensity of the blurring by ``r_blur``.
        Requires OpenCV for the circling and the blurring.
        Automatically deals with the case where part of the image goes
        offscreen.
        """

        def fl(gf, t):
            im = gf(t)
            h, w, d = im.shape
            x1, x2 = x, min(x + width, w)
            y1, y2 = y, min(y + height, h)
            region_size = y2 - y1, x2 - x1

            mask = np.zeros(region_size).astype('uint8')
            cv2.rectangle(mask, (0, 0), (width, height), 255, -1, lineType=cv2.CV_AA)
            # cv2.circle(mask, (r_zone, r_zone), r_zone, 255, -1,
            #            lineType=cv2.CV_AA)

            mask = np.dstack(3 * [(1.0 / 255) * mask])

            orig = im[y1:y2, x1:x2]
            blurred = cv2.blur(orig, (r_blur, r_blur))
            # im[y1:y2, x1:x2] = mask*blurred + (1-mask)*orig
            # return im
            imblur = mask * blurred + (1 - mask) * orig
            imseg = np.hstack(
                (im[y1:y2, 0:x1], imblur, im[y1:y2, x2:]))  # 取模糊化对应矩形范围同一水平位置的矩形左边和右边的数据以及模糊数据一起水平堆叠获得模糊化矩形范围对应的所有水平数据
            imnew = np.vstack((im[0:y1, 0:], imseg, im[y2:, 0:]))  # 将模糊化对应矩形对应的所有水平数据与其上和其下的数据竖直堆叠作为返回的帧数据

            return imnew

        return clip.fl(fl)

    # ------- OVERWRITE IF REQUIREMENTS NOT MET -----------------------------
    if not headblur_possible:
        doc = my_headblur.__doc__

        def my_headblur(clip, x, y, width, height, r_blur=50):
            raise IOError("fx painting needs opencv")

        my_headblur.__doc__ = doc
    # -----------------------------------------------------------------------

    videoPath = '04一醉十年.mp4'
    times = 3
    cap = cv2.VideoCapture(videoPath)
    (flag, image) = cap.read()
    video = VideoFileClip(videoPath)
    for i in range(times):
        bbox = cv2.selectROI('selectroi', image)
        print('第{0}次选择的是：'.format(i + 1), bbox)
        video = video.fx(my_headblur, bbox[0], bbox[1], bbox[2], bbox[3], 80)
    # 使用到了moviepy/video/fx/headblur.py中的内容，有疑问可以参考对应的源码
    # 马赛克位置可以通过程序“选择图片或视频位置.py”获取
    # video = video.fx(my_headblur, 1260, 36, 283, 57, 20)
    video.write_videofile('result.{0}'.format(videoPath))


if __name__ == "__main__":
    test4()

from moviepy.editor import *
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

def logo_fix(clip, x, y, width, height, ratio=1):
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
        upPix = im[y1-ratio:y1,x1:x2]
        downPix = im[y2:y2+ratio,x1:x2]

        imsegs = list()
        # 上一半取上面的最近一个像素
        for i in range((y2-y1)//(2*ratio)):
            # 一个一个像素把图标左边的和修复的，以及图标右边的组合起来
            imseg = upPix
            imsegs.append(imseg)

        # 下一半取下面的最近的一个像素
        for i in range((y2-y1)//(2*ratio), (y2-y1)//ratio):
            imseg = downPix
            imsegs.append(imseg)

        imseg = np.vstack(tuple(imsegs))
        imseg = np.hstack(
            (im[y1:y2, 0:x1], imseg, im[y1:y2, x2:]))
        imnew = np.vstack((im[0:y1, 0:], imseg, im[y2:, 0:]))  # 将模糊化对应矩形对应的所有水平数据与其上和其下的数据竖直堆叠作为返回的帧数据
        return imnew

    return clip.fl(fl)



def logo_fix2(clip, x, y, width, height):
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

        upPix = im[y1-height//2:y1,x1:x2]
        downPix = im[y2:y2+height-height//2,x1:x2]

        # 上一半取上面的最近一个像素
        # 下一半取下面的最近的一个像素
        imsegs = (upPix, downPix)
        imseg = np.vstack(tuple(imsegs))
        imseg = np.hstack(
            (im[y1:y2, 0:x1], imseg, im[y1:y2, x2:]))
        imnew = np.vstack((im[0:y1, 0:], imseg, im[y2:, 0:]))  # 将模糊化对应矩形对应的所有水平数据与其上和其下的数据竖直堆叠作为返回的帧数据
        return imnew

    return clip.fl(fl)


# ------- OVERWRITE IF REQUIREMENTS NOT MET -----------------------------
if not headblur_possible:
    doc = logo_fix.__doc__


    def logo_fix(clip, x, y, width, height):
        raise IOError("fx painting needs opencv")


    logo_fix.__doc__ = doc


# 取图标之外的某一处的内容放那里
def logo_fix3(clip, x, y, width, _height):
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
        height = _height
        x1, x2 = x, min(x + width, w)
        y1, y2 = y, min(y + height, h)

        # 在左边则取左上角那块
        if x1 < w//2:
            if y1 < height:
                if y1%2 != 0:
                    y1 += 1
                if height%2 != 0:
                    height += 1
                imseg1 = im[0:height//2:, 0:width]
                imseg2 = im[y1-height//2+1:y1, 0:width]
                imseg = np.vstack((imseg1, imseg2))
            else:
                imseg = im[0:height, 0:width]
        else:
            # 在右边则取右上角那块
            imseg = im[0:height, w-width:w]
        imseg = np.hstack(
            (im[y1:y2, 0:x1], imseg, im[y1:y2, x2:]))
        imnew = np.vstack((im[0:y1, 0:], imseg, im[y2:, 0:]))  # 将模糊化对应矩形对应的所有水平数据与其上和其下的数据竖直堆叠作为返回的帧数据
        return imnew

    return clip.fl(fl)



# 取左下角的一块内容放到左上角
def logo_fix4(clip, x, y, width, _height):
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
        height = _height
        x1 = 0
        x2 = x + width

        y1 = y+height
        y2 = 2*y1

        imseg = im[y1:y2, x1:x2]
        imseg = np.hstack(
            (imseg, im[0:y+height, x2:]))
        imnew = np.vstack((imseg, im[y+height:, 0:]))  # 将模糊化对应矩形对应的所有水平数据与其上和其下的数据竖直堆叠作为返回的帧数据
        return imnew

    return clip.fl(fl)



def test():
    video = VideoFileClip('你好.sub.result.mp4')
    size = video.size
    print(size)
    # 使用到了moviepy/video/fx/headblur.py中的内容，有疑问可以参考对应的源码
    # 马赛克位置可以通过程序“选择图片或视频位置.py”获取
    # clip_blurred = video.fx(logo_fix, 1260, 36, 283, 60)
    clip_blurred = video.fx(logo_fix, 1260, 36, 283, 60)
    # 预览
    # clip_blurred.show(10.5, interactive = True)
    clip_blurred.preview()
    # clip_blurred.write_videofile('你好李焕英111.mp4')


# 本方法来源于网上，不太好使
def test2():
    from copy import deepcopy
    # 如果画面中像素的RBG各分量之和超过580,就处理，这是一个经验值，可调整
    threshold = 500
    def remove_watermark(image):
        image = deepcopy(image)
        shape = image.shape
        print(shape[-1])
        if shape[-1] == 3:
            image[image.sum(axis=2)>threshold] = [255] * 3
        elif shape[-1] == 4:
            image[image[:,:,:3].sum(axis=2)>threshold] = [255] * 4
        return image

    video:VideoFileClip = VideoFileClip('/home/elim/dev/视频录制/元宵晚会-彩排.mp4')
    video.fl_image(remove_watermark).preview()


# a
def test3():
    from copy import deepcopy
    # 如果画面中像素的RBG各分量之和超过580,就处理，这是一个经验值，可调整
    threshold = 500
    # x,y,w,h
    p1 = (117, 86, 252, 101)
    p2 = (1546, 84, 313, 96)
    ps = (p1, p2)

    def remove_watermark(image):
        image = deepcopy(image)
        # shape = image.shape
        # print(shape[-1])
        # print(shape)
        """
        第1次选择的是： (117, 86, 252, 101)
        第2次选择的是： (1546, 84, 313, 96)
        """
        items = []
        for p in ps:
            for x in range(p[0], p[0] + p[2]):
                for y in range(p[1], p[1] + p[3]):
                    # print(image[y][x])
                    color = image[y][x]
                    if color[0] > 150 and color[1] > 100 and color[2] > 100:
                        color[2] = 0
                        # color1 = image[y + 5, x - 5]
                        # for y1 in range(y - 5, y + 5):
                        #     for x1 in range(x - 5, x + 5):
                        #         color = image[y1][x1]
                        #         color[0] = color1[0]
                        #         color[1] = color1[1]
                        #         color[2] = color1[2]

        # if shape[-1] == 3:
        #     image[image.sum(axis=2)>threshold] = [150] * 3
        # elif shape[-1] == 4:
        #     image[image[:,:,:3].sum(axis=2)>threshold] = [25] * 4
        return image

    video:VideoFileClip = VideoFileClip('/home/elim/dev/视频录制/sub.mp4')
    video.fl_image(remove_watermark).write_videofile('/home/elim/dev/视频录制/sub.handled.mp4')


def test4():
    p1 = (117, 86, 252, 101)
    p2 = (1546, 84, 313, 96)
    ps = (p1, p2)
    clips = []
    video: VideoFileClip = VideoFileClip('/home/elim/dev/视频录制/sub.mp4')
    clips.append(video)
    for p in ps:
        crop1 = video.crop(x1=p[0], x2=p[0] + p[2], y1=p[1], y2=p[1] - p[3] // 2).set_position((p[0], p[1]))
        crop2 = video.crop(x1=p[0], x2=p[0] + p[2], y1=p[1]+p[3], y2=p[1] + 3 * p[3] // 2).set_position((p[0], p[1]+p[3]//2))
        clips.append(crop1)
        clips.append(crop2)

    CompositeVideoClip(clips).write_videofile('/home/elim/dev/视频录制/sub.handled.mp4')


# 使用log_fix3
def test5():
    video = VideoFileClip('/home/elim/dev/视频录制/元宵晚会-彩排.mp4').subclip(0, -30)
    video = video.fx(logo_fix3, 117, 86, 252, 101)
    video = video.fx(logo_fix3, 1546, 84, 313, 96)
    # 预览
    # clip_blurred.show(10.5, interactive = True)
    # video.preview()
    video.write_videofile('/home/elim/dev/视频录制/sub.handled.mp4')


# 使用log_fix4
def test6():
    video = VideoFileClip('/home/elim/dev/视频录制/元宵晚会-彩排.mp4').subclip(0, 30)
    video = video.fx(logo_fix4, 117, 86, 252, 101)
    # video = video.fx(logo_fix3, 1546, 84, 313, 96)
    # 预览
    # clip_blurred.show(10.5, interactive = True)
    # video.preview()
    video.write_videofile('/home/elim/dev/视频录制/sub.handled.mp4')



if __name__ == "__main__":
    # test()
    # test3()
    # test4()
    # test5()
    test6()

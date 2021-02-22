
import cv2, os
import numpy as np

def video2img(videoroot):
    cap = cv2.VideoCapture(videoroot)
    isOpened = cap.isOpened    # 判断是否打开‘
    print(isOpened)
    fps = cap.get(cv2.CAP_PROP_FPS)  # 帧率
    width = int(cap.get(cv2.CAP_PROP_FRAME_WIDTH))
    height = int(cap.get(cv2.CAP_PROP_FRAME_HEIGHT))
    print(fps ,width ,height)
    i = 0
    while(isOpened):
        i += 1
        (flag ,frame) = cap.read() # 读取每一张 flag frame
        fileName = './imgs/image ' +str(i ) +'.jpg'
        print(fileName)
        if flag == True:
            frame = np.rot90(frame, 1)
            cv2.imwrite(fileName ,frame)
        else:
            break
    return fps

def img2video(outvideoroot, fps):
    img = cv2.imread('./imgs/image1.jpg')
    imgInfo = img.shape
    size = (imgInfo[1] ,imgInfo[0])
    print(size)
    fourcc = cv2.VideoWriter_fourcc(*'XVID')
    videowriter = cv2.VideoWriter(outvideoroot ,fourcc ,fps ,size)
    list_imgs = os.listdir('./imgs')
    for i in range(1, len(list_imgs)):
        fileName = './imgs/image ' +str(i ) +'.jpg'
        img = cv2.imread(fileName)
        videowriter.write(img)


def get_xoy():
    imgsroot = './imgs'
    list_ = os.listdir(imgsroot)
    for n in list_:
        path_ = os.path.join(imgsroot, n)
        img = cv2.imread(path_)
        bbox = cv2.selectROI('selectroi', img)
        break
    return bbox


def fitsign():
    box = get_xoy()
    xo, yo, w, h = box[0], box[1] ,box[2] ,box[3],
    print(box)
    dstroot = './imgs'
    dstlist = os.listdir(dstroot)
    kernel_size = (41, 41)
    sigma = 50
    for n in dstlist:
        path_ = os.path.join(dstroot, n)
        img = cv2.imread(path_)
        crop = img[yo:yo +h, xo:xo +w, :]
        crop = cv2.GaussianBlur(crop, kernel_size, sigma)
        # crop = cv2.blur(crop, (41,41))
        img[yo:yo +h, xo:xo +w, :] = crop
        cv2.imwrite(path_, img)


# 选择图片的位置
def selectPosition(videoPath, times=1):
    cap = cv2.VideoCapture(videoPath)
    (flag, image) = cap.read()
    for i in range(times):
        # cv2.putText(image, 'Times-{0}'.format(i+1), (50, 50), color=(255,0,0), fontFace='font.sans-serif')
        # cv2.addText(image, 'Times-{0}'.format(i+1), (50, 50), color=(255,0,0), weight=15, pointSize=30, nameFont='font.sans-serif')
        bbox = cv2.selectROI('selectroi', image)
        print('第{0}次选择的是：'.format(i+1), bbox)


if __name__ == "__main__":
   selectPosition('01.简介-2021-02-21_22.02.19.mp4', 1)

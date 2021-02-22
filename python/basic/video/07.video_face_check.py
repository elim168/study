import cv2


def face_check(image):

    # 通过画矩形把每张人脸圈起来
    cv2.rectangle(image, (100, 100), (150, 150), color=(0, 0, 255), thickness=2)
    cv2.imshow('test', image)


videoCapture = cv2.VideoCapture('/home/elim/下载/yueyunpeng_shenteng.mp4')
while True:
    # 视频由很多帧组成，每一帧就是一张图片
    flag, frame = videoCapture.read()
    # print(flag, frame)
    if not flag:
        break

    face_check(frame)
    key = cv2.waitKey(5)  # 必须要监听键盘，图片才能展示出来，否则就是一闪而过
    if ord('q') == key:  # 按了键盘上的Q则退出
        break

videoCapture.release()
cv2.destroyAllWindows()
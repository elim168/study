import cv2


def face_check(image):
    # 把原图转换为灰色图片
    gray_image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    # 默认的人脸检测
    detector = cv2.CascadeClassifier(
        '/home/elim/dev/projects/study/python/venv/lib/python3.6/site-packages/cv2/data/haarcascade_frontalface_default.xml')
    # 检测图片，返回每张人脸的信息。每张人脸包含x,y,width,height四项信息，其中width和height会相等。
    faces = detector.detectMultiScale(gray_image)
    # print(faces)
    for x, y, w, h in faces:
        # 通过画矩形把每张人脸圈起来
        cv2.rectangle(image, (x, y), (x + w, y + h), color=(0, 0, 255), thickness=2)
    cv2.imshow('test', image)


videoCapture = cv2.VideoCapture('video3.mp4')
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
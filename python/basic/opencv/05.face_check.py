# 定位人类区域

import cv2

image = cv2.imread('face01.jpg')
# 把原图转换为灰色图片
gray_image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
# 默认的人脸检测
detector = cv2.CascadeClassifier('/usr/local/share/opencv4/haarcascades/haarcascade_frontalface_default.xml')
# 检测图片，返回每张人脸的信息。每张人脸包含x,y,width,height四项信息，其中width和height会相等。
faces = detector.detectMultiScale(gray_image)
print(faces)
for x, y, w, h in faces:
    # 通过画矩形把每张人脸圈起来
    cv2.rectangle(image, (x, y), (x + w, y + h), color=(0, 0, 255), thickness=2)
cv2.imshow('test', image)
cv2.waitKey(0)
cv2.destroyAllWindows()

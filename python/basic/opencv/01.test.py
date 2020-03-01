# opencv可以用来做人脸识别
# 通过pip3 install opencv-python安装opencv
# 使用opencv需要导入cv2模块

import cv2

# 读取图片
image = cv2.imread('face01.jpg')
# 展示刚刚读取的图片，展示的名称是test-image-name
cv2.imshow('test-image-name', image)
# 无限期等待键盘输入任意字符，否则上面的内容会一闪而过
cv2.waitKey(0)
# 释放资源
cv2.destroyAllWindows()

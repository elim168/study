# 调整大小
import cv2

image = cv2.imread('face01.jpg')
print(type(image))  # numpy.ndarray

# 读取出来的图片以numpy三维数组表示，每一维表示一个颜色值。
print(image.shape)  # (300, 534, 3) height: 300, width: 534

cv2.imshow('old image', image)

# resize to height: 400, width: 600
resized_image = cv2.resize(image, (600, 400))
print(resized_image.shape)

cv2.imshow('resized image', resized_image)

while True:
    if ord('q') == cv2.waitKey(0):  # 只有输入的是q时才退出。ord()用于转换字母对应的ASC码
        break

cv2.destroyAllWindows()


# 在图片上绘图
import cv2

image = cv2.imread('face01.jpg')
print(image.shape)

# 通过图片的numpy的三维数组知道图片的高和宽
h, w, c = image.shape


# 画一个圆。center是圆心的坐标，radius是半径。color是BGR三色，所以下面的是蓝色。thickness是线条的宽度
cv2.circle(image, center=(w//2, h//2), radius=100, color=(255, 0, 0), thickness=5)  # color = blue. BGR

height = 150
width = 250
x1 = w//2 - width//2
x2 = x1 + width
y1 = h//2 - height//2
y2 = y1 + height

# 画矩形，红色
cv2.rectangle(image, (x1, y1), (x2, y2), color=(0, 0, 255), thickness=3)

cv2.line(image, (20, 20), (100, 100), color=(0, 255, 0), thickness=2)
cv2.imshow('result image', image)

cv2.waitKey(0)
cv2.destroyAllWindows()

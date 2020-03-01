# 测试将图片转换为灰色图片
import cv2

image = cv2.imread('face01.jpg')
cv2.imshow('test-image-name', image)

# opencv的图片的颜色是BGR，它对应于RGB只是把三色的位置放不同而已，下面就是将BGR图片转换为灰色图片。
gray_image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
cv2.imshow('Gray Image', gray_image)

# 将新生成的灰色图片保存到当前目录的gray_face01.jpg文件中
cv2.imwrite('gray_face01.jpg', gray_image)

cv2.waitKey(0)
cv2.destroyAllWindows()

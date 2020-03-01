# 人脸识别
# 使用人脸识别需要额外安装opencv-contrib-python库，pip3 install opencv-contrib-python


import cv2
from PIL import Image
import numpy


# 进行数据训练
def train():
    detector = cv2.CascadeClassifier(
        '/home/elim/dev/projects/study/python/venv/lib/python3.6/site-packages/cv2/data/haarcascade_frontalface_default.xml')
    images = ['face01.jpg', 'face02.jpg', 'face04.jpg']
    face_datas = []
    ids = [1, 2, 4]    # 简单的直接定死
    for image_path in images:
        image = Image.open(image_path)
        gray_image = image.convert('L')
        print(gray_image)
        gray_image_array = numpy.array(gray_image, dtype='uint8')
        print(gray_image_array.shape)   # (height,width)
        # 检测图片，返回每张人脸的信息。每张人脸包含x,y,width,height四项信息，其中width和height会相等。
        faces = detector.detectMultiScale(gray_image_array)
        print(faces)
        for x,y,w,h in faces:
            face_data = gray_image_array[y:y+h, x:x+w]  # 从图片中截取出人脸信息
            face_datas.append(face_data)

    recognizer = cv2.face.LBPHFaceRecognizer_create()
    print(dir(recognizer))
    labels = numpy.array(ids)   # 只能是整数
    print(len(face_datas), len(labels))
    recognizer.train(numpy.array(face_datas), labels)
    recognizer.save('train.yml')


def recognize(image_path):
    recognizer = cv2.face.LBPHFaceRecognizer_create()
    recognizer.read('train.yml')    # 加载保存的训练数据

    image = cv2.imread(image_path)
    gray_image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    print(gray_image.shape)
    detector = cv2.CascadeClassifier(
        '/home/elim/dev/projects/study/python/venv/lib/python3.6/site-packages/cv2/data/haarcascade_frontalface_default.xml')
    # 识别人脸区域
    faces = detector.detectMultiScale(gray_image)
    for x,y,w,h in faces:
        id, confidence = recognizer.predict(gray_image[y:y+h, x:x+w])
        # 置信度越小说明匹配度越高，同一图片的置信度为0。
        print('图片[{}]，id={}置信度为{}'.format(image_path, id, confidence))



# 训练
train()

# 人脸识别
recognize('face01.jpg')
recognize('face02.jpg')
# recognize('face03.jpg')
recognize('face04.jpg')
recognize('face05.jpg')
recognize('face06.jpg')
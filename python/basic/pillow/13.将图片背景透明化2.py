from PIL import Image
import numpy


def transparent_background(path):
    try:
        img = Image.open(path)
        img = img.convert("RGBA")  # 转换获取信息
        pixdata = img.load()
        color_no = get_convert_middle(path) +27  # 抠图的容错值

        # 从左到右，遇到第一个非背景色退出
        for y in range(img.size[1]):
            for x in range(img.size[0]):
                if pixdata[x, y][0] > color_no and pixdata[x, y][1] > color_no and pixdata[x, y][2] > color_no and \
                        pixdata[x, y][3] > color_no:
                    pixdata[x, y] = (255, 255, 255, 0)
                else:
                    break
        # 从右到左，遇到第一个非背景色退出
        for y in range(img.size[1]):
            for x in range(img.size[0]-1,0,-1):
                if pixdata[x, y][0] > color_no and pixdata[x, y][1] > color_no and pixdata[x, y][2] > color_no and \
                        pixdata[x, y][3] > color_no:
                    pixdata[x, y] = (255, 255, 255, 0)
                else:
                    break
        # 从上到下，遇到第一个非背景色退出
        for x in range(img.size[0]):
            for y in range(img.size[1]):
                if pixdata[x, y][0] > color_no and pixdata[x, y][1] > color_no and pixdata[x, y][
                    2] > color_no and \
                        pixdata[x, y][3] > color_no:
                    pixdata[x, y] = (255, 255, 255, 0)
                elif pixdata[x, y] != (255, 255, 255, 0):
                    break
        # 从下到上，遇到第一个非背景色退出
        for x in range(img.size[0]):
            for y in range(img.size[1]-1,0,-1):
                if pixdata[x, y][0] > color_no and pixdata[x, y][1] > color_no and pixdata[x, y][
                    2] > color_no and \
                        pixdata[x, y][3] > color_no:
                    pixdata[x, y] = (255, 255, 255, 0)
                elif pixdata[x, y] != (255, 255, 255, 0):
                    break
        # if not path.endswith('png'):
        #     os.remove(path)
        #     replace_path_list = path.split('.')
        #     replace_path_list = replace_path_list[:-1]
        #     path = '.'.join(replace_path_list) + '.png'
        #
        # img.save(path)
        img = img.resize((250, 250))
        img.show()
        img.save('250.250.png')
        # img.close()
    except Exception as e:
        print(e)
        return False
    return path


def get_convert_middle(img_path):
    I = Image.open(img_path)
    L = I.convert('L')
    im = numpy.array(L)
    im4 = 255.0 * (im / 255.0) ** 2  # 对图像的像素值求平方后得到的图像
    middle = (int(im4.min()) + int(im4.max())) / 2
    return middle

# 调用 transparent_background, 传入图片路径, 该方法把图片修改后替换了源文件


if __name__ == '__main__':
    transparent_background('a.jpg')
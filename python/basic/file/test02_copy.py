# 拷贝文件需要以二进制的方式进行文件的读写，以字符的形式读取二进制文件时可能会出错，因为有些字节不能正确的转换为字符

with open(r'/home/elim/a.jpg', 'rb') as src:
    with open(r'/home/elim/a.copy.jpg', 'wb') as target:
        for line in src:
            target.write(line)


with open(r'/home/elim/a.jpg', 'rb') as src:
    print(len(src.readlines()))
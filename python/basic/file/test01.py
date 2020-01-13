# 通过open()方法可以打开一个文件，打开的模式默认是只读，即r，其它可选项包括：
# w:写
# a:追加写
# b:以二进制方式进行操作，默认是以字符进行操作
# +:读写


f = open('/home/elim/file1.txt', 'r')   # 默认也是读模式

for line in f:
    print(line)

f.close()

f = open(r'/home/elim/file1.txt', 'a')  # 路径前加的r是字符串的语法，表示原始字符，减少转译的写法
for i in range(10):
    f.write('这是第{0}行'.format(i+1))
f.close()
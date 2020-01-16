# 通过open()方法可以打开一个文件，打开的模式默认是只读，即r，其它可选项包括：
# w:写
# a:追加写
# b:以二进制方式进行操作，默认是以字符进行操作
# +:读写


f = open('/home/elim/file1.txt', 'r')   # 默认也是读模式

print(f.name) # 打印文件名

for line in f:
    print(line)

f.close()

# 标准的文件打开和关闭操作。try...except...finally

try:
    f = open(r'/home/elim/file1.txt', 'w')  # 路径前加的r是字符串的语法，表示原始字符，减少转译的写法
    for i in range(10):
        f.write('这是第{0}行\n'.format(i+1))
    f.writelines(['这是第{0}行\n'.format(x+1) for x in range(20)])
finally:
    if f:
        f.close()

# 更简单的关闭资源的方法是使用with open语句，这样python将自动关闭资源

with open(r'/home/elim/file1.txt', 'w') as f:   # with块运行完后会自动关闭打开的资源文件
    f.write('Hello 中国World!Hello World!Hello World!Hello World!\nHello World!\nHello World!')

# open(r'/home/elim/file1.txt', 'w', encoding='GBK')  # 指定字符集


print('==============================')
with open(r'/home/elim/file1.txt') as f:
    print(f.read(10))   # 读取10个字符
    print(f.read()) # 一直读到文件结束

print('==============================')
with open(r'/home/elim/file1.txt') as f:
    print(f.readlines())   # 读取所有的行，以列表形式返回。

with open(r'/home/elim/file1.txt') as f:
    print(f.tell()) # 打印当前文件指针的索引位置
    f.seek(5)   # 指针跳转到5的位置
    print(f.read(10))   # 从5的位置开始读10个字符
    # 下面语句会报错，因为默认以字符读取文件时只能从文件头开始跳过
    # f.seek(10, 1)   # 从当前位置往后跳10个位置
    print(f.read(5))

print('------------分界线---------------' * 5)
with open(r'/home/elim/file1.txt', 'rb') as f:  # 以字节形式读
    f.seek(5)   # 从文件头开始跳转到位置5
    print(f.tell()) # 5
    print(f.read(10))   # 从位置5开始往后读10个字节
    f.seek(5, 1) # 从当前位置开始往后跳5个字节
    print(f.tell()) # 到了位置20
    f.seek(-5, 1)   # 从当前位置往前跳转5个位置
    print(f.tell()) # 到了位置15
    f.seek(10) # 从文件头开始跳转到位置10
    print(f.tell()) # 10
    f.seek(10, 2) # 从文件尾开始跳转10个位置，即到了倒数10位
    print(f.tell()) # 90


# 通过open()方法可以打开一个文件，打开的模式默认是只读，即r，其它可选项包括：
# w:写
# a:追加写
# b:以二进制方式进行操作，默认是以字符进行操作
# +:读写


f = open('/home/elim/file1.txt', 'r')   # 默认也是读模式

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
    f.write('Hello World!')

# open(r'/home/elim/file1.txt', 'w', encoding='GBK')  # 指定字符集


print('==============================')
with open(r'/home/elim/file1.txt') as f:
    print(f.read(10))   # 读取10个字符
    print(f.read()) # 一直读到文件结束

print('==============================')
with open(r'/home/elim/file1.txt') as f:
    print(f.readlines())   # 读取所有的行，以列表形式返回。
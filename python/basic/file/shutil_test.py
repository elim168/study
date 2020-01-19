# shutil模块测试，用于拷贝/压缩文件等

import shutil

# shutil.copy('test.csv', 'test_copy.csv')    # 拷贝test.csv为test_copy.csv
# shutil.copyfile('test.csv', 'test_copy_2.csv')
#
# shutil.copytree('../file', '../file2')  # 拷贝目录file为file2
# # 拷贝目录file为file2_ignore，但是会忽略文件中以test和os开头的文件
# shutil.copytree('../file', '../file2_ignore', ignore=shutil.ignore_patterns('test*', 'os*'))

# 把父母录的class以zip格式进行压缩，压缩后生成test.zip文件在当前目录下。test也可以指定为绝对路径，这里是相对路径
shutil.make_archive('test', 'zip', '../class')

shutil.unpack_archive('test.zip', 'extracted', 'zip')   # 把当前目录下的test.zip文件以zip算法进行解压缩到当前目录下的extracted目录下

print(shutil.get_archive_formats()) # 获取可用的压缩算法
print(shutil.get_unpack_formats()) # 获取可用的解压缩算法

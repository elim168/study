# Excel是可以另存为csv文件的，其中只包含数据，没有格式。通过csv模块可以对csv文件进行读写

import csv

with open('test.csv', 'w') as f: # 测试写入内容到csv文件中
    writer = csv.writer(f) # 获取一个写入器
    writer.writerow(["张三", 30, '男']) # 写入一行
    writer.writerow(["李四", 32, '男'])
    writer.writerows([['王五', 33, '男'],['赵六', 35, '男']]) # 写入多行

with open('test.csv') as f: # 测试从csv文件读取内容
    reader = csv.reader(f)  # 返回一个可迭代文件对象
    for row in reader:
        print(row)

'''
['张三', '30', '男']
['李四', '32', '男']
['王五', '33', '男']
['赵六', '35', '男']
'''

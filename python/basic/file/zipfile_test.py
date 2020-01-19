# zipfile用于压缩文件的操作

import zipfile

with zipfile.ZipFile('test_zipfile.zip', 'w') as f:
    f.write('test.csv')
    f.write('zipfile_test.py')
    f.write('zipfile_test.py', 'rename.py') # 把当前目录下的zipfile_test.py压缩为rename.py文件。

with zipfile.ZipFile('test_zipfile.zip', 'r') as f:
    f.extractall('extracted_zip')   # 解压所有的文件到extracted_zip目录下。

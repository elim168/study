#常见的数据存档和压缩格式由模块直接支持，包括：zlib, gzip, bz2, lzma, zipfile 和 tarfile。

import zlib
s = b'witch which has which witches wrist watch'
print(s)
print('the len is:', len(s))

t = zlib.compress(s)
print('压缩后的内容是：', t)
print('the len is:', len(t))

print('对t解压缩后的内容是：', zlib.decompress(t))

print()

print(zlib.crc32(s))

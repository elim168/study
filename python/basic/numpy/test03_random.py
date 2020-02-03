import numpy

# 生成一维的随机数组
ndarray = numpy.random.random(10) # 随机生成10个[0, 1.0)的浮点数组成一个ndarray类型的数组
print(ndarray)

# 生成二维的随机数组，下面生成的是3行5列的数组
print(numpy.random.random((3, 5)))

# 生成四维的随机数组则传递一个4位的元组，多维的也是类似的
print(numpy.random.random((3, 4, 5, 6)))


# -------随机生成整数数组-------------
ndarray = numpy.random.randint(8, size=10)    # 随机生成10个[0,8)的整数组成的ndarray
print(ndarray)

ndarray = numpy.random.randint(10, 100, size=10)  # 随机生成10个[10,100)的整数组成的ndarray
print(ndarray)

# 需要生成多维的整数数组，则将size替换为多维的元组，如下生成的是一个三维的数组
ndarray = numpy.random.randint(100, size=(2, 3, 5))
print(ndarray)

# ---------生成符合标准正太分布的数组。期望为0,方差为1
ndarray = numpy.random.randn(10)    # 生成一维的数组，长度为10
print(ndarray)

ndarray = numpy.random.randn(2, 3, 4)   # 生成三维的数组，长度分别为2/3/4
print(ndarray)

# ----------生成指定期望和方差的数组----------------
ndarray = numpy.random.normal(size=10)  # 以默认的期望是0,方差是1,生成长度为10的数组
print(ndarray)

ndarray = numpy.random.normal(loc=2, scale=2.5, size=(2, 5))  # 以期望为2,方差为2.5生成两行5列的二维数组
print(ndarray)

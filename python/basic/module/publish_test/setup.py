import setuptools

# 把模块的说明文档写入到README.md中
with open("README.md", "r") as fh:
    long_description = fh.read()

setuptools.setup(
    name='mymodule-test',  # 需要发布的模块的名称
    version='1.0.2',  # 需要发布的模块的版本号
    description='这是一个对外发布的模块，发布后可以供他人使用。测试/test',  # 简单描述
    long_description=long_description,  # 模块的说明文档
    long_description_content_type="text/markdown",  # 模块说明文档的格式
    author='elim',
    author_email='yeelimzhang@163.com',
    # py_modules=['mymodule.module1', 'mymodule.module2'],  # 需要对外发布的模块
    packages=setuptools.find_packages(),    # 自动获取所有的包
    classifiers=[
        "Programming Language :: Python :: 3",
        "License :: OSI Approved :: MIT License",
        "Operating System :: OS Independent",
    ],
    python_requires='>=3.6',
)

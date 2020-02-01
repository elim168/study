from setuptools import setup

setup(
    name='mymodule',    # 需要发布的模块的名称
    version='1.0.0',    # 需要发布的模块的版本号
    description='这是一个对外发布的模块，发布后可以供他人使用',
    author='elim',
    author_email='yeelimzhang@163.com',
    py_modules=['mymodule.module1', 'mymodule.module2'] # 需要对外发布的模块
)

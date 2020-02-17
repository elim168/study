import re


# findall可以找到所有匹配到的字符串，而search只会取一个
result = re.findall(r'\d+', 'a123bbbcc1234ccc898')
print(result)  # ['123', '1234', '898']


# finditer作用和findall类似，它返回的是一个iterator对象，里面的每个元素又是一个匹配结果对象，需要通过for循环进行迭代获取。
iter = re.finditer(r'\d+', 'a123bbbcc1234ccc898')
for i in iter:
    print(i)  # <_sre.SRE_Match object; span=(1, 4), match='123'>
    print(i.group())  # 123

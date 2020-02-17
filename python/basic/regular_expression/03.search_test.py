# 测试正则表达式的search方法，它跟match方法的语法是一样的。match是从字符串的开头部分开始匹配，search可以从指定字符串中找匹配正则表达式的部分。

# 首先需要引入re模块
import re

pattern = 'wo'
string = 'hello world'
result = re.search(pattern, string)
print(result)  # <_sre.SRE_Match object; span=(6, 8), match='wo'>
# group获取匹配到的内容
print(result.group())  # wo


result = re.search(r'\d{3}(\d{4})(?P<group_name_1>\d{3})', 'aaaa0123456789bbbb')
print(result)  # <_sre.SRE_Match object; span=(4, 14), match='0123456789'>
print(result.group())  # 0123456789
# 获取所有的分组
print(result.groups())  # ('3456', '789')
# 获取命名分组
print(result.groupdict())  # {'group_name_1': '789'}
# 获取匹配到的指定命名分组
print(result.group('group_name_1'))  # 789

# 在正则表达式中可以通过\数字引用分组，\1表示第一个分组，\2表示第2个，\N表示第N个。
result = re.search(r'(\d{3})\1', '12312304567')
print(result)  # <_sre.SRE_Match object; span=(0, 6), match='123123'>
# 对于命名分组，如果需要以命名分组引用时，语法是“(?P=group_name)”。
result = re.search(r'(?P<g_name>\d{3})(?P=g_name)\1', '12312312304567')
print(result)  # <_sre.SRE_Match object; span=(0, 6), match='123123123'>



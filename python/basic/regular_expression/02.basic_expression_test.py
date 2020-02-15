# python正则表达式的语法和java中的类似，它也支持.、*、+、?、[]、{}、\d、\w、\s和转译字符等

import re
print(re.match('.{5}', 'abcd') is not None)  # False
print(re.match('.{5}', 'abcdef') is not None)  # True
print(re.match('.{5,}', 'abcdef') is not None)  # True
print(re.match('.{5,6}', 'abcdefgh') is not None)  # True
# 使用转译字符匹配原始的点
print(re.match('\\.abc', '.abcdefgh') is not None)  # True
# \w匹配所有的字母、数字和下划线
print(re.match(r'\w', 'abcdef') is not None)  # True
print(re.match(r'\w', '/abcdef') is not None)  # False
print(re.match(r'\W', '/abcdef') is not None)  # True
print(re.match(r'\d+', '1111/abcdef') is not None)  # True
print(re.match(r'\d+', 'a1111/abcdef') is not None)  # False
print(re.match(r'\D+', 'a1111/abcdef') is not None)  # True
print(re.match(r'\s', 'a1111/abcdef') is not None)  # False
print(re.match(r'\s', '\na1111/abcdef') is not None)  # True
print(re.match(r'\S', '\na1111/abcdef') is not None)  # False
print(re.match(r'\d?[abc]\w{3}\d*', '\na1111/abcdef') is not None)  # False
print(re.match(r'\d?[abc]\w{3}\d*', 'a1111/abcdef') is not None)  # True
print(re.match(r'\d?[abc]\w{3}\d*', '1a1111/abcdef') is not None)  # True
print(re.match(r'\d?[abc]\w{3}\d*', '1e1111/abcdef') is not None)  # False





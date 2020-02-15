# 测试正则表达式的match方法

# 首先需要引入re模块
import re

pattern = 'hello'
string = 'hello world'
# match是从起始位置开始匹配的，如果起始位置不能匹配给定的正则表达式，则不匹配。所以当对上面的string匹配world时返回的就是None
result = re.match(pattern, string)
if result:  # 不为None则匹配成功
    print('matched', result)
    print(dir(result))
    print(result.group())  # 输出匹配的字符串本身
    print(result.start())  # 输出匹配的字符串的起始位置
    print(result.end())  # 输出匹配的字符串的终止位置
    print(result.span())  # 输出匹配的字符串的起始位置和终止位置

print(re.match('world', string))  # None

# 正则表达式默认是区分大小写匹配的。下面最后一个参数指定忽略大小写，所以下面的内容也可以匹配。
print(re.match('Hello', string, re.I))  # 匹配结果对象，非None

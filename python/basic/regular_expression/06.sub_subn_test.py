# sub和subn用于字符串替换，它们的区别在于sub直接返回替换后的字符串，而subn会以元组的形式多返回一个替换数量。

import re


result = re.sub(r'\d+', '-', 'AAA123BBB234CCC')
print(result)  # AAA-BBB-CCC


result = re.subn(r'\d+', '-', 'AAA123BBB234CCC')
print(result)  # ('AAA-BBB-CCC', 2)

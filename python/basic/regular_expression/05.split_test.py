import re


# split用于基于正则表达式进行拆分
result = re.split(r'\d+', 'AAA123BBB3CCC89DDD889EEE')
print(result)  # ['AAA', 'BBB', 'CCC', 'DDD', 'EEE']


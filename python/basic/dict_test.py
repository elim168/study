'''
字典，键值对

'''

#直接通过花括号里面的键值对初始化一个字典
a = {'key1': 'value1', 'key2': 'value2'}

print(a)    #{'key1': 'value1', 'key2': 'value2'}

#通过dict()创建字典

a = dict(key1 = 'value1', key2 = [1, 2, 3])
print(a)    #{'key1': 'value1', 'key2': [1, 2, 3]}

##也可以把元组列表丢到dict()中来创建字典
a = dict([('key1', 'value1'), ('key2', 'value2'), ('key3', [1, 2, 3])])    #每一个元组包含两个元素，第一个是Key，第二个是Value
print(a)    #{'key1': 'value1', 'key2': 'value2', 'key3': [1, 2, 3]}

#还可以通过zip
keys = ['key1', 'key2', 'key3'] #列表也可以换成是元组
values = ['value1', 'value2', 'value3']
a = zip(keys, values)   #组合两个列表或元素，加上dict后，第一个列表中的都为Key，第二个都为Value，相同位置的组成键值对。
print(dict(a))  #{'key1': 'value1', 'key2': 'value2', 'key3': 'value3'}


##构造只有Key，Value为None的字典
a = dict.fromkeys(('key1', 'key2', 'key3'))
print(a)    #{'key1': None, 'key2': None, 'key3': None}

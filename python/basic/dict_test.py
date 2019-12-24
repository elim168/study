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

print('key1' in a)  #True


a = {'A': 10, 'B': 20, 'C': 30}
print(a['A'] == a.get('A') == 10)
'''
可以通过dictObj[key]或dictObj.get(key)获取字典中指定Key的值。
通过dictObj[key]形式获取时，如果字典中不存在key，则抛出异常KeyError；
而dictObj.get(key)时如果key不存在则返回None。另外dictObj.get(key, defaultValue)还可以在key不存在时返回指定的defaultValue。
'''
print(a.get('AA'))  #None
print(a.get('AA', 50))  #50

print(a.items())    #dict_items([('A', 10), ('B', 20), ('C', 30)])
print(a.keys()) #dict_keys(['A', 'B', 'C'])
print(a.values())   #dict_values([10, 20, 30])
print(sum(a.values()))
print(len(a))


a['D'] = 40 #新增或修改键值对
print(a)

b = {'D': 100, 'E': 200}
a.update(b) #把字典b中的内容全部合并到字典a中。
print(a)

del(a['A']) #删除字典中的键
print(a)
print(a.pop('B'))   #删除指定的键，并返回值

print(a.popitem())  #随机删除一个键值对，并返回键值对
print(a)

a.clear()   #清除所有的内容
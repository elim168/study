sum = 0
for i in range(100):
    sum += i+1
print('1-100累加的结果是：', sum)

d = {'name': '张三', 'age': 30}

for x in d.items():
    print(x[0], x[1])
else:
    print('循环正常结束，没有被break，执行else语句')

for x in range(10):
    if x == 5:
        break
else:
    print('循环被break了，所以此处不会被执行。')

names = ('张三', '李四', '王五')
ages = (30, 40, 50)

for name, age in zip(names, ages):
    print('name:{0}, age:{1}'.format(name, age))


sum = 0
for i in range(100):
    sum += i+1
print('1-100累加的结果是：', sum)

d = {'name': '张三', 'age': 30}

for x in d.items():
    print(x[0], x[1])
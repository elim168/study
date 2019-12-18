import random
items = ['apple', 'pear', 'banana']
print("items =", items)

for i in range(10):
    print('本次选择的是：', random.choice(items))

print('从一个列表中随机选择10个数')
print('从0-100随机选择10个数', random.sample(range(100), 10))
print('从0-10随机选择10个数', random.sample(range(10), 10))

print('产生一个随机数,小于1的浮点数', random.random())
print('产生一个100以内的随机数', random.randrange(100))

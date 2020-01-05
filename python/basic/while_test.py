# while循环

len = 100
sum = 0
i = 0
while i < len:
    i += 1
    sum += i
print('1-{0}累加的和是{1}'.format(len, sum))



i = 0
sum = 0
while i < 100:
    i += 1
    if i % 7 == 0 or i % 9 == 0:
        continue
    print(i)
    sum += i
    if sum > 200:
        break

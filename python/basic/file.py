def write():
    f = open('a.txt', 'a')
    for i in range(20):
        f.write('Hello---' + str(i))
        f.write('\n')
    f.close()

def write2(times):
    with open('a.txt', 'a') as f:
        for i in range(times):
            f.write('hello write2------' + str(i))
            f.write('\n')



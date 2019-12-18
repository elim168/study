from urllib.request import urlopen
#with urlopen('http://tycho.usno.navy.mil/cgi-bin/timer.pl') as response:
#    for line in response:
#        line = line.decode('utf-8')  # Decoding the binary data to text.
#        if 'EST' in line or 'EDT' in line:  # look for Eastern Time
#            print(line)


print('以下是访问https://cn.bing.com/的结果')
with urlopen('https://cn.bing.com/') as response:
    for line in response:
        print(line)
        print(line.decode('utf-8'))

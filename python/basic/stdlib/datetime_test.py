from datetime import date
now = date.today()
print('now is ', now)

#输出的是类似下面这样的格式：
#'12-02-03. 02 Dec 2003 is a Tuesday on the 02 day of December.'
print(now.strftime('%m-%d-%y. %d %b %Y is a %A on the %d day of %B.'))

print('now is', now.strftime('%Y-%m-%d'))

birthday = date(1964, 7, 31)
print(birthday)

age = now - birthday

print('now - birthday = ', age)

print(age.days)

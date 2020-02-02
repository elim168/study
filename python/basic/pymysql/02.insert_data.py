import pymysql

# 创建一个mysql数据库连接
connection = pymysql.connect('localhost',   # 数据库ip
                             'root', # 用户名
                             '123456', # 密码
                             'test_db', # 需要连接的数据库名称
                             3306 # 端口号
                             )

# 插入语句中的占位符用%s，而不是?
insert_sql = 'insert into t_user(username, password, nickname) values(%s, %s, %s)'

# 也可以使用参数名占位符，注意参数名占位符格式后的s。
insert_sql2 = 'insert into t_user(username, password, nickname) values(%(username)s, %(password)s, %(nickname)s)'


cursor = connection.cursor()
r = cursor.execute(insert_sql, ('user1', 'password', '昵称-1'))
print(r)    # 1，成功插入的行数
# 参数名占位符的参数通过字典传递
# cursor.execute(insert_sql2, {'username': 'user100', 'password': 'password', 'nickname': '昵称-100'})
datas = []
for i in range(2, 11):
    datas.append(('user{0}'.format(i), 'password', '昵称-{0}'.format(i)))

# 批量插入
r = cursor.executemany(insert_sql, datas)
print(r) # 9，成功插入的行数

# 注意需要进行提交操作
connection.commit()

# rollback可以用来回滚
# connection.rollback()

cursor.close()
connection.close()



import sqlite3

database = 'database_data_file.db'  # 数据库文件
connection = sqlite3.connect(database)  # 创建数据库连接
cursor = connection.cursor()    # 创建游标对象
create_sql = '''create table t_user(
    id integer primary key autoincrement,
    username varchar not null,
    password varchar not null
)
'''
try:
    cursor.execute(create_sql)
except Exception as e:  # 表只能建一次，重复执行会失败
    print('建表失败', e)
insert_sql = 'insert into t_user(username, password) values(?, ?)'
try:
    cursor.execute(insert_sql, ('user1', 'password'))   # 执行单条插入
    connection.commit() # 插入成功时进行提交

except Exception as e:
    print('插入数据失败', e)
    connection.rollback()   # 插入数据失败时进行回滚


users = []
for i in range(2, 11):
    users.append(('user{0}'.format(i), 'password'))
r = cursor.executemany(insert_sql, users)   # 执行批量插入

# connection.commit() # 需要提交事务

# 参数还可以使用参数名占位符
cursor.execute('update t_user set password=:password where id=:id', {'id': 136, 'password': 'new_password'})

rows = cursor.execute('select * from t_user')
for row in rows:
    print(row)

cursor.close()
connection.close()



connection = sqlite3.connect(database)
connection.execute(insert_sql, ('user100', 'password')) # 也可以直接通过connection的execute方法执行SQL
connection.execute('update t_user set password=? where id=?', ('new_password', 150)) # 修改

connection.commit()

connection.execute('delete from t_user where id = ?', (169,))   # 删除
connection.commit()


with connection:    # with语句包含的connection里面的语句执行完成后会自动调用connection.commit()
    connection.execute(insert_sql, ('user101', 'password'))

connection.close()

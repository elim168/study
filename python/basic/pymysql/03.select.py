import pymysql

# 创建一个mysql数据库连接
connection = pymysql.connect('localhost',   # 数据库ip
                             'root', # 用户名
                             '123456', # 密码
                             'test_db', # 需要连接的数据库名称
                             3306 # 端口号
                             )

cursor = connection.cursor()
r = cursor.execute('select * from t_user')
print(r)    # 记录数
rows = cursor.fetchall()
# cursor.fetchmany(10)
for row in rows:
    print(row)

cursor.close()
connection.close()
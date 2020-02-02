import sqlite3

database = 'database_data_file.db'  # 数据库文件
connection = sqlite3.connect(database)  # 创建数据库连接

cursor = connection.cursor()
try:
    connection.execute('delete from t_user where id > ?', (135,))
    connection.commit()
    rows = cursor.execute('select * from t_user')
    for row in rows:
        print(row)
finally:
    cursor.close()
    connection.close()

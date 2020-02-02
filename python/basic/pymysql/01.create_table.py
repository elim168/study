import pymysql

# 创建一个mysql数据库连接
connection = pymysql.connect('localhost',   # 数据库ip
                             'root', # 用户名
                             '123456', # 密码
                             'test_db', # 需要连接的数据库名称
                             3306 # 端口号
                             )
# 创建数据库连接的完整参数信息见下文
'''
    def __init__(self, host=None, user=None, password="",
                 database=None, port=0, unix_socket=None,
                 charset='', sql_mode=None,
                 read_default_file=None, conv=None, use_unicode=None,
                 client_flag=0, cursorclass=Cursor, init_command=None,
                 connect_timeout=10, ssl=None, read_default_group=None,
                 compress=None, named_pipe=None,
                 autocommit=False, db=None, passwd=None, local_infile=False,
                 max_allowed_packet=16*1024*1024, defer_connect=False,
                 auth_plugin_map=None, read_timeout=None, write_timeout=None,
                 bind_address=None, binary_prefix=False, program_name=None,
                 server_public_key=None):
'''

print(connection.autocommit_mode, connection.bind_address, connection.charset, connection.character_set_name(), connection.server_charset, connection.server_version, connection.db, connection.connect_timeout)


create_sql = '''create table t_user(
    id integer primary key auto_increment,
    username varchar(30) not null,
    password varchar(30) not null,
    nickname varchar(30) not null
)'''

# 创建一个cursor
cursor = connection.cursor()

try:
    cursor.execute(create_sql)
except Exception as e:
    print('SQL执行失败', e)
finally:
    cursor.close()
    connection.close()

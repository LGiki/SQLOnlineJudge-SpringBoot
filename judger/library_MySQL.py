import pymysql
import uuid
from library_common import get_config_value

# 读取config文件
def init_config(config_parser):
    MYSQL_JUDGE_DB_HOST = get_config_value(config_parser, 'Judge_MySQL', 'host')
    MYSQL_JUDGE_DB_PORT = int(get_config_value(config_parser, 'Judge_MySQL', 'port'))
    MYSQL_JUDGE_DB_USERNAME = get_config_value(config_parser, 'Judge_MySQL', 'username')
    MYSQL_JUDGE_DB_PASSWORD = get_config_value(config_parser, 'Judge_MySQL', 'password')
    MYSQL_JUDGE_DB_CHARSET = get_config_value(config_parser, 'Judge_MySQL', 'charset')
    DB_HOST = get_config_value(config_parser, 'Main_Database', 'host')
    DB_USERNAME = get_config_value(config_parser, 'Main_Database', 'username')
    DB_PASSWORD = get_config_value(config_parser, 'Main_Database', 'password')
    DB_DATABASE = get_config_value(config_parser, 'Main_Database', 'database')
    DB_CHARSET = get_config_value(config_parser, 'Main_Database', 'charset')
    return MYSQL_JUDGE_DB_HOST, MYSQL_JUDGE_DB_PORT, MYSQL_JUDGE_DB_USERNAME, MYSQL_JUDGE_DB_PASSWORD, MYSQL_JUDGE_DB_CHARSET, DB_HOST, DB_USERNAME, DB_PASSWORD, DB_DATABASE, DB_CHARSET


# 执行MySQL代码
def exec_code(cursor, source_code):
    exec_result = None
    try:
        cursor.execute(source_code)
        exec_result = cursor.fetchall()
    except BaseException as e:
        return None, str(e)
    return str(exec_result), None


# 执行MySQL脚本
def exec_script(conn, cursor, source_code):
    sqlCommands = source_code.split(';')
    for command in sqlCommands:
        try:
            if command.strip() != '':
                cursor.execute(command)
                conn.commit()
        except BaseException as e:
            return False, str(e)
    return True, None


# 创建MySQL数据库
def create_database_mysql(MYSQL_JUDGE_DB_HOST, MYSQL_JUDGE_DB_PORT, MYSQL_JUDGE_DB_USERNAME, MYSQL_JUDGE_DB_PASSWORD,
                          judge_db_name):
    judge_db_conn = pymysql.connect(
        host=MYSQL_JUDGE_DB_HOST,
        port=MYSQL_JUDGE_DB_PORT,
        user=MYSQL_JUDGE_DB_USERNAME,
        password=MYSQL_JUDGE_DB_PASSWORD
    )
    judge_db_cursor = judge_db_conn.cursor()
    judge_db_cursor.execute(f"CREATE DATABASE {judge_db_name}")
    judge_db_cursor.close()
    judge_db_conn.close()


# 删除MySQL数据库
def drop_database_mysql(MYSQL_JUDGE_DB_HOST, MYSQL_JUDGE_DB_PORT, MYSQL_JUDGE_DB_USERNAME, MYSQL_JUDGE_DB_PASSWORD,
                        judge_db_name):
    judge_db_conn = pymysql.connect(
        host=MYSQL_JUDGE_DB_HOST,
        port=MYSQL_JUDGE_DB_PORT,
        user=MYSQL_JUDGE_DB_USERNAME,
        password=MYSQL_JUDGE_DB_PASSWORD
    )
    judge_db_cursor = judge_db_conn.cursor()
    judge_db_cursor.execute(f"DROP DATABASE {judge_db_name}")
    judge_db_cursor.close()
    judge_db_conn.close()


# 随机生成数据库名
def generate_random_db_name():
    return str(uuid.uuid4()).replace('-', '')

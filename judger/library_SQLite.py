import sqlite3
import os
from library_common import get_config_value


# 读取config文件
def init_config(config_parser):
    SQLITE_DIR = get_config_value(config_parser, 'Judge_SQLite', 'sqlite_dir')
    SQLITE_TEMP_DIR = get_config_value(config_parser, 'Judge_SQLite', 'temp_sqlite_dir')
    DB_HOST = get_config_value(config_parser, 'Main_Database', 'host')
    DB_USERNAME = get_config_value(config_parser, 'Main_Database', 'username')
    DB_PASSWORD = get_config_value(config_parser, 'Main_Database', 'password')
    DB_DATABASE = get_config_value(config_parser, 'Main_Database', 'database')
    DB_CHARSET = get_config_value(config_parser, 'Main_Database', 'charset')
    return SQLITE_DIR, SQLITE_TEMP_DIR, DB_HOST, DB_USERNAME, DB_PASSWORD, DB_DATABASE, DB_CHARSET


# 执行sqlite代码
def exec_code(sqlite_db_file_path, source_code):
    exec_result = None
    try:
        sqlite_conn = sqlite3.connect(sqlite_db_file_path)
        sqlite_cursor = sqlite_conn.cursor()
        sqlite_cursor.execute(source_code)
        exec_result = sqlite_cursor.fetchall()
    except BaseException as e:
        return None, str(e)
    return str(exec_result), None


# 执行sqlite脚本
def exec_script(sqlite_db_file_path, source_code):
    try:
        sqlite_conn = sqlite3.connect(sqlite_db_file_path)
        sqlite_cursor = sqlite_conn.cursor()
        sqlite_cursor.executescript(source_code)
        sqlite_conn.commit()
    except BaseException as e:
        return False, str(e)
    return True, None


# 初始化工作目录
def init_work_directory(SQLITE_TEMP_DIR):
    if not os.path.exists(SQLITE_TEMP_DIR):
        os.mkdir(SQLITE_TEMP_DIR)

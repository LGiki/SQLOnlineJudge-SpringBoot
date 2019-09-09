#!/usr/bin/python
# -*- coding: UTF-8 -*-

import pymysql
import os
import sqlite3
import json
import configparser

# 配置文件路径
CONFIG_FILE_PATH = './config.ini'
# 返回状态码
RESPONSE_CODE = {
    'OK': 0,
    'FAIL': 1
}


# 生成json返回值
def construct_json_response(code, data, message):
    return json.dumps(
        {
            'code': code,
            'data': data,
            'message': message
        }
    )


# 获取config项的value
def get_config_value(config_parser, category, name):
    return config_parser.get(category, name)


# 读取config文件
def init_config(config_parser):
    SQLITE_DIR = get_config_value(config_parser, 'Judge', 'sqlite_dir')
    SQLITE_TEMP_DIR = get_config_value(config_parser, 'Judge', 'temp_sqlite_dir')
    DB_HOST = get_config_value(config_parser, 'Main_Database', 'host')
    DB_USERNAME = get_config_value(config_parser, 'Main_Database', 'username')
    DB_PASSWORD = get_config_value(config_parser, 'Main_Database', 'password')
    DB_DATABASE = get_config_value(config_parser, 'Main_Database', 'database')
    DB_CHARSET = get_config_value(config_parser, 'Main_Database', 'charset')
    return SQLITE_DIR, SQLITE_TEMP_DIR, DB_HOST, DB_USERNAME, DB_PASSWORD, DB_DATABASE, DB_CHARSET


# 初始化工作目录
def init_work_directory(SQLITE_DIR):
    if not os.path.exists(SQLITE_DIR):
        os.mkdir(SQLITE_DIR)
        # logger.info('Create SQLite Dir: {}'.format(SQLITE_DIR))


# 创建数据库
def create_database(SQLITE_DIR, cursor, database_id):
    sql = '''
        select `create_table`, `test_data`, `is_created`
        from `data_base`
        where id = %s 
        '''
    cursor.execute(sql, [database_id])
    select_result = cursor.fetchall()
    sqlite_db_file_path = os.path.join(SQLITE_DIR, '{}.db'.format(database_id))
    if os.path.exists(sqlite_db_file_path):
        os.remove(sqlite_db_file_path)
    create_table = select_result[0][0]
    test_data = select_result[0][1]
    sqlite_conn = sqlite3.connect(sqlite_db_file_path)
    sqlite_cursor = sqlite_conn.cursor()
    try:
        sqlite_cursor.executescript(create_table)
        sqlite_cursor.executescript(test_data)
        sqlite_conn.commit()
    except BaseException as e:
        return False, str(e)
    sqlite_cursor.close()
    sqlite_conn.close()
    return True, None


def main(database_id):
    config_parser = configparser.ConfigParser()
    if len(config_parser.read(CONFIG_FILE_PATH)) == 0:
        return construct_json_response(RESPONSE_CODE['FAIL'], None, 'Can not load config.ini.')
    SQLITE_DIR, SQLITE_TEMP_DIR, DB_HOST, DB_USERNAME, DB_PASSWORD, DB_DATABASE, DB_CHARSET = init_config(config_parser)
    init_work_directory(SQLITE_DIR)
    conn = pymysql.connect(
        host=DB_HOST,
        user=DB_USERNAME,
        password=DB_PASSWORD,
        database=DB_DATABASE,
        charset=DB_CHARSET
    )
    cursor = conn.cursor()
    create_result, create_exception = create_database(SQLITE_DIR, cursor, database_id)
    cursor.close()
    conn.close()
    if create_result:
        return construct_json_response(RESPONSE_CODE['OK'], None, 'OK')
    else:
        return construct_json_response(RESPONSE_CODE['FAIL'], None, create_exception)


if __name__ == '__main__':
    print(main(1))

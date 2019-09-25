#!/usr/bin/python
# -*- coding: UTF-8 -*-

import os
import sqlite3
import json
import configparser
import shutil
from sys import argv

# 配置文件路径
CONFIG_FILE_PATH = './judger/config.ini'
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


# 通过问题ID获取正确答案
def get_true_result(SQLITE_DIR, SQLITE_TEMP_DIR, answer, database_id):
    sqlite_db_file_path = os.path.join(SQLITE_DIR, '{}.db'.format(database_id))
    temp_sqlite_db_file_path = os.path.join(SQLITE_TEMP_DIR, '{}_temp.db'.format(database_id))
    shutil.copyfile(sqlite_db_file_path, temp_sqlite_db_file_path)
    true_result, run_exception = exec_code(sqlite_db_file_path, answer)
    os.remove(temp_sqlite_db_file_path)
    if true_result is None:
        return None, run_exception
    return true_result, None


# 初始化工作目录
def init_work_directory(SQLITE_TEMP_DIR):
    if not os.path.exists(SQLITE_TEMP_DIR):
        os.mkdir(SQLITE_TEMP_DIR)
        # logger.info('Create Temp SQLite Dir: {}'.format(SQLITE_TEMP_DIR))


def main(answer, database_id):
    config_parser = configparser.ConfigParser()
    if len(config_parser.read(CONFIG_FILE_PATH)) == 0:
        return construct_json_response(RESPONSE_CODE['FAIL'], None, 'Can not load config.ini.')
    SQLITE_DIR, SQLITE_TEMP_DIR, DB_HOST, DB_USERNAME, DB_PASSWORD, DB_DATABASE, DB_CHARSET = init_config(config_parser)
    init_work_directory(SQLITE_TEMP_DIR)
    true_result, run_exception = get_true_result(SQLITE_DIR, SQLITE_TEMP_DIR, answer, database_id)
    if true_result is None:
        return construct_json_response(RESPONSE_CODE['FAIL'], None, run_exception)
    else:
        return construct_json_response(RESPONSE_CODE['OK'], {
            'trueResult': true_result
        }, None)


if __name__ == '__main__':
    print(main(argv[1], argv[2]))

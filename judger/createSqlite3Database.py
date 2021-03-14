#!/usr/bin/python
# -*- coding: UTF-8 -*-

import os
import sqlite3
import configparser
from sys import argv
import uuid
import shutil
from library_common import construct_json_response
from library_SQLite import init_config
from library_SQLite import init_work_directory
from config import CONFIG_FILE_PATH
from config import RESPONSE_CODE


# 创建数据库
def create_database(SQLITE_DIR, database_id, create_table, test_data):
    sqlite_db_file_path = os.path.join(SQLITE_DIR, '{}.db'.format(database_id))
    sqlite_db_file_path_temp = os.path.join(SQLITE_DIR, '{}_{}.db'.format(database_id, uuid.uuid4()))
    if os.path.exists(sqlite_db_file_path_temp):
        os.remove(sqlite_db_file_path_temp)
    sqlite_conn = sqlite3.connect(sqlite_db_file_path_temp)
    sqlite_cursor = sqlite_conn.cursor()
    try:
        sqlite_cursor.executescript(create_table)
        sqlite_cursor.executescript(test_data)
        sqlite_conn.commit()
    except BaseException as e:
        sqlite_cursor.close()
        sqlite_conn.close()
        os.remove(sqlite_db_file_path_temp)
        return False, str(e)
    sqlite_cursor.close()
    sqlite_conn.close()
    # 新数据库创建成功了才将旧数据库删除
    if os.path.exists(sqlite_db_file_path_temp):
        if os.path.exists(sqlite_db_file_path):
            os.remove(sqlite_db_file_path)
        shutil.copyfile(sqlite_db_file_path_temp, sqlite_db_file_path)
        os.remove(sqlite_db_file_path_temp)
    return True, None


def main(database_id, create_table, test_data):
    config_parser = configparser.ConfigParser()
    if len(config_parser.read(CONFIG_FILE_PATH)) == 0:
        return construct_json_response(RESPONSE_CODE['FAIL'], None, 'Can not load config.ini.')
    SQLITE_DIR, SQLITE_TEMP_DIR, DB_HOST, DB_USERNAME, DB_PASSWORD, DB_DATABASE, DB_CHARSET = init_config(config_parser)
    init_work_directory(SQLITE_DIR)
    create_result, create_exception = create_database(SQLITE_DIR, database_id, create_table, test_data)
    if create_result:
        return construct_json_response(RESPONSE_CODE['OK'], None, 'OK')
    else:
        return construct_json_response(RESPONSE_CODE['FAIL'], None, create_exception)


if __name__ == '__main__':
    print(main(argv[1], argv[2], argv[3]))

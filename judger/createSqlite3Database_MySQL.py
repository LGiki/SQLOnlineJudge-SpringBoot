#!/usr/bin/python
# -*- coding: UTF-8 -*-

import json
import configparser
from sys import argv
import uuid
import pymysql

# 配置文件路径
CONFIG_FILE_PATH = './judger/config.ini'
# 返回状态码
RESPONSE_CODE = {
    'OK': 0,
    'FAIL': 1,
    'NO_DB_FILE': 2
}


# 随机生成数据库名
def generate_random_db_name():
    return str(uuid.uuid4()).replace('-', '')


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
    MYSQL_JUDGE_DB_HOST = get_config_value(config_parser, 'Judge_MySQL', 'host')
    MYSQL_JUDGE_DB_PORT = get_config_value(config_parser, 'Judge_MySQL', 'port')
    MYSQL_JUDGE_DB_USERNAME = get_config_value(config_parser, 'Judge_MySQL', 'username')
    MYSQL_JUDGE_DB_PASSWORD = get_config_value(config_parser, 'Judge_MySQL', 'password')
    MYSQL_JUDGE_DB_CHARSET = get_config_value(config_parser, 'Judge_MySQL', 'charset')
    DB_HOST = get_config_value(config_parser, 'Main_Database', 'host')
    DB_USERNAME = get_config_value(config_parser, 'Main_Database', 'username')
    DB_PASSWORD = get_config_value(config_parser, 'Main_Database', 'password')
    DB_DATABASE = get_config_value(config_parser, 'Main_Database', 'database')
    DB_CHARSET = get_config_value(config_parser, 'Main_Database', 'charset')
    return MYSQL_JUDGE_DB_HOST, MYSQL_JUDGE_DB_PORT, MYSQL_JUDGE_DB_USERNAME, MYSQL_JUDGE_DB_PASSWORD, MYSQL_JUDGE_DB_CHARSET, DB_HOST, DB_USERNAME, DB_PASSWORD, DB_DATABASE, DB_CHARSET


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


# 创建数据库
def create_database(MYSQL_JUDGE_DB_HOST, MYSQL_JUDGE_DB_PORT, MYSQL_JUDGE_DB_USERNAME, MYSQL_JUDGE_DB_PASSWORD,
                    MYSQL_JUDGE_DB_CHARSET,
                    create_table, test_data):
    judge_db_name = generate_random_db_name()
    create_database_mysql(MYSQL_JUDGE_DB_HOST, MYSQL_JUDGE_DB_PORT, MYSQL_JUDGE_DB_USERNAME, MYSQL_JUDGE_DB_PASSWORD,
                          judge_db_name)
    judge_db_conn = pymysql.connect(
        host=MYSQL_JUDGE_DB_HOST,
        port=MYSQL_JUDGE_DB_PORT,
        user=MYSQL_JUDGE_DB_USERNAME,
        password=MYSQL_JUDGE_DB_PASSWORD,
        database=judge_db_name,
        charset=MYSQL_JUDGE_DB_CHARSET
    )
    judge_db_cursor = judge_db_conn.cursor()
    try:
        judge_db_cursor.executemany(create_table)
        judge_db_cursor.executemany(test_data)
        judge_db_cursor.commit()
    except BaseException as e:
        judge_db_cursor.close()
        judge_db_conn.close()
        drop_database_mysql(MYSQL_JUDGE_DB_HOST, MYSQL_JUDGE_DB_PORT, MYSQL_JUDGE_DB_USERNAME, MYSQL_JUDGE_DB_PASSWORD,
                            judge_db_name)
        return False, str(e)
    judge_db_cursor.close()
    judge_db_conn.close()
    drop_database_mysql(MYSQL_JUDGE_DB_HOST, MYSQL_JUDGE_DB_PORT, MYSQL_JUDGE_DB_USERNAME, MYSQL_JUDGE_DB_PASSWORD,
                        judge_db_name)
    return True, None


def main(database_id, create_table, test_data):
    config_parser = configparser.ConfigParser()
    if len(config_parser.read(CONFIG_FILE_PATH)) == 0:
        return construct_json_response(RESPONSE_CODE['FAIL'], None, 'Can not load config.ini.')
    MYSQL_JUDGE_DB_HOST, MYSQL_JUDGE_DB_PORT, MYSQL_JUDGE_DB_USERNAME, MYSQL_JUDGE_DB_PASSWORD, MYSQL_JUDGE_DB_CHARSET, DB_HOST, DB_USERNAME, DB_PASSWORD, DB_DATABASE, DB_CHARSET = init_config(
        config_parser)
    create_result, create_exception = create_database(MYSQL_JUDGE_DB_HOST, MYSQL_JUDGE_DB_PORT, MYSQL_JUDGE_DB_USERNAME,
                                                      MYSQL_JUDGE_DB_PASSWORD, MYSQL_JUDGE_DB_CHARSET, create_table,
                                                      test_data)
    if create_result:
        return construct_json_response(RESPONSE_CODE['OK'], None, 'OK')
    else:
        return construct_json_response(RESPONSE_CODE['FAIL'], None, create_exception)


if __name__ == '__main__':
    # argv[1] -> database_id
    # argv[2] -> create table code
    # argv[3] -> test data code
    print(main(argv[1], argv[2], argv[3]))

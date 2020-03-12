#!/usr/bin/python
# -*- coding: UTF-8 -*-

import os
import sqlite3
import json
import configparser
import shutil
from sys import argv
import uuid
import pymysql

# TODO
# 配置文件路径
CONFIG_FILE_PATH = './judger/config.ini'
# 返回状态码
RESPONSE_CODE = {
    'OK': 0,
    'FAIL': 1,
    'NO_DB_FILE': 2
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


# 执行sqlite代码
def exec_code(cursor, source_code):
    exec_result = None
    try:
        cursor.execute(source_code)
        exec_result = cursor.fetchall()
    except BaseException as e:
        return None, str(e)
    return str(exec_result), None


# 执行sqlite脚本
def exec_script(conn, cursor, source_code):
    try:
        cursor.executescript(source_code)
        conn.commit()
    except BaseException as e:
        return False, str(e)
    return True, None


# 判断是否是select类型的题目
def is_select_problem(answer):
    if answer.endswith(';'):
        answer = answer[:-1]
    splitted_answer = answer.split(';')
    if len(splitted_answer) > 1:
        return False
    return True


# 随机生成数据库名
def generate_random_db_name():
    return str(uuid.uuid4()).replace('-', '')


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


# 根据数据库 ID 获取数据库的建表语句与测试数据语句
def get_database_detail(cursor, database_id):
    sql = '''
    select `create_table`, `test_data`
    from `data_base`
    where `id` = %s
    '''
    cursor.execute(sql, [database_id])
    select_result = cursor.fetchall()
    create_table = None
    test_data = None
    if select_result and len(select_result) > 0:
        create_table = select_result[0][0]
        test_data = select_result[0][1]
    return create_table, test_data


# 分割答案，取出update/delete语句与select语句
def split_answer(answer):
    if answer.endswith(';'):
        answer = answer[:-1]
    splitted_answer_temp = answer.split(';')
    splitted_answer = []
    for code in splitted_answer_temp:
        if len(code) != 0:
            splitted_answer.append(code)
    operation_code = ';'.join(splitted_answer[:-1]) + ';'
    select_code = splitted_answer[-1]
    return operation_code, select_code


# 通过问题ID获取正确答案
def get_true_result(MYSQL_JUDGE_DB_HOST, MYSQL_JUDGE_DB_PORT, MYSQL_JUDGE_DB_USERNAME, MYSQL_JUDGE_DB_PASSWORD,
                    MYSQL_JUDGE_DB_CHARSET, answer, create_table, test_data):
    answer = answer.strip()
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
    exec_result, run_exception = exec_script(judge_db_conn, judge_db_cursor, create_table)
    if not exec_result:
        return None, 'Can not exec create table code'
    exec_result, run_exception = exec_script(judge_db_conn, judge_db_cursor, test_data)
    if not exec_result:
        return None, 'Can not exec test data code'
    if not is_select_problem(answer):
        operation_code, select_code = split_answer(answer)
        exec_result, run_exception = exec_script(judge_db_conn, judge_db_cursor, operation_code)
        if exec_result:
            true_result, run_exception = exec_code(judge_db_cursor, select_code)
        else:
            true_result = None
    else:
        true_result, run_exception = exec_code(judge_db_cursor, answer)
    if true_result is None:
        return None, run_exception
    return true_result, None


def main(answer, database_id):
    config_parser = configparser.ConfigParser()
    if len(config_parser.read(CONFIG_FILE_PATH)) == 0:
        return construct_json_response(RESPONSE_CODE['FAIL'], None, 'Can not load config.ini.')
    MYSQL_JUDGE_DB_HOST, MYSQL_JUDGE_DB_PORT, MYSQL_JUDGE_DB_USERNAME, MYSQL_JUDGE_DB_PASSWORD, MYSQL_JUDGE_DB_CHARSET, DB_HOST, DB_USERNAME, DB_PASSWORD, DB_DATABASE, DB_CHARSET = init_config(
        config_parser)
    main_db_conn = pymysql.connect(
        host=DB_HOST,
        user=DB_USERNAME,
        password=DB_PASSWORD,
        database=DB_DATABASE,
        charset=DB_CHARSET
    )
    main_db_cursor = main_db_conn.cursor()
    create_table, test_data = get_database_detail(main_db_cursor, database_id)
    if create_table is None or test_data is None:
        main_db_cursor.close()
        main_db_conn.close()
        return construct_json_response(RESPONSE_CODE['FAIL'], None, '无法找到该题目对应数据库信息')

    main_db_cursor.close()
    main_db_conn.close()
    true_result, run_exception = get_true_result(MYSQL_JUDGE_DB_HOST, MYSQL_JUDGE_DB_PORT, MYSQL_JUDGE_DB_USERNAME,
                                                 MYSQL_JUDGE_DB_PASSWORD,
                                                 MYSQL_JUDGE_DB_CHARSET, answer, create_table, test_data)

    if true_result is None:
        return construct_json_response(RESPONSE_CODE['FAIL'], None, run_exception)
    else:
        return construct_json_response(RESPONSE_CODE['OK'], {
            'trueResult': true_result
        }, None)


if __name__ == '__main__':
    print(main(argv[1], argv[2]))

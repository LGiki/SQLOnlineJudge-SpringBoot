#!/usr/bin/python
# -*- coding: UTF-8 -*-

import configparser
from sys import argv
import pymysql
from library_common import construct_json_response
from library_common import is_select_problem
from library_common import split_answer
from library_MySQL import init_config
from library_MySQL import exec_code
from library_MySQL import exec_script
from library_MySQL import generate_random_db_name
from library_MySQL import create_database_mysql
from config import CONFIG_FILE_PATH
from config import RESPONSE_CODE


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

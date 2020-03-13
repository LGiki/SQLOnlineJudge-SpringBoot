#!/usr/bin/python
# -*- coding: UTF-8 -*-

import pymysql
import configparser
from sys import argv
import hashlib
from library_common import construct_json_response
from library_common import is_select_problem
from library_common import get_last_select_code_in_answer
from library_MySQL import init_config
from library_MySQL import exec_code
from library_MySQL import exec_script
from library_MySQL import generate_random_db_name
from library_MySQL import create_database_mysql
from library_MySQL import drop_database_mysql
from config import CONFIG_FILE_PATH
from config import SOLUTION_RESULT
from config import RESPONSE_CODE


# 通过题目ID获取数据库ID
def get_problem_detail_by_problem_id(cursor, problem_id):
    sql = '''
    select `database_id`, `answer`
    from `problems`
    where `id` = %s
    '''
    cursor.execute(sql, [problem_id])
    select_result = cursor.fetchall()
    return select_result[0][0], select_result[0][1]


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


# 通过题目ID获取正确的结果
# Return: trueResult
def get_true_result_by_problem_id(cursor, problem_id):
    sql = '''
    select `true_result`
    from `problems`
    where `id` = %s
    '''
    cursor.execute(sql, [problem_id])
    select_result = cursor.fetchall()
    return select_result[0][0]


# 通过提交ID获取提交详情
def get_solution_detail_by_solution_id(cursor, solution_id):
    sql = '''
        select `pid`, `source_code`
        from `solutions`
        where id = %s 
        '''
    cursor.execute(sql, [solution_id])
    select_result = cursor.fetchall()
    problem_id = select_result[0][0]
    source_code = select_result[0][1]
    return problem_id, source_code


# 判题
def judge(MYSQL_JUDGE_DB_HOST, MYSQL_JUDGE_DB_PORT, MYSQL_JUDGE_DB_USERNAME, MYSQL_JUDGE_DB_PASSWORD,
          MYSQL_JUDGE_DB_CHARSET, main_db_conn, main_db_cursor, solution_id):
    problem_id, source_code = get_solution_detail_by_solution_id(main_db_cursor, solution_id)
    database_id, answer = get_problem_detail_by_problem_id(main_db_cursor, problem_id)
    answer = answer.strip()
    create_table, test_data = get_database_detail(main_db_cursor, database_id)
    true_result = get_true_result_by_problem_id(main_db_cursor, problem_id)
    judge_db_name = generate_random_db_name()
    print('judge_db_name:', judge_db_name)

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
    if run_exception:
        drop_database_mysql(MYSQL_JUDGE_DB_HOST, MYSQL_JUDGE_DB_PORT, MYSQL_JUDGE_DB_USERNAME, MYSQL_JUDGE_DB_PASSWORD,
                            judge_db_name)
        return construct_json_response(RESPONSE_CODE['NO_DB_FILE'], None,
                                       'Can not exec create table code: ' + run_exception)
    exec_result, run_exception = exec_script(judge_db_conn, judge_db_cursor, test_data)
    if run_exception:
        drop_database_mysql(MYSQL_JUDGE_DB_HOST, MYSQL_JUDGE_DB_PORT, MYSQL_JUDGE_DB_USERNAME, MYSQL_JUDGE_DB_PASSWORD,
                            judge_db_name)
        return construct_json_response(RESPONSE_CODE['NO_DB_FILE'], None,
                                       'Can not exec test data code: ' + run_exception)
    if not is_select_problem(answer):
        last_select_code = get_last_select_code_in_answer(answer)
        exec_script_result, run_exception = exec_script(judge_db_conn, judge_db_cursor, source_code)
        if exec_script_result:
            exec_result, exec_exception = exec_code(judge_db_cursor, last_select_code)
        else:
            exec_result = None
            exec_exception = run_exception
    else:
        exec_result, exec_exception = exec_code(judge_db_cursor, source_code)
    judge_db_cursor.close()
    judge_db_conn.close()
    drop_database_mysql(MYSQL_JUDGE_DB_HOST, MYSQL_JUDGE_DB_PORT, MYSQL_JUDGE_DB_USERNAME, MYSQL_JUDGE_DB_PASSWORD,
                        judge_db_name)
    if exec_result is None:
        judge_result_index = SOLUTION_RESULT['Compile Error']
    else:
        exec_result = hashlib.md5(exec_result.encode(encoding='UTF-8')).hexdigest().upper()
        if exec_result == true_result:
            judge_result_index = SOLUTION_RESULT['Accepted']
        else:
            judge_result_index = SOLUTION_RESULT['Wrong Answer']
    return construct_json_response(RESPONSE_CODE['OK'], {
        'solutionId': solution_id,
        'result': judge_result_index,
        'runError': exec_exception,
    }, None)


def main(solution_id):
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
    result = judge(MYSQL_JUDGE_DB_HOST, MYSQL_JUDGE_DB_PORT, MYSQL_JUDGE_DB_USERNAME, MYSQL_JUDGE_DB_PASSWORD,
                   MYSQL_JUDGE_DB_CHARSET, main_db_conn, main_db_cursor, solution_id)
    main_db_cursor.close()
    main_db_conn.close()
    return result


if __name__ == '__main__':
    print(main(argv[1]))

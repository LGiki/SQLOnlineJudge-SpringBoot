#!/usr/bin/python
# -*- coding: UTF-8 -*-

import pymysql
import os
import shutil
import configparser
from sys import argv
import hashlib
from library_common import construct_json_response
from library_common import is_select_problem
from library_common import get_last_select_code_in_answer
from library_SQLite import init_config
from library_SQLite import exec_code
from library_SQLite import exec_script
from library_SQLite import init_work_directory
from config import CONFIG_FILE_PATH
from config import RESPONSE_CODE
from config import SOLUTION_RESULT


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
def judge(SQLITE_DIR, SQLITE_TEMP_DIR, cursor, solution_id):
    problem_id, source_code = get_solution_detail_by_solution_id(cursor, solution_id)
    # logging.info('Start judging solution {}'.format(solution_id))
    database_id, answer = get_problem_detail_by_problem_id(cursor, problem_id)
    answer = answer.strip()
    sqlite_db_file_path = os.path.join(SQLITE_DIR, '{}.db'.format(database_id))
    if not os.path.exists(sqlite_db_file_path):
        return construct_json_response(RESPONSE_CODE['NO_DB_FILE'], None, '无法找到该题目对应数据库文件')
    temp_sqlite_db_file_path = os.path.join(SQLITE_TEMP_DIR, '{}_{}_temp.db'.format(database_id, solution_id))
    shutil.copyfile(sqlite_db_file_path, temp_sqlite_db_file_path)
    true_result = get_true_result_by_problem_id(cursor, problem_id)
    if not is_select_problem(answer):
        last_select_code = get_last_select_code_in_answer(answer)
        exec_script_result, run_exception = exec_script(temp_sqlite_db_file_path, source_code)
        if exec_script_result:
            exec_result, exec_exception = exec_code(temp_sqlite_db_file_path, last_select_code)
        else:
            exec_result = None
            exec_exception = run_exception
    else:
        exec_result, exec_exception = exec_code(temp_sqlite_db_file_path, source_code)
    if exec_result is None:
        judge_result_index = SOLUTION_RESULT['Compile Error']
    else:
        exec_result = hashlib.md5(exec_result.encode(encoding='UTF-8')).hexdigest().upper()
        if exec_result == true_result:
            judge_result_index = SOLUTION_RESULT['Accepted']
        else:
            judge_result_index = SOLUTION_RESULT['Wrong Answer']
    os.remove(temp_sqlite_db_file_path)
    return construct_json_response(RESPONSE_CODE['OK'], {
        'solutionId': solution_id,
        'result': judge_result_index,
        'runError': exec_exception,
    }, None)
    # logger.info('Judge completed solution {}: {}'.format(solution_id, judge_result))


def main(solution_id):
    config_parser = configparser.ConfigParser()
    if len(config_parser.read(CONFIG_FILE_PATH)) == 0:
        return construct_json_response(RESPONSE_CODE['FAIL'], None, 'Can not load config.ini.')
    SQLITE_DIR, SQLITE_TEMP_DIR, DB_HOST, DB_USERNAME, DB_PASSWORD, DB_DATABASE, DB_CHARSET = init_config(config_parser)
    init_work_directory(SQLITE_TEMP_DIR)
    conn = pymysql.connect(
        host=DB_HOST,
        user=DB_USERNAME,
        password=DB_PASSWORD,
        database=DB_DATABASE,
        charset=DB_CHARSET
    )
    cursor = conn.cursor()
    result = judge(SQLITE_DIR, SQLITE_TEMP_DIR, cursor, solution_id)
    cursor.close()
    conn.close()
    return result


if __name__ == '__main__':
    # logging.basicConfig(
    #     level=logging.INFO,
    #     format="%(asctime)s %(name)s:%(levelname)s:%(message)s",
    #     datefmt="%Y-%m-%d %H:%M:%S"
    # )
    # logger = logging.getLogger()
    print(main(argv[1]))

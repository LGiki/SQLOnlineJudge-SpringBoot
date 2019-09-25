#!/usr/bin/python
# -*- coding: UTF-8 -*-

import pymysql
import os
import sqlite3
import shutil
import json
import configparser
from sys import argv

# 配置文件路径
CONFIG_FILE_PATH = './judger/config.ini'
# 提交状态Dict
SOLUTION_RESULT = {
    'Unknown': 0,  # 未知
    'Accepted': 1,  # 通过
    'Compile Error': 2,  # 编译错误
    'Wrong Answer': 3,  # 答案错误
    'Judging': 4  # 正在判题（未使用）
}
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


# 通过题目ID获取数据库ID
def get_database_id_by_problem_id(cursor, problem_id):
    sql = '''
    select `database_id`
    from `problem`
    where `id` = %s
    '''
    cursor.execute(sql, [problem_id])
    select_result = cursor.fetchall()
    return select_result[0][0]


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


# 通过题目ID获取正确的结果
# Return: trueResult
def get_true_result_by_problem_id(cursor, problem_id):
    sql = '''
    select `true_result`
    from `problem`
    where `id` = %s
    '''
    cursor.execute(sql, [problem_id])
    select_result = cursor.fetchall()
    return select_result[0][0]


# 通过提交ID获取提交详情
def get_solution_detail_by_solution_id(cursor, solution_id):
    sql = '''
        select `pid`, `source_code`
        from `solution`
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
    database_id = get_database_id_by_problem_id(cursor, problem_id)
    sqlite_db_file_path = os.path.join(SQLITE_DIR, '{}.db'.format(database_id))
    temp_sqlite_db_file_path = os.path.join(SQLITE_TEMP_DIR, '{}_temp.db'.format(database_id))
    shutil.copyfile(sqlite_db_file_path, temp_sqlite_db_file_path)
    true_result = get_true_result_by_problem_id(cursor, problem_id)
    exec_result, exec_exception = exec_code(temp_sqlite_db_file_path, source_code)
    if exec_result is None:
        judge_result_index = SOLUTION_RESULT['Compile Error']
    else:
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


# 初始化工作目录
def init_work_directory(SQLITE_TEMP_DIR):
    if not os.path.exists(SQLITE_TEMP_DIR):
        os.mkdir(SQLITE_TEMP_DIR)
        # logger.info('Create Temp SQLite Dir: {}'.format(SQLITE_TEMP_DIR))


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

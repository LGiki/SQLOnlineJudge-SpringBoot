#!/usr/bin/python
# -*- coding: UTF-8 -*-

import pymysql
import os
import sqlite3
import shutil
import logging
import json

# 主 SQLite 数据库目录
SQLITE_DIR = './judgeDB'
# 临时 SQLite 数据库目录
SQLITE_TEMP_DIR = './temp_judgeDB'
# 数据库主机
DB_HOST = 'localhost'
# 数据库用户名
DB_USERNAME = 'root'
# 数据库密码
DB_PASSWORD = 'root'
# 数据库名
DB_DATABASE = 'sqlonlinejudge'
# 数据库字符集
DB_CHARSET = 'utf8'
# 提交状态Dict
SOLUTION_RESULT = {
    'Unknown': 0,  # 未知
    'Accepted': 1,  # 通过
    'Compile Error': 2,  # 编译错误
    'Wrong Answer': 3,  # 答案错误
    'Judging': 4  # 正在判题（未使用）
}


# 通过解答ID更新判题结果
# def update_judge_result(conn, cursor, solution_id, result):
#     sql = '''
#     update `solution`
#     set `result` = %s
#     where `id` = %s
#     '''
#     cursor.execute(sql, [result, solution_id])
#     conn.commit()
#     return cursor.rowcount == 1


# 查询还未判题的解答列表
def select_need_judge_solution(cursor):
    sql = '''
    select `id`, `uid`, `pid`, `source_code`
    from `solution`
    where `result` = %s
    '''
    cursor.execute(sql, [SOLUTION_RESULT['Unknown']])
    return cursor.fetchall()


# 根据数据库ID获取sqlite文件路径，如果sqlite文件还未被创建则创建并返回路径
def get_sqlite_db_file_path(conn, cursor, database_id):
    sql = '''
    select `create_table`, `test_data`, `is_created`
    from `data_base`
    where id = %s 
    '''
    cursor.execute(sql, [database_id])
    select_result = cursor.fetchall()
    sqlite_db_file_path = os.path.join(SQLITE_DIR, '{}.db'.format(database_id))
    # Create database when is_create is 0
    if select_result[0][2] == b'\x00' or not os.path.exists(sqlite_db_file_path):
        if os.path.exists(sqlite_db_file_path):
            os.remove(sqlite_db_file_path)
        create_table = select_result[0][0]
        test_data = select_result[0][1]
        sqlite_conn = sqlite3.connect(sqlite_db_file_path)
        sqlite_cursor = sqlite_conn.cursor()
        print(create_table)
        sqlite_cursor.executescript(create_table)
        sqlite_cursor.executescript(test_data)
        sqlite_conn.commit()
        sqlite_cursor.close()
        sqlite_conn.close()
        # set_database_created(conn, cursor, database_id)
    return sqlite_db_file_path


# 设置数据库的is_created位为1
# def set_database_created(conn, cursor, database_id):
#     sql = '''
#     update `data_base`
#     set `is_created` = 1
#     where `id` = %s
#     '''
#     cursor.execute(sql, [database_id])
#     conn.commit()
#     return cursor.rowcount == 1


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


# 通过题目ID获取正确的结果，如果结果不存在就执行一次正确答案获得正确结果
def get_true_result_by_problem_id(conn, cursor, problem_id, sqlite_db_file_path):
    sql = '''
    select `true_result`, `answer`
    from `problem`
    where `id` = %s
    '''
    cursor.execute(sql, [problem_id])
    select_result = cursor.fetchall()
    # Get true result when it is null
    if select_result[0][0] is None:
        answer = select_result[0][1]
        true_result, _ = exec_code(sqlite_db_file_path, answer)
        # update_true_result_by_problem_id(conn, cursor, problem_id, true_result)
        return true_result, False
    return select_result[0][0], True


# 通过题目ID更新正确结果
# def update_true_result_by_problem_id(conn, cursor, problem_id, true_result):
#     sql = '''
#     update `problem`
#     set `true_result` = %s
#     where `id` = %s
#     '''
#     cursor.execute(sql, [true_result, problem_id])
#     conn.commit()
#     return cursor.rowcount == 1


# 增加用户的通过数
# def increase_user_solve(conn, cursor, user_id):
#     sql = '''
#     update `sys_user`
#     set `solved` = `solved` + 1
#     where `id` = %s
#     '''
#     cursor.execute(sql, [user_id])
#     conn.commit()
#     return cursor.rowcount == 1


# 增加题目的通过数
# def increase_problem_solve(conn, cursor, problem_id):
#     sql = '''
#     update `problem`
#     set `solve` = `solve` + 1
#     where `id` = %s
#     '''
#     cursor.execute(sql, [problem_id])
#     conn.commit()
#     return cursor.rowcount == 1


# 通过提交ID更新运行错误信息
# def update_run_error(conn, cursor, solution_id, run_error):
#     sql = '''
#     update `solution`
#     set `run_error` = %s
#     where `id` = %s
#     '''
#     cursor.execute(sql, [run_error, solution_id])
#     conn.commit()
#     return cursor.rowcount == 1


# 判题
def judge(conn, cursor):
    need_judge_solution_list = select_need_judge_solution(cursor)
    if len(need_judge_solution_list) == 0:
        pass
        # logger.info('No any solution need judge.')
    else:
        for need_judge_item in select_need_judge_solution(cursor):
            solution_id = need_judge_item[0]
            user_id = need_judge_item[1]
            problem_id = need_judge_item[2]
            source_code = need_judge_item[3]
            # logging.info('Start judging solution {}'.format(solution_id))
            database_id = get_database_id_by_problem_id(cursor, problem_id)
            sqlite_db_file_path = get_sqlite_db_file_path(conn, cursor, database_id)
            _, db_file_name = os.path.split(sqlite_db_file_path)
            db_file_name, _ = os.path.splitext(db_file_name)
            temp_sqlite_db_file_path = os.path.join(SQLITE_TEMP_DIR, '{}_temp.db'.format(db_file_name))
            shutil.copyfile(sqlite_db_file_path, temp_sqlite_db_file_path)
            exec_result, exec_exception = exec_code(temp_sqlite_db_file_path, source_code)
            true_result, is_true_result_exist = get_true_result_by_problem_id(conn, cursor, problem_id,
                                                                              temp_sqlite_db_file_path)
            if exec_result is None:
                # update_judge_result(conn, cursor, solution_id, SOLUTION_RESULT['Compile Error'])
                # update_run_error(conn, cursor, solution_id, exec_exception)
                judge_result = 'Compile Error'
                judge_result_index = SOLUTION_RESULT['Compile Error']
            else:
                if exec_result == true_result:
                    # update_judge_result(conn, cursor, solution_id, SOLUTION_RESULT['Accepted'])
                    # increase_user_solve(conn, cursor, user_id)
                    # increase_problem_solve(conn, cursor, problem_id)
                    judge_result = 'Accepted'
                    judge_result_index = SOLUTION_RESULT['Accepted']

                else:
                    # update_judge_result(conn, cursor, solution_id, SOLUTION_RESULT['Wrong Answer'])
                    judge_result = 'Wrong Answer'
                    judge_result_index = SOLUTION_RESULT['Wrong Answer']

            os.remove(temp_sqlite_db_file_path)
            result = json.dumps(
                {
                    'solutionId': solution_id,
                    'result': judge_result_index,
                    'runError': exec_exception,
                    'trueResult': None if is_true_result_exist else true_result,
                }
            )
            return result
            # logger.info('Judge completed solution {}: {}'.format(solution_id, judge_result))


def init():
    if not os.path.exists(SQLITE_DIR):
        os.mkdir(SQLITE_DIR)
        # logger.info('Create SQLite Dir: {}'.format(SQLITE_DIR))
    if not os.path.exists(SQLITE_TEMP_DIR):
        os.mkdir(SQLITE_TEMP_DIR)
        # logger.info('Create Temp SQLite Dir: {}'.format(SQLITE_TEMP_DIR))


def main():
    init()
    conn = pymysql.connect(
        host=DB_HOST,
        user=DB_USERNAME,
        password=DB_PASSWORD,
        database=DB_DATABASE,
        charset=DB_CHARSET
    )
    cursor = conn.cursor()
    result = judge(conn, cursor)
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
    main()

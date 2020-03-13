#!/usr/bin/python
# -*- coding: UTF-8 -*-

import os
import configparser
import shutil
from sys import argv
import uuid
from library_common import construct_json_response
from library_common import is_select_problem
from library_common import split_answer
from library_SQLite import init_config
from library_SQLite import exec_code
from library_SQLite import exec_script
from library_SQLite import init_work_directory
from config import CONFIG_FILE_PATH
from config import RESPONSE_CODE


# 通过问题ID获取正确答案
def get_true_result(SQLITE_DIR, SQLITE_TEMP_DIR, answer, database_id):
    sqlite_db_file_path = os.path.join(SQLITE_DIR, '{}.db'.format(database_id))
    temp_sqlite_db_file_path = os.path.join(SQLITE_TEMP_DIR, '{}_{}_temp.db'.format(database_id, uuid.uuid4()))
    shutil.copyfile(sqlite_db_file_path, temp_sqlite_db_file_path)
    answer = answer.strip()
    if not is_select_problem(answer):
        operation_code, select_code = split_answer(answer)
        exec_result, run_exception = exec_script(temp_sqlite_db_file_path, operation_code)
        if exec_result:
            true_result, run_exception = exec_code(temp_sqlite_db_file_path, select_code)
        else:
            true_result = None
    else:
        true_result, run_exception = exec_code(temp_sqlite_db_file_path, answer)
    os.remove(temp_sqlite_db_file_path)
    if true_result is None:
        return None, run_exception
    return true_result, None


def main(answer, database_id):
    config_parser = configparser.ConfigParser()
    if len(config_parser.read(CONFIG_FILE_PATH)) == 0:
        return construct_json_response(RESPONSE_CODE['FAIL'], None, 'Can not load config.ini.')
    SQLITE_DIR, SQLITE_TEMP_DIR, DB_HOST, DB_USERNAME, DB_PASSWORD, DB_DATABASE, DB_CHARSET = init_config(config_parser)
    init_work_directory(SQLITE_TEMP_DIR)
    sqlite_db_file_path = os.path.join(SQLITE_DIR, '{}.db'.format(database_id))
    if not os.path.exists(sqlite_db_file_path):
        return construct_json_response(RESPONSE_CODE['NO_DB_FILE'], None, '无法找到该题目对应数据库文件')
    true_result, run_exception = get_true_result(SQLITE_DIR, SQLITE_TEMP_DIR, answer, database_id)
    if true_result is None:
        return construct_json_response(RESPONSE_CODE['FAIL'], None, run_exception)
    else:
        return construct_json_response(RESPONSE_CODE['OK'], {
            'trueResult': true_result
        }, None)


if __name__ == '__main__':
    print(main(argv[1], argv[2]))

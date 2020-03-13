#!/usr/bin/python
# -*- coding: UTF-8 -*-

import configparser
from sys import argv
import pymysql
from library_common import construct_json_response
from library_MySQL import init_config
from library_MySQL import generate_random_db_name
from library_MySQL import create_database_mysql
from library_MySQL import drop_database_mysql
from config import CONFIG_FILE_PATH
from config import RESPONSE_CODE


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

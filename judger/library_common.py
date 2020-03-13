import json


# 构造json返回值
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


# 判断是否是select类型的题目
def is_select_problem(answer):
    if answer.endswith(';'):
        answer = answer[:-1]
    splitted_answer_temp = answer.split(';')
    splitted_answer = []
    for code in splitted_answer_temp:
        if len(code) != 0:
            splitted_answer.append(code)
    if len(splitted_answer) > 1:
        return False
    return True


# 分割答案，取出select语句
def get_last_select_code_in_answer(answer):
    _, select_code = split_answer(answer)
    return select_code


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

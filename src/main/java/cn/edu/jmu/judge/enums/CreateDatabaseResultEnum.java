package cn.edu.jmu.judge.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author xeathen
 * @date 2019/9/18 15:52
 */
public enum CreateDatabaseResultEnum {
    /**
     * CreateDatabaseResult
     */
    OK("0", "OK"),
    FAIL("1", "FAIL");

    @EnumValue
    private final String value;

    private final String msg;

    CreateDatabaseResultEnum(String value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public String getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }}

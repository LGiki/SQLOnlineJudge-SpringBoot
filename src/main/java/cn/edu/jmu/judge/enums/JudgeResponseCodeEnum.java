package cn.edu.jmu.judge.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author xeathen
 * @date 2019/9/18 15:52
 */
public enum JudgeResponseCodeEnum {
    /**
     * CreateDatabaseResult
     */
    OK("0", "OK"),
    FAIL("1", "Fail"),
    NO_DB_FILE("2", "No DB File");

    @EnumValue
    private final String value;

    private final String msg;

    JudgeResponseCodeEnum(String value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public String getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }
}

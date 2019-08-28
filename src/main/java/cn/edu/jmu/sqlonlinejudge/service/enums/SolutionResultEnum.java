package cn.edu.jmu.sqlonlinejudge.service.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author sgh
 * @date 2019/6/24 18:28
 */
public enum SolutionResultEnum {

    /**
     * Solution Result
     */
    UNKNOWN("0", "Unknown"),
    ACCEPTED("1", "Accepted"),
    WRONG("2", "Wrong Answer"),
    ;

    @EnumValue
    private final String value;

    private final String msg;

    SolutionResultEnum(String value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public String getValue() {
        return this.value;
    }

    @JsonValue
    public String getMsg() {
        return this.msg;
    }
}

package cn.edu.jmu.sqlonlinejudge.service.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author sgh
 * @date 2019/6/24 18:28
 */
public enum SolutionResultEnum {

    /**
     * Accepted
     */
    ACCEPTED("0", "Accepted"),
    /**
     * Wrong Answer
     */
    WRONG("1", "Wrong Answer"),
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

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
    ACCEPTED("0", "通过"),
    /**
     * Wrong Answer
     */
    WRONG("1", "答案错误"),
    ;

    @EnumValue
    private final String value;

    private final String displayName;

    SolutionResultEnum(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public String getValue() {
        return this.value;
    }

    @JsonValue
    public String getDisplayName() {
        return this.displayName;
    }
}

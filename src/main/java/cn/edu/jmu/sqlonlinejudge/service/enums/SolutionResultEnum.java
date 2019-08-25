package cn.edu.jmu.sqlonlinejudge.service.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author sgh
 * @date 2019/6/24 18:28
 */
public enum SolutionResultEnum {
    /**
     * Accepted
     */
    ACCEPTED("0"),
    /**
     * Wrong Answer
     */
    WRONG("1"),
    ;

    @EnumValue
    private final String value;

    SolutionResultEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

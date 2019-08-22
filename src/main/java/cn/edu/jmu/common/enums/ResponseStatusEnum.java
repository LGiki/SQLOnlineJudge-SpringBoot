package cn.edu.jmu.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author sgh
 * @date 2019/8/16 下午9:49
 */
public enum ResponseStatusEnum {
    /**
     * Ok
     */
    OK(0, "正常"),
    /**
     * Fail
     */
    FAIL(1, "失败"),
    ;


    private final Integer code;

    private final String msg;

    ResponseStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @JsonValue
    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}

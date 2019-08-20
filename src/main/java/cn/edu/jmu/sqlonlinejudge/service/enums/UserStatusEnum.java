package cn.edu.jmu.sqlonlinejudge.service.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author sgh
 * @date 2019/6/20 15:51
 */
public enum UserStatusEnum {

    /**
     * 未激活
     */
    INACTIVATED("0", "未激活状态"),
    /**
     * 正常状态
     */
    NORMAL("1", "正常状态"),
    /**
     * 锁定状态
     */
    LOCK("2", "账号被锁定状态"),
    ;

    @EnumValue
    private final String value;

    private final String displayName;


    UserStatusEnum(String value, String displayName) {
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


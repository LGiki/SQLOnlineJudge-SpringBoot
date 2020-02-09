package cn.edu.jmu.system.service.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author sgh
 * @date 2019/6/20 15:51
 */
public enum UserStatusEnum {

    /**
     * 账号被锁定
     */
    LOCK(false, "账号被锁定"),

    /**
     * 正常
     */
    NORMAL(true, "正常");

    @EnumValue
    private final Boolean value;

    private final String displayName;

    UserStatusEnum(Boolean value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public Boolean getValue() {
        return this.value;
    }

    @JsonValue
    public String getDisplayName() {
        return this.displayName;
    }
}


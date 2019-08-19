package cn.edu.jmu.sqlonlinejudge.service.enumerate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sgh
 * @date 2019/6/20 15:51
 */
public enum UserStatusEnum implements BaseEnum<UserStatusEnum, String> {

    /**
     * 未激活
     */
    INACTIVATED("0", "inactivated"),
    /**
     * 正常状态
     */
    NORMAL("1", "normal"),
    /**
     * 删除状态
     */
    LOCK("2", "lockd"),
    ;

    private final String value;
    private final String displayName;
    static Map<String, UserStatusEnum> enumMap=new HashMap<String, UserStatusEnum>();
    static{
        for(UserStatusEnum type: UserStatusEnum.values()){
            enumMap.put(type.getValue(), type);
        }
    }


    private UserStatusEnum(String value, String displayName) {
        this.value=value;
        this.displayName=displayName;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    public static UserStatusEnum getEnum(String value) {
        return enumMap.get(value);
    }
}


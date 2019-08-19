package cn.edu.jmu.sqlonlinejudge.service.enumerate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sgh
 * @date 2019/6/20 17:44
 */
public enum UserRoleEnum implements BaseEnum<UserRoleEnum, String>{
    /**
     * 普通用户
     */
    USER("0","普通用户"),
    /**
     * 管理员
     */
    ADMIN("1","管理员"),
    ;

    private final String value;
    private final String displayName;
    static Map<String, UserRoleEnum> enumMap=new HashMap<String, UserRoleEnum>();
    static{
        for(UserRoleEnum type: UserRoleEnum.values()){
            enumMap.put(type.getValue(), type);
        }
    }

    private UserRoleEnum(String value, String displayName) {
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

    public static UserRoleEnum getEnum(String value) {
        return enumMap.get(value);
    }
}

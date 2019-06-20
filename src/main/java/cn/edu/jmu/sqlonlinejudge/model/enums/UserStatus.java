package cn.edu.jmu.sqlonlinejudge.model.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sgh
 * @date 2019/6/20 15:51
 */
public enum UserStatus implements BaseEnum<UserStatus, String> {
    /**
     * 正常状态
     */
    NORMAL("1","正常"),
    /**
     * 删除状态
     */
    DETELE("0","删除"),
    ;

    private final String value;
    private final String displayName;
    static Map<String,UserStatus> enumMap=new HashMap<String, UserStatus>();
    static{
        for(UserStatus type:UserStatus.values()){
            enumMap.put(type.getValue(), type);
        }
    }


    private UserStatus(String value,String displayName) {
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

    public static UserStatus getEnum(String value) {
        return enumMap.get(value);
    }
}


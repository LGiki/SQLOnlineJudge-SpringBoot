package cn.edu.jmu.common.enums;

/**
 * @author sgh
 * @date 2019/8/19 下午5:07
 */
public enum LoginTypeEnum {
    /**
     * 登录类型
     */
    USER("User"),
    ADMIN("Admin"),
    JWT("Jwt"),
    ;

    private String type;

    LoginTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

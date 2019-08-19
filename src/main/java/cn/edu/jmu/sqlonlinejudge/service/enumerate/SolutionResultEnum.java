package cn.edu.jmu.sqlonlinejudge.service.enumerate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sgh
 * @date 2019/6/24 18:28
 */
public enum SolutionResultEnum implements BaseEnum<SolutionResultEnum, String> {
    /**
     * Accepted
     */
    ACCEPTED("0","Accepted"),
    /**
     * Wrong Answer
     */
    WRONG("1","Wrong Answer"),
    ;

    private final String value;
    private final String displayName;
    static Map<String, SolutionResultEnum> enumMap=new HashMap<String, SolutionResultEnum>();
    static{
        for(SolutionResultEnum type: SolutionResultEnum.values()){
            enumMap.put(type.getValue(), type);
        }
    }

    private SolutionResultEnum(String value, String displayName) {
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

    public static SolutionResultEnum getEnum(String value) {
        return enumMap.get(value);
    }
}

package cn.edu.jmu.sqlonlinejudge.model.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sgh
 * @date 2019/6/24 18:28
 */
public enum SolutionResult implements BaseEnum<SolutionResult, String> {
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
    static Map<String, SolutionResult> enumMap=new HashMap<String, SolutionResult>();
    static{
        for(SolutionResult type:SolutionResult.values()){
            enumMap.put(type.getValue(), type);
        }
    }

    private SolutionResult(String value,String displayName) {
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

    public static SolutionResult getEnum(String value) {
        return enumMap.get(value);
    }
}

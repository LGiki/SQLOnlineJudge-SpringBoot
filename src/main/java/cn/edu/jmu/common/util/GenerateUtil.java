package cn.edu.jmu.common.util;

import java.util.UUID;

public class GenerateUtil {
    /**
     * 生成UUID
     *
     * @return String 生成的UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
}

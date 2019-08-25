package cn.edu.jmu.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author sgh
 * @date 2019/8/21 下午4:37
 */
public class ThrowableUtil {
    /**
     * 获取堆栈信息
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }
}

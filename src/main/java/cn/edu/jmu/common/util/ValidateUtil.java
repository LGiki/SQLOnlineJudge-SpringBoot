package cn.edu.jmu.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.regex.Pattern;

/**
 * @author sgh
 * @date 2019/8/25 下午6:04
 */
public class ValidateUtil {

    /**
     * 允许的图片文件扩展名
     */
    private static final String[] allowFileNameExtensions = {".jpg", ".jpeg", ".webp", ".gif", ".png", ".bmp"};

    /**
     * 验证字符串是否为null
     */
    public static boolean isEmpty(final CharSequence cs) {
        return StringUtils.isEmpty(cs);
    }

    /**
     * 验证字符串是否不为null
     */
    public static boolean isNotEmpty(final CharSequence cs) {
        return StringUtils.isNotEmpty(cs);
    }

    /**
     * 验证字符串是否为空
     */
    public static boolean isBlank(final CharSequence cs) {
        return StringUtils.isBlank(cs);
    }

    /**
     * 验证字符串是否不为空
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return StringUtils.isNotBlank(cs);
    }

    /**
     * 验证字符串是否是纯数字字符串
     */
    public static boolean isDigits(final CharSequence cs) {
        return isNotBlank(cs) && StringUtils.isNumeric(cs);
    }

    /**
     * 验证字符串是否是数字型字符串，包括小数
     */
    public static boolean isNumber(String str) {
        return isNotBlank(str) && NumberUtils.isParsable(str);
    }

    /**
     * 判断是否为正数
     */
    public static boolean isPositiveNumber(String str) {
        if (!isNumber(str)) {
            return false;
        }
        return Double.parseDouble(str) > 0;
    }

    /**
     * 判断是否为负数
     */
    public static boolean isNegativeNumber(String str) {
        if (!isNumber(str)) {
            return false;
        }
        return Double.parseDouble(str) < 0;
    }

    /**
     * 验证字符串是否是纯字母字符串
     */
    public static boolean isAlpha(String str) {
        return isNotBlank(str) && StringUtils.isAlpha(str);
    }

    /**
     * 验证是否是url
     */
    public static boolean isUrl(String str) {
        if (isBlank(str)) {
            return false;
        }
        String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
        return match(regex, str);
    }

    /**
     * 判断是否是email
     */
    public static boolean isEmail(String str) {
        if (isBlank(str)) {
            return false;
        }
        // 复杂匹配
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return match(regex, str);
    }

    /**
     * @param regex 正则表达式字符串
     * @param str   要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str) {
        if (isEmpty(str)) {
            return false;
        } else {
            return Pattern.compile(regex).matcher(str).matches();
        }
    }

    /**
     * 根据文件名判断是否是图片
     *
     * @param fileName 文件名
     * @return boolean 如果文件名符合规定的图片格式扩展名则返回true，否则返回false
     */
    public static boolean isImage(String fileName) {
        if (fileName == null || isEmpty(fileName)) {
            return false;
        }
        for (String allowFileNameExtension : allowFileNameExtensions) {
            if (fileName.endsWith(allowFileNameExtension)) {
                return true;
            }
        }
        return false;
    }
}

package cn.edu.jmu.sqlonlinejudge.util;

import org.springframework.util.DigestUtils;

/**
 * @author LGiki
 * @date 2019/06/22 15:15
 */
public class EncryptUtil {
    /**
     * 计算MD5值
     * @param password 密码
     * @return String 密码对应的MD5值
     */
    public static String md5(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    public static String md5(CharSequence charSequence) {
        return DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
    }
}

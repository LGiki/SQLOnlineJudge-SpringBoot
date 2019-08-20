package cn.edu.jmu.sqlonlinejudge.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.DigestUtils;

/**
 * @author LGiki
 * @date 2019/06/22 15:15
 */
public class EncryptUtil {
    /**
     * 计算MD5值
     *
     * @param password 密码
     * @return String 密码对应的MD5值
     */
    public static String md5(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    public static String md5(CharSequence charSequence) {
        return DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
    }

    /**
     * 加密算法MD5
     *
     * @param username username
     * @param password password
     * @param salt     salt
     * @return String
     */
    public static String encryption(String username, String password, String salt) {
        // 加密算法 MD5
        // salt = username + salt
        // 迭代次数 2
        return new SimpleHash("MD5", password,
                ByteSource.Util.bytes(username + salt), 2).toHex();
    }
}

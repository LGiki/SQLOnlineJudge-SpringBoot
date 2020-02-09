package cn.edu.jmu.common.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.security.SecureRandom;

/**
 * @author LGiki
 * @date 2019/06/22 15:15
 */
public class EncryptUtil {

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

    public static String generatorSalt() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[15];
        random.nextBytes(bytes);
        return org.apache.commons.codec.binary.Base64.encodeBase64String(bytes);
    }
}

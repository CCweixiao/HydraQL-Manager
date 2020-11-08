package com.leo.hbase.manager.common.utils.security;

import com.leo.hbase.manager.common.utils.StringUtils;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * 字符串的简单加密和解密
 *
 * @author leojie 2020/9/26 9:04 下午
 */
public class StrEnDeUtils {
    /**
     * 自定义 KEY
     */
    private static final byte[] KEY_BYTES = {0x31, 0x32, 0x33, 0x34, 0x35, 0x50,
            0x37, 0x38, 0x39, 0x40, 0x41, 0x42, 0x43, 0x44, 0x45, 0x46};


    public static String encrypt(String value) {
        String s = null;

        int mode = Cipher.ENCRYPT_MODE;

        try {
            Cipher cipher = initCipher(mode);

            byte[] outBytes = cipher.doFinal(value.getBytes());

            s = String.valueOf(Hex.encodeHex(outBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }

    public static String decrypt(String value) {
        if (StringUtils.isBlank(value)) {
            return "";
        }
        String s = null;

        int mode = Cipher.DECRYPT_MODE;

        try {
            Cipher cipher = initCipher(mode);

            byte[] outBytes = cipher
                    .doFinal(Hex.decodeHex(value.toCharArray()));

            s = new String(outBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }


    private static Cipher initCipher(int mode) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        Key key = new SecretKeySpec(KEY_BYTES, "AES");
        cipher.init(mode, key);
        return cipher;
    }

    public static void main(String[] args) {
        String tableName = "TEST:USER";
        System.out.println(encrypt(tableName));
        System.out.println(decrypt(encrypt(tableName)));

        String tableName2 = "TEST:USER1";
        System.out.println(encrypt(tableName2));
        System.out.println(decrypt(encrypt(tableName2)));
    }

}

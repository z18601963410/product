package com.ike.product.until;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * DES对称加密算法:加密和解密使用相同密钥的算法。
 */
public class DESUtil {
    // 设置密钥key
    private static final Key key;
    //密匙种子
    private static final String KEY_STR = "myKey";
    //编码格式
    private static final String CHARSET_NAME = "UTF-8";
    //算法
    private static final String ALGORITHM = "DES";

    // 生成密钥
    static {
        try {
            // 生成DES算法对象
            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
            // 运用SHA1安全策略
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            // 设置上密钥种子
            secureRandom.setSeed(KEY_STR.getBytes());
            // 初始化基于SHA1的算法对象
            generator.init(secureRandom);
            // 生成密钥对象
            key = generator.generateKey();
            //generator = null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取加密后的信息
     *
     * @param plaintext 明文对象
     * @return 加密后的String对象
     */
    public static String getEncryptString(String plaintext) {
        // 基于BASE64编码，接收byte[]并转换成String
        //BASE64Encoder base64encoder = new BASE64Encoder();
        Base64.Encoder encoder = Base64.getEncoder();
        try {
            // 按UTF8编码
            byte[] bytes = plaintext.getBytes(CHARSET_NAME);
            // 获取加密对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 初始化密码信息
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 加密
            byte[] doFinal = cipher.doFinal(bytes);
            // byte[]to encode好的String并返回
            return encoder.encodeToString(doFinal);

        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取解密之后的信息
     *
     * @param ciphertext 密文对象
     * @return 解密后的明文对象
     */
    public static String getDecryptString(String ciphertext) {
        // 基于BASE64编码，接收byte[]并转换成String
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            // 将字符串decode成byte[]
            byte[] bytes = decoder.decode(ciphertext);
            // 获取解密对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 初始化解密信息
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 解密
            byte[] doFinal = cipher.doFinal(bytes);
            // 返回解密之后的信息
            return new String(doFinal, CHARSET_NAME);
        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String name = DESUtil.getEncryptString("root");
        String password = DESUtil.getEncryptString("123456");

        System.out.println(name);
        System.out.println(password);
    }
}
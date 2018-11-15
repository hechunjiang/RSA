package international.huinews.sven.com.myapplication.utils;

import android.util.Base64;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 作者：created by hcj
 * 邮箱：2654313873@qq.com
 * 日期：2018/11/14 08
 */
public class aes {
    public static final String ALGORITHM = "AES";

    public static byte[] decryptBASE64(String key) throws Exception {
        return Base64.decode(key, Base64.DEFAULT);
    }

    public static String encryptBASE64(byte[] key) throws Exception {
        return Base64.encodeToString(key, Base64.DEFAULT);
    }

    private static Key toKey(byte[] key) throws Exception {
        //DESKeySpec dks = new DESKeySpec(key);
        //SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        //SecretKey secretKey = keyFactory.generateSecret(dks);

        // 当使用其他对称加密算法时，如AES、Blowfish等算法时，用下述代码替换上述三行代码
        SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);

        return secretKey;
    }

    public static byte[] decrypt(byte[] data, String key) throws Exception {
        Key k = toKey(decryptBASE64(key));

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, k);

        return cipher.doFinal(data);
    }

    public static byte[] encrypt(byte[] data, String key) throws Exception {
        Key k = toKey(decryptBASE64(key));
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, k);

        return cipher.doFinal(data);
    }

    public static String initKey() throws Exception {
        return initKey(null);
    }

    public static String initKey(String seed) throws Exception {
        try {
            SecureRandom secureRandom = null;

            if (seed != null) {
                secureRandom = new SecureRandom(decryptBASE64(seed));
            } else {
                secureRandom = new SecureRandom();
            }

            KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
            kg.init(secureRandom);

            SecretKey secretKey = kg.generateKey();

            return encryptBASE64(secretKey.getEncoded());
        } catch (Exception e) {

        }
        return null;
    }

}

package international.huinews.sven.com.myapplication.utils;

import java.security.MessageDigest;

/**
 * 作者：created by hcj
 * 邮箱：2654313873@qq.com
 * 日期：2018/11/14 09
 */
public class sha {
    public static byte[] encryptSHA(byte[] data, String shaN) throws Exception {

        MessageDigest sha = MessageDigest.getInstance(shaN);
        sha.update(data);
        return sha.digest();

    }
}

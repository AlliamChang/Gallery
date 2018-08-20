package club.williamleon.util;

import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Created by 53068 on 2018/4/17 0017.
 */
public class MD5 {

    public static final String[] hex = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private static String randomSalt() {
        StringBuilder sb = new StringBuilder(32);
        Random r = new Random();
        for(int i = 0; i < sb.capacity(); i ++) {
            sb.append(hex[r.nextInt(hex.length)]);
        }
        return sb.toString();
    }

    public static String digest(String content) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(content.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException", e);
        }
        System.out.println();

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public static String encrypt(@NotNull String rawMsg) {

        int m = rawMsg.charAt(0) % 4;
        char[] salt = randomSalt().toCharArray();
        char[] rawMd5 = digest(rawMsg).toCharArray();
        char[] c = new char[64];
        for(int i = 0; i < rawMd5.length; i += 2) {
            c[2*i+m%4] = rawMd5[i];
            c[2*i+(m+1)%4] = salt[i];
            c[2*i+(m+2)%4] = rawMd5[i+1];
            c[2*i+(m+3)%4] = salt[i+1];
        }
        return String.valueOf(c);
    }

    public static boolean equal(@NotNull String rawMsg, String md5){
        if(rawMsg == null || rawMsg.equals("")){
            throw new RuntimeException("");
        }

        int m = rawMsg.charAt(0) % 4;
        char[] extract = new char[32];
        char[] c = md5.toCharArray();
        for(int i = 0; i < extract.length; i += 2) {
            extract[i] = c[i*2+m%4];
            extract[i+1] = c[i*2+(m+2)%4];
        }

        return String.valueOf(extract).equals(digest(rawMsg));
    }

}

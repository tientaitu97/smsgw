package vn.vnpay.sercutity;

import lombok.SneakyThrows;

import java.security.MessageDigest;
import java.util.Base64;

public class HashUtils {
    @SneakyThrows
    public static String sha1Base64(String base){
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] hash = digest.digest(base.getBytes("US-ASCII"));
        return Base64.getEncoder().encodeToString(hash);
    }
}

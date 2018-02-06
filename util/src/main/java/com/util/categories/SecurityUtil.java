package com.util.categories;

import android.util.Base64;

import com.google.common.hash.Hashing;

import java.io.UnsupportedEncodingException;

/**
 * The type SecurityUtil.
 */
public class SecurityUtil {

    /**
     * private constructor
     */
    protected SecurityUtil() {
    }

    /**
     * Encode in base64
     *
     * @param toEncode String to be encoded
     * @param flags    flags to encode the String
     * @return encoded String in base64
     */
    public static String encodeBase64(String toEncode, int flags) {
        return privateBase64Encoder(toEncode, flags);

    }

    /**
     * Encode in base64
     *
     * @param toEncode String to be encoded
     * @return encoded String in base64
     */
    public static String encodeBase64(String toEncode) {
        return privateBase64Encoder(toEncode, -1);
    }

    /**
     * private Encoder in base64
     *
     * @param toEncode String to be encoded
     * @param flags    flags to encode the String
     * @return encoded String in base64
     */
    private static String privateBase64Encoder(String toEncode, int flags) {
        byte[] data = null;
        try {
            data = toEncode.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        if (flags == -1) {
            flags = Base64.DEFAULT;
        }

        return Base64.encodeToString(data, flags);
    }

    /**
     * Decode in base64
     *
     * @param toDecode String to be encoded
     * @return decoded String in base64
     */
    public static String decodeBase64(String toDecode) {
        return privateBase64Decoder(toDecode, -1);
    }

    /**
     * Decode in base64
     *
     * @param toDecode String to be encoded
     * @param flags    flags to decode the String
     * @return decoded String in base64
     */
    public static String decodeBase64(String toDecode, int flags) {
        return privateBase64Decoder(toDecode, flags);
    }

    /**
     * Private decoder in base64
     *
     * @param decode String to be encoded
     * @param flags  flags to decode the String
     * @return decoded String in base64
     */
    private static String privateBase64Decoder(String decode, int flags) {
        if (flags == -1) {
            flags = Base64.DEFAULT;
        }

        byte[] data1 = Base64.decode(decode, flags);
        String decodedBase64 = null;
        try {
            decodedBase64 = new String(data1, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return decodedBase64;
    }

    /**
     * Calculate the MD5 of a given String
     *
     * @param string String to be MD5'ed
     * @return MD5 'ed String
     */
    public static String calculateMD5(String string) {
        return Hashing.md5().hashBytes(string.getBytes()).toString();
    }

    /**
     * Calculate the SHA-1 of a given String
     *
     * @param string String to be SHA1'ed
     * @return SHA1 'ed String
     */
    public static String calculateSHA1(String string) {
        return Hashing.sha1().hashBytes(string.getBytes()).toString();
    }

    /**
     * Calculate the SHA256 of a given String
     *
     * @param string String to be SHA256 'ed
     * @return SHA256 'ed String
     */
    public static String calculateSHA256(String string) {
        return Hashing.sha256().hashBytes(string.getBytes()).toString();
    }
}

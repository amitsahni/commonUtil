package com.util.categories;

import android.util.Base64;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public final class SecurityUtil {

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
        return Base64.encodeToString(toEncode.getBytes(), flags == -1 ? Base64.DEFAULT : flags);
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
        String decodedBase64 = null;
        try {
            decodedBase64 = new String(Base64.decode(decode, flags == -1 ? Base64.DEFAULT : flags), "UTF-8");
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
        return new String(DigestUtils.md5(string.getBytes()), Charset.defaultCharset());
    }

    /**
     * Calculate the SHA-1 of a given String
     *
     * @param string String to be SHA1'ed
     * @return SHA1 'ed String
     */
    public static String calculateSHA1(String string) {
        return new String(DigestUtils.sha1(string.getBytes()), Charset.defaultCharset());
    }

    /**
     * Calculate the SHA256 of a given String
     *
     * @param string String to be SHA256 'ed
     * @return SHA256 'ed String
     */
    public static String calculateSHA256(String string) {
        return new String(DigestUtils.sha256(string.getBytes()), Charset.defaultCharset());
    }
}

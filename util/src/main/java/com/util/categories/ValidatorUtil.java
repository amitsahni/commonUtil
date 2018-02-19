package com.util.categories;

import android.text.TextUtils;

/**
 * The type ValidatorUtil.
 */
public class ValidatorUtil {

    private static final String PASS_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?=\\S+$).{8,20})";
    private static final String USERNAME_PATTEN = "(?=\\S+$).{3,}";


    public static boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isEmptyOrNull(String str) {
        return !(!TextUtils.isEmpty(str) && !str.equals("null"));
    }

    public static boolean isMatches(String str1, String str2) {
        return TextUtils.equals(str1, str2);
    }

}

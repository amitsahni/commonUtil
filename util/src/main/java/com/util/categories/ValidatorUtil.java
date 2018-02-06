package com.util.categories;

import android.text.TextUtils;

import org.apache.commons.lang3.StringUtils;


/**
 * The type ValidatorUtil.
 */
public class ValidatorUtil extends StringUtils {

    // alphanumeric parttern for password
//    private static final String PASSWORDPARTTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
    private static final String PASS_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?=\\S+$).{8,20})";
    private static final String USERNAME_PATTEN = "(?=\\S+$).{3,}";


    /**
     *
     * Is email valid boolean.
     *
     * @param email email address that need to check
     * @return true if email address is matched with defined pattern. Here pattern is used from api level 22 android
     */
    public static boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    /**
     * Is password valid boolean.
     *
     * @param password that need to check
     * @return true if password is matched with defined pattern.
     * @link http ://stackoverflow.com/questions/3802192/regexp-java-for-password-validation
     */
    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.matches(PASS_PATTERN);
    }


    /**
     * Is password matches boolean.
     *
     * @param password        the password
     * @param confirmPassword that need to match
     * @return true if email address is matched with defined pattern. Here pattern is used from api level 22 android
     */
    public static boolean isPasswordMatches(String password, String confirmPassword) {
        return isMatches(password, confirmPassword);
    }

    /**
     * Is username valid boolean.
     *
     * @param username that need to check
     * @return true if email address is matched with defined pattern. Here pattern is used from api level 22 android
     */
    public static boolean isUsernameValid(String username) {
        return username.matches(USERNAME_PATTEN);
    }

    /**
     * This method is used to check if the entered string is null, blank, or
     * "null"
     *
     * @param str set String to check
     * @return true if null else false
     */
    public static boolean isEmptyOrNull(String str) {
        return !(!TextUtils.isEmpty(str) && !str.equals("null"));
    }

    /**
     * Is matches boolean.
     *
     * @param str1 the str 1
     * @param str2 the str 2
     * @return the boolean
     */
    public static boolean isMatches(String str1, String str2) {
        return TextUtils.equals(str1, str2);
    }

}

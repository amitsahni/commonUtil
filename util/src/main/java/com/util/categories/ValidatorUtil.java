package com.util.categories;

import android.text.TextUtils;
import android.util.Patterns;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.util.categories.LogUtil.d;

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

    public static boolean isValidUrl(String url) {
        return Patterns.WEB_URL.matcher(url).matches();
    }

    public static boolean isJSONValid(String json) {
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String message = jsonObject.toString(2);
                d(message);
                return true;
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(2);
                d(message);
                return true;
            }
            return false;
        } catch (JSONException e) {
            LogUtil.e("Invalid Json");
            return false;
        }

    }

}

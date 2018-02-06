package com.util.categories;


import com.util.BuildConfig;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * The type LogUtil.
 */
public class LogUtil {
    /**
     * The constant VERBOSE.
     */
    public static final int VERBOSE = android.util.Log.VERBOSE;
    /**
     * The constant DEBUG.
     */
    public static final int DEBUG = android.util.Log.DEBUG;
    /**
     * The constant INFO.
     */
    public static final int INFO = android.util.Log.INFO;
    /**
     * The constant WARN.
     */
    public static final int WARN = android.util.Log.WARN;
    /**
     * The constant ERROR.
     */
    public static final int ERROR = android.util.Log.ERROR;
    /**
     * The constant TAG.
     */
    public static final String TAG = "log";

    private static boolean isLogEnabled = false;

    /**
     * private constructor
     *
     * @param isEnabled the value
     */
//	private log() {
//	}
    public static void logEnable(boolean isEnabled) {
        isLogEnabled = isEnabled;
    }

    /**
     * Sends an ERROR log message
     *
     * @param message The message you would like logged.
     * @return the int
     */
    public static int e(String message) {
        return logger(ERROR, message);
    }

    /**
     * Sends an ERROR log message
     *
     * @param tag     The tag
     * @param message The message you would like logged.
     * @return the int
     */
    public static int e(String tag, String message) {
        return logger(ERROR, tag, message);
    }

    /**
     * Sends an ERROR log message
     *
     * @param message   The message you would like logged.
     * @param throwable An exception to log
     * @return the int
     */
    public static int e(String message, Throwable throwable) {
        return logger(ERROR, message, throwable);
    }

    /**
     * Sends an INFO log message.
     *
     * @param message The message you would like logged.
     * @return the int
     */
    public static int i(String message) {
        return logger(INFO, message);
    }

    /**
     * Sends an INFO log message
     *
     * @param tag     The tag
     * @param message The message you would like logged.
     * @return the int
     */
    public static int i(String tag, String message) {
        return logger(INFO, tag, message);
    }

    /**
     * Sends an INFO log message.
     *
     * @param message   The message you would like logged.
     * @param throwable An exception to log
     * @return the int
     */
    public static int i(String message, Throwable throwable) {
        return logger(INFO, message, throwable);
    }

    /**
     * Sends a VERBBOSE log message.
     *
     * @param message The message you would like logged.
     * @return the int
     */
    public static int v(String message) {
        return logger(VERBOSE, message);
    }

    /**
     * Sends an VERBOSE log message
     *
     * @param tag     The tag
     * @param message The message you would like logged.
     * @return the int
     */
    public static int v(String tag, String message) {
        return logger(VERBOSE, tag, message);
    }

    /**
     * Sends a VERBBOSE log message.
     *
     * @param message   The message you would like logged.
     * @param throwable An exception to log
     * @return the int
     */
    public static int v(String message, Throwable throwable) {
        return logger(VERBOSE, message, throwable);
    }

    /**
     * Sends a WARNING log message.
     *
     * @param message The message you would like logged.
     * @return the int
     */
    public static int w(String message) {
        return logger(WARN, message);
    }

    /**
     * Sends an WARNING log message
     *
     * @param tag     The tag
     * @param message The message you would like logged.
     * @return the int
     */
    public static int w(String tag, String message) {
        return logger(WARN, tag, message);
    }

    /**
     * Sends a WARNING log message.
     *
     * @param message   The message you would like logged.
     * @param throwable An exception to log
     * @return the int
     */
    public static int w(String message, Throwable throwable) {
        return logger(WARN, message, throwable);
    }

    /**
     * Sends a DEBUG log message.
     *
     * @param message The message you would like logged.
     * @return the int
     */
    public static int d(String message) {
        return logger(DEBUG, message);
    }

    /**
     * Sends an DEBUG log message
     *
     * @param tag     The tag
     * @param message The message you would like logged.
     * @return the int
     */
    public static int d(String tag, String message) {
        return logger(DEBUG, tag, message);
    }

    /**
     * Sends a DEBUG log message and log the exception.
     *
     * @param message   The message you would like logged.
     * @param throwable An exception to log
     * @return the int
     */
    public static int d(String message, Throwable throwable) {
        return logger(DEBUG, message, throwable);

    }

    /**
     * Private Logger function to handle LogUtil calls
     *
     * @param level     level of the log message
     * @param message   log output
     * @param throwable
     */
    private static int logger(int level, String message, Throwable throwable) {

        if (BuildConfig.DEBUG) {
            switch (level) {

                case DEBUG:
                    return android.util.Log.d(TAG, message, throwable);
                case VERBOSE:
                    return android.util.Log.v(TAG, message, throwable);
                case INFO:
                    return android.util.Log.i(TAG, message, throwable);
                case WARN:
                    return android.util.Log.w(TAG, message, throwable);
                case ERROR:
                    return android.util.Log.e(TAG, message, throwable);
                default:
                    break;
            }
        }

        return -1;
    }

    /**
     * Private Logger function to handle LogUtil calls
     *
     * @param level   level of the log message
     * @param message log output
     */
    private static int logger(int level, String message) {

        if (isLogEnabled) {
            switch (level) {

                case DEBUG:
                    return android.util.Log.d(TAG, message);
                case VERBOSE:
                    return android.util.Log.v(TAG, message);
                case INFO:
                    return android.util.Log.i(TAG, message);
                case WARN:
                    return android.util.Log.w(TAG, message);
                case ERROR:
                    return android.util.Log.e(TAG, message);
                default:
                    break;
            }
        }
        return -1;
    }

    /**
     * Private Logger function to handle Log calls
     *
     * @param level   level of the log message
     * @param tag     TAG
     * @param message log output
     */
    private static int logger(int level, String tag, String message) {

        if (isLogEnabled) {
            switch (level) {
                case DEBUG:
                    return android.util.Log.d(tag, message);
                case VERBOSE:
                    return android.util.Log.v(tag, message);
                case INFO:
                    return android.util.Log.i(tag, message);
                case WARN:
                    return android.util.Log.w(tag, message);
                case ERROR:
                    return android.util.Log.e(tag, message);
                default:
                    break;
            }
        }
        return -1;
    }

    /**
     * Static fields.
     *
     * @param clazz the clazz
     * @throws IllegalAccessException the illegal access exception
     */
    public static void staticFields(Class<?> clazz) throws IllegalAccessException {
        for (Field f : clazz.getDeclaredFields()) {
            if (Modifier.isStatic(f.getModifiers())) {
                boolean wasAccessible = f.isAccessible();
                f.setAccessible(true);
                LogUtil.i(f.getName() + ": " + f.get(null));
                f.setAccessible(wasAccessible);
            }
        }
    }

    /**
     * Private fields.
     *
     * @param clazz the clazz
     * @throws IllegalAccessException the illegal access exception
     */
    public static void privateFields(Class<?> clazz) throws IllegalAccessException {
        for (Field f : clazz.getDeclaredFields()) {
            if (Modifier.isPrivate(f.getModifiers())) {
                boolean wasAccessible = f.isAccessible();
                f.setAccessible(true);
                LogUtil.i(f.getName() + ": " + f.get(null));
                f.setAccessible(wasAccessible);
            }
        }
    }
}

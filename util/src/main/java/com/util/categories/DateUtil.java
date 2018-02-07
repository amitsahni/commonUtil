package com.util.categories;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.util.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


public final class DateUtil {
    private DateUtil() {
    }

    public static int getCurrentYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }

    public static int getCurrentMonth() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MONTH);
    }

    public static int getCurrentDay() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static String getDayAsString(int day, String format) {
        return getDateFormat(format).format(getDayAsDate(day));
    }

    public static String getDayAsString(int day, String format, Locale locale) {
        return getDateFormat(format, locale).format(getDayAsDate(day));
    }

    public static Date getDayAsDate(int day) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    public static long getTimeSinceMidnight() {
        Calendar c = Calendar.getInstance();
        long now = c.getTimeInMillis();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return now - c.getTimeInMillis();
    }

    public static SimpleDateFormat getDateFormat() {
        return getDateFormatBase(null, null, null);
    }

    public static SimpleDateFormat getDateFormat(@NonNull String format) {
        return getDateFormatBase(format, null, null);
    }

    public static SimpleDateFormat getDateFormat(@NonNull String format,
                                                 @NonNull Locale locale) {
        return getDateFormatBase(format, null, locale);
    }

    public static SimpleDateFormat getDateFormat(@NonNull String format,
                                                 @NonNull String timeZone, @NonNull Locale locale) {
        return getDateFormatBase(format, timeZone, locale);
    }

    private static SimpleDateFormat getDateFormatBase(String format,
                                                      String timeZone, Locale locale) {
        if (locale == null) {
            locale = Locale.ENGLISH;
        }
        SimpleDateFormat simpleFormat;
        if (format == null) {
            simpleFormat = new SimpleDateFormat();
        } else {
            simpleFormat = new SimpleDateFormat(format, locale);
        }
        if (timeZone != null) {
            simpleFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        } else {
            simpleFormat.setTimeZone(TimeZone.getDefault());
        }
        return simpleFormat;
    }

    public static String getCurrentUTC(String format, Locale locale) {
        SimpleDateFormat formatter = getDateFormat(format, locale);
        return formatter.format(getCurrentUTC().getTime());
    }

    public static Date getCurrentUTC() {
        Calendar calendar = Calendar.getInstance();
        int ro = calendar.getTimeZone().getRawOffset();
        int dst = calendar.getTimeZone().getDSTSavings();

        boolean isDayLight = TimeZone.getDefault().inDaylightTime(
                calendar.getTime());

        long gmtMillis = calendar.getTimeInMillis() - (ro);
        if (isDayLight) {
            gmtMillis = calendar.getTimeInMillis() - ro - dst;
        }
        return new Date(gmtMillis);
    }

    public static Date convertToUTCDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        int ro = calendar.getTimeZone().getRawOffset();
        int dst = calendar.getTimeZone().getDSTSavings();

        boolean isDayLight = TimeZone.getDefault().inDaylightTime(
                date);

        long gmtMillis = date.getTime() - (ro);
        if (isDayLight) {
            gmtMillis = date.getTime() - ro - dst;
        }
        return new Date(gmtMillis);
    }

    public static String convertMillisecondToUTC(String format, long milli) {
        SimpleDateFormat sdf = getDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String text = sdf.format(new Date(milli));
        LogUtil.i("Date = " + convertToUTCDate(sdf.getCalendar().getTime()));
        return text;
    }

    public static String formatDate(long date, @NonNull String format) {
        return formatDateBase(date, format, null, Locale.ENGLISH);
    }

    public static String formatDate(long date, @NonNull String format,
                                    @NonNull String timeZone) {
        return formatDateBase(date, format, timeZone, Locale.ENGLISH);
    }

    public static String formatDate(long date, @NonNull String format,
                                    @NonNull String timeZone, @NonNull Locale locale) {
        return formatDateBase(date, format, timeZone, locale);
    }

    private static String formatDateBase(long date, @NonNull String format,
                                         @NonNull String timeZone, @NonNull Locale locale) {
        return getDateFormat(format, timeZone, locale).format(date);
    }

    @Nullable
    public static Date parseDate(@NonNull String dateString,
                                 @NonNull String dateFormat) {
        return parseDateBase(dateString, dateFormat, Locale.ENGLISH, null);
    }

    /**
     * Parse a data string into a real Date
     * <p>
     * Note: (e.g. "yyyy-MM-dd HH:mm:ss")
     *
     * @param dateString date in String format
     * @param dateFormat desired format (e.g. "yyyy-MM-dd HH:mm:ss")
     * @param locale     Locale
     * @return java . util . date
     */
    @Nullable
    public static Date parseDate(@NonNull String dateString,
                                 @NonNull String dateFormat,
                                 @NonNull Locale locale) {
        return parseDateBase(dateString, dateFormat, locale, null);
    }

    /**
     * Parse a data string into a real Date
     * <p>
     * Note: (e.g. "yyyy-MM-dd HH:mm:ss")
     *
     * @param dateString date in String format
     * @param dateFormat desired format (e.g. "yyyy-MM-dd HH:mm:ss")
     * @param locale     Locale
     * @param timeZone   timeZone (UTC,GMT)
     * @return java . util . date
     */
    @Nullable
    public static Date parseDate(@NonNull String dateString,
                                 @NonNull String dateFormat,
                                 @NonNull Locale locale,
                                 @NonNull String timeZone) {
        return parseDateBase(dateString, dateFormat, locale, timeZone);
    }

    private static Date parseDateBase(@NonNull String dateString,
                                      @NonNull String dateFormat,
                                      @NonNull Locale locale,
                                      String timeZone) {
        Date date = null;
        SimpleDateFormat format = getDateFormat(dateFormat, null, locale);
        try {
            date = format.parse(dateString);
            format.setTimeZone(TimeZone.getTimeZone(timeZone));
            dateString = format.format(date.getTime());
            date = format.parse(dateString);
        } catch (ParseException e) {
            LogUtil.e("parse error", e);
        }
        return date;
    }

    @Nullable
    public static Date convertLocalDateToUTC(long localMilli) {
        Date date = null;
        SimpleDateFormat format = getDateFormat();
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        String localDate = format.format(new Date(localMilli));
        try {
            date = format.parse(localDate);
        } catch (ParseException e) {
            LogUtil.e("parse error", e);
        }
        return date;
    }

    /**
     * get Current time in milliseconds
     *
     * @return current time in milliseconds
     */
    public static long getCurrentTimeInMiliseconds() {
        return TimeUnit.MILLISECONDS.toMillis(Calendar.getInstance()
                .getTimeInMillis());
    }

    /**
     * get Current time in seconds
     *
     * @return current time in seconds
     */
    public static long getCurrentTimeInSeconds() {
        return TimeUnit.SECONDS.toSeconds(Calendar.getInstance()
                .getTimeInMillis());

    }


    /**
     * Get number with a suffix
     *
     * @param number number that will be converted
     * @return (e.g. 1becomes1st, 3becomes3rd, etc)
     */
    public static String getNumberWithSuffix(int number) {
        int j = number % 10;
        if (j == 1 && number != 11) {
            return number + "st";
        }
        if (j == 2 && number != 12) {
            return number + "nd";
        }
        if (j == 3 && number != 13) {
            return number + "rd";
        }
        return number + "th";
    }


    /**
     * Converts a month by number to full text
     *
     * @param month    number of the month 1..12
     * @param useShort boolean that gives "Jun" instead of "June" if true
     * @return returns "January" if "1" is given
     */
    public static String convertMonth(int month, boolean useShort) {
        String monthStr;
        switch (month) {
            default:
                monthStr = "January";
                break;
            case Calendar.FEBRUARY:
                monthStr = "February";
                break;
            case Calendar.MARCH:
                monthStr = "March";
                break;
            case Calendar.APRIL:
                monthStr = "April";
                break;
            case Calendar.MAY:
                monthStr = "May";
                break;
            case Calendar.JUNE:
                monthStr = "June";
                break;
            case Calendar.JULY:
                monthStr = "July";
                break;
            case Calendar.AUGUST:
                monthStr = "August";
                break;
            case Calendar.SEPTEMBER:
                monthStr = "September";
                break;
            case Calendar.OCTOBER:
                monthStr = "October";
                break;
            case Calendar.NOVEMBER:
                monthStr = "November";
                break;
            case Calendar.DECEMBER:
                monthStr = "December";
                break;
        }
        if (useShort) monthStr = monthStr.substring(0, 3);
        return monthStr;
    }

    private final static String TAG = DateUtil.class.getSimpleName();
    /**
     * The constant FORMAT.
     */
    public final static String FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    /**
     * The constant DATE_TIME_FORMAT.
     */
    public final static String DATE_TIME_FORMAT = "dd MMM - HH:mm";
    /**
     * The constant TIME_FORMAT.
     */
    public final static String TIME_FORMAT = "HH:mm:ss";
    /**
     * The constant DATETIME_FORMAT.
     */
    public final static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * The constant DATE_TIME_FORMAT_DHM_DATE_MO.
     */
    public final static String DATE_TIME_FORMAT_DHM_DATE_MO = "E HH:mm  dd MMM";
    /**
     * The constant DATE.
     */
    public final static String DATE = "yyyy-MM-dd";
    private final static long ONE_SECOND = 1000;
    private final static long HALF_SECONDS = ONE_SECOND * 15;

    private final static long ONE_MINUTE = ONE_SECOND * 60;
    private final static long MINUTES = 60;

    private final static long ONE_HOUR = ONE_MINUTE * 60;
    private final static long HOURS = 24;

    private final static long ONE_DAY = ONE_HOUR * 24;


    /**
     * Functionality to calculate time difference
     *
     * @param firstDateTime  the first date time
     * @param secondDateTime the second date time
     * @return time difference in milliseconds
     * @throws ParseException the parse exception
     */
    public long getTimeDifferenceInMilliseconds(Date firstDateTime,
                                                Date secondDateTime) throws ParseException {
        long diffInMs = secondDateTime.getTime() - firstDateTime.getTime();
        return TimeUnit.MILLISECONDS.toSeconds(diffInMs);
    }


    /**
     * Functionality to get date time after addition or subtract the time
     * accordingly
     *
     * @param date                     in which the time need to add/subtract
     * @param addingSubtractTimeString date time string that need to add/subtract by default 00:00
     * @param isNeedToAdd              true if + else false
     * @return new date object
     */
    public static Date addSubtractTimeWithDate(Date date,
                                               String addingSubtractTimeString, boolean isNeedToAdd) {
        if (!TextUtils.isEmpty(addingSubtractTimeString) && date != null) {
            String[] hourMinuteArr = addingSubtractTimeString.split(":");

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            if (isNeedToAdd) {
                calendar.add(Calendar.HOUR,
                        +(Integer.parseInt(hourMinuteArr[0])));
                calendar.add(Calendar.MINUTE,
                        +(Integer.parseInt(hourMinuteArr[1])));
            } else {
                calendar.add(Calendar.HOUR,
                        -(Integer.parseInt(hourMinuteArr[0])));
                calendar.add(Calendar.MINUTE,
                        -(Integer.parseInt(hourMinuteArr[1])));
            }

            return calendar.getTime();
        }
        return null;

    }


    /**
     * Functionality to find yesterday date in milliseconds
     *
     * @param numberOfDays should be greater than 0 otherwise return current date
     * @return date in milliseconds
     */
    public static long getOldDateFromCurrentDateInMilli(int numberOfDays) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(new Date());

        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.YEAR, c1.get(Calendar.YEAR));
        c2.set(Calendar.MONTH, c1.get(Calendar.MONTH));
        c2.set(Calendar.DAY_OF_MONTH, c1.get(Calendar.DAY_OF_MONTH));
        c2.set(Calendar.HOUR_OF_DAY, 0);
        c2.set(Calendar.MINUTE, 0);
        c2.set(Calendar.SECOND, 0);

        if (numberOfDays > 0) {
            c2.add(Calendar.DATE, -numberOfDays);
            return c2.getTimeInMillis();
        }
        return c2.getTimeInMillis();
    }


    /**
     * Functionality to find yesterday date in milliseconds
     *
     * @param numberOfDays should be greater than 0 otherwise return current date
     * @return date in milliseconds
     */
    public static long getFutureDateFromCurrentDateInMilli(int numberOfDays) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(new Date());

        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.YEAR, c1.get(Calendar.YEAR));
        c2.set(Calendar.MONTH, c1.get(Calendar.MONTH));
        c2.set(Calendar.DAY_OF_MONTH, c1.get(Calendar.DAY_OF_MONTH));
        c2.set(Calendar.HOUR_OF_DAY, 0);
        c2.set(Calendar.MINUTE, 0);
        c2.set(Calendar.SECOND, 0);

        if (numberOfDays > 0) {
            c2.add(Calendar.DATE, numberOfDays);
            return c2.getTimeInMillis();
        }
        return c2.getTimeInMillis();
    }

    /**
     * http://www.rgagnon.com/javadetails/java-0585.html
     * converts time (in milliseconds) to human-readable format
     * "<w> days, <x> hours, <y> minutes and (z) seconds"
     *
     * @param context  the context
     * @param duration the duration
     * @return the string
     */
    public static String millisToLongDHMS(Context context, long duration) {
        StringBuilder res = new StringBuilder();

        long temp = 0;
        if (duration >= HALF_SECONDS) {
            temp = duration / ONE_DAY;
            if (temp > 0) {
                duration -= temp * ONE_DAY;
            }
            res.append(temp).append(" " + context.getString(R.string.days)).append(temp > 1 ? "" : "")
                    .append(duration >= ONE_MINUTE ? ", " : "");

            temp = duration / ONE_HOUR;
            if (temp > 0) {
                duration -= temp * ONE_HOUR;
                res.append(temp).append(" " + context.getString(R.string.hours)).append(temp > 1 ? "" : "")
                        .append(duration >= ONE_MINUTE ? ", " : "");
            }

            temp = duration / ONE_MINUTE;
            if (temp > 0) {
                duration -= temp * ONE_MINUTE;
                res.append(temp).append(" " + context.getString(R.string.minutes)).append(temp > 1 ? "" : "");
            }

            if (!res.toString().equals("") && duration >= ONE_SECOND) {
                res.append(",");
            }

            temp = duration / ONE_SECOND;
            if (temp > 0) {
                res.append(temp).append(" " + context.getString(R.string.seconds)).append(temp > 1 ? "" : "");
            }
            return res.toString();
        } else {
            return context.getString(R.string.justnow_time);
        }
    }

    public static String getTimeAgo(Context context, Date date) {
        long diff = new Date().getTime() - date.getTime();
        String time = millisToLongDHMS(context, diff);
        String[] timeAgo = time.split(",");
        if (timeAgo.length > 0)
            return timeAgo[0];
        return "";
    }


    /**
     * Functionality to get current timezone of the device
     * <ahref http://stackoverflow.com/questions/15068113/how-to-get-the-timezone-offset-in-gmtlike-gmt700-from-android-device/>
     *
     * @return timezone in the format GMT+00:00
     */
    public static String getCurrentTimezoneOffset() {
        TimeZone tz = TimeZone.getDefault();
        Calendar cal = GregorianCalendar.getInstance(tz);
        int offsetInMillis = tz.getOffset(cal.getTimeInMillis());
        String offset = String.format("%02d:%02d", Math.abs(offsetInMillis / 3600000), Math.abs((offsetInMillis / 60000) % 60));
        offset = "GMT" + (offsetInMillis >= 0 ? "+" : "-") + offset;
        return offset;
    }
}



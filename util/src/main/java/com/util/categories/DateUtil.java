package com.util.categories;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.util.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


public final class DateUtil {
    /**
     * The constant FORMAT.
     */
    public final static String FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private final static String TAG = DateUtil.class.getSimpleName();

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

    public static DateFormat getDateFormat() {
        return getDateFormatBase(null, TimeZone.getDefault(), Locale.ENGLISH);
    }

    public static DateFormat getDateFormat(@NonNull String format) {
        return getDateFormatBase(format, TimeZone.getDefault(), Locale.ENGLISH);
    }

    public static DateFormat getDateFormat(@NonNull String format,
                                           @NonNull Locale locale) {
        return getDateFormatBase(format, TimeZone.getDefault(), locale);
    }

    public static DateFormat getDateFormat(@NonNull String format,
                                           @NonNull TimeZone timeZone, @NonNull Locale locale) {
        return getDateFormatBase(format, timeZone, locale);
    }

    private static DateFormat getDateFormatBase(String format,
                                                TimeZone timeZone, Locale locale) {
        if (locale == null) {
            locale = Locale.ENGLISH;
        }
        DateFormat simpleFormat;
        if (format == null) {
            simpleFormat = SimpleDateFormat.getDateInstance();
        } else {
            simpleFormat = new SimpleDateFormat(format, locale);
        }

        if (timeZone == null) {
            timeZone = TimeZone.getDefault();
        }
        simpleFormat.setTimeZone(timeZone);
        return simpleFormat;
    }

    public static String getCurrentUTC(String format, Locale locale) {
        DateFormat formatter = getDateFormat(format, locale);
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
        DateFormat sdf = getDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String text = sdf.format(new Date(milli));
        LogUtil.i("Date = " + convertToUTCDate(sdf.getCalendar().getTime()));
        return text;
    }

    public static String formatDate(long date, @NonNull String format) {
        return formatDateBase(date, format, TimeZone.getDefault(), Locale.ENGLISH);
    }

    public static String formatDate(long date, @NonNull String format,
                                    @NonNull TimeZone timeZone) {
        return formatDateBase(date, format, timeZone, Locale.ENGLISH);
    }

    public static String formatDate(long date, @NonNull String format,
                                    @NonNull Locale locale) {
        return formatDateBase(date, format, TimeZone.getDefault(), locale);
    }

    public static String formatDate(long date, @NonNull String format,
                                    @NonNull TimeZone timeZone, @NonNull Locale locale) {
        return formatDateBase(date, format, timeZone, locale);
    }

    private static String formatDateBase(long date, @NonNull String format,
                                         @NonNull TimeZone timeZone, @NonNull Locale locale) {
        return getDateFormat(format, timeZone, locale).format(date);
    }

    @Nullable
    public static Date parseDate(@NonNull String date,
                                 @NonNull String format) {
        return parseDateBase(date, format, Locale.ENGLISH, TimeZone.getDefault());
    }

    @Nullable
    public static Date parseDate(@NonNull String date,
                                 @NonNull String format,
                                 @NonNull Locale locale) {
        return parseDateBase(date, format, locale, TimeZone.getDefault());
    }

    @Nullable
    public static Date parseDate(@NonNull String date,
                                 @NonNull String format,
                                 @NonNull TimeZone timeZone) {
        return parseDateBase(date, format, Locale.ENGLISH, timeZone);
    }

    @Nullable
    public static Date parseDate(@NonNull String date,
                                 @NonNull String format,
                                 @NonNull Locale locale,
                                 @NonNull TimeZone timeZone) {
        return parseDateBase(date, format, locale, timeZone);
    }

    private static Date parseDateBase(@NonNull String date,
                                      @NonNull String format,
                                      @NonNull Locale locale,
                                      @NonNull TimeZone timeZone) {
        Date d = null;
        DateFormat sdf = getDateFormat(format, timeZone, locale);
        try {
            d = sdf.parse(date);
            date = getDateFormat(format, timeZone, locale).format(d.getTime());
            d = sdf.parse(date);
        } catch (ParseException e) {
            LogUtil.e("parse error", e);
        }
        return d;
    }

    public static long getTimeInMillis() {
        return Calendar.getInstance()
                .getTimeInMillis();
    }

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

    public static String convertMonth(int month, boolean useShort) {
        Calendar c = Calendar.getInstance();
        DateFormat sdf = getDateFormat(useShort ? "MMM" : "MMMM");
        c.set(Calendar.MONTH, month - 1);
        return sdf.format(c.getTime());
    }

    public static String millisToLongDHMS(Context context, long duration) {
        StringBuilder res = new StringBuilder();
        final long ONE_SECOND = 1000;
        final long HALF_SECONDS = ONE_SECOND * 15;
        final long ONE_MINUTE = ONE_SECOND * 60;
        final long ONE_HOUR = ONE_MINUTE * 60;
        final long ONE_DAY = ONE_HOUR * 24;
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

    public long getTimeDifferenceInMilliseconds(Date firstDateTime,
                                                Date secondDateTime) {
        long diffInMs = secondDateTime.getTime() - firstDateTime.getTime();
        return TimeUnit.MILLISECONDS.toSeconds(diffInMs);
    }
}



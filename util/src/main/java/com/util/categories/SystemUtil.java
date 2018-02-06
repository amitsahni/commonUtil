package com.util.categories;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.StringRes;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;

import org.apache.commons.io.FileUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.security.auth.x500.X500Principal;

/**
 * The type SystemUtil.
 */
public class SystemUtil {

    /**
     * private constructor
     */
    protected SystemUtil() {
    }

    /**
     * Common Buffer Size
     */
    static final int BUFFER = 2048;

    /**
     * Gets font.
     *
     * @param context the context
     * @param resId   the res id
     * @return the font
     */
    public static String getFontName(Context context, @StringRes int resId) {
        if (resId != -1)
            return context.getString(resId);
        return "";
    }


    /**
     * Functionality to check the Internet availability
     *
     * @param context Activity context
     * @return true if Internet is connected
     */
    public static boolean isInternetConnected(Context context) {
        boolean isWifiConnected = false;
        boolean isMobileInternetConnected = false;
        if (context != null) {
            try {

                ConnectivityManager cm = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null) { // connected to the internet
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        // connected to wifi
                        isWifiConnected = true;
                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        // connected to the mobile provider's data plan
                        isMobileInternetConnected = true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            LogUtil.d("isInternetConnected() -> Context is null");
        }
        return isWifiConnected || isMobileInternetConnected;
    }

    /**
     * Your app key hash is required for example, for Facebook Login in order to
     * perform security check before authorizing your app.
     *
     * @param context     the context
     * @param packageName name of the package (e.g. "com.example.app")
     * @return the application hash key
     */
    public static String getApplicationHashKey(Context context, String packageName) {

        String hash = "";
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                hash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
            }
        } catch (NameNotFoundException e) {
            LogUtil.e("NameNotFoundException");
        } catch (NoSuchAlgorithmException e) {
            LogUtil.e("NoSuchAlgorithmException");

        }
        return hash;
    }

    /**
     * Make the smartphone vibrate for a giving time. You need to put the
     * vibration permission in the manifest as follows: <uses-permission
     * android:name="android.permission.VIBRATE"/>
     *
     * @param context  the context
     * @param duration duration of the vibration in miliseconds
     */
    public static void vibrate(Context context, int duration) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(duration);
    }

    /**
     * Sleep
     *
     * @param milliseconds seconds that the app will sleep
     */
    public static void sleep(int milliseconds) {

        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            LogUtil.e("Interrupted exception", e);
        }
    }

    /**
     * Get the name of method where it puts
     *
     * @return current method name
     */
    public static String getCurrentMethodName() {
        return Thread.currentThread().getStackTrace()[3].getMethodName();
    }

    /**
     * Get device unique ID
     *
     * @param context the context
     * @return device id
     */
    public static String getDeviceID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    /**
     * Toggles the SoftKeyboard Input be careful where you call this from as if you want to
     * hide the keyboard and its already hidden it will be shown
     *
     * @param context the context
     */
    public static void toggleKeyboard(Context context) {
        InputMethodManager imm = ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE));
        imm.toggleSoftInput(0, 0);
    }

    /**
     * Hides the SoftKeyboard input careful as if you pass a mView that didn't open the
     * soft-keyboard it will ignore this call and not close
     *
     * @param v the mView that opened the soft-keyboard
     */
    public static void requestHideKeyboard(View v) {
        InputMethodManager imm = ((InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /**
     * Converts the number in pixels to the number in dips
     *
     * @param displayMetrics the display metrics
     * @param sizeInPixels   the size in pixels
     * @return the int
     */
    public static int convertToDip(DisplayMetrics displayMetrics, int sizeInPixels) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeInPixels, displayMetrics);
    }

    /**
     * Converts the number in dips to the number in pixels
     *
     * @param density    the density
     * @param sizeInDips the size in dips
     * @return the int
     */
    public static int convertToPix(float density, int sizeInDips) {
        float size = sizeInDips * density;
        return (int) size;
    }


    /**
     * Is this service running?
     *
     * @param context the context
     * @param service service to check
     * @return true if the service is running
     */
    public static boolean isServiceRunning(Context context, Class<? extends Service> service) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo runningServiceInfo : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (service.getName().equals(runningServiceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets application name by context.
     *
     * @param context the context
     * @return the application name by context
     */
    public static String getApplicationNameByContext(Context context) {
        final PackageManager pm = context.getPackageManager();
        ApplicationInfo ai;
        try {
            ai = pm.getApplicationInfo(context.getPackageName(), 0);
        } catch (final NameNotFoundException e) {
            ai = null;
        }
        return (String) (ai != null ? pm.getApplicationLabel(ai) : "(unknown)");
    }

    /**
     * Writes the specified byte[] to the specified File path.
     *
     * @param theFile File Object representing the path to write to.
     * @param bytes   The byte[] of data to write to the File.
     * @throws IOException Thrown if there is problem creating or writing the                     File.
     */
    public static void writeBytesToFile(File theFile, byte[] bytes) {
        try {
            FileUtils.writeByteArrayToFile(theFile, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read bytes from a File into a byte[].
     *
     * @param file The File to read.
     * @return A byte[] containing the contents of the File.
     * @throws IOException Thrown if the File is too long to read or couldn't be                     read fully.
     */
    public static byte[] readBytesFromFile(File file) {
        byte[] bytes = new byte[0];
        try {
            bytes = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * Compresses a single file (source) and prepares a zip file (target)
     *
     * @param source the source
     * @param target the target
     * @throws IOException the io exception
     */
    public static void compress(File source, File target) throws IOException {

        ZipOutputStream zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)));
        ZipEntry zipEntry = new ZipEntry(source.getName());
        zipOut.putNextEntry(zipEntry);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source), BUFFER);
        byte data[] = new byte[BUFFER];

        int count = 0;
        while ((count = bis.read(data, 0, BUFFER)) != -1) {
            zipOut.write(data, 0, count);
        }
        bis.close();
        zipOut.close();


    }

    /**
     * Decompresses a zip file (source) that has a single zip entry.
     *
     * @param source the source
     * @param target the target
     * @throws IOException the io exception
     */
    public static void decompress(File source, File target) throws IOException {
        ZipInputStream zipIn = new ZipInputStream(new BufferedInputStream(new FileInputStream(source), BUFFER));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(target));
        zipIn.getNextEntry();
        byte data[] = new byte[BUFFER];

        int count = 0;
        while ((count = zipIn.read(data, 0, BUFFER)) != -1) {
            bos.write(data, 0, count);
        }
        bos.close();
        zipIn.close();
    }

    private static final X500Principal DEBUG_DN = new X500Principal("CN=Android Debug,O=Android,C=US");


    /**
     * Gets version name.
     *
     * @param context the context
     * @return The version name of the application
     */
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    /**
     * Gets version code.
     *
     * @param context the context
     * @return The version code of the application
     */
    public static Integer getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    /**
     * Gets package name.
     *
     * @param context the context
     * @return The package name of the application
     */
    public static String getPackageName(Context context) {
        return getPackageInfo(context).packageName;
    }

    /**
     * Gets application name.
     *
     * @param context the context
     * @return The name of the application
     */
    public static String getApplicationName(Context context) {
        ApplicationInfo applicationInfo = getApplicationInfo(context);
        return context.getPackageManager().getApplicationLabel(applicationInfo).toString();
    }

    /**
     * Gets package info.
     *
     * @param context the context
     * @return the package info
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            // Do Nothing
        }
        return info;
    }

    /**
     * Gets application info.
     *
     * @param context the context
     * @return the application info
     */
    public static ApplicationInfo getApplicationInfo(Context context) {
        ApplicationInfo info = null;
        try {
            info = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
        } catch (NameNotFoundException e) {
            // Do Nothing
        }
        return info;
    }

    /**
     * Show soft input.
     *
     * @param activity the activity
     */
    public static void showSoftInput(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    /**
     * Hide soft input.
     *
     * @param view the mView
     */
    public static void hideSoftInput(View view) {
        ((InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                view.getWindowToken(), 0);
    }

    /**
     * Scroll to bottom.
     *
     * @param scroll the scroll
     */
    public static void scrollToBottom(final ScrollView scroll) {
        if (scroll != null) {
            scroll.post(new Runnable() {

                @Override
                public void run() {
                    scroll.fullScroll(View.FOCUS_DOWN);
                }
            });
        }
    }

    /**
     * Gets network operator name.
     *
     * @param context the context
     * @return the network operator name
     */
    public static String getNetworkOperatorName(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getNetworkOperatorName();
    }

    /**
     * Gets sim operator name.
     *
     * @param context the context
     * @return the sim operator name
     */
    public static String getSimOperatorName(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getSimOperatorName();
    }

    /**
     * Gets heap size.
     *
     * @param context the context
     * @return The HEAP size in MegaBytes
     */
    public static Integer getHeapSize(Context context) {
        return ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
    }

    /**
     * Gets available internal data size.
     *
     * @return The available storage in MegaBytes
     */
    @SuppressWarnings("deprecation")
    public static Long getAvailableInternalDataSize() {
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        long size = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
        return size / FileUtil.BYTES_TO_MB;
    }

    /**
     * Gets total internal data size.
     *
     * @return The total storage in MegaBytes
     */
    @SuppressWarnings("deprecation")
    public static Long getTotalInternalDataSize() {
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        long size = (long) stat.getBlockCount() * (long) stat.getBlockSize();
        return size / FileUtil.BYTES_TO_MB;
    }

    /**
     * Checks if the application is installed on the SD card.
     *
     * @param context the context
     * @return <code>true</code> if the application is installed on the sd card
     */
    public static Boolean isInstalledOnSdCard(Context context) {
        return (getPackageInfo(context).applicationInfo.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) == ApplicationInfo.FLAG_EXTERNAL_STORAGE;
    }

    /**
     * Is media mounted boolean.
     *
     * @return the boolean
     */
    public static Boolean isMediaMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * Gets device model.
     *
     * @return the device model
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * Gets device manufacturer.
     *
     * @return the device manufacturer
     */
    public static String getDeviceManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * Gets api level.
     *
     * @return the api level
     */
    public static Integer getApiLevel() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * Is pre kitkat boolean.
     *
     * @return the boolean
     */
    public static Boolean isPreKitkat() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT;
    }

    /**
     * Gets platform version.
     *
     * @return the platform version
     */
    public static String getPlatformVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * Has camera boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public static Boolean hasCamera(Context context) {
        return isIntentAvailable(context, MediaStore.ACTION_IMAGE_CAPTURE);
    }

    /**
     * Gets device name.
     *
     * @return the device name
     */
    public static String getDeviceName() {
        String manufacturer = getDeviceManufacturer();
        String model = getDeviceModel();
        if ((model != null) && model.startsWith(manufacturer)) {
            return ValidatorUtil.capitalize(model);
        } else if (manufacturer != null) {
            return ValidatorUtil.capitalize(manufacturer) + " " + model;
        } else {
            return "Unknown";
        }
    }


    /**
     * Indicates whether the specified action can be used as an intent. This method queries the package manager for
     * installed packages that can respond to an intent with the specified action. If no suitable package is found, this
     * method returns false.
     *
     * @param context the context
     * @param action  The Intent action to check for availability.
     * @return True if an Intent with the specified action can be sent and responded to, false otherwise.
     */
    public static boolean isIntentAvailable(Context context, String action) {
        return isIntentAvailable(context, new Intent(action));
    }

    /**
     * Indicates whether the specified intent can be used. This method queries the package manager for installed
     * packages that can respond to the specified intent. If no suitable package is found, this method returns false.
     *
     * @param context the context
     * @param intent  The Intent to check for availability.
     * @return True if the specified Intent can be sent and responded to, false otherwise.
     */
    public static boolean isIntentAvailable(Context context, Intent intent) {
        List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return !list.isEmpty();
    }

    /**
     * Start url.
     *
     * @param activity the activity
     * @param url      the url
     */
    public static void startUrl(Activity activity, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        activity.startActivity(intent);
    }

    /**
     * Execute an {@link AsyncTask} on a thread pool.
     *
     * @param <T>  Task argument type.
     * @param task Task to execute.
     * @param args Optional arguments to pass to {@link AsyncTask#execute(Object[])}.
     */
    @SafeVarargs
    public static <T> void execute(AsyncTask<T, ?, ?> task, T... args) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.DONUT) {
            throw new UnsupportedOperationException("This class can only be used on API 4 and newer.");
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            task.execute(args);
        } else {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, args);
        }
    }


}

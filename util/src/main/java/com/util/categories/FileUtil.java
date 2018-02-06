package com.util.categories;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * The type File util.
 */
public class FileUtil extends FileUtils {
    /**
     * The constant BYTES_TO_MB.
     */
// Amount of bytes on a megabyte
    public static final int BYTES_TO_MB = 1048576;
    private static final int BUFFER_SIZE = 16384;

    /**
     * protected constructor
     */
    protected FileUtil() {
    }

    /**
     * Check if the SD Card is Available
     *
     * @return true if the sd card is available and false if it is not
     */
    public static boolean isSDCardAvailable() {
        boolean mExternalStorageAvailable = false;

        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageAvailable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            mExternalStorageAvailable = true;

        } else {
            // Something else is wrong. It may be one of many other states,
            // but all we need
            // to know is we can neither read nor write
            mExternalStorageAvailable = false;
        }

        return mExternalStorageAvailable;

    }

    /**
     * Check if the SD Card is writable
     *
     * @return true if the sd card is writable and false if it is not
     */
    public static boolean isSDCardWritable() {

        boolean mExternalStorageWriteable = false;

        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media

            mExternalStorageWriteable = false;
        } else {
            // Something else is wrong. It may be one of many other states,
            // but all we need
            // to know is we can neither read nor write
            mExternalStorageWriteable = false;
        }

        return mExternalStorageWriteable;

    }

    /**
     * Creates the specified <code>toFile</code> as a byte for byte copy of the
     * <code>fromFile</code>. If <code>toFile</code> already exists, then it
     * will be replaced with a copy of <code>fromFile</code>. The name and path
     * of <code>toFile</code> will be that of <code>toFile</code>.<br/>
     * <br/>
     * <i> Note: <code>fromFile</code> and <code>toFile</code> will be closed by
     * this function.</i>
     *
     * @param fromFile - FileInputStream for the file to copy from.
     * @param toFile   - FileOutpubStream for the file to copy to.
     * @throws IOException the io exception
     */
    public static void copyFile(FileInputStream fromFile, FileOutputStream toFile) throws IOException {
        FileChannel fromChannel = null;
        FileChannel toChannel = null;
        try {
            fromChannel = fromFile.getChannel();
            toChannel = toFile.getChannel();
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
        } finally {
            try {
                if (fromChannel != null) {
                    fromChannel.close();
                }
            } finally {
                if (toChannel != null) {
                    toChannel.close();
                }
            }
        }
    }

    /**
     * Creates the specified <code>toFile</code> as a byte for byte copy of the
     * <code>fromFile</code>. If <code>toFile</code> already exists, then it
     * will be replaced with a copy of <code>fromFile</code>. The name and path
     * of <code>toFile</code> will be that of <code>toFile</code>.<br/>
     * <br/>
     * <i> Note: <code>fromFile</code> and <code>toFile</code> will be closed by
     * this function.</i>
     *
     * @param fromFile - File to copy from.
     * @param toFile   - File to copy to.
     */
    public static void copyFile(File fromFile, File toFile) {
        try {
            FileUtils.copyFile(fromFile, toFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the SDCard Path
     *
     * @return the complete path to the SDCard
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().toString() + "/";
    }

    /**
     * Get the SDCard Path as a File
     *
     * @return the complete path to the SDCard
     */
    public static File getSDCardPathFile() {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * Check if file exists on SDCard or not
     *
     * @param filePath - its the path of the file after SDCardDirectory (no need for                 getExternalStorageDirectory())
     * @return boolean - if file exist on SDCard or not
     */
    public static boolean checkIfFileExists(String filePath) {
        File file = new File(filePath);// getSDCardPath(), filePath);
        return file.exists();
    }

    /**
     * Create folder in the SDCard
     *
     * @param path the path
     * @return boolean
     */
    public static boolean createFolder(String path) {
        File direct = new File(Environment.getExternalStorageDirectory() + "/" + path);

        if (!direct.exists()) {
            if (direct.mkdir()) {
                return true;
            }

        }
        return false;
    }


    private final static String FILE_NAME = "Sparta_";
    private final static String FILE_NAME_IMAGE = "IMG_";
    private final static String FILE_NAME_VIDEO = "VID_";
    private final static String FILE_NAME_CAMERA_IMAGE = "IMG_CAMERA_";


    private final static String TAG = FileUtils.class.getSimpleName();

    /**
     * Functionality to get directory name file
     *
     * @param context the context
     * @return directory file where the all the application's files are stored
     */
    public static File getDirectoryApp(Context context) {
        String cacheFilePath = Environment.getExternalStorageDirectory() + "/Android/data/" + context.getPackageName();
        File appCacheDir = null;
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && hasExternalStoragePermission(context)) {
            appCacheDir = new File(cacheFilePath);
        }
        if (appCacheDir == null
                || (!appCacheDir.exists() && !appCacheDir.mkdirs())) {
            appCacheDir = context.getCacheDir();
        }
        LogUtil.d("directory path = "
                + appCacheDir);

        return appCacheDir;
    }

    /**
     * Functionality to get directory of images cache file
     *
     * @param context the context
     * @return directory file where the all the application's images are stored
     */
    public static File getDirectoryAppCacheImages(Context context) {
        File dir = new File(getDirectoryApp(context),
                "/CacheImages");
        if (!dir.exists() && !dir.mkdirs()) {
            dir = context.getCacheDir();
        }
        LogUtil.d("image directory path = "
                + dir);

        return dir;
    }

    /**
     * Functionality to get directory of images file
     *
     * @param context the context
     * @return directory file where the all the application's images are stored
     */
    public static File getDirectoryAppImages(Context context) {
        File dir = new File(getDirectoryApp(context),
                "/Images");
        if (!dir.exists() && !dir.mkdirs()) {
            dir = context.getCacheDir();
        }
        LogUtil.d("image directory path = "
                + dir);

        return dir;
    }

    /**
     * Functionality to get directory of images file
     *
     * @param context the context
     * @return directory file where the all the application's images are stored
     */
    public static File getDirectoryAppTemp(Context context) {
        File dir = new File(getDirectoryApp(context),
                "/Temp");
        if (!dir.exists() && !dir.mkdirs()) {
            dir = context.getCacheDir();
        }
        LogUtil.d("image directory path = "
                + dir);

        return dir;
    }

    /**
     * Functionality to get directory of images file
     *
     * @param context the context
     * @return directory file where the all the application's images are stored
     */
    public static File getDirectoryAppVideos(Context context) {
        File dir = new File(getDirectoryApp(context),
                "/Videos");
        if (!dir.exists() && !dir.mkdirs()) {
            dir = context.getCacheDir();
        }
        LogUtil.d("video directory path = "
                + dir);

        return dir;
    }


    /**
     * Gets static file.
     *
     * @param context the context
     * @param name    the name
     * @return the static file
     */
    public static File getStaticFile(Context context, String name) {
        if (TextUtils.isEmpty(name)) {
            name = "temp.jpg";
        }
        File dir = new File(getDirectoryAppTemp(context), name);
        return dir;

    }

    /**
     * Write string to file.
     *
     * @param file the file
     * @param data the data
     */
    public static void writeStringToFile(File file, String data) {
        try {
            org.apache.commons.io.FileUtils.writeStringToFile(file, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write string to file.
     *
     * @param file   the file
     * @param data   the data
     * @param append the append
     */
    public static void writeStringToFile(File file, String data, boolean append) {
        try {
            org.apache.commons.io.FileUtils.writeStringToFile(file, data, append);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context
                .checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
        return perm == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Gets application name.
     *
     * @param context the context
     * @return the application name
     */
    public static String getApplicationName(Context context) {
        int stringId = context.getApplicationInfo().labelRes;
        return context.getString(stringId);
    }


    /**
     * Define exif orientation int.
     *
     * @param imageUri the image uri
     * @return the int
     */
    public static int defineExifOrientation(String imageUri) {
        int rotation = 0;

        try {
            ExifInterface exif = new ExifInterface(imageUri);
            int exifOrientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (exifOrientation) {

                case ExifInterface.ORIENTATION_NORMAL:
                    rotation = 0;

                    break;

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotation = 90;

                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotation = 180;

                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotation = 270;

                    break;
                default:
                    // Nothing
                    break;
            }
        } catch (IOException e) {
            LogUtil.e("Can't read EXIF tags from file [%s]" + imageUri);
        }

        return rotation;
    }

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri     The Uri to query.
     * @return the path
     * @author paulburke
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

}

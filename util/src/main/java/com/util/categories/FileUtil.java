package com.util.categories;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * The type File util.
 */
public final class FileUtil {
    public static final int BYTES_TO_MB = 1048576;
    private final static String TAG = FileUtil.class.getSimpleName();
    static final int BUFFER = 2048;

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

    public static void copyFile(FileInputStream fromFile, FileOutputStream toFile) {
        try {
            IOUtils.copy(fromFile, toFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyFile(File fromFile, File toFile) {
        try {
            FileUtils.copyFile(fromFile, toFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File createFolder(Context context, String path) {
        File direct = new File(getDirectoryApp(context), path);
        if (!direct.exists()) {
            if (direct.mkdir()) {
                return direct;
            }
        }
        return direct;
    }

    public static File createFile(Context context, String path, String name) {
        return new File(createFolder(context, path), name);
    }

    /**
     * Functionality to get directory name file
     *
     * @param context the context
     * @return directory file where the all the application's files are stored
     */
    public static File getDirectoryApp(Context context) {
        String cacheFilePath = Environment.getExternalStorageDirectory() + "/Android/data/" + context.getPackageName();
        File appCacheDir = null;
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
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

    public static File getImages(Context context) {
        return createFolder(context, "images");
    }

    public static File getVideos(Context context) {
        return createFolder(context, "videos");
    }

    public static File createImageFile(Context context, String name) {
        return createFile(context, getImages(context).getPath(), name);
    }

    public static File createVideoFile(Context context, String name) {
        return createFile(context, getVideos(context).getPath(), name);
    }

    public static void writeStringToFile(File file, String data) {
        try {
            FileUtils.writeStringToFile(file, data, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeStringToFile(File file, String data, boolean append) {
        try {
            FileUtils.writeStringToFile(file, data, Charset.defaultCharset(), append);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeBytesToFile(File file, byte[] bytes) {
        try {
            FileUtils.writeByteArrayToFile(file, bytes);
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

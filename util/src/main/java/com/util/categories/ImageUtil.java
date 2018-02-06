package com.util.categories;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.Display;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * The type Image util.
 */
public class ImageUtil {

    /**
     * Convert drawable resource to bitmap
     *
     * @param context  the context
     * @param drawable drawable resource to be converted
     * @return a bitmap
     */
    public static Bitmap convertImageResourceToBitmap(@NonNull Context context, @DrawableRes int drawable) {
        return BitmapFactory.decodeResource(context.getResources(), drawable);
    }

    /**
     * Convert drawable to bitmap
     *
     * @param drawable drawable to be converted
     * @return a bitmap
     */
    public static Bitmap convertDrawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    /**
     * Save bitmap downscaled file.
     *
     * @param bitmap    the bitmap
     * @param filename  the filename
     * @param path      the path
     * @param recycle   the recycle
     * @param maxWidth  the max width
     * @param maxHeight the max height
     * @return the file
     */
    public static File saveBitmapDownscaled(Bitmap bitmap, String filename, String path, boolean recycle, int maxWidth, int maxHeight) {
        float heightScaleFactor = 1;
        float widthScaleFactor = 1;
        float scaleFactor = 1;

        if (bitmap.getHeight() > maxHeight) {
            heightScaleFactor = (float) maxHeight / (float) bitmap.getHeight();
        }

        if (bitmap.getWidth() > maxWidth) {
            widthScaleFactor = (float) maxWidth / (float) bitmap.getWidth();
        }
        if (heightScaleFactor < 1 || widthScaleFactor < 1) {
            scaleFactor = MathUtil.min(heightScaleFactor, widthScaleFactor);
        }
        bitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * scaleFactor), (int) (bitmap.getHeight() * scaleFactor), true);
        return saveBitmap(bitmap, filename, path, recycle);
    }

    /**
     * Save bitmap file.
     *
     * @param bitmap   the bitmap
     * @param filename the filename
     * @param path     the path
     * @param recycle  the recycle
     * @return the file
     */
    public static File saveBitmap(Bitmap bitmap, String filename, String path, boolean recycle) {
        FileOutputStream out = null;
        try {
            File f = new File(path, filename);
            if (!f.exists()) {
                f.createNewFile();
            }
            out = new FileOutputStream(f);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)) {
                return f;
            }
        } catch (Exception e) {
            LogUtil.e("Could not save bitmap", e);
        } finally {
            try {
                out.close();
            } catch (Throwable ignore) {
            }
            if (recycle) {
                bitmap.recycle();
            }
        }
        return null;
    }

    /**
     * Flip bitmap.
     *
     * @param src the src
     * @return the bitmap
     */
    public static Bitmap flip(Bitmap src) {
        Matrix m = new Matrix();
        m.preScale(-1, 1);
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, false);
    }

    /**
     * Size of int.
     *
     * @param data the data
     * @return the int
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static int sizeOf(Bitmap data) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
            return data.getRowBytes() * data.getHeight();
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return data.getByteCount();
        } else {
            return data.getAllocationByteCount();
        }
    }

    /**
     * Change bitmap contrast brightness bitmap.
     *
     * @param bmp        input bitmap
     * @param contrast   0..10 1 is default
     * @param brightness -255..255 0 is default
     * @return new bitmap
     */
    public static Bitmap changeBitmapContrastBrightness(Bitmap bmp, float contrast, float brightness) {
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        contrast, 0, 0, 0, brightness,
                        0, contrast, 0, 0, brightness,
                        0, 0, contrast, 0, brightness,
                        0, 0, 0, 1, 0
                });

        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        Canvas canvas = new Canvas(ret);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bmp, 0, 0, paint);

        return ret;
    }

    /**
     * Stores an image on the storage
     *
     * @param image       the image to store.
     * @param pictureFile the file in which it must be stored
     */
    public static void storeImage(Bitmap image, File pictureFile) {
        if (pictureFile == null) {
            LogUtil.d("Error creating media file, check storage permissions: ");
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            LogUtil.d("File not found: " + e.getMessage());
        } catch (IOException e) {
            LogUtil.d("Error accessing file: " + e.getMessage());
        }
    }

    /**
     * Get the screen height.
     *
     * @param context the context
     * @return the screen height
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static int getScreenHeight(Activity context) {

        Display display = context.getWindowManager().getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            display.getSize(size);
            return size.y;
        }
        return display.getHeight();
    }

    /**
     * Get the screen width.
     *
     * @param context the context
     * @return the screen width
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static int getScreenWidth(Activity context) {

        Display display = context.getWindowManager().getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            display.getSize(size);
            return size.x;
        }
        return display.getWidth();
    }
//
//    /**
//     * Gets tint drawable.
//     *
//     * @param context  the context
//     * @param drawable the drawable
//     * @param resId    the res id
//     * @return the tint drawable
//     */
//    public static Drawable getTintDrawable(@NonNull Context context, @DrawableRes int drawable, int resId) {
//        Drawable d = ContextCompat.getDrawable(context, drawable);
//        DrawableCompat.wrap(d);
//        DrawableCompat.setTintList(d, ContextCompat.getColorStateList(context, resId));
//        return d;
//    }
//
//    /**
//     * To compress Image
//     *
//     * @param context the context
//     * @return Compressor
//     */
//    public static Compressor compressor(Context context) {
//        return Comp`ressor.getDefault(context);
//    }
}

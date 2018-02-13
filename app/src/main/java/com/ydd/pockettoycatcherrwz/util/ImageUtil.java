package com.ydd.pockettoycatcherrwz.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class ImageUtil {

    /**
     * 图像缩放类型 - 适合
     */
    public static final int SCALE_FIT = 0;
    /**
     * 图像缩放类型 - 裁剪
     */
    public static final int SCALE_CROP = 1;

    /**
     * @param drawable drawable图片
     * @param roundPx  角度
     * @return
     * @Description:// 获得圆角图片的方法
     */

    public static Bitmap toRoundedCornerBitmap(Drawable drawable, float roundPx) {
        Bitmap bitmap = drawableToBitmap(drawable);
        return toRoundedCornerBitmap(bitmap, roundPx);
    }

    /**
     * @param drawable
     * @return
     * @Description:将Drawable转化为Bitmap
     */

    private static Bitmap drawableToBitmap(Drawable drawable) {

        int width = drawable.getIntrinsicWidth();

        int height = drawable.getIntrinsicHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height,

                drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888

                        : Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);

        drawable.setBounds(0, 0, width, height);

        drawable.draw(canvas);

        return bitmap;

    }

    /**
     * 转换图片成圆角矩形
     *
     * @param bitmap
     * @param pixels
     * @return
     */
    public static Bitmap toRoundedCornerBitmap(Bitmap bitmap, float pixels) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect((int) 0, (int) 0, (int) width, (int) height);
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 转换图片成圆形
     *
     * @param bitmap
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    /**
     * Get images from SD card by path and the name of image
     *
     * @param photoName
     * @return
     */
    public static Bitmap getBitmapFromDisk(String path, String photoName) {
        Bitmap photoBitmap = BitmapFactory.decodeFile(path + File.separator
                + photoName + ".png");
        if (photoBitmap == null) {
            return null;
        } else {
            return photoBitmap;
        }
    }

    /**
     * Get image from SD card by path and the name of image
     *
     * @param path
     * @param photoName
     * @return
     */
    public static boolean findBitmapFromDisk(String path, String photoName) {
        boolean flag = false;

        if (!TextUtils.isEmpty(path)) {
            File dir = new File(path);
            if (dir.exists()) {
                File folders = new File(path);
                File photoFile[] = folders.listFiles();
                for (int i = 0; i < photoFile.length; i++) {
                    String fileName = photoFile[i].getName().split("\\.")[0];
                    if (fileName.equals(photoName)) {
                        flag = true;
                    }
                }
            } else {
                flag = false;
            }
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * Save image to the SD card
     *
     * @param photoBitmap
     * @param photoName
     * @param path
     */
    public static void saveBitmapToDisk(Bitmap photoBitmap, String path,
                                        String photoName) {
        if (!TextUtils.isEmpty(path)) {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File photoFile = new File(path, photoName + ".png");
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmap != null) {
                    if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100,
                            fileOutputStream)) {
                        fileOutputStream.flush();
                    }
                }
            } catch (FileNotFoundException e) {
                photoFile.delete();
                e.printStackTrace();
            } catch (IOException e) {
                photoFile.delete();
                e.printStackTrace();
            } finally {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Get rounded corner images
     *
     * @param bitmap
     * @param roundPx 5 10
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, w, h);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * 从文件中读取指定缩放参数的图片
     *
     * @param pathName
     * @param dstWidth
     * @param dstHeight
     * @param scaleType
     * @return
     */
    public static Bitmap createScaledBitmap(String pathName, int dstWidth,
                                            int dstHeight, int scaleType) {
        Bitmap unscaledBitmap = decodeFile(pathName, dstWidth, dstHeight,
                scaleType);
        return createScaledBitmap(unscaledBitmap, dstWidth, dstHeight,
                scaleType);
    }

    /**
     * 从文件中读取保持比例的图片
     *
     * @param pathName
     * @param dstWidth
     * @param dstHeight
     * @param scaleType
     * @return
     */
    public static Bitmap decodeFile(String pathName, int dstWidth,
                                    int dstHeight, int scaleType) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inJustDecodeBounds = false;
        options.inSampleSize = calculateSampleSize(options.outWidth,
                options.outHeight, dstWidth, dstHeight, scaleType);
        Bitmap unscaledBitmap = BitmapFactory.decodeFile(pathName, options);
        return unscaledBitmap;
    }

    /**
     * 从文件中读取保持比例的图片
     *
     * @param pathName
     * @param dstWidth
     * @param dstHeight
     * @param scaleType
     * @param config
     * @return
     */
    public static Bitmap decodeFile(String pathName, int dstWidth,
                                    int dstHeight, int scaleType, Config config) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = config;
        options.inSampleSize = calculateSampleSize(options.outWidth,
                options.outHeight, dstWidth, dstHeight, scaleType);
        Bitmap unscaledBitmap = BitmapFactory.decodeFile(pathName, options);
        return unscaledBitmap;
    }

    public static int calculateSampleSize(int srcWidth, int srcHeight,
                                          int dstWidth, int dstHeight, int scaleType) {
        if (scaleType == SCALE_FIT) {
            final float srcAspect = (float) srcWidth / (float) srcHeight;
            final float dstAspect = (float) dstWidth / (float) dstHeight;

            if (srcAspect > dstAspect) {
                return srcWidth / dstWidth;
            } else {
                return srcHeight / dstHeight;
            }
        } else {
            final float srcAspect = (float) srcWidth / (float) srcHeight;
            final float dstAspect = (float) dstWidth / (float) dstHeight;

            if (srcAspect > dstAspect) {
                return srcHeight / dstHeight;
            } else {
                return srcWidth / dstWidth;
            }
        }
    }

    /**
     * 创建指定缩放类型的图片
     *
     * @param unscaledBitmap
     * @param dstWidth
     * @param dstHeight
     * @param scaleType
     * @return
     */
    public static Bitmap createScaledBitmap(Bitmap unscaledBitmap,
                                            int dstWidth, int dstHeight, int scaleType) {
        Rect srcRect = calculateSrcRect(unscaledBitmap.getWidth(),
                unscaledBitmap.getHeight(), dstWidth, dstHeight, scaleType);
        Rect dstRect = calculateDstRect(unscaledBitmap.getWidth(),
                unscaledBitmap.getHeight(), dstWidth, dstHeight, scaleType);

        Bitmap scaledBitmap = Bitmap.createBitmap(dstRect.width(),
                dstRect.height(), Config.ARGB_8888);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.drawBitmap(unscaledBitmap, srcRect, dstRect, new Paint(
                Paint.FILTER_BITMAP_FLAG));
        return scaledBitmap;
    }

    private static Rect calculateSrcRect(int srcWidth, int srcHeight,
                                         int dstWidth, int dstHeight, int scaleType) {
        if (scaleType == SCALE_CROP) {
            final float srcAspect = (float) srcWidth / (float) srcHeight;
            final float dstAspect = (float) dstWidth / (float) dstHeight;

            if (srcAspect > dstAspect) {
                final int srcRectWidth = (int) (srcHeight * dstAspect);
                final int srcRectLeft = (srcWidth - srcRectWidth) / 2;
                return new Rect(srcRectLeft, 0, srcRectLeft + srcRectWidth,
                        srcHeight);
            } else {
                final int srcRectHeight = (int) (srcWidth / dstAspect);
                final int scrRectTop = (int) (srcHeight - srcRectHeight) / 2;
                return new Rect(0, scrRectTop, srcWidth, scrRectTop
                        + srcRectHeight);
            }
        } else {
            return new Rect(0, 0, srcWidth, srcHeight);
        }
    }

    private static Rect calculateDstRect(int srcWidth, int srcHeight,
                                         int dstWidth, int dstHeight, int scaleType) {
        if (scaleType == SCALE_FIT) {
            final float srcAspect = (float) srcWidth / (float) srcHeight;
            final float dstAspect = (float) dstWidth / (float) dstHeight;
            if (srcAspect > dstAspect) {
                return new Rect(0, 0, dstWidth, (int) (dstWidth / srcAspect));
            } else {
                return new Rect(0, 0, (int) (dstHeight * srcAspect), dstHeight);
            }
        } else {
            return new Rect(0, 0, dstWidth, dstHeight);
        }
    }


    /**
     * 字节数组转化成图片
     *
     * @param imgBytes
     * @return
     */
    public static Bitmap bytes2Bimap(byte[] imgBytes) {
        if (imgBytes.length != 0) {
            try {
                return BitmapFactory.decodeByteArray(imgBytes, 0,
                        imgBytes.length);
            } catch (Exception e) {
            }
        }
        return null;

    }

    /**
     * 图片比例压缩
     *
     * @param image
     * @return
     */
    public static Bitmap compressZoom(Bitmap image) {
        return compressZoom(image, 0, 0, null, null);
    }

    /**
     * 图片比例压缩
     *
     * @param image
     * @param be       图片压缩比例
     * @param fileSize 图片压缩后的大小
     * @return
     */
    public static Bitmap compressZoom(Bitmap image, int be, int fileSize,
                                      Bitmap.CompressFormat compressFormat,
                                      Options compressOpts) {
        if (null == image) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        // 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
        if (baos.toByteArray().length / 1024 > 1024) {
            baos.reset();
            // 这里压缩50%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Options newOpts = new Options();
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        if (be <= 0) {
            float hh = 800f;// 这里设置高度为800f
            float ww = 480f;// 这里设置宽度为480f
            // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            // 如果宽度大的话根据宽度固定大小缩放
            if (w > h && w > ww) {
                be = (int) (newOpts.outWidth / ww);
            } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
                be = (int) (newOpts.outHeight / hh);
            }
            if (be <= 0) {
                be = 1;// be=1表示不缩放
            }
        }
        // 设置缩放比例
        newOpts.inSampleSize = be;
        // 降低图片从ARGB888到RGB565
        newOpts.inPreferredConfig = Config.RGB_565;
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap, fileSize, compressFormat, compressOpts);
    }

    /**
     * 图片比例压缩
     *
     * @param image
     * @param be    图片压缩比例
     * @return
     */
    public static Bitmap compressZoom(Bitmap image, int be) {
        if (null == image) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Options newOpts = new Options();
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        if (be <= 0) {
            float hh = 800f;// 这里设置高度为800f
            float ww = 480f;// 这里设置宽度为480f
            // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            // 如果宽度大的话根据宽度固定大小缩放
            if (w > h && w > ww) {
                be = (int) (newOpts.outWidth / ww);
            } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
                be = (int) (newOpts.outHeight / hh);
            }
            if (be <= 0) {
                be = 1;// be=1表示不缩放
            }
        }
        // 设置缩放比例
        newOpts.inSampleSize = be;
        // 降低图片从ARGB888到RGB565
        newOpts.inPreferredConfig = Config.RGB_565;
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return bitmap;
    }

    /**
     * 图片质量压缩
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        return compressImage(image, 0, null, null);
    }

    /**
     * 图片质量压缩
     *
     * @param image
     * @param fileSize 图片压缩后的大小
     * @return
     */
    public static Bitmap compressImage(Bitmap image, int fileSize,
                                       Bitmap.CompressFormat compressFormat, Options opts) {
        if (null == image) {
            return null;
        }
        if (compressFormat == null) {
            compressFormat = Bitmap.CompressFormat.JPEG;
        }
        if (fileSize <= 0) {
            fileSize = 100;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(compressFormat, 100, baos);
        if (compressFormat != Bitmap.CompressFormat.PNG) {
            int options = 100;
            // 循环判断如果压缩后图片是否大于100KB,大于继续压缩
            while (options > 0 && baos.toByteArray().length / 1024 >= fileSize) {
                baos.reset();// 重置baos即清空baos
                options -= 10;// 每次都减少10
                image.compress(compressFormat, options, baos);

            }
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, opts);
        return bitmap;
    }

    /**
     * 获取纵向的图片
     *
     * @param filePath
     * @return
     */
    public static Bitmap getVerticalBitmap(String filePath) {

        if (TextUtils.isEmpty(filePath)) {
            return null;
        }

        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(filePath);
        } catch (IOException e) {
            return null;
        }
        int tag = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                -1);
        // 角度旋转
        int degree = 0;
        if (tag == ExifInterface.ORIENTATION_ROTATE_90) {
            degree = 90;
        } else if (tag == ExifInterface.ORIENTATION_ROTATE_180) {
            degree = 180;
        } else if (tag == ExifInterface.ORIENTATION_ROTATE_270) {
            degree = 270;
        }
        Bitmap bitmap = decodeFile(filePath, 1280, 720, SCALE_FIT);
        if (degree != 0 && bitmap != null) {
            Matrix m = new Matrix();
            m.setRotate(degree, (float) bitmap.getWidth() / 2,
                    (float) bitmap.getHeight() / 2);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
        }

        return bitmap;
    }

    /**
     * bitmap修改为黑白图片
     *
     * @param graymap
     * @return
     */
    public static Bitmap toGrayscale(Bitmap graymap) {
        // 得到图形的宽度和长度
        int width = graymap.getWidth();
        int height = graymap.getHeight();
        // 创建二值化图像
        Bitmap binarymap = null;
        binarymap = graymap.copy(Config.ARGB_8888, true);
        // 依次循环，对图像的像素进行处理
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // 得到当前像素的值
                int col = binarymap.getPixel(i, j);
                // 得到alpha通道的值
                int alpha = col & 0xFF000000;
                // 得到图像的像素RGB的值
                int red = (col & 0x00FF0000) >> 16;
                int green = (col & 0x0000FF00) >> 8;
                int blue = (col & 0x000000FF);
                // 用公式X = 0.3×R+0.59×G+0.11×B计算出X代替原来的RGB
                // 图像知识：byqiuchunlong
                // 当其权值取不同的值时，能够形成不同灰度的灰度图象，由于人眼对绿色的敏感度最高，红色次之，
                // 蓝色最低，因此当wg > wr > wb时，所产生的灰度图像更符合人眼的视觉感受。
                // 通常wr=30%，wg=59%，wb=11%，图像的灰度最合理。
                int gray = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                // 对图像进行二值化处理
                // 再此不做二值话处理
                // if (gray <= 95) {
                // gray = 0;
                // } else {
                // gray = 255;
                // }
                // 新的ARGB
                int newColor = alpha | (gray << 16) | (gray << 8) | gray;
                // 设置新图像的当前像素值
                binarymap.setPixel(i, j, newColor);
            }
        }
        return binarymap;
    }


    /**
     * Bitmap转换成byte[]并且进行压缩,压缩到不大于maxkb
     *
     * @param bitmap
     * @param maxkb
     * @return
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap, int maxkb) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        LogUtil.printJ(output.toByteArray().length + "= length");
        int options = 100;
        while (output.toByteArray().length > maxkb && options != 10) {
            output.reset(); //清空output
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
            options -= 10;
        }
        LogUtil.printJ(output.toByteArray().length + "= length");
        return output.toByteArray();
    }


    /**
     * 图片压缩比例计算
     *
     * @param options        BitmapFactory.Options
     * @param minSideLength  小边长，单位为像素，如果为-1，则不按照边来压缩图片
     * @param maxNumOfPixels 这张片图片最大像素值，单位为byte，如100*1024
     * @return 压缩比例, 必须为2的次幂
     */
    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }


    /**
     * 计算图片的压缩比例，用于图片压缩
     *
     * @param options        BitmapFactory.Options
     * @param minSideLength  小边长，单位为像素，如果为-1，则不按照边来压缩图片
     * @param maxNumOfPixels 这张片图片最大像素值，单位为byte，如100*1024
     * @return 压缩比例
     */
    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }

    }


}

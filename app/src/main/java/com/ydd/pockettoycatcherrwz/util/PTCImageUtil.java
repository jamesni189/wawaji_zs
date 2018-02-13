package com.ydd.pockettoycatcherrwz.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片处理
 * 
 * Created by czhang on 17/4/21.
 */

public class PTCImageUtil {

	/**
	 * 图像缩放类型 - 适合
	 */
	public static final int SCALE_FIT = 0;
	/**
	 * 图像缩放类型 - 裁剪
	 */
	public static final int SCALE_CROP = 1;
	/**
	 * 图片最大宽高
	 */
	private static final int MAX_IMG_SIZE = 1080;

	/**
	 * 压缩图片
	 * 
	 * @param imgPath
	 *            图片地址
	 * @return
	 */
	public static Bitmap compressImg(String imgPath) {
		int degree = readPictureDegree(imgPath);
		Bitmap bmp = decodeFile(imgPath, MAX_IMG_SIZE, MAX_IMG_SIZE,
				ImageUtil.SCALE_FIT);
		if (bmp == null) {
			return null;
		}
		// 旋转图片
		if (degree == 0) {
			return bmp;
		}
		Bitmap newBmp = rotateImageView(degree, bmp);
		return newBmp;
	}

	/**
	 * 读取图片属性：旋转的角度
	 * 
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 旋转图片
	 * 
	 * @param angle
	 *            旋转角度
	 * @param bitmap
	 *            待旋转图片
	 * @return 旋转后的图片
	 */
	public static Bitmap rotateImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	/**
	 * 回收图片
	 * 
	 * @param bmp
	 *            待回收图片
	 */
	public static void recycleBitmap(Bitmap bmp) {
		if (bmp != null && !bmp.isRecycled()) {
			bmp.recycle();
		}
	}

	/**
	 * 从文件中读取保持比例的图片,色彩模式为RGB565
	 *
	 * @param pathName
	 *            图片地址
	 * @return
	 */
	public static Bitmap decodeFile565(String pathName) {
		if (TextUtils.isEmpty(pathName)) {
			return null;
		}
		File file = new File(pathName);
		if (!file.exists()) {
			return null;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		Bitmap bmp = BitmapFactory.decodeFile(pathName, options);
		return bmp;
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
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pathName, options);
		options.inJustDecodeBounds = false;
		options.inSampleSize = calculateSampleSize(options.outWidth,
				options.outHeight, dstWidth, dstHeight, scaleType);
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		Bitmap unscaledBitmap = BitmapFactory.decodeFile(pathName, options);
		return unscaledBitmap;
	}

	/**
	 * 计算sampleSize
	 * 
	 * @return
	 */
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

			File photoFile = new File(path, photoName + ".jpg");
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(photoFile);
				if (photoBitmap != null) {
					if (photoBitmap.compress(Bitmap.CompressFormat.JPEG, 80,
							fileOutputStream)) {
						fileOutputStream.flush();
					}
				}
			} catch (FileNotFoundException e) {
				photoFile.delete();
				// e.printStackTrace();
			} catch (IOException e) {
				photoFile.delete();
				// e.printStackTrace();
			} finally {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					// e.printStackTrace();
				}
			}
		}
	}



}

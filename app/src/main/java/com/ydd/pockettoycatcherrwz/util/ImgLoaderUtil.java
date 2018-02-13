package com.ydd.pockettoycatcherrwz.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

/**
 * Android-Universal-Image-Loader辅助类
 */
public class ImgLoaderUtil {

	/**
	 * 通用的option
	 */
	public static DisplayImageOptions commonOption = new DisplayImageOptions.Builder()
			.bitmapConfig(Bitmap.Config.RGB_565)
			.imageScaleType(ImageScaleType.EXACTLY).cacheInMemory(true)
			.cacheOnDisk(true).build();

	/**
	 * 加载图片，使用默认DisplayImageOptions
	 *
	 * @param url
	 * @param imageView
	 */
	public static void displayImage(String url, ImageView imageView) {
		displayImage(url, imageView, commonOption, null, null);
	}

	/**
	 * 加载图片，使用自定义的placeholder
	 *
	 * @param url
	 * @param imageView
	 * @param placeholder
	 */
	public static void displayImage(String url, ImageView imageView,
			Drawable placeholder) {
		DisplayImageOptions options = null;
		if (placeholder == null) {
			options = commonOption;
		} else {
			options = new DisplayImageOptions.Builder()
					.bitmapConfig(Bitmap.Config.RGB_565)
					.imageScaleType(ImageScaleType.EXACTLY)
					.showImageForEmptyUri(placeholder)
					.showImageOnFail(placeholder)
					.showImageOnLoading(placeholder).cacheInMemory(true)
					.cacheOnDisk(true).considerExifParams(true).build();
		}
		displayImage(url, imageView, options, null, null);
	}

	/**
	 * 异步加载图片统一方法
	 *
	 * @param url
	 *            图片相对地址
	 * @param imageView
	 *            需要展示图片的imageView视图
	 * @param options
	 *            加载图片的配置项
	 */
	public static void displayImage(String url, ImageView imageView,
			DisplayImageOptions options) {
		if (options == null) {
			options = commonOption;
		}
		displayImage(url, imageView, options, null, null);
	}

	/**
	 * 异步加载图片统一方法
	 *
	 * @param url
	 *            图片相对地址
	 * @param imageView
	 *            需要展示图片的imageView视图
	 * @param options
	 *            加载图片的配置项
	 */
	public static void displayImage(String url, ImageView imageView,
			DisplayImageOptions options, ImageLoadingListener listener) {
		if (options == null) {
			options = commonOption;
		}
		displayImage(url, imageView, options, listener, null);
	}

	/**
	 * 异步加载图片统一方法
	 * 
	 * @param url
	 *            图片相对地址
	 * @param imageView
	 *            需要展示图片的imageView视图
	 * @param options
	 *            加载图片的配置项
	 * @param listener
	 *            加载图片回调接口
	 * @param progressListener
	 *            加载图片进度回调接口
	 */
	public static void displayImage(String url, ImageView imageView,
			DisplayImageOptions options, ImageLoadingListener listener,
			ImageLoadingProgressListener progressListener) {
		if (options == null) {
			options = commonOption;
		}
		ImageLoader.getInstance().displayImage(url, imageView, options,
				listener, progressListener);
	}

}

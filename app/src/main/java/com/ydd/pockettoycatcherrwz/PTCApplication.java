package com.ydd.pockettoycatcherrwz;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.WindowManager;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.mina.MinaManager;
import com.ydd.pockettoycatcherrwz.util.Constants;
import com.ydd.pockettoycatcherrwz.util.hx.HXBaseUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import io.agora.openlive.model.WorkerThread;
import wawaji_client.ZegoApiManager;

/**
 * 应用application
 * 
 */
public class PTCApplication extends Application {

	public static PTCApplication instance;

	public static boolean isUploaded;

	public static boolean isHomeLoaded;

	private List<Activity> oList;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		// 网络框架初始化
		BusinessManager.getInstance().init(this);
		// imageloader初始化
		initImageLoader(this);
		// 初始化屏幕宽高
		initScreenSize();
		// 环信初始化
		HXBaseUtil.init(this);
		// 长连接
		MinaManager.getInstance();
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
		CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(this,R.layout.view_jpush,R.id.jpush_icon,R.id.jpush_title,R.id.jpush_content);
		builder.statusBarDrawable = R.drawable.jpush_staus;
		builder.layoutIconDrawable = R.mipmap.ic_launcher;
		JPushInterface.setDefaultPushNotificationBuilder(builder);

		File f = new File(Constants.APP_PATH);

		MyCrashHandler.getInstance().init(getApplicationContext());

		if (!f.exists()) {
			f.mkdir();
		}
		f = new File(Constants.VIDEO_PATH);
		if (!f.exists()) {
			f.mkdir();
		}

		// 初始化友盟
		UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE,
				"5a0a8f73f43e48645f0001a2");
		//	5a0a8f73f43e48645f0001a2
		// 场景设置为普通统计场景类型
		MobclickAgent.setScenarioType(this,
				MobclickAgent.EScenarioType.E_DUM_NORMAL);

		oList = new ArrayList<Activity>();

		//zego 初始化sdk
		ZegoApiManager.getInstance().initSDK();

//		UMConfigure.init(this,null, null,UMConfigure.DEVICE_TYPE_PHONE, String pushSecret);


		// 新增secret Key接口,防止appkey被盗用,secretkey网站申请
//		MobclickAgent.setSecret(this, "5a0a8f73f43e48645f0001a2");
	}

	/**
	 * 初始化ImageLoader
	 */
	private void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
						// .memoryCacheExtraOptions(1280, 1920) // default =
						// device screen dimensions
						// .diskCacheExtraOptions(1280, 1920, null)
						.threadPoolSize(3)
						// default
						.threadPriority(Thread.NORM_PRIORITY - 2)
						// default
						.tasksProcessingOrder(QueueProcessingType.FIFO)
						// default ，不需要多个尺寸大小在内存中
						// .denyCacheImageMultipleSizesInMemory()
						/**
						 * UIL中的内存缓存策略 1. 只使用的是强引用缓存
						 * LruMemoryCache（这个类就是这个开源框架默认的内存缓存类，缓存的是bitmap的强引用
						 * ，下面我会从源码上面分析这个类） 2.使用强引用和弱引用相结合的缓存有
						 * UsingFreqLimitedMemoryCache（如果缓存的图片总量超过限定值，
						 * 先删除使用频率最小的bitmap） LRULimitedMemoryCache
						 * （这个也是使用的lru算法，和LruMemoryCache不同的是，他缓存的是bitmap的弱引用）
						 * FIFOLimitedMemoryCache（先进先出的缓存策略，当超过设定值，
						 * 先删除最先加入缓存的bitmap）
						 * LargestLimitedMemoryCache(当超过缓存限定值，先删除最大的bitmap对象)
						 * LimitedAgeMemoryCache（当 bitmap加入缓存中的时间超过我们设定的值，将其删除）
						 * 3.只使用弱引用缓存
						 * WeakMemoryCache（这个类缓存bitmap的总大小没有限制，唯一不足的地方就是不稳定，
						 * 缓存的图片容易被回收掉）
						 */
						.memoryCache(new UsingFreqLimitedMemoryCache(
								8 * 1024 * 1024))
						// 限制内存大小
						.memoryCacheSize(8 * 1024 * 1024)
						.memoryCacheSizePercentage(10)
						// default
						// .diskCache(new UnlimitedDiscCache(cacheDir)) //
						// default
						.diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(100)
						.diskCacheFileNameGenerator(
								new HashCodeFileNameGenerator()) // default
						.imageDownloader(new BaseImageDownloader(context)) // default
						.imageDecoder(new BaseImageDecoder(true)) // default
						.defaultDisplayImageOptions(
								DisplayImageOptions.createSimple()) // default
						.build();

		// 初始化imageLoader
		ImageLoader.getInstance().init(config);
	}

	/**
	 * 初始化屏幕宽高，若失败，取默认的
	 */
	private void initScreenSize() {
		WindowManager wm = (WindowManager) getSystemService(
				Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		if (width > 0) {
			RunningContext.screenWidth = width;
		} else {
			RunningContext.screenWidth = (int) getResources()
					.getDimension(R.dimen.default_screen_width);
		}
		if (height > 0) {
			RunningContext.screenHeight = height;
		} else {
			RunningContext.screenHeight = (int) getResources()
					.getDimension(R.dimen.default_screen_height);
		}
	}

	// ------------------声网--------------------
	private WorkerThread mWorkerThread;

	public synchronized void initWorkerThread() {
		if (mWorkerThread == null) {
			mWorkerThread = new WorkerThread(getApplicationContext());
			mWorkerThread.start();

			mWorkerThread.waitForReady();
		}
	}

	public synchronized WorkerThread getWorkerThread() {
		return mWorkerThread;
	}

	public synchronized void deInitWorkerThread() {
		mWorkerThread.exit();
		try {
			mWorkerThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		mWorkerThread = null;
	}


	/**
	 * 添加Activity
	 */
	public void addActivity_(Activity activity) {
// 判断当前集合中不存在该Activity
		if (!oList.contains(activity)) {
			oList.add(activity);//把当前Activity添加到集合中
		}
	}

	/**
	 * 销毁单个Activity
	 */
	public void removeActivity_(Activity activity) {
//判断当前集合中存在该Activity
		if (oList.contains(activity)) {
			oList.remove(activity);//从集合中移除
			activity.finish();//销毁当前Activity
		}
	}

	/**
	 * 销毁所有的Activity
	 */
	public void removeALLActivity_() {
		//通过循环，把集合中的所有Activity销毁
		for (Activity activity : oList) {
			activity.finish();
		}
	}
}

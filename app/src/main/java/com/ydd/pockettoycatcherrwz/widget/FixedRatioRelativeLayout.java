package com.ydd.pockettoycatcherrwz.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;

import com.ydd.pockettoycatcherrwz.R;

/**
 * 可配置宽高比的自定义Layout
 * 
 * @author mowei May 23, 2015
 */
public class FixedRatioRelativeLayout extends RelativeLayout {

	private static final float DEFAULT_RATIO = 1.0f;

	/**
	 * mRatio = height / width
	 */
	private float mRatio = DEFAULT_RATIO;

	/**
	 * 是否需要根据屏幕密度做适配
	 */
	private boolean mAdaptDensity = false;

	public FixedRatioRelativeLayout(Context context) {
		super(context);
	}

	public FixedRatioRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		Init(context, attrs);
	}

	public FixedRatioRelativeLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		Init(context, attrs);
	}

	private void Init(Context context, AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.FixedRatioRelativeLayout);
		mRatio = a.getFloat(R.styleable.FixedRatioRelativeLayout_ratio,
				DEFAULT_RATIO);
		mAdaptDensity = a.getBoolean(
				R.styleable.FixedRatioRelativeLayout_adaptDensity, false);
		a.recycle();
	}

	/**
	 * 重新设置宽高比例，并且刷新视图
	 * 
	 * @param ratio
	 */
	public void resetRatio(float ratio) {
		this.mRatio = ratio;
		invalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int originalWidth = MeasureSpec.getSize(widthMeasureSpec);
		float realRatio = calculateRatioByDensity();
		int aspectHeight = (int) (originalWidth * realRatio);

		super.onMeasure(
				MeasureSpec.makeMeasureSpec(originalWidth, MeasureSpec.EXACTLY),
				MeasureSpec.makeMeasureSpec(aspectHeight, MeasureSpec.EXACTLY));
	}

	/**
	 * 根据当前屏幕密度重新计算ratio
	 * 
	 * @return
	 */
	private float calculateRatioByDensity() {
		if (mAdaptDensity) {
			// 需要根据屏幕密度适配
			// 使用场景，内容是文字，大小跟密度相关
			Activity activity = (Activity) getContext();
			DisplayMetrics dm = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
			int standardDensity = Math.round(dm.density);
			return mRatio * (dm.density / standardDensity);
		} else {
			// 不需要屏幕密度适配
			// 使用场景，内容是纯图片
			return mRatio;
		}
	}

}
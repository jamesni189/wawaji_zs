package com.ydd.pockettoycatcherrwz.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.ydd.pockettoycatcherrwz.R;


/**
 * 带圆角/圆形图片处理
 * 支持以下特性：
 * 1.全/部分矩形圆角处理
 * 2.圆形图片处理
 * 3.边框处理（色值和大小）
 * Created by xiuliang on 2016/10/10.
 */
public class RoundedCPImageView extends ImageView {
	/**
	 * 默认高宽比
	 */
	protected static final float DEFAUT_RATIO = -1.0F;
	/**
	 * 高宽比，mHeightRatio = height/width
	 */
	protected float mHeightRatio = DEFAUT_RATIO;
	/**
	 * 设置的Drawable
	 */
	private Drawable mDrawable;
	/**
	 * 图片资源ID
	 */
	private int mResource;
	/**
	 * 边框宽度
	 */
	private float mBorderWidth;
	/**
	 * 是否是圆形图片
	 */
	private boolean mIsOval = false;
	/**
	 * 图片缩放类型
	 */
	private ScaleType mScaleType;
	/**
	 * 圆角半径大小
	 */
	private float mRadius;
	/**
	 * 圆角半径大小左上
	 */
	private float mRadiusTopLeft;
	/**
	 * 圆角半径大小右上
	 */
	private float mRadiusTopRight;
	/**
	 * 圆角半径大小左下
	 */
	private float mRadiusBottomLeft;
	/**
	 * 圆角半径大小右下
	 */
	private float mRadiusBottomRight;
	/**
	 * 边框颜色
	 */
	private ColorStateList mBorderColor;

	private static final ScaleType[] SCALE_TYPES = { ScaleType.MATRIX,
			ScaleType.FIT_XY, ScaleType.FIT_START, ScaleType.FIT_CENTER,
			ScaleType.FIT_END, ScaleType.CENTER, ScaleType.CENTER_CROP,
			ScaleType.CENTER_INSIDE };

	public RoundedCPImageView(Context context) {
		super(context);
		init(context, null);
	}

	public RoundedCPImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public RoundedCPImageView(Context context, AttributeSet attrs,
							  int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	public RoundedCPImageView(Context context, boolean isOval, int radius) {
		super(context, null);
		mIsOval = isOval;
		mRadius = radius;
		init(context, null);
	}

	private void init(Context context, AttributeSet attrs) {
		if (null == attrs) {
			return;
		}
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.NJRoundedCPImageView);
		int index = a.getInt(R.styleable.NJRoundedCPImageView_android_scaleType,
				-1);
		if (index >= 0) {
			setScaleType(SCALE_TYPES[index]);
		} else {
			setScaleType(ScaleType.FIT_CENTER);
		}
		mRadius = a.getDimensionPixelSize(
				R.styleable.NJRoundedCPImageView_frame_corner_radius, -1);
		mRadiusTopLeft = a.getDimensionPixelSize(
				R.styleable.NJRoundedCPImageView_frame_corner_radius_top_left, 0);
		mRadiusTopRight = a.getDimensionPixelSize(
				R.styleable.NJRoundedCPImageView_frame_corner_radius_top_right, 0);
		mRadiusBottomLeft = a.getDimensionPixelSize(
				R.styleable.NJRoundedCPImageView_frame_corner_radius_bottom_left, 0);
		mRadiusBottomRight = a.getDimensionPixelSize(
				R.styleable.NJRoundedCPImageView_frame_corner_radius_bottom_right, 0);
		mBorderWidth = a.getDimensionPixelSize(
				R.styleable.NJRoundedCPImageView_frame_border_width, 0);
		mIsOval = a.getBoolean(R.styleable.NJRoundedCPImageView_frame_oval, false);
		mHeightRatio = a.getFloat(
				R.styleable.NJRoundedCPImageView_frame_rounder_height_ratio,
				DEFAUT_RATIO);
		mBorderColor = a
				.getColorStateList(R.styleable.NJRoundedCPImageView_frame_border_color);
		if (mBorderColor == null) {
			mBorderColor = ColorStateList
					.valueOf(RoundedDrawable.DEFAULT_BORDER_COLOR);
		}
		a.recycle();
		updateDrawableAttrs();
	}

	@Override
	protected void drawableStateChanged() {
		super.drawableStateChanged();
		invalidate();
	}

	@Override
	public void setScaleType(ScaleType scaleType) {
		if (mScaleType != scaleType) {
			mScaleType = scaleType;
			switch (scaleType) {
			case MATRIX:
				super.setScaleType(scaleType);
				break;
			default:
				super.setScaleType(ScaleType.FIT_XY);
				break;
			}
			updateDrawableAttrs();
			invalidate();
		}
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		if (bm == null) {
			return;
		}
		mResource = 0;
		mDrawable = RoundedDrawable.fromBitmap(bm);
		updateDrawableAttrs();
		super.setImageDrawable(mDrawable);
		if (null != mOnBitmapLoadCompletedListener) {
			mOnBitmapLoadCompletedListener.onLoadCompleted();
		}
	}

	@Override
	public void setImageDrawable(Drawable drawable) {
		if (drawable == null) {
			return;
		}
		mResource = 0;
		mDrawable = RoundedDrawable.fromDrawable(drawable);
		updateDrawableAttrs();
		super.setImageDrawable(mDrawable);
	}

	@Override
	public void setImageResource(int resId) {
		if (mResource == resId) {
			return;
		}
		mResource = resId;
		mDrawable = resolveResource();
		updateDrawableAttrs();
		super.setImageDrawable(mDrawable);
	}

	@Override
	public void setImageURI(Uri uri) {
		super.setImageURI(uri);
		setImageDrawable(getDrawable());
	}

	private Drawable resolveResource() {
		Resources resources = getResources();
		if (resources == null) {
			return null;
		}
		Drawable drawable = null;
		if (mResource != 0) {
			try {
				drawable = resources.getDrawable(mResource);
			} catch (Exception e) {
				mResource = 0;
			}
		}
		return RoundedDrawable.fromDrawable(drawable);
	}

	/**设置边框大小*/
	public void setBorderWidth(float width){
		mBorderWidth = width;
	}

	/**设置边框色值*/
	public void setBorderColor(ColorStateList color){
		mBorderColor = color;
	}

	/**设置是否圆形，否则为矩形*/
	public void setOval(boolean isOval){
		mIsOval = isOval;
	}

	/**设置圆角或圆形半径*/
	public void setRadius(float radius){
		mRadius = radius;
	}

	/**设置四个方向圆角半径*/
	public void setCornerRadius(float topLeft ,float topRight,float bottomLeft,float bottomRight){
		mRadiusTopLeft = topLeft;
		mRadiusTopRight = topRight;
		mRadiusBottomLeft = bottomLeft;
		mRadiusBottomRight = bottomRight;
	}

	private void updateDrawableAttrs() {
		updateAttrs(mDrawable, mScaleType);
	}

	/**更新属性*/
	private void updateAttrs(Drawable drawable, ScaleType scaleType) {
		if (drawable == null) {
			return;
		}
		// 更新图片属性
		((RoundedDrawable) drawable)
				.setScaleType(scaleType)
				.setBorderWidth(mBorderWidth)
				.setBorderColor(mBorderColor)
				.setOval(mIsOval)
				.setCornerRadius(mRadius,mRadiusTopLeft, mRadiusTopRight,
						mRadiusBottomLeft, mRadiusBottomRight);
	}

	/**图片加载完成之后的回调*/
	private OnBitmapLoadCompletedListener mOnBitmapLoadCompletedListener;

	public void setOnBitmapLoadCompletedListener(OnBitmapLoadCompletedListener l) {
		mOnBitmapLoadCompletedListener = l;
	}

	/**
	 * 图片加载到控件之后的回调
	 */
	public interface OnBitmapLoadCompletedListener {
		void onLoadCompleted();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// 计算原始宽高
		int originalWidth = MeasureSpec.getSize(widthMeasureSpec);
		int originalHeight = MeasureSpec.getSize(heightMeasureSpec);
		// 根据配置，计算实际宽高
		int realHeight = originalHeight;
		if (mHeightRatio > 0) {
			realHeight = (int) (originalWidth * mHeightRatio);
		}
		super.onMeasure(
				MeasureSpec.makeMeasureSpec(originalWidth, MeasureSpec.EXACTLY),
				MeasureSpec.makeMeasureSpec(realHeight, MeasureSpec.EXACTLY));
	}
}

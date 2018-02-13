/*
 * Copyright (C) 2015 Vincent Mi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ydd.pockettoycatcherrwz.widget;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView.ScaleType;

/**
 * 支持：1.圆角处理 2.圆形图片处理 3.边框大小和色值设置
 * 
 */
public class RoundedDrawable extends Drawable {
	/**
	 * 默认边框色值
	 */
	public static final int DEFAULT_BORDER_COLOR = Color.BLACK;
	/**
	 * 控件边界区域
	 */
	private final RectF mBounds = new RectF();
	/**
	 * Drawable区域
	 */
	private final RectF mDrawableRect = new RectF();
	/**
	 * 原始图片区域
	 */
	private final RectF mBitmapRect = new RectF();
	/**
	 * 原始图片
	 */
	private final Bitmap mBitmap;
	/**
	 * 画笔
	 */
	private final Paint mBitmapPaint;
	/**
	 * 图片的宽度
	 */
	private final int mBitmapWidth;
	/**
	 * 图片的高度
	 */
	private final int mBitmapHeight;
	/**
	 * 控件边框的区域
	 */
	private final RectF mBorderRect = new RectF();
	/**
	 * 绘制边框的画笔
	 */
	private final Paint mBorderPaint;
	/**
	 * 图片缩放矩阵
	 */
	private final Matrix mShaderMatrix = new Matrix();
	/**
	 * 圆角半径
	 */
	private float mCornerRadius = 0f;
	/**
	 * 是否为圆形，注意：外界设置圆形图片需保证控件宽高一致，否则成形后为椭圆
	 */
	private boolean mOval = false;
	/**
	 * 边框的大小
	 */
	private float mBorderWidth = 0;
	/**
	 * 边框的色值
	 */
	private ColorStateList mBorderColor = ColorStateList
			.valueOf(DEFAULT_BORDER_COLOR);
	/**
	 * 图片的缩放类型
	 */
	private ScaleType mScaleType = ScaleType.FIT_CENTER;
	/**
	 * 图片的着色器，绘制圆角
	 */
	private BitmapShader mBitmapShader;
	/**
	 * 是否需要重新构建着色器，目前没有对该变量进行重置
	 */
	private boolean mRebuildShader = true;
	/**
	 * 非四个圆角同时处理
	 */
	private boolean isNotAllCorner = false;
	/**
	 * 左上圆角大小
	 */
	private float mCornerTopLeft;
	/**
	 * 右上圆角大小
	 */
	private float mCornerTopRight;
	/**
	 * 左下圆角大小
	 */
	private float mCornerBottomLeft;
	/**
	 * 右下圆角大小
	 */
	private float mConerBottomRight;

	public RoundedDrawable(Bitmap bitmap) {
		mBitmap = bitmap;
		mBitmapWidth = bitmap.getWidth();
		mBitmapHeight = bitmap.getHeight();
		mBitmapRect.set(0, 0, mBitmapWidth, mBitmapHeight);
		mBitmapPaint = new Paint();
		mBitmapPaint.setStyle(Paint.Style.FILL);
		mBitmapPaint.setAntiAlias(true);
		mBorderPaint = new Paint();
		mBorderPaint.setStyle(Paint.Style.STROKE);
		mBorderPaint.setAntiAlias(true);
		mBorderPaint.setColor(mBorderColor.getColorForState(getState(),
				DEFAULT_BORDER_COLOR));
		mBorderPaint.setStrokeWidth(mBorderWidth);
	}

	public static RoundedDrawable fromBitmap(Bitmap bitmap) {
		if (bitmap != null) {
			return new RoundedDrawable(bitmap);
		} else {
			return null;
		}
	}

	public static Drawable fromDrawable(Drawable drawable) {
		if (drawable != null) {
			if (drawable instanceof RoundedDrawable) {
				return drawable;
			}
			Bitmap bitmap = drawableToBitmap(drawable);
			if (bitmap != null) {
				return new RoundedDrawable(bitmap);
			}
		}
		return drawable;
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}
		// 如果不是BitmapDrawable，需要重新创建Bitmap
		Bitmap bitmap;
		int width = Math.max(drawable.getIntrinsicWidth(), 2);
		int height = Math.max(drawable.getIntrinsicHeight(), 2);
		try {
			bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
			drawable.draw(canvas);
		} catch (Exception e) {
			e.printStackTrace();
			bitmap = null;
		}
		return bitmap;
	}

	@Override
	public boolean isStateful() {
		return mBorderColor.isStateful();
	}

	@Override
	protected boolean onStateChange(int[] state) {
		int newColor = mBorderColor.getColorForState(state, 0);
		if (mBorderPaint.getColor() != newColor) {
			mBorderPaint.setColor(newColor);
			return true;
		} else {
			return super.onStateChange(state);
		}
	}

	/**
	 * 根据mScaleType来重新计算ShaderMatrix，然后对图片进行相应的裁剪
	 */
	private void updateShaderMatrix() {
		float scale = 0;
		float dx = 0;
		float dy = 0;
		switch (mScaleType) {
		case CENTER:
			mBorderRect.set(mBounds);
			mBorderRect.inset(mBorderWidth / 2, mBorderWidth / 2);
			mShaderMatrix.reset();
			mShaderMatrix
					.setTranslate(
							(int) ((mBorderRect.width() - mBitmapWidth) * 0.5f + 0.5f),
							(int) ((mBorderRect.height() - mBitmapHeight) * 0.5f + 0.5f));
			break;
		case CENTER_CROP:
			mBorderRect.set(mBounds);
			mBorderRect.inset(mBorderWidth / 2, mBorderWidth / 2);
			mShaderMatrix.reset();
			dx = 0;
			dy = 0;
			if (mBitmapWidth * mBorderRect.height() > mBorderRect.width()
					* mBitmapHeight) {
				scale = mBorderRect.height() / (float) mBitmapHeight;
				dx = (mBorderRect.width() - mBitmapWidth * scale) * 0.5f;
			} else {
				scale = mBorderRect.width() / (float) mBitmapWidth;
				dy = (mBorderRect.height() - mBitmapHeight * scale) * 0.5f;
			}
			mShaderMatrix.setScale(scale, scale);
			mShaderMatrix.postTranslate((int) (dx + 0.5f) + mBorderWidth / 2,
					(int) (dy + 0.5f) + mBorderWidth / 2);
			break;
		case CENTER_INSIDE:
			mShaderMatrix.reset();

			if (mBitmapWidth <= mBounds.width()
					&& mBitmapHeight <= mBounds.height()) {
				scale = 1.0f;
			} else {
				scale = Math.min(mBounds.width() / (float) mBitmapWidth,
						mBounds.height() / (float) mBitmapHeight);
			}
			dx = (int) ((mBounds.width() - mBitmapWidth * scale) * 0.5f + 0.5f);
			dy = (int) ((mBounds.height() - mBitmapHeight * scale) * 0.5f + 0.5f);
			mShaderMatrix.setScale(scale, scale);
			mShaderMatrix.postTranslate(dx, dy);
			mBorderRect.set(mBitmapRect);
			mShaderMatrix.mapRect(mBorderRect);
			mBorderRect.inset(mBorderWidth / 2, mBorderWidth / 2);
			mShaderMatrix.setRectToRect(mBitmapRect, mBorderRect,
					Matrix.ScaleToFit.FILL);
			break;
		case FIT_CENTER:
			mBorderRect.set(mBitmapRect);
			mShaderMatrix.setRectToRect(mBitmapRect, mBounds,
					Matrix.ScaleToFit.CENTER);
			mShaderMatrix.mapRect(mBorderRect);
			mBorderRect.inset(mBorderWidth / 2, mBorderWidth / 2);
			mShaderMatrix.setRectToRect(mBitmapRect, mBorderRect,
					Matrix.ScaleToFit.FILL);
			if(mOval){
				//针对圆形图片默认scaleType为fit_center特殊处理，保证画的区域为正圆
				mBorderRect.set(mBounds);
				mBorderRect.inset(mBorderWidth / 2, mBorderWidth / 2);
			}
			break;
		case FIT_END:
			mBorderRect.set(mBitmapRect);
			mShaderMatrix.setRectToRect(mBitmapRect, mBounds,
					Matrix.ScaleToFit.END);
			mShaderMatrix.mapRect(mBorderRect);
			mBorderRect.inset(mBorderWidth / 2, mBorderWidth / 2);
			mShaderMatrix.setRectToRect(mBitmapRect, mBorderRect,
					Matrix.ScaleToFit.FILL);
			break;

		case FIT_START:
			mBorderRect.set(mBitmapRect);
			mShaderMatrix.setRectToRect(mBitmapRect, mBounds,
					Matrix.ScaleToFit.START);
			mShaderMatrix.mapRect(mBorderRect);
			mBorderRect.inset(mBorderWidth / 2, mBorderWidth / 2);
			mShaderMatrix.setRectToRect(mBitmapRect, mBorderRect,
					Matrix.ScaleToFit.FILL);
			break;
		case FIT_XY:
			mBorderRect.set(mBounds);
			mBorderRect.inset(mBorderWidth / 2, mBorderWidth / 2);
			mShaderMatrix.reset();
			mShaderMatrix.setRectToRect(mBitmapRect, mBorderRect,
					Matrix.ScaleToFit.FILL);
			break;
		default:
			break;
		}
		mDrawableRect.set(mBorderRect);
	}

	@Override
	protected void onBoundsChange(Rect bounds) {
		super.onBoundsChange(bounds);
		mBounds.set(bounds);
		updateShaderMatrix();
	}

	@Override
	public void draw(Canvas canvas) {
		if (mRebuildShader) {
			mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP,
					Shader.TileMode.CLAMP);
			mBitmapShader.setLocalMatrix(mShaderMatrix);
			mBitmapPaint.setShader(mBitmapShader);
			mRebuildShader = false;
		}
		if (mOval) {
			//圆形图片处理，区分是否带边框
			if (mBorderWidth > 0) {
				canvas.drawOval(mDrawableRect, mBitmapPaint);
				canvas.drawOval(mBorderRect, mBorderPaint);
			} else {
				canvas.drawOval(mDrawableRect, mBitmapPaint);
			}
		} else {
			//矩形圆角图片处理，区分是否带边框
			float radius = mCornerRadius;
			if (radius <= 0) {
				canvas.drawRect(mDrawableRect, mBitmapPaint);
			} else {
				// 先画一个圆角矩形将图片显示为圆角
				canvas.drawRoundRect(mDrawableRect, radius, radius,
						mBitmapPaint);
				// 哪个角不是圆角我再重新用矩形补偿画出来
				if (isNotAllCorner && mCornerTopLeft == 0) {
					canvas.drawRect(0, 0, radius, radius, mBitmapPaint);
				}
				if (isNotAllCorner && mCornerTopRight == 0) {
					canvas.drawRect(mDrawableRect.right - radius, 0,
							mDrawableRect.right, radius, mBitmapPaint);
				}
				if (isNotAllCorner && mCornerBottomLeft == 0) {
					canvas.drawRect(0, mDrawableRect.bottom - radius,
							radius, mDrawableRect.bottom, mBitmapPaint);
				}
				if (isNotAllCorner && mConerBottomRight == 0) {
					canvas.drawRect(mDrawableRect.right - radius,
							mDrawableRect.bottom - radius,
							mDrawableRect.right, mDrawableRect.bottom,
							mBitmapPaint);
				}
			}
			if (mBorderWidth > 0) {
				if (radius <= 0) {
					canvas.drawRect(mBorderRect, mBorderPaint);
				} else {
					canvas.drawRoundRect(mBorderRect, radius, radius,
							mBorderPaint);
				}
			}
		}
	}

	@Override
	public int getOpacity() {
		return PixelFormat.TRANSLUCENT;
	}

	@Override
	public int getAlpha() {
		return mBitmapPaint.getAlpha();
	}

	@Override
	public void setAlpha(int alpha) {
		mBitmapPaint.setAlpha(alpha);
		invalidateSelf();
	}

	@Override
	public ColorFilter getColorFilter() {
		return mBitmapPaint.getColorFilter();
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		mBitmapPaint.setColorFilter(cf);
		invalidateSelf();
	}

	@Override
	public void setDither(boolean dither) {
		mBitmapPaint.setDither(dither);
		invalidateSelf();
	}

	@Override
	public void setFilterBitmap(boolean filter) {
		mBitmapPaint.setFilterBitmap(filter);
		invalidateSelf();
	}

	@Override
	public int getIntrinsicWidth() {
		return mBitmapWidth;
	}

	@Override
	public int getIntrinsicHeight() {
		return mBitmapHeight;
	}

	public RoundedDrawable setCornerRadius(float radius) {
		mCornerRadius = radius;
		return this;
	}

	public RoundedDrawable setCornerRadius(float radius,float topLeft, float topRight,
			float bottomLeft, float bottomRight) {
		mCornerRadius = radius;
		isNotAllCorner = topLeft > 0 || topRight > 0 || bottomLeft > 0
				|| bottomRight > 0;
		mCornerTopLeft = topLeft;
		mCornerTopRight = topRight;
		mCornerBottomLeft = bottomLeft;
		mConerBottomRight = bottomRight;
		return this;
	}

	public RoundedDrawable setBorderWidth(float width) {
		mBorderWidth = width;
		mBorderPaint.setStrokeWidth(mBorderWidth);
		return this;
	}

	public RoundedDrawable setBorderColor(int color) {
		return setBorderColor(ColorStateList.valueOf(color));
	}

	public RoundedDrawable setBorderColor(ColorStateList colors) {
		mBorderColor = colors != null ? colors : ColorStateList.valueOf(0);
		mBorderPaint.setColor(mBorderColor.getColorForState(getState(),
				DEFAULT_BORDER_COLOR));
		return this;
	}

	public RoundedDrawable setOval(boolean oval) {
		mOval = oval;
		return this;
	}

	public RoundedDrawable setScaleType(ScaleType scaleType) {
		if (scaleType == null) {
			scaleType = ScaleType.FIT_CENTER;
		}
		if (mScaleType != scaleType) {
			mScaleType = scaleType;
			updateShaderMatrix();
		}
		return this;
	}
}

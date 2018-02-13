package com.ydd.pockettoycatcherrwz.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by C-yl on 2017/12/12.
 */

public class TransView {
    private static Bitmap bitmap;
    private static Bitmap bitmap1;

    public static void setClick(Context context, ImageView iv, final View.OnClickListener onClickListener){
        iv.setOnClickListener(onClickListener);
        bitmap = ((BitmapDrawable) (iv.getDrawable())).getBitmap();
        bitmap1 = zoomImg(bitmap,iv.getLayoutParams().width,iv.getLayoutParams().height);
        iv.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1)
            {
                // TODO Auto-generated method stub
                try{
                    if(bitmap1.getPixel((int)(arg1.getX()),((int)arg1.getY()))==0)
                    {
                        Log.i("Test", "透明区域"+(int)arg1.getX()+"---"+(int)arg1.getY()+"---"+bitmap1.getPixel((int)(arg1.getX()),((int)arg1.getY())));

                        return true;//透明区域返回true
                    }
                    else {
                        Log.i("Test", "非透明区域"+(int)arg1.getX()+"---"+(int)arg1.getY()+"---"+bitmap1.getPixel((int)(arg1.getX()),((int)arg1.getY())));
                        return false;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    return false;
                }


            }
        });

    }

    //bitmap 缩放
    public static Bitmap zoomImg(Bitmap bm, int newWidth , int newHeight){
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }
}

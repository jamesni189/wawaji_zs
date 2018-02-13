package com.ydd.pockettoycatcherrwz.transformer;


import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by C-yl on 2017/12/7.
 */

public class AlphaPageTransformer implements ViewPager.PageTransformer {

    private static final float DEFAULT_MIN_SCALE = 0.85f;
    private float mMinScale = DEFAULT_MIN_SCALE;

    public AlphaPageTransformer() {
    }
    @Override
    public void transformPage(View view, float position) {

        if (position < -1) { // [-Infinity,-1)
            view.setScaleX(mMinScale);
            view.setScaleY(mMinScale);
        } else if (position <= 1) { // [-1,1]
            if (position < 0)
            {
               // 页1的postion变化为：从0到-1
             //   float scaleX = 1 + 0.3f * position;
                float scaleFactor = (1 + position) * (1 - mMinScale) + mMinScale;
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
            } else //1-2:2[1,0] ;2-1:2[0,1]
            {
                float scaleFactor = (1 - position) * (1 - mMinScale) + mMinScale;
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
            }
        }else
        { // (1,+Infinity]
            view.setScaleX(mMinScale);
            view.setScaleY(mMinScale);
        }

    }
}

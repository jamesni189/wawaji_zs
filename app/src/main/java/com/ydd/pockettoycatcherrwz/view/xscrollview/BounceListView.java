package com.ydd.pockettoycatcherrwz.view.xscrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2018/2/1.
 */

public class BounceListView extends ListView {

    public BounceListView(Context context) {
        super(context);

    }

    public BounceListView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public BounceListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}

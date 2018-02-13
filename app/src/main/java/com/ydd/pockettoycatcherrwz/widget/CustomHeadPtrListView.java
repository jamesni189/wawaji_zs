package com.ydd.pockettoycatcherrwz.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * 可添加HeaderView的下拉刷新列表
 *
 * @author czhang
 */
public class CustomHeadPtrListView extends PullToRefreshListView {
    private int mHeaderHeight;

    public CustomHeadPtrListView(Context context) {
        super(context);
    }

    public CustomHeadPtrListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomHeadPtrListView(Context context, com.handmark.pulltorefresh.library.PullToRefreshBase.Mode mode,
                                 com.handmark.pulltorefresh.library.PullToRefreshBase.AnimationStyle style) {
        super(context, mode, style);
    }

    public CustomHeadPtrListView(Context context, com.handmark.pulltorefresh.library.PullToRefreshBase.Mode mode) {
        super(context, mode);
    }

    /**
     * 用于记录拖拉图片移动的坐标位置
     */
    public Matrix matrix = new Matrix();

    /**
     * 用于记录图片要进行拖拉时候的坐标位置
     */
    public Matrix currentMatrix = new Matrix();

    /**
     * 添加头部View
     *
     * @param headerView   要添加的view
     * @param data         和这个view关联的数据
     * @param isSelectable 该条item是否可悲点击
     */
    public void addHeaderView(View headerView, Object data, boolean isSelectable) {
        if (headerView == null) {
            return;
        }
        mRefreshableView.addHeaderView(headerView, null, isSelectable);
    }

    /**
     * 实际返回内部嵌套的ListView的child
     *
     * @param index
     */
    public View getOriginalChildAt(int index) {
        return mRefreshableView.getChildAt(index);
    }

    /**
     * 获取最后一个可见item的position
     *
     * @return 最后一个可见position
     */
    public int getLastVisiblePosition() {
        return mRefreshableView.getFirstVisiblePosition() + getChildCount() - 1;
    }

    /**
     * 获得listview的y轴滑动高度<br>
     * 不精确，主要是以第一个headview的高度来计算的
     */
    public int getRealScrollY() {
        View c = mRefreshableView.getChildAt(2);
        if (c == null) {
            return -5000;
        }
        int firstVisiblePosition = mRefreshableView.getFirstVisiblePosition();
        int top = c.getTop();
        int headerHeight = c.getHeight();
        if (headerHeight > 100) {
            mHeaderHeight = headerHeight;
        }
        return -top + firstVisiblePosition * mHeaderHeight;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }
}

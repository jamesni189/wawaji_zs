package com.ydd.pockettoycatcherrwz.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;

import com.ydd.pockettoycatcherrwz.R;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 * Auto Scroll View Pager
 * <ul>
 * <strong>Basic Setting and Usage</strong>
 * <li>{@link #startAutoScroll()} start auto scroll, or {@link #startAutoScroll(int)} start auto scroll delayed</li>
 * <li>{@link #stopAutoScroll()} stop auto scroll</li>
 * <li>{@link #setInterval(long)} set auto scroll time in milliseconds, default is {@link #DEFAULT_INTERVAL}</li>
 * </ul>
 * <ul>
 * <strong>Advanced Settings and Usage</strong>
 * <li>{@link #setDirection(int)} set auto scroll direction</li>
 * <li>{@link #setCycle(boolean)} set whether automatic cycle when auto scroll reaching the last or first item, default
 * is true</li>
 * <li>{@link #setSlideBorderMode(int)} set how to process when sliding at the last or first item</li>
 * <li>{@link #setStopScrollWhenTouch(boolean)} set whether stop auto scroll when touching, default is true</li>
 * </ul>
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-12-30
 */
public class AutoScrollViewPager extends ViewPager
{
    
    public static final int DEFAULT_INTERVAL = 1500;
    
    @SuppressLint("RtlHardcoded")
	public static final int LEFT = 0;
    
    @SuppressLint("RtlHardcoded")
	public static final int RIGHT = 1;
    
    /** do nothing when sliding at the last or first item **/
    public static final int SLIDE_BORDER_MODE_NONE = 0;
    
    /** cycle when sliding at the last or first item **/
    public static final int SLIDE_BORDER_MODE_CYCLE = 1;
    
    /** deliver event to parent when sliding at the last or first item **/
    public static final int SLIDE_BORDER_MODE_TO_PARENT = 2;
    
    /** auto scroll time in milliseconds, default is {@link #DEFAULT_INTERVAL} **/
    private long interval = DEFAULT_INTERVAL;
    
    /** auto scroll direction, default is {@link #RIGHT} **/
    private int direction = RIGHT;
    
    /** whether automatic cycle when auto scroll reaching the last or first item, default is true **/
    private boolean isCycle = true;
    
    /** whether stop auto scroll when touching, default is true **/
    private boolean stopScrollWhenTouch = true;
    
    /** how to process when sliding at the last or first item, default is {@link #SLIDE_BORDER_MODE_NONE} **/
    private int slideBorderMode = SLIDE_BORDER_MODE_NONE;
    
    /** whether animating when auto scroll at the last or first item **/
    private boolean isBorderAnimation = true;
    
    /** scroll factor for auto scroll animation, default is 1.0 **/
    private double autoScrollFactor = 1.0;
    
    /** scroll factor for swipe scroll animation, default is 1.0 **/
    private double swipeScrollFactor = 1.0;
    
    private Handler handler;
    
    private boolean isAutoScroll = false;
    
    private boolean isStopByTouch = false;
    
    private CustomDurationScroller scroller = null;
    
    public static final int SCROLL_WHAT = 0;

    /**
     * 默认高宽比
     */
    private static final float DEFAUT_RATIO = -1.0F;

    /**
     * 高宽比，mHeightRatio = height/width
     */
    private float mHeightRatio = DEFAUT_RATIO;
    
    public AutoScrollViewPager(Context paramContext)
    {
        super(paramContext);
        init();
    }
    
    public AutoScrollViewPager(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
        TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet,
                R.styleable.AutoScrollViewPager);
        mHeightRatio = typedArray.getFloat(
                R.styleable.AutoScrollViewPager_height_ratio, DEFAUT_RATIO);
        typedArray.recycle();
        init();
    }
    
    private void init()
    {
        handler = new MyHandler(this);
        setViewPagerScroller();
    }
    
    /**
     * start auto scroll, first scroll delay time is {@link #getInterval()}
     */
    public void startAutoScroll()
    {
        isAutoScroll = true;
        sendScrollMessage((long)(interval + scroller.getDuration() / autoScrollFactor * swipeScrollFactor));
    }
    
    /**
     * start auto scroll
     * 
     * @param delayTimeInMills first scroll delay time
     */
    public void startAutoScroll(int delayTimeInMills)
    {
        isAutoScroll = true;
        sendScrollMessage(delayTimeInMills);
    }
    
    /**
     * stop auto scroll
     */
    public void stopAutoScroll()
    {
        isAutoScroll = false;
        handler.removeMessages(SCROLL_WHAT);
    }
    
    /**
     * set the factor by which the duration of sliding animation will change while swiping
     */
    public void setSwipeScrollDurationFactor(double scrollFactor)
    {
        swipeScrollFactor = scrollFactor;
    }
    
    /**
     * set the factor by which the duration of sliding animation will change while auto scrolling
     */
    public void setAutoScrollDurationFactor(double scrollFactor)
    {
        autoScrollFactor = scrollFactor;
    }
    
    private void sendScrollMessage(long delayTimeInMills)
    {
        /** remove messages before, keeps one message is running at most **/
        handler.removeMessages(SCROLL_WHAT);
        handler.sendEmptyMessageDelayed(SCROLL_WHAT, delayTimeInMills);
    }
    
    /**
     * set ViewPager scroller to change animation duration when sliding
     */
    private void setViewPagerScroller()
    {
        try
        {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            Field interpolatorField = ViewPager.class.getDeclaredField("sInterpolator");
            interpolatorField.setAccessible(true);
            
            scroller = new CustomDurationScroller(getContext(), (Interpolator)interpolatorField.get(null));
            scrollerField.set(this, scroller);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * scroll only once
     */
    public void scrollOnce()
    {
        PagerAdapter adapter = getAdapter();
        int currentItem = getCurrentItem();
        int totalCount;
        if (adapter == null || (totalCount = adapter.getCount()) <= 1)
        {
            return;
        }
        
        int nextItem = (direction == LEFT) ? --currentItem : ++currentItem;
        if (nextItem < 0)
        {
            if (isCycle)
            {
                setCurrentItem(totalCount - 1, isBorderAnimation);
            }
        }
        else if (nextItem == totalCount)
        {
            if (isCycle)
            {
                setCurrentItem(0, isBorderAnimation);
            }
        }
        else
        {
            setCurrentItem(nextItem, true);
        }
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
        if(null != mPageShowListener){
            mPageShowListener.onCurrentPageShow(item);
        }
    }

    private onPageShowListener mPageShowListener;

    public void setOnPageShowListener(onPageShowListener l){
        mPageShowListener = l;
    }
    public interface onPageShowListener{
        void onCurrentPageShow(int index);
    }

    /**
     * <ul>
     * if stopScrollWhenTouch is true
     * <li>if event is down, stop auto scroll.</li>
     * <li>if event is up, start auto scroll again.</li>
     * </ul>
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        int action = MotionEventCompat.getActionMasked(ev);

        if (stopScrollWhenTouch)
        {
            if ((action == MotionEvent.ACTION_DOWN) && isAutoScroll)
            {
                isStopByTouch = true;
                stopAutoScroll();
            }
            else if ((ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL)
                && isStopByTouch)
            {
                startAutoScroll();
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    
    private static class MyHandler extends Handler
    {
        
        private final WeakReference<AutoScrollViewPager> autoScrollViewPager;
        
        public MyHandler(AutoScrollViewPager autoScrollViewPager)
        {
            this.autoScrollViewPager = new WeakReference<AutoScrollViewPager>(autoScrollViewPager);
        }
        
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            
            switch (msg.what)
            {
                case SCROLL_WHAT:
                    AutoScrollViewPager pager = this.autoScrollViewPager.get();
                    if (pager != null)
                    {
                        pager.scroller.setScrollDurationFactor(pager.autoScrollFactor);
                        pager.scrollOnce();
                        pager.scroller.setScrollDurationFactor(pager.swipeScrollFactor);
                        pager.sendScrollMessage(pager.interval + pager.scroller.getDuration());
                    }
                default:
                    break;
            }
        }
    }
    
    /**
     * get auto scroll time in milliseconds, default is {@link #DEFAULT_INTERVAL}
     * 
     * @return the interval
     */
    public long getInterval()
    {
        return interval;
    }
    
    /**
     * set auto scroll time in milliseconds, default is {@link #DEFAULT_INTERVAL}
     * 
     * @param interval the interval to set
     */
    public void setInterval(long interval)
    {
        this.interval = interval;
    }
    
    /**
     * get auto scroll direction
     * 
     * @return {@link #LEFT} or {@link #RIGHT}, default is {@link #RIGHT}
     */
    public int getDirection()
    {
        return (direction == LEFT) ? LEFT : RIGHT;
    }
    
    /**
     * set auto scroll direction
     * 
     * @param direction {@link #LEFT} or {@link #RIGHT}, default is {@link #RIGHT}
     */
    public void setDirection(int direction)
    {
        this.direction = direction;
    }
    
    /**
     * whether automatic cycle when auto scroll reaching the last or first item, default is true
     * 
     * @return the isCycle
     */
    public boolean isCycle()
    {
        return isCycle;
    }
    
    /**
     * set whether automatic cycle when auto scroll reaching the last or first item, default is true
     * 
     * @param isCycle the isCycle to set
     */
    public void setCycle(boolean isCycle)
    {
        this.isCycle = isCycle;
    }
    
    /**
     * whether stop auto scroll when touching, default is true
     * 
     * @return the stopScrollWhenTouch
     */
    public boolean isStopScrollWhenTouch()
    {
        return stopScrollWhenTouch;
    }
    
    /**
     * set whether stop auto scroll when touching, default is true
     * 
     * @param stopScrollWhenTouch
     */
    public void setStopScrollWhenTouch(boolean stopScrollWhenTouch)
    {
        this.stopScrollWhenTouch = stopScrollWhenTouch;
    }
    
    /**
     * get how to process when sliding at the last or first item
     * 
     * @return the slideBorderMode {@link #SLIDE_BORDER_MODE_NONE}, {@link #SLIDE_BORDER_MODE_TO_PARENT},
     *         {@link #SLIDE_BORDER_MODE_CYCLE}, default is {@link #SLIDE_BORDER_MODE_NONE}
     */
    public int getSlideBorderMode()
    {
        return slideBorderMode;
    }
    
    /**
     * set how to process when sliding at the last or first item
     * 
     * @param slideBorderMode {@link #SLIDE_BORDER_MODE_NONE}, {@link #SLIDE_BORDER_MODE_TO_PARENT},
     *            {@link #SLIDE_BORDER_MODE_CYCLE}, default is {@link #SLIDE_BORDER_MODE_NONE}
     */
    public void setSlideBorderMode(int slideBorderMode)
    {
        this.slideBorderMode = slideBorderMode;
    }
    
    /**
     * whether animating when auto scroll at the last or first item, default is true
     * 
     * @return
     */
    public boolean isBorderAnimation()
    {
        return isBorderAnimation;
    }
    
    /**
     * set whether animating when auto scroll at the last or first item, default is true
     * 
     * @param isBorderAnimation
     */
    public void setBorderAnimation(boolean isBorderAnimation)
    {
        this.isBorderAnimation = isBorderAnimation;
    }

    /**
     * 设置公共基础参数
     * @param time 时间
     * @param order 方向
     */
    public void setBaseParm(long time,int order){
        setInterval(time > 0 ? time * 1000: 5000);
        // 设置自动滚动的方向
        setDirection(order == 0 ? AutoScrollViewPager.LEFT : AutoScrollViewPager.RIGHT);
        // 当手指碰到ViewPager时是否停止自动滚动，默认为true
        setStopScrollWhenTouch(true);
        // 滑动到第一个或最后一个Item的处理方式，支持没有任何操作、轮播以及传递到父View三种模式
        setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_NONE);
        // 设置循环滚动时滑动到从边缘滚动到下一个是否需要动画，默认为true
        setBorderAnimation(true);
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

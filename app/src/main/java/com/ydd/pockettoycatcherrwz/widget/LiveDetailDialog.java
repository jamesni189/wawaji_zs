package com.ydd.pockettoycatcherrwz.widget;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.entity.RoomItem;
import com.ydd.pockettoycatcherrwz.ui.home.LiveGrabRecordsView;
import com.ydd.pockettoycatcherrwz.ui.home.LiveImgView;

import java.util.ArrayList;
import java.util.List;

/**
 * 抓取页详情对话框
 */
public class LiveDetailDialog extends CommonDialog {

    /**
     * 抓取记录
     */
    private TextView mRecordsTv;
    /**
     * 查看大图
     */
    private TextView mImgsTv;
    /**
     * viewpager
     */
    private ViewPager mViewPager;

    private RoomItem item;

    private PagerAdapter adapter;

    private List<View> viewList;

    private FragmentActivity context;

    private LiveImgView liveImgView;

    public LiveDetailDialog(FragmentActivity context, RoomItem item) {
        super(context);
        this.context = context;
        this.item = item;
        initView();
    }

    private void initView() {
        addContent(R.layout.dlg_live_detail);
        mViewPager = (ViewPager) findViewById(R.id.vp_live_detail);
        mRecordsTv = (TextView) findViewById(R.id.tv_live_detail_records);
        mImgsTv = (TextView) findViewById(R.id.tv_live_detail_imgs);
        mRecordsTv.setOnClickListener(mOnClickListener);
        mImgsTv.setOnClickListener(mOnClickListener);
        liveImgView = new LiveImgView(context);
        viewList = new ArrayList<>();
        viewList.add(liveImgView);
        viewList.add(new LiveGrabRecordsView(context,item.machineId));
        initAdapter();
        liveImgView.setData(item.imgs);
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==1){
                    mRecordsTv.setTextColor(getContext().getResources()
                            .getColor(R.color.app_common_color));
                    mImgsTv.setTextColor(getContext().getResources()
                            .getColor(R.color.common_text_black));
                }
                if(position==0){
                    mRecordsTv.setTextColor(getContext().getResources()
                            .getColor(R.color.common_text_black));
                    mImgsTv.setTextColor(getContext().getResources()
                            .getColor(R.color.app_common_color));
                    liveImgView.setData(item.imgs);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_live_detail_records:
                    // 抓取记录
                    mRecordsTv.setTextColor(getContext().getResources()
                            .getColor(R.color.app_common_color));
                    mImgsTv.setTextColor(getContext().getResources()
                            .getColor(R.color.common_text_black));
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.tv_live_detail_imgs:
                    // 查看大图
                    mRecordsTv.setTextColor(getContext().getResources()
                            .getColor(R.color.common_text_black));
                    mImgsTv.setTextColor(getContext().getResources()
                            .getColor(R.color.app_common_color));
                    liveImgView.setData(item.imgs);
                    mViewPager.setCurrentItem(0);
                    break;
            }
        }
    };

    private void initAdapter() {
        adapter = new PagerAdapter() {

            @Override
            public int getCount() {
                return viewList.size();
            }

            // 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            // PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
            @Override
            public void destroyItem(ViewGroup view, int position, Object object) {
                view.removeView(viewList.get(position));
            }

            // 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
            @Override
            public Object instantiateItem(ViewGroup view, int position) {
                view.addView(viewList.get(position));
                return viewList.get(position);
            }

        };
    }


}

package com.ydd.pockettoycatcherrwz.ui.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ydd.pockettoycatcherrwz.entity.ViewPagerIndicateMessage;

import java.util.List;

/**
 * Created by C-yl on 2017/11/20.
 */

public class MyfragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    private String[] mTITLE;
    private List<ViewPagerIndicateMessage> result;
    public MyfragmentAdapter(FragmentManager fm, List<Fragment> mlist, List<ViewPagerIndicateMessage> mresult) {
        super(fm);
        list=mlist;
        result=mresult;
    }
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("vpmessage", result.get(position));
        list.get(position).setArguments(bundle);
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return result.get(position).getName();
    }
}

package com.kaskys.speedreadinginformation.app.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import com.kaskys.speedreadinginformation.app.ui.fragment.base.BaseLazyFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 卡你基巴 on 2015/11/9.
 */
public class HomeFragmentAdapter extends FragmentPagerAdapter{
    private List<BaseLazyFragment> mFragments = null;

    public HomeFragmentAdapter(FragmentManager fm, List<BaseLazyFragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}

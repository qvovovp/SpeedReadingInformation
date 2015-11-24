package com.kaskys.speedreadinginformation.app.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.kaskys.speedreadinginformation.app.bean.NewsChanne;
import com.kaskys.speedreadinginformation.app.ui.fragment.child.NewsListFragment;

import java.util.List;

/**
 * Created by 卡你基巴 on 2015/11/10.
 */
public class NewsContainerAdapter extends FragmentPagerAdapter{
    private List<NewsChanne> mChannes;

    public NewsContainerAdapter(FragmentManager fm, List<NewsChanne> channes) {
        super(fm);
        mChannes = channes;
    }

    @Override
    public int getCount() {
        return mChannes.size();
    }

    @Override
    public Fragment getItem(int i) {
        NewsListFragment mFragment = new NewsListFragment();
        mFragment.onPageSelected(i,mChannes.get(i).channeId);
        return mFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mChannes.get(position).channeName;
    }
}

package com.kaskys.speedreadinginformation.app.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import com.kaskys.speedreadinginformation.app.bean.PictureType;
import com.kaskys.speedreadinginformation.app.ui.fragment.child.PictureListFragment;

import java.util.List;

/**
 * Created by 卡你基巴 on 2015/11/21.
 */
public class PictureContainerAdapter extends FragmentPagerAdapter{
    private List<PictureType> mTypes;

    public PictureContainerAdapter(FragmentManager fm,List<PictureType> types) {
        super(fm);
        mTypes = types;
    }

    @Override
    public int getCount() {
        return mTypes.size();
    }

    @Override
    public Fragment getItem(int position) {
        PictureListFragment fragment = new PictureListFragment();
        fragment.getTypeId(mTypes.get(position).id);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTypes.get(position).name;
    }
}

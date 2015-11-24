package com.kaskys.speedreadinginformation.app.interactor.impl;

import android.support.v4.app.Fragment;
import com.kaskys.speedreadinginformation.app.interactor.HomeInteractor;
import com.kaskys.speedreadinginformation.app.ui.fragment.MusicFragment;
import com.kaskys.speedreadinginformation.app.ui.fragment.NewsFragment;
import com.kaskys.speedreadinginformation.app.ui.fragment.PictureFragment;
import com.kaskys.speedreadinginformation.app.ui.fragment.ReadFragment;
import com.kaskys.speedreadinginformation.app.ui.fragment.base.BaseLazyFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 卡你基巴 on 2015/11/9.
 */
public class HomeInteractorImpl implements HomeInteractor{
    @Override
    public List<BaseLazyFragment> getPagerFragments() {
        List<BaseLazyFragment> fragments = new ArrayList<BaseLazyFragment>();
        fragments.add(new NewsFragment());
        fragments.add(new PictureFragment());
        fragments.add(new MusicFragment());
        fragments.add(new ReadFragment());
        return fragments;
    }
}

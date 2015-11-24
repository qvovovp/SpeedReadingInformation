package com.kaskys.speedreadinginformation.app.ui.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import com.kaskys.speedreadinginformation.app.R;
import com.kaskys.speedreadinginformation.app.bean.PictureType;
import com.kaskys.speedreadinginformation.app.presenter.Presenter;
import com.kaskys.speedreadinginformation.app.presenter.impl.PictureTypeContainerPresenterImpl;
import com.kaskys.speedreadinginformation.app.ui.adapter.PictureContainerAdapter;
import com.kaskys.speedreadinginformation.app.ui.fragment.base.BaseFragment;
import com.kaskys.speedreadinginformation.app.ui.view.PictureView;
import com.kaskys.speedreadinginformation.app.ui.widget.tablayout.ITabLayout;

import java.util.List;

/**
 * Created by 卡你基巴 on 2015/11/9.
 */
public class PictureFragment extends BaseFragment implements PictureView {
    private ITabLayout mTabLayout;
    private ViewPager mViewPager;
    private Presenter mPictureContainerPresenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_picture;
    }

    @Override
    protected View getLoadingTargetView() {
        return mRootView.findViewById(R.id.picture_container);
    }

    @Override
    protected void initViewsAndEvents() {
        mTabLayout = (ITabLayout) mRootView.findViewById(R.id.tl_top);
        mViewPager = (ViewPager) mRootView.findViewById(R.id.picture_container);
    }

    @Override
    protected void onFirstUserVisible() {
        mPictureContainerPresenter = new PictureTypeContainerPresenterImpl(mContext,this);
        mPictureContainerPresenter.initialized();
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    public void initializePagerViews(List<PictureType> typeList) {
        if(null != typeList && typeList.size() > 0){
            mViewPager.setOffscreenPageLimit(typeList.size());
            PictureContainerAdapter mAdapter = new PictureContainerAdapter(getChildFragmentManager(),typeList);
            mViewPager.setAdapter(mAdapter);
            mTabLayout.setViewPager(mViewPager);
        }
    }
}

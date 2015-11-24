package com.kaskys.speedreadinginformation.app.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;
import com.kaskys.speedreadinginformation.app.R;
import com.kaskys.speedreadinginformation.app.bean.NewsChanne;
import com.kaskys.speedreadinginformation.app.presenter.Presenter;
import com.kaskys.speedreadinginformation.app.presenter.impl.NewsChanneContainerPresenterImpl;
import com.kaskys.speedreadinginformation.app.ui.adapter.NewsContainerAdapter;
import com.kaskys.speedreadinginformation.app.ui.fragment.base.BaseFragment;
import com.kaskys.speedreadinginformation.app.ui.view.NewsContainerView;
import com.kaskys.speedreadinginformation.app.ui.widget.tablayout.ITabLayout;

import java.util.List;

/**
 * Created by 卡你基巴 on 2015/11/9.
 */
public class NewsFragment extends BaseFragment implements NewsContainerView{
    private ITabLayout mTabLayout;
    private ViewPager mViewPager;
    private Presenter mNewsContainerPresenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_news;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        mTabLayout = (ITabLayout) mRootView.findViewById(R.id.tl_top);
        mViewPager = (ViewPager) mRootView.findViewById(R.id.news_container);
    }

    @Override
    protected void onFirstUserVisible() {
        mNewsContainerPresenter = new NewsChanneContainerPresenterImpl(mContext,this);
        mNewsContainerPresenter.initialized();
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    public void initializePagerViews(final List<NewsChanne> channeList) {
        if(null !=channeList || channeList.size() > 0){
            mViewPager.setOffscreenPageLimit(channeList.size());
            NewsContainerAdapter mAdapter = new NewsContainerAdapter(getChildFragmentManager(),channeList);
            mViewPager.setAdapter(mAdapter);
            mTabLayout.setViewPager(mViewPager);
        }
    }
}

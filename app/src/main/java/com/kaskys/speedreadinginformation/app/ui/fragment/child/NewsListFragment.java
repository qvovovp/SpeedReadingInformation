package com.kaskys.speedreadinginformation.app.ui.fragment.child;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import com.kaskys.speedreadinginformation.app.R;
import com.kaskys.speedreadinginformation.app.bean.NewsData;
import com.kaskys.speedreadinginformation.app.presenter.DataListPresenter;
import com.kaskys.speedreadinginformation.app.presenter.impl.NewsListPresenterImpl;
import com.kaskys.speedreadinginformation.app.ui.activity.HomeActivity;
import com.kaskys.speedreadinginformation.app.ui.activity.NewsDetailActivity;
import com.kaskys.speedreadinginformation.app.ui.adapter.NewsListAdapter;
import com.kaskys.speedreadinginformation.app.ui.adapter.NewsTopAdapter;
import com.kaskys.speedreadinginformation.app.ui.fragment.base.BaseFragment;
import com.kaskys.speedreadinginformation.app.ui.view.NewsListView;
import com.kaskys.speedreadinginformation.app.ui.widget.HeaderViewPager;
import com.kaskys.speedreadinginformation.app.ui.widget.RefreshListView;

import java.util.List;

/**
 * Created by 卡你基巴 on 2015/11/10.
 */
public class NewsListFragment extends BaseFragment implements NewsListView{
    private String mCurrentChanneId = "0";
    private int mCurrentPage = 0;

    private RefreshListView mRefreshListView;
    private NewsListAdapter mAdapter = null;
    private NewsTopAdapter mTopAdapter;

    private View mHeaderView;
    private HeaderViewPager mTopViewPager;
    private LinearLayout mIndicator;

    private DataListPresenter mNewsListPresenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_news_list;
    }

    @Override
    protected View getLoadingTargetView() {
        return mRootView.findViewById(R.id.news_item_container);
    }

    @Override
    protected void initViewsAndEvents() {
        mHeaderView  = View.inflate(mContext,R.layout.fragment_news_list_header,null);
        mRefreshListView = (RefreshListView) mRootView.findViewById(R.id.news_item_container);;
        mTopViewPager = (HeaderViewPager) mHeaderView.findViewById(R.id.news_header);
        mIndicator = (LinearLayout) mHeaderView.findViewById(R.id.rl_indicator);

        toggleShowLoading(true,"正在加载...");

        mRefreshListView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentPage = 1;
                mNewsListPresenter.initialized(mCurrentChanneId,mCurrentPage);
            }

            @Override
            public void onLoadMore() {
                mCurrentPage = mCurrentPage + 1;
                mNewsListPresenter.loadMore(mCurrentChanneId,mCurrentPage);
            }
        });
    }

    @Override
    protected void onFirstUserVisible() {
        mCurrentPage = 1;
        mNewsListPresenter = new NewsListPresenterImpl(mContext,this);
        mNewsListPresenter.initialized(mCurrentChanneId,mCurrentPage);
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    public void onPageSelected(int position, String keyword) {
        mCurrentChanneId = keyword;
    }

    @Override
    public void setupData(List<NewsData.Body.Bean.Detail> topNews, List<NewsData.Body.Bean.Detail> newsData) {
        if(null == topNews || null == newsData){
            toggleShowError(true, "网络异常...", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggleShowLoading(true,"正在加载...");
                    mCurrentPage = 1;
                    mNewsListPresenter.initialized(mCurrentChanneId,mCurrentPage);
                }
            });
        }else{
            toggleShowLoading(false,"");
            if(null == mAdapter && null == mTopAdapter){
                mAdapter = new NewsListAdapter(mContext, newsData,this);
                mRefreshListView.setAdapter(mAdapter);
                mRefreshListView.setOnScrollListener(mRefreshListView);
                mRefreshListView.onRefreshComplete();

                if(topNews.size() > 0) {
                    mRefreshListView.addHeaderView(mHeaderView);
                    mTopAdapter = new NewsTopAdapter(mContext, topNews,this);
                    mTopViewPager.setAdapter(mTopAdapter);
                    mTopViewPager.setPageIndicator(mIndicator);
                    mTopViewPager.addOnPageChangeListener(null);
                    mTopViewPager.startCarousel();
                }
            }else if(null != mTopAdapter && null != mAdapter){
                mAdapter.clear();
                mAdapter.addAll(newsData);
                mAdapter.notifyDataSetChanged();

                mTopAdapter.clear();
                mTopAdapter.addAll(topNews);
                mTopAdapter.notifyDataSetChanged();
                mRefreshListView.onRefreshComplete();
            }
        }
    }

    @Override
    public void onLoadMore(List<NewsData.Body.Bean.Detail> newsData) {
        if(null != newsData){
            mAdapter.addAll(newsData);
            mAdapter.notifyDataSetChanged();
        }else{
            showToast("访问网络错误...");
        }
        mRefreshListView.onRefreshComplete();
    }

    @Override
    public void onItemClick(String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        ((HomeActivity)getActivity()).readyGO(NewsDetailActivity.class,bundle);
    }

}

package com.kaskys.speedreadinginformation.app.ui.fragment.child;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.kaskys.speedreadinginformation.app.R;
import com.kaskys.speedreadinginformation.app.bean.PictureData;
import com.kaskys.speedreadinginformation.app.presenter.DataListPresenter;
import com.kaskys.speedreadinginformation.app.presenter.impl.PictureListPresenterImpl;
import com.kaskys.speedreadinginformation.app.ui.activity.HomeActivity;
import com.kaskys.speedreadinginformation.app.ui.adapter.PictureListAdapter;
import com.kaskys.speedreadinginformation.app.ui.fragment.base.BaseFragment;
import com.kaskys.speedreadinginformation.app.ui.view.PictureListView;
import com.kaskys.speedreadinginformation.app.ui.widget.IRefreshView;
import com.kaskys.speedreadinginformation.app.ui.widget.MyImageView;
import com.kaskys.speedreadinginformation.app.ui.widget.SingleMenuView;
import com.kaskys.speedreadinginformation.app.utils.DensityUtils;
import com.kaskys.speedreadinginformation.app.utils.MyImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by 卡你基巴 on 2015/11/21.
 */
public class PictureListFragment extends BaseFragment implements PictureListView {
    private String mCurrentTypeId = "0";
    private int mCurrentPage = 0;

    private IRefreshView mRecyclerView;
    private PictureListAdapter mAdapter;

    private SingleMenuView mSingleMenuView;
    private ImageView mImage;

    private DataListPresenter mPictureListPresenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_picture_list;
    }

    @Override
    protected View getLoadingTargetView() {
        return mRootView.findViewById(R.id.picture_item_container);
    }

    @Override
    protected void initViewsAndEvents() {
        mRecyclerView = (IRefreshView) mRootView.findViewById(R.id.picture_item_container);
        mRecyclerView.setStaggeredGridLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setOnRefreshListener(new IRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentPage = 1;
                mPictureListPresenter.initialized(mCurrentTypeId,mCurrentPage);
            }

            @Override
            public void onLoadMore() {
                mCurrentPage = mCurrentPage + 1;
                mPictureListPresenter.loadMore(mCurrentTypeId,mCurrentPage);
            }
        });

        mSingleMenuView = new SingleMenuView(getActivity(),((HomeActivity)getActivity()).getRoot(),((HomeActivity)getActivity()).getFrame());
        mSingleMenuView.setMenuView(R.layout.fragment_picture_list_single, DensityUtils.getDisplayWidth(mContext)/15,
                DensityUtils.getDisplayHeight(mContext)/13,0);
        mImage = (ImageView) mSingleMenuView.getMenuView().findViewById(R.id.img_single);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != mSingleMenuView){
                    mSingleMenuView.closeMenuView();
                }
            }
        });

        toggleShowLoading(true,"正在加载...");
    }

    @Override
    protected void onFirstUserVisible() {
        mCurrentPage = 1;
        mPictureListPresenter = new PictureListPresenterImpl(mContext,this);
        mPictureListPresenter.initialized(mCurrentTypeId,mCurrentPage);
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    public void getTypeId(String id) {
        mCurrentTypeId = id;
    }

    @Override
    public void setupData(List<PictureData.Body.Detail> pictures) {
        if(null == pictures){
            toggleShowError(true, "网络异常...", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggleShowLoading(true,"正在加载...");
                    mCurrentPage = 1;
                    mPictureListPresenter.initialized(mCurrentTypeId,mCurrentPage);
                }
            });
        }else{
            toggleShowLoading(false,"");
            if(null == mAdapter){
                mAdapter = new PictureListAdapter(mContext,pictures);
                mAdapter.setOnItemClickListener(new OnClickListener());
                mRecyclerView.setAdapter(mAdapter);
            }else if(null != mAdapter){
                mAdapter.clear();
                mAdapter.addAll(pictures);
                mAdapter.notifyDataSetChanged();
            }
            mRecyclerView.onRefreshComplete();
        }
    }

    @Override
    public void onLoadMore(List<PictureData.Body.Detail> pictures) {
        if(null != pictures){
            mAdapter.addAll(pictures);
            mAdapter.notifyDataSetChanged();
        }else{
            showToast("访问网络错误...");
        }
        mRecyclerView.onRefreshComplete();
    }

    @Override
    public void onItemClick(String url) {

    }

    /**
     * 点击事件监听
     */
    private class OnClickListener implements PictureListAdapter.OnItemClickListener {
        @Override
        public void onItemClick(View view, int position) {
            if(null != mAdapter){
//                ImageLoader.getInstance().displayImage(mAdapter.getPictures().get(position).thumb,mImage);
                MyImageView img = (MyImageView) view.getTag();
                MyImageLoader.getInstance().display(img.getImageBitmap(),mImage);
                mSingleMenuView.openMenuView(view);
            }
        }
    }
}

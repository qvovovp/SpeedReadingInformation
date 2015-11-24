package com.kaskys.speedreadinginformation.app.ui.fragment.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.kaskys.speedreadinginformation.app.ui.widget.loading.LoadingViewHelperController;
import com.kaskys.speedreadinginformation.app.utils.DensityUtils;

/**
 * Created by 卡你基巴 on 2015/11/9.
 */
public abstract class BaseLazyFragment extends Fragment{
    /**
     * 屏幕信息
     */
    protected int mScreenWidth = 0;
    protected int mScreenHeight = 0;
    protected float mScreenDensity = 0.0f;

    protected Context mContext;
    protected View mRootView;

    private boolean isFirstResume = true;
    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;
    private boolean isPrepared;

    protected LoadingViewHelperController mLoadingViewHelperController;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(0 != getContentViewLayoutID()){
            return inflater.inflate(getContentViewLayoutID(),null);
        }else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRootView = view;
        if(null != getLoadingTargetView()){
            mLoadingViewHelperController = new LoadingViewHelperController(getLoadingTargetView());
        }

        mScreenDensity = DensityUtils.getDensity(mContext);
        mScreenWidth = DensityUtils.getDisplayWidth(mContext);
        mScreenHeight = DensityUtils.getDisplayHeight(mContext);

        initViewsAndEvents();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isFirstResume){
            isFirstResume = false;
            return;
        }
        if(getUserVisibleHint()){
            onUserVisible();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(getUserVisibleHint()){
            onUserInvisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if(isFirstVisible){
                isFirstVisible = false;
                initPrepare();
            }else{
                onUserVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisible();
            } else {
                onUserInvisible();
            }
        }
    }

    private synchronized void initPrepare(){
        if(isPrepared){
            onFirstUserVisible();
        }else{
            isPrepared = true;
        }
    }

    /**
     * 获取主视图资源文件
     * @return
     */
    protected abstract int getContentViewLayoutID();

    /**
     * 获取加载视图
     * @return
     */
    protected abstract View getLoadingTargetView();

    /**
     * 初始化视图和事件
     */
    protected abstract void initViewsAndEvents();

    /**
     * 当fragment第一次可见时,我们可以做一些初始化的工作或刷新数据，该方法只调用一次
     */
    protected abstract void onFirstUserVisible();

    /**
     * 这种方法就像fragment的生命周期方法onResume()
     */
    protected abstract void onUserVisible();

    /**
     * 当片段第一次隐藏
     */
    private void onFirstUserInvisible() {
        // 建议在这里不做任何处理
    }

    /**
     * 这种方法就像fragment的生命周期方法onPause()
     */
    protected abstract void onUserInvisible();
}

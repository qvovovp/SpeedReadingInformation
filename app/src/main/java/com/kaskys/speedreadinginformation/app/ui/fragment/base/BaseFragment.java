package com.kaskys.speedreadinginformation.app.ui.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 卡你基巴 on 2015/11/9.
 */
public abstract class BaseFragment extends BaseLazyFragment{

    protected void showToast(String msg) {
        //防止遮盖虚拟按键
        if (null != msg && !msg.isEmpty()) {
            Snackbar.make(getLoadingTargetView(), msg, Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * 是否显示加载
     * @param toggle
     * @param msg
     */
    protected void toggleShowLoading(boolean toggle, String msg){
        if(mLoadingViewHelperController == null){
            throw new IllegalArgumentException("You must return a target view for loading");
        }
        if(toggle){
            mLoadingViewHelperController.showLoading(msg);
        }else{
            mLoadingViewHelperController.restore();
        }
    }

    protected void toggleShowEmpty(boolean toggle, String msg, View.OnClickListener onClickListener){
        if(mLoadingViewHelperController == null){
            throw new IllegalArgumentException("You must return a target view for loading");
        }
        if(toggle){
            mLoadingViewHelperController.showEmpty(msg, onClickListener);
        }else{
            mLoadingViewHelperController.restore();
        }
    }

    /**
     * 是否显示错误信息
     * @param toggle
     * @param msg
     */
    protected void toggleShowError(boolean toggle, String msg, View.OnClickListener onClickListener){
        if(mLoadingViewHelperController == null){
            throw new IllegalArgumentException("You must return a target view for loading");
        }
        if(toggle){
            mLoadingViewHelperController.showError(msg, onClickListener);
        }else{
            mLoadingViewHelperController.restore();
        }
    }

    /**
     * 是否显示网络错误信息
     * @param toggle
     * @param onClickListener
     */
    protected void toggleNetworkError(boolean toggle, View.OnClickListener onClickListener){
        if(mLoadingViewHelperController == null){
            throw new IllegalArgumentException("You must return a target view for loading");
        }
        if(toggle){
            mLoadingViewHelperController.showNetwoekError(onClickListener);
        }else{
            mLoadingViewHelperController.restore();
        }
    }
}

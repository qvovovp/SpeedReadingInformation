package com.kaskys.speedreadinginformation.app.ui.widget.loading;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 卡你基巴 on 2015/11/7.
 */
public class LoadingViewHelper implements ILoadingViewHelper{
    private View view;
    private ViewGroup parentView;
    private int viewIndex;
    private ViewGroup.LayoutParams params;
    private View currentView;

    public LoadingViewHelper(View view){
        super();
        this.view = view;
    }

    /**
     * 初始化
     */
    private void init(){
        params = view.getLayoutParams();
        if(null != view.getParent()){
            parentView = (ViewGroup) view.getParent();
        }else{
            parentView = (ViewGroup) view.getRootView().findViewById(android.R.id.content);
        }
        int count = parentView.getChildCount();
        for(int index = 0;index<count;index++){
            if(view == parentView.getChildAt(index)){
                viewIndex = index;
                break;
            }
        }
        currentView = view;
    }

    @Override
    public View getCurrentLayout() {
        return currentView;
    }

    @Override
    public void restoreView() {
        showLayout(view);
    }

    @Override
    public void showLayout(View view) {
        if(null == parentView){
            init();
        }
        this.currentView = view;
        // 如果已经是那个view，那就不需要再进行替换操作了
        if(parentView.getChildAt(viewIndex) != view){
            ViewGroup parent = (ViewGroup) view.getParent();
            if(parent != null){
                parent.removeView(view);
            }
            parentView.removeViewAt(viewIndex);
            parentView.addView(view,viewIndex,params);
        }
    }

    @Override
    public View inflate(int layoutId) {
        return LayoutInflater.from(getContext()).inflate(layoutId,null);
    }

    @Override
    public Context getContext() {
        return view.getContext();
    }

    @Override
    public View getView() {
        return view;
    }
}
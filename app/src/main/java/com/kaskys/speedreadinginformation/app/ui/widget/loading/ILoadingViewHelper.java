package com.kaskys.speedreadinginformation.app.ui.widget.loading;

import android.content.Context;
import android.view.View;

/**
 * Created by 卡你基巴 on 2015/11/7.
 */
public interface ILoadingViewHelper {
    public abstract View getCurrentLayout();

    public abstract void restoreView();

    public abstract void showLayout(View view);

    public abstract View inflate(int layoutId);

    public abstract Context getContext();

    public abstract View getView();
}

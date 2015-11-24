package com.kaskys.speedreadinginformation.app.ui.view;

import android.view.animation.Animation;

/**
 * Created by 卡你基巴 on 2015/11/7.
 */
public interface SplashView {
    void animateBackgroundImage(Animation animation);
    void initializeViews(String appName,int backgroundResId);
    void initializeUmengConfig();
    void navigateToHomePage();
}

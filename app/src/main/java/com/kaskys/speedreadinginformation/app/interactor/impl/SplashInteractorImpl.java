package com.kaskys.speedreadinginformation.app.interactor.impl;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.kaskys.speedreadinginformation.app.R;
import com.kaskys.speedreadinginformation.app.interactor.SplashInteractor;

/**
 * Created by 卡你基巴 on 2015/11/7.
 */
public class SplashInteractorImpl implements SplashInteractor{
    @Override
    public String getAppName(Context context) {
        return context.getResources().getString(R.string.app_name);
    }

    @Override
    public int getBackgroundImageResID() {
        return R.mipmap.ic_splash;
    }

    @Override
    public Animation getBackgroundImageAnimation(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.splash);
    }
}

package com.kaskys.speedreadinginformation.app.interactor;

import android.content.Context;
import android.view.animation.Animation;

/**
 * Created by 卡你基巴 on 2015/11/7.
 */
public interface SplashInteractor {
    String getAppName(Context context);
    int getBackgroundImageResID();
    Animation getBackgroundImageAnimation(Context context);
}

package com.kaskys.speedreadinginformation.app.presenter.impl;

import android.content.Context;
import android.view.animation.Animation;
import com.kaskys.speedreadinginformation.app.interactor.SplashInteractor;
import com.kaskys.speedreadinginformation.app.interactor.impl.SplashInteractorImpl;
import com.kaskys.speedreadinginformation.app.presenter.Presenter;
import com.kaskys.speedreadinginformation.app.ui.view.SplashView;

/**
 * Created by 卡你基巴 on 2015/11/7.
 */
public class SplashPresenterImpl implements Presenter{
    private Context mContext;
    private SplashView mSplashView;
    private SplashInteractor mSplashInteractor;

    public SplashPresenterImpl(Context context,SplashView splashView){
        if(null == splashView){
            throw new IllegalArgumentException("Presenter must not be Null");
        }
        mContext = context;
        mSplashView = splashView;
        mSplashInteractor = new SplashInteractorImpl();
    }

    @Override
    public void initialized() {
        mSplashView.initializeUmengConfig();
        mSplashView.initializeViews(mSplashInteractor.getAppName(mContext),
                                mSplashInteractor.getBackgroundImageResID());
        Animation animation = mSplashInteractor.getBackgroundImageAnimation(mContext);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mSplashView.navigateToHomePage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mSplashView.animateBackgroundImage(animation);
    }
}

package com.kaskys.speedreadinginformation.app.ui.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.kaskys.speedreadinginformation.app.R;
import com.kaskys.speedreadinginformation.app.presenter.Presenter;
import com.kaskys.speedreadinginformation.app.presenter.impl.SplashPresenterImpl;
import com.kaskys.speedreadinginformation.app.ui.activity.base.BaseActivity;
import com.kaskys.speedreadinginformation.app.ui.view.SplashView;

import java.io.ByteArrayOutputStream;

public class SplashActivity extends BaseActivity implements SplashView{
    private ImageView mImgSplahs;
    private TextView mAppName;
    private Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    protected int getMenuViewLayoutID() {
        return 0;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected String getTitleName() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        mImgSplahs = (ImageView) this.findViewById(R.id.img_splash);
        mAppName = (TextView) this.findViewById(R.id.tv_app_name);

        mPresenter = new SplashPresenterImpl(this,this);
        mPresenter.initialized();
    }

    @Override
    protected void onActivityDestroy() {

    }

    @Override
    public void animateBackgroundImage(Animation animation) {
        mAppName.startAnimation(animation);
    }

    @Override
    public void initializeViews(String appName, int backgroundResId) {
        mAppName.setText(appName);
        mImgSplahs.setBackgroundResource(backgroundResId);
    }

    @Override
    public void initializeUmengConfig() {

    }

    @Override
    public void navigateToHomePage() {
        readyGoKill(HomeActivity.class,0,0);
    }
}

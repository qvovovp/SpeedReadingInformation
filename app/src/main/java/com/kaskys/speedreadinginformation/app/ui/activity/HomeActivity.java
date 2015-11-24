package com.kaskys.speedreadinginformation.app.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.kaskys.speedreadinginformation.app.R;
import com.kaskys.speedreadinginformation.app.presenter.Presenter;
import com.kaskys.speedreadinginformation.app.presenter.impl.HomePresenterImpl;
import com.kaskys.speedreadinginformation.app.ui.adapter.HomeFragmentAdapter;
import com.kaskys.speedreadinginformation.app.ui.fragment.base.BaseLazyFragment;
import com.kaskys.speedreadinginformation.app.ui.view.HomeView;
import com.kaskys.speedreadinginformation.app.ui.widget.NoScrollViewPager;
import com.kaskys.speedreadinginformation.app.utils.AnimUtils;
import com.kaskys.speedreadinginformation.app.ui.activity.base.BaseActivity;

import java.util.List;

/**
 * Created by 卡你基巴 on 2015/11/7.
 */
public class HomeActivity extends BaseActivity implements HomeView{
    private FrameLayout mFrame;
    private RelativeLayout rlContent;
    private LinearLayout llSplash;
    private NoScrollViewPager mViewPager;
    private ImageView mLeftImage,mRightImage;
    private RadioGroup mRadioGroup;

    private Presenter mHomePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    protected int getMenuViewLayoutID() {
        return R.menu.menu_main;
    }

    @Override
    protected View getLoadingTargetView() {
        return this.findViewById(R.id.home_container);
    }

    @Override
    protected String getTitleName() {
        return "主页";
    }

    @Override
    protected void initViewsAndEvents() {
        mFrame = (FrameLayout) this.findViewById(R.id.frame);
        rlContent = (RelativeLayout) this.findViewById(R.id.rl_content);
        llSplash = (LinearLayout) this.findViewById(R.id.ll_splash);
        mLeftImage = (ImageView) this.findViewById(R.id.img_left);
        mRightImage = (ImageView) this.findViewById(R.id.img_right);
        mViewPager = (NoScrollViewPager) this.findViewById(R.id.home_container);
        mRadioGroup = (RadioGroup) this.findViewById(R.id.rg_group);

        AnimUtils.homeStartAnim(HomeActivity.this,rlContent,mLeftImage,mRightImage);
        mHomePresenter = new HomePresenterImpl(this,this);
        mHomePresenter.initialized();

        mRadioGroup.check(R.id.rb_news);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                switch (checkId){
                    case R.id.rb_news:
                        mViewPager.setCurrentItem(0,false);
                        break;
                    case R.id.rb_picture:
                        mViewPager.setCurrentItem(1,false);
                        break;
                    case R.id.rb_music:
                        mViewPager.setCurrentItem(2,false);
                        break;
                    case R.id.rb_read:
                        mViewPager.setCurrentItem(3,false);
                        break;
                }
            }
        });
    }

    @Override
    public void initViews(List<BaseLazyFragment> fragments) {
        if(null != fragments && fragments.size() > 0){
            mViewPager.setOffscreenPageLimit(fragments.size());
            mViewPager.setAdapter(new HomeFragmentAdapter(getSupportFragmentManager(),fragments));
        }
    }

    public void removeStartView(){
        if(null != llSplash){
            ((ViewGroup)llSplash.getParent()).removeView(llSplash);
        }
    }

    public ViewGroup getRoot(){
        return rlContent;
    }

    public ViewGroup getFrame(){
        return mFrame;
    }
}

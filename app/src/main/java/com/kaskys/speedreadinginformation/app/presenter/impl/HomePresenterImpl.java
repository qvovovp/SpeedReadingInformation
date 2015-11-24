package com.kaskys.speedreadinginformation.app.presenter.impl;

import android.content.Context;
import android.widget.Toast;
import com.kaskys.speedreadinginformation.app.interactor.HomeInteractor;
import com.kaskys.speedreadinginformation.app.interactor.impl.HomeInteractorImpl;
import com.kaskys.speedreadinginformation.app.presenter.Presenter;
import com.kaskys.speedreadinginformation.app.ui.view.HomeView;

/**
 * Created by 卡你基巴 on 2015/11/9.
 */
public class HomePresenterImpl implements Presenter{
    private Context mContext;
    private HomeView mHomeView;
    private HomeInteractor mHomeInteractor;

    public HomePresenterImpl(Context context,HomeView homeView){
        if(null == homeView){
            throw new IllegalArgumentException("Presenter must not be Null");
        }
        mContext = context;
        mHomeView = homeView;
        mHomeInteractor = new HomeInteractorImpl();
    }

    @Override
    public void initialized() {
        mHomeView.initViews(mHomeInteractor.getPagerFragments());
    }
}

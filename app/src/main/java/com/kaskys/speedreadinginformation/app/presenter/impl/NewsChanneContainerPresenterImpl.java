package com.kaskys.speedreadinginformation.app.presenter.impl;

import android.content.Context;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.kaskys.speedreadinginformation.app.bean.NewsChanne;
import com.kaskys.speedreadinginformation.app.interactor.NewsChanneContainerInteractor;
import com.kaskys.speedreadinginformation.app.interactor.impl.NewsChanneContainerInteractorImpl;
import com.kaskys.speedreadinginformation.app.listeners.OnRequestListener;
import com.kaskys.speedreadinginformation.app.presenter.Presenter;
import com.kaskys.speedreadinginformation.app.ui.view.NewsContainerView;
import com.kaskys.speedreadinginformation.app.utils.PreferenceManager;

/**
 * Created by 卡你基巴 on 2015/11/10.
 */
public class NewsChanneContainerPresenterImpl implements Presenter{
    private static final String CHANNE_KEY = "channe_key";

    private Context mContext;
    private NewsContainerView mNewsContainerView;
    private NewsChanneContainerInteractor mNewsContainerInteractor;


    public NewsChanneContainerPresenterImpl(Context context, NewsContainerView newsContainerView){
        if(null == newsContainerView){
            throw new IllegalArgumentException("Presenter must not be Null");
        }
        mContext = context;
        mNewsContainerView = newsContainerView;
        mNewsContainerInteractor = new NewsChanneContainerInteractorImpl();
    }

    @Override
    public void initialized() {
        mNewsContainerView.initializePagerViews(mNewsContainerInteractor.getNewsChanneList(mContext));
    }

}

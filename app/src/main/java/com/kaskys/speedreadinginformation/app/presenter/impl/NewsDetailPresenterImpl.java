package com.kaskys.speedreadinginformation.app.presenter.impl;

import android.content.Context;
import com.kaskys.speedreadinginformation.app.interactor.NewsDetailInteractor;
import com.kaskys.speedreadinginformation.app.interactor.impl.NewsDetailInteractorImpl;
import com.kaskys.speedreadinginformation.app.presenter.Presenter;
import com.kaskys.speedreadinginformation.app.ui.view.NewsDetailView;

/**
 * Created by 卡你基巴 on 2015/11/15.
 */
public class NewsDetailPresenterImpl implements Presenter{
    private Context mContext;
    private NewsDetailView mNewsDetailView;
    private NewsDetailInteractor mNewsDetailInteractor;

    public NewsDetailPresenterImpl(Context context, NewsDetailView newsDetailView){
        if(null == newsDetailView){
            throw new IllegalArgumentException("Presenter must not be Null");
        }
        mContext = context;
        mNewsDetailView = newsDetailView;
        mNewsDetailInteractor = new NewsDetailInteractorImpl();
    }

    @Override
    public void initialized() {
        mNewsDetailView.initializeSizeData(mNewsDetailInteractor.getSizeChoiceData());
    }
}

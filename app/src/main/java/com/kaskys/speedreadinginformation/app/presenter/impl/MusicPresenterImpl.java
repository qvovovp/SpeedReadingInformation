package com.kaskys.speedreadinginformation.app.presenter.impl;

import android.content.Context;
import android.net.Uri;
import com.kaskys.speedreadinginformation.app.interactor.MusicInteractor;
import com.kaskys.speedreadinginformation.app.interactor.impl.MusicInteractorImpl;
import com.kaskys.speedreadinginformation.app.presenter.Presenter;
import com.kaskys.speedreadinginformation.app.ui.view.MusicView;

/**
 * Created by 卡你基巴 on 2015/12/3.
 */
public class MusicPresenterImpl implements Presenter{
    private Context mContext;
    private MusicView mMusicView;
    private MusicInteractor mMusicTopListInteractor;

    public MusicPresenterImpl(Context context, MusicView musicView){
        if(null == musicView){
            throw new IllegalArgumentException("Presenter must not be Null");
        }
        mContext = context;
        mMusicView = musicView;
        mMusicTopListInteractor = new MusicInteractorImpl();
    }

    @Override
    public void initialized() {
        mMusicView.initializeGridDatas(mMusicTopListInteractor.getGridDatas(mContext));
    }

    public Uri getMusicUri(String name){
        return mMusicTopListInteractor.getMusicUri(name);
    }
}

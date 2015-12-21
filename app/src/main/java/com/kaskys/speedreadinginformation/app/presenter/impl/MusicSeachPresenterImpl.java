package com.kaskys.speedreadinginformation.app.presenter.impl;

import android.content.Context;
import android.graphics.Bitmap;
import com.android.volley.VolleyError;
import com.kaskys.speedreadinginformation.app.bean.MusicBaiduTop;
import com.kaskys.speedreadinginformation.app.bean.MusicTop;
import com.kaskys.speedreadinginformation.app.interactor.MusicSeachInteractor;
import com.kaskys.speedreadinginformation.app.interactor.impl.MusicSeachInteractorImpl;
import com.kaskys.speedreadinginformation.app.listeners.OnImageLoadingListener;
import com.kaskys.speedreadinginformation.app.listeners.OnRequestListener;
import com.kaskys.speedreadinginformation.app.presenter.Presenter;
import com.kaskys.speedreadinginformation.app.ui.view.MusicSeachView;

/**
 * Created by 卡你基巴 on 2015/12/15.
 */
public class MusicSeachPresenterImpl implements Presenter,OnRequestListener<MusicBaiduTop>,OnImageLoadingListener{
    private Context mContext;
    private MusicSeachView mMusicSeachView;
    private MusicSeachInteractor mInteractor;
    private String mKey;
    private int mType;

    public MusicSeachPresenterImpl(Context context, MusicSeachView musicSeachView, String key, int type){
        if(null == musicSeachView){
            throw new IllegalArgumentException("Presenter must not be Null");
        }
        this.mContext = context;
        this.mMusicSeachView = musicSeachView;
        this.mKey = key;
        this.mType = type;
        mInteractor = new MusicSeachInteractorImpl(mContext,this,this);
    }

    @Override
    public void initialized() {
        mInteractor.getMusicDataFormNetwork(mKey,mType);
    }

    @Override
    public void onSuccess(MusicBaiduTop data) {
        if(null != data && null != data.billboard && data.song_list.size() > 0) {
            mMusicSeachView.setupData(data.song_list);
            mInteractor.getMusicHeaderImgBitmap(data.billboard.pic_s260);
            mMusicSeachView.setupHeaderData(data.billboard);
        }else{
            mMusicSeachView.setupData(null);
        }
    }

    @Override
    public void onLoadMore(MusicBaiduTop data) {

    }

    @Override
    public void onError(VolleyError volleyError) {
        System.out.println("onError-->");
        mMusicSeachView.setupData(null);
    }

    @Override
    public void onImageLoadComplete(Bitmap bitmap) {
        mMusicSeachView.setupHeaderImg(bitmap);
    }
}

package com.kaskys.speedreadinginformation.app.interactor.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kaskys.speedreadinginformation.app.bean.MusicBaiduTop;
import com.kaskys.speedreadinginformation.app.bean.MusicTop;
import com.kaskys.speedreadinginformation.app.interactor.MusicSeachInteractor;
import com.kaskys.speedreadinginformation.app.listeners.OnImageLoadingListener;
import com.kaskys.speedreadinginformation.app.listeners.OnRequestListener;
import com.kaskys.speedreadinginformation.app.net.UrlManager;
import com.kaskys.speedreadinginformation.app.net.VolleyWrapper;
import com.kaskys.speedreadinginformation.app.ui.activity.MusicSeachActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by 卡你基巴 on 2015/12/15.
 */
public class MusicSeachInteractorImpl implements MusicSeachInteractor{
    private Context mContext;
    private OnRequestListener mListener;
    private OnImageLoadingListener mImageListener;

    public MusicSeachInteractorImpl(Context context, OnRequestListener listener,OnImageLoadingListener imageListener){
        mContext = context;
        mListener = listener;
        mImageListener = imageListener;
    }

    @Override
    public void getMusicDataFormNetwork(String key, int type) {
        String url = "";
        if(type == MusicSeachActivity.SEACCH_TYPE_TOP){
            url = UrlManager.getInstance().getBaiduMusicTopListUrl(key);
        }else if(type == MusicSeachActivity.SEACCH_TYPE_DETAIIL){
            url = UrlManager.getInstance().getMusicQQDataListUrl(key,"20");
        }

        VolleyWrapper volleyManager = new VolleyWrapper(Request.Method.GET, url, MusicBaiduTop.class,
                new Response.Listener<MusicBaiduTop>() {
                    @Override
                    public void onResponse(MusicBaiduTop data) {
                        mListener.onSuccess(data);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        mListener.onError(volleyError);
                }
        });

        volleyManager.sendRequest();
    }

    @Override
    public void getMusicHeaderImgBitmap(String url) {
        ImageLoader.getInstance().loadImage(url, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                mImageListener.onImageLoadComplete(bitmap);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
    }
}

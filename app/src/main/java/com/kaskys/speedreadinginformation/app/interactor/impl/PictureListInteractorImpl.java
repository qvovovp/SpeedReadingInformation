package com.kaskys.speedreadinginformation.app.interactor.impl;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kaskys.speedreadinginformation.app.bean.PictureData;
import com.kaskys.speedreadinginformation.app.interactor.PictureListInteractor;
import com.kaskys.speedreadinginformation.app.listeners.OnRequestListener;
import com.kaskys.speedreadinginformation.app.net.UrlManager;
import com.kaskys.speedreadinginformation.app.net.VolleyWrapper;

/**
 * Created by 卡你基巴 on 2015/11/21.
 */
public class PictureListInteractorImpl implements PictureListInteractor{
    private Context mContext;
    private OnRequestListener mOnRequestListener;

    public PictureListInteractorImpl(Context context, OnRequestListener onRequestListener){
        mContext = context;
        mOnRequestListener = onRequestListener;
    }

    @Override
    public void getPictureDataFromNetwork(String typeId, int page, final boolean isLoadMore) {
        VolleyWrapper volleyWrapper = new VolleyWrapper(Request.Method.GET, UrlManager.getInstance().getPictureListUrl(typeId, String.valueOf(page)), PictureData.class,
                new Response.Listener<PictureData>() {
                    @Override
                    public void onResponse(PictureData data) {
                        if(isLoadMore){
                            mOnRequestListener.onLoadMore(data);
                        }else{
                            mOnRequestListener.onSuccess(data);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        mOnRequestListener.onError(volleyError);
                    }
                });
        volleyWrapper.sendRequest();
    }
}

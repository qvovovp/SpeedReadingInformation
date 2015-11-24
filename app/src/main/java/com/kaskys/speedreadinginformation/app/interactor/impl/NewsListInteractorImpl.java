package com.kaskys.speedreadinginformation.app.interactor.impl;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kaskys.speedreadinginformation.app.bean.NewsData;
import com.kaskys.speedreadinginformation.app.interactor.NewsListInteractor;
import com.kaskys.speedreadinginformation.app.listeners.OnRequestListener;
import com.kaskys.speedreadinginformation.app.net.UrlManager;
import com.kaskys.speedreadinginformation.app.net.VolleyWrapper;

/**
 * Created by 卡你基巴 on 2015/11/11.
 */
public class NewsListInteractorImpl implements NewsListInteractor{
    private Context mContext;
    private OnRequestListener mOnRequestListener;

    public NewsListInteractorImpl(Context context,OnRequestListener onRequestListener){
        mContext = context;
        mOnRequestListener = onRequestListener;
    }

    @Override
    public void getNewsDataFromNetwork(String channeId, int page, final boolean isLoadMore) {
        VolleyWrapper volleyWrapper = new VolleyWrapper(Request.Method.GET, UrlManager.getInstance().getNewsDataListUrl(channeId,null,null,String.valueOf(page)),NewsData.class,
                new Response.Listener<NewsData>(){
                    @Override
                    public void onResponse(NewsData newsData) {
                        if(isLoadMore){
                            mOnRequestListener.onLoadMore(newsData);
                        }else {
                            mOnRequestListener.onSuccess(newsData);
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

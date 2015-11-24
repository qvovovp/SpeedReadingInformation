package com.kaskys.speedreadinginformation.app.listeners;

import com.android.volley.VolleyError;

/**
 * Created by 卡你基巴 on 2015/11/10.
 */
public interface OnRequestListener<T>{
    void onSuccess(T data);
    void onLoadMore(T data);
    void onError(VolleyError volleyError);
}

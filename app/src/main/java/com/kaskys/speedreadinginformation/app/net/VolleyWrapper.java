package com.kaskys.speedreadinginformation.app.net;

import com.android.volley.Response;

/**
 * Created by 卡你基巴 on 2015/11/9.
 */
public class VolleyWrapper<T> {
    private int method;
    private String mUrl;
    private Class<T> mClazz;
    private Response.Listener mSuccessListener;
    private  Response.ErrorListener mErrorListener;

    public VolleyWrapper(int method, String url, Class<T> clazz, Response.Listener successListener, Response.ErrorListener errorListener){
        this.method = method;
        mUrl = url;
        mClazz = clazz;
        mSuccessListener = successListener;
        mErrorListener = errorListener;
    }

    /**
     * 发送请求
     */
    public void sendRequest(){
        GsonRequest request = new GsonRequest(method,mUrl,mClazz,mSuccessListener,mErrorListener);
        VolleyManager.getInstance().getRequestQueue().add(request);
    }

    /**
     * 发送有标记的请求
     * @param tag   标记
     */
    public void sendRequest(String tag){
        GsonRequest request = new GsonRequest(method,mUrl,mClazz,mSuccessListener,mErrorListener);
        request.setTag(tag);
        VolleyManager.getInstance().getRequestQueue().add(request);
    }
}

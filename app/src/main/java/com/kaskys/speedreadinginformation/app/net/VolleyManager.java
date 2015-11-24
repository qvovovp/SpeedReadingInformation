package com.kaskys.speedreadinginformation.app.net;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by 卡你基巴 on 2015/11/9.
 */
public class VolleyManager {
    private RequestQueue mRequestQueue = null;
    private static VolleyManager instance = null;

    private VolleyManager(){};

    public static VolleyManager getInstance(){
        if(null == instance){
            synchronized (VolleyManager.class){
                if(null == instance){
                    instance = new VolleyManager();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化网络请求
     * @param context
     */
    public void init(Context context){
        mRequestQueue = Volley.newRequestQueue(context);
    }

    /**
     * 获取请求队列
     * @return
     */
    public RequestQueue getRequestQueue(){
        if (null != mRequestQueue) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("VolleyManager没有被初始化");
        }
    }
}

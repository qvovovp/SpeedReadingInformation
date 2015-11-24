package com.kaskys.speedreadinginformation.app.net;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by 卡你基巴 on 2015/11/9.
 */
public class GsonRequest<T> extends Request<T> {
    private final Gson mGson;
    private final Class<T> mClazz;
    private final Response.Listener mSuccessListener;

    public GsonRequest(int method, String url, Class<T> clazz, Response.Listener successListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        System.out.println("GsonRequest.ok-->"+url);
        mGson = new Gson();
        mClazz = clazz;
        mSuccessListener = successListener;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            String json = new  String(networkResponse.data,"UTF-8");
            System.out.println("parseNetworkResponse.ok-->"+json);
            return Response.success(mGson.fromJson(json, mClazz), HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mSuccessListener.onResponse(response);
    }
}

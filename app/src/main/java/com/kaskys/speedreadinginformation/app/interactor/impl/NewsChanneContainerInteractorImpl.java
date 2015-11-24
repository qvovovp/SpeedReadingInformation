package com.kaskys.speedreadinginformation.app.interactor.impl;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kaskys.speedreadinginformation.app.R;
import com.kaskys.speedreadinginformation.app.api.APIConstants;
import com.kaskys.speedreadinginformation.app.bean.NewsChanne;
import com.kaskys.speedreadinginformation.app.interactor.NewsChanneContainerInteractor;
import com.kaskys.speedreadinginformation.app.listeners.OnRequestListener;
import com.kaskys.speedreadinginformation.app.net.UrlManager;
import com.kaskys.speedreadinginformation.app.net.VolleyManager;
import com.kaskys.speedreadinginformation.app.net.VolleyWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 卡你基巴 on 2015/11/10.
 */
public class NewsChanneContainerInteractorImpl implements NewsChanneContainerInteractor {

    @Override
    public List<NewsChanne> getNewsChanneList(Context context) {
        List<NewsChanne> channes = new ArrayList<NewsChanne>();
        String[] newsChanneArrayId = context.getResources().getStringArray(R.array.news_channe_list_id);
        String[] newsChanneArrayName = context.getResources().getStringArray(R.array.news_channe_list_name);
        for(int i=0;i<newsChanneArrayId.length;i++){
            NewsChanne channe = new NewsChanne();
            channe.channeId = newsChanneArrayId[i];
            channe.channeName = newsChanneArrayName[i];
            channes.add(channe);
        }
        return channes;
    }
}

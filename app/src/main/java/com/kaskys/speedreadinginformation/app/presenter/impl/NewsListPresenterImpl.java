package com.kaskys.speedreadinginformation.app.presenter.impl;

import android.content.Context;
import com.android.volley.VolleyError;
import com.kaskys.speedreadinginformation.app.bean.NewsData;
import com.kaskys.speedreadinginformation.app.bean.NewsData.Body.Bean.Detail;
import com.kaskys.speedreadinginformation.app.interactor.NewsListInteractor;
import com.kaskys.speedreadinginformation.app.interactor.impl.NewsListInteractorImpl;
import com.kaskys.speedreadinginformation.app.listeners.OnRequestListener;
import com.kaskys.speedreadinginformation.app.presenter.DataListPresenter;
import com.kaskys.speedreadinginformation.app.ui.view.NewsListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 卡你基巴 on 2015/11/11.
 */
public class NewsListPresenterImpl implements DataListPresenter,OnRequestListener<NewsData>{
    private Context mContext;
    private NewsListView mNewsListView;
    private NewsListInteractor mNewsListInteractor;

    public NewsListPresenterImpl(Context context, NewsListView newsListView){
        if(null == newsListView){
            throw new IllegalArgumentException("Presenter must not be Null");
        }
        mContext = context;
        mNewsListView = newsListView;
        mNewsListInteractor = new NewsListInteractorImpl(mContext,this);
    }

    @Override
    public void initialized(String id,int page) {
        mNewsListInteractor.getNewsDataFromNetwork(id,page,false);
    }

    @Override
    public void loadMore(String id, int page) {
        mNewsListInteractor.getNewsDataFromNetwork(id,page,true);
    }

    @Override
    public void onSuccess(NewsData newsData) {
        if(null == newsData.showapi_res_body.pagebean.contentlist || newsData.showapi_res_body.pagebean.contentlist.size() <= 0){
            mNewsListView.setupData(null,null);
        }else {
            List<List<Detail>> result = onDivisionData(newsData);
            mNewsListView.setupData(result.get(0), result.get(1));
        }
    }

    @Override
    public void onLoadMore(NewsData newsData) {
        if(null == newsData.showapi_res_body.pagebean.contentlist || newsData.showapi_res_body.pagebean.contentlist.size() <= 0) {
            mNewsListView.onLoadMore(null);
        }else {
            mNewsListView.onLoadMore(newsData.showapi_res_body.pagebean.contentlist);
        }
    }

    private List<List<Detail>> onDivisionData(NewsData newsData) {
        List<List<Detail>> result = new ArrayList<List<Detail>>();
        List<Detail> top = new ArrayList<Detail>();
        List<Detail> content = newsData.showapi_res_body.pagebean.contentlist;

        for(int i=0;i<content.size();i++){
            if(null != content.get(i).imageurls){
                if(content.get(i).imageurls.size() > 0) {
                    System.out.println("i.url-->" + i + "," + content.get(i).imageurls.get(0).url);
                    top.add(content.get(i));
                    if (top.size() >= 3) {
                        break;
                    }
                }
            }
        }

        for(int i=0;i<top.size();i++){
            content.remove(top.get(i));
        }

        result.add(0,top);
        result.add(1,content);
        return result;
    }

    @Override
    public void onError(VolleyError volleyError) {
        mNewsListView.setupData(null,null);
    }
}

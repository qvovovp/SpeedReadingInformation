package com.kaskys.speedreadinginformation.app.presenter;

/**
 * Created by 卡你基巴 on 2015/11/11.
 */
public interface DataListPresenter {
    void initialized(String id,int page);
    void loadMore(String id,int page);
}

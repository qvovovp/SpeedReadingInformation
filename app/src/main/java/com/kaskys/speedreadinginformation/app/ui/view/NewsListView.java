package com.kaskys.speedreadinginformation.app.ui.view;

import com.kaskys.speedreadinginformation.app.bean.NewsData.Body.Bean.Detail;

import java.util.List;

/**
 * Created by 卡你基巴 on 2015/11/10.
 */
public interface NewsListView {
    void onPageSelected(int position,String keyword);
    void setupData(List<Detail> topNews,List<Detail> newsData);
    void onLoadMore(List<Detail> newsData);
    void onItemClick(String url);
}

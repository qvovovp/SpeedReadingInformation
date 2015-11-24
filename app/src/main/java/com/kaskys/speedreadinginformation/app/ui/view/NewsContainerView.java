package com.kaskys.speedreadinginformation.app.ui.view;

import com.kaskys.speedreadinginformation.app.bean.NewsChanne;

import java.util.List;

/**
 * Created by 卡你基巴 on 2015/11/10.
 */
public interface NewsContainerView {
    void initializePagerViews(List<NewsChanne> categoryList);
}

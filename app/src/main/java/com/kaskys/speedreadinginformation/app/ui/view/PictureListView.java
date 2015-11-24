package com.kaskys.speedreadinginformation.app.ui.view;


import com.kaskys.speedreadinginformation.app.bean.PictureData;

import java.util.List;

/**
 * Created by 卡你基巴 on 2015/11/21.
 */
public interface PictureListView {
    void getTypeId(String id);
    void setupData(List<PictureData.Body.Detail> pictures);
    void onLoadMore(List<PictureData.Body.Detail> pictures);
    void onItemClick(String url);
}

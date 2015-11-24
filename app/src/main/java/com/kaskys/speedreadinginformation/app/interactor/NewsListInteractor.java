package com.kaskys.speedreadinginformation.app.interactor;

import android.content.Context;

/**
 * Created by 卡你基巴 on 2015/11/11.
 */
public interface NewsListInteractor {
    void getNewsDataFromNetwork(String channeId,int page,boolean isLoadMore);
}

package com.kaskys.speedreadinginformation.app.interactor;

/**
 * Created by 卡你基巴 on 2015/11/21.
 */
public interface PictureListInteractor {
    void getPictureDataFromNetwork(String typeId,int page,boolean isLoadMore);
}

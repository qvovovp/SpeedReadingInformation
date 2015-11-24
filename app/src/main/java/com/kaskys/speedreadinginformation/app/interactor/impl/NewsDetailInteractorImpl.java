package com.kaskys.speedreadinginformation.app.interactor.impl;

import com.kaskys.speedreadinginformation.app.interactor.NewsDetailInteractor;

/**
 * Created by 卡你基巴 on 2015/11/15.
 */
public class NewsDetailInteractorImpl implements NewsDetailInteractor{
    @Override
    public String[] getSizeChoiceData() {
        return new String[]{"超大号","大号","正常","小号","超小号"};
    }
}

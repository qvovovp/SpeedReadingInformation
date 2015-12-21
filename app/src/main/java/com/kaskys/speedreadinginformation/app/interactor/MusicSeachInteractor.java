package com.kaskys.speedreadinginformation.app.interactor;

import android.graphics.Bitmap;
import com.kaskys.speedreadinginformation.app.bean.MusicTop;

import java.util.List;

/**
 * Created by 卡你基巴 on 2015/12/15.
 */
public interface MusicSeachInteractor {
    void getMusicDataFormNetwork(String key,int type);
    void getMusicHeaderImgBitmap(String url);
}

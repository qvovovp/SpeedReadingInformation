package com.kaskys.speedreadinginformation.app.interactor;

import android.content.Context;
import android.net.Uri;
import com.kaskys.speedreadinginformation.app.bean.MusicType;

import java.util.List;

/**
 * Created by 卡你基巴 on 2015/12/3.
 */
public interface MusicInteractor {
    List<MusicType> getGridDatas(Context context);
    Uri getMusicUri(String name);
}

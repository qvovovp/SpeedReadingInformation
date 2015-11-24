package com.kaskys.speedreadinginformation.app.interactor;

import android.content.Context;
import com.kaskys.speedreadinginformation.app.bean.PictureType;

import java.util.List;

/**
 * Created by 卡你基巴 on 2015/11/21.
 */
public interface PictureTypeContainerInteractor {
    List<PictureType> initializePagerViews(Context context);
}

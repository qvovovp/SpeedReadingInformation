package com.kaskys.speedreadinginformation.app.interactor;


import android.content.Context;
import com.kaskys.speedreadinginformation.app.bean.NewsChanne;

import java.util.List;

/**
 * Created by 卡你基 on 2015/11/10.
 */
public interface NewsChanneContainerInteractor {
    List<NewsChanne> getNewsChanneList(Context context);
}

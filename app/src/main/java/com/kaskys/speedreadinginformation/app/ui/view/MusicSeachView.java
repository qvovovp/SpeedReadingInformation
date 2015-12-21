package com.kaskys.speedreadinginformation.app.ui.view;


import android.graphics.Bitmap;
import com.kaskys.speedreadinginformation.app.bean.MusicBaiduTop;

import java.util.List;

/**
 * Created by 卡你基巴 on 2015/12/15.
 */
public interface MusicSeachView {
    void setupData(List<MusicBaiduTop.Detail> musics);
    void setupHeaderImg(Bitmap bitmap);
    void setupHeaderData(MusicBaiduTop.Billboard billboard);
}

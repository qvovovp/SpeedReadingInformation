package com.kaskys.speedreadinginformation.app.ui.view;

import com.kaskys.speedreadinginformation.app.bean.MusicType;

import java.util.List;

/**
 * Created by 卡你基巴 on 2015/12/3.
 */
public interface MusicView {
    void initializeGridDatas(List<MusicType> typeList);
    void playMusic();
    void pauseMusic();
}

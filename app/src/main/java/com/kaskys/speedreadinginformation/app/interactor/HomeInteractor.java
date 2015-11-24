package com.kaskys.speedreadinginformation.app.interactor;

import android.support.v4.app.Fragment;
import com.kaskys.speedreadinginformation.app.ui.fragment.base.BaseLazyFragment;

import java.util.List;

/**
 * Created by 卡你基巴 on 2015/11/9.
 */
public interface HomeInteractor {
    List<BaseLazyFragment> getPagerFragments();
}

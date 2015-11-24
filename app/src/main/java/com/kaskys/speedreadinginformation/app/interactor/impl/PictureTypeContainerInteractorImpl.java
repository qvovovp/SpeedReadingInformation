package com.kaskys.speedreadinginformation.app.interactor.impl;

import android.content.Context;
import com.kaskys.speedreadinginformation.app.R;
import com.kaskys.speedreadinginformation.app.bean.PictureType;
import com.kaskys.speedreadinginformation.app.interactor.PictureTypeContainerInteractor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 卡你基巴 on 2015/11/21.
 */
public class PictureTypeContainerInteractorImpl implements PictureTypeContainerInteractor{
    @Override
    public List<PictureType> initializePagerViews(Context context) {
        List<PictureType> list = new ArrayList<PictureType>();
        int[] ids = context.getResources().getIntArray(R.array.picture_type_id_list);
        String[] names = context.getResources().getStringArray(R.array.picture_type_name_list);

        for(int i=0;i<names.length;i++){
            PictureType type = new PictureType();
            type.id = String.valueOf(ids[i]);
            type.name = names[i];
            list.add(type);
        }

        return list;
    }
}

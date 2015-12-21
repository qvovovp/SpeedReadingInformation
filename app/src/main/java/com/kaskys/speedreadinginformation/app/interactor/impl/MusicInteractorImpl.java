package com.kaskys.speedreadinginformation.app.interactor.impl;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import com.kaskys.speedreadinginformation.app.R;
import com.kaskys.speedreadinginformation.app.bean.MusicType;
import com.kaskys.speedreadinginformation.app.interactor.MusicInteractor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 卡你基巴 on 2015/12/3.
 */
public class MusicInteractorImpl implements MusicInteractor {
    private final int[] bgResIds = new int[]{R.mipmap.ic_music_top_om,R.mipmap.ic_music_top_nd,R.mipmap.ic_music_top_gt,
                    R.mipmap.ic_music_top_hg,R.mipmap.ic_music_top_rb,R.mipmap.ic_music_top_my,R.mipmap.ic_music_top_yg,
                    R.mipmap.ic_music_top_xl,R.mipmap.ic_music_top_rg};

    @Override
    public List<MusicType> getGridDatas(Context context) {
        List<MusicType> list = new ArrayList<MusicType>();
        int[] ids = context.getResources().getIntArray(R.array.music_top_id_list);
        String[] names = context.getResources().getStringArray(R.array.music_top_name_list);

        for(int i=0;i<names.length;i++){
            MusicType type = new MusicType();
            type.id = String.valueOf(ids[i]);
            type.name = names[i];
            type.bgResId = bgResIds[i];
            list.add(type);
        }

        return list;
    }

    @Override
    public Uri getMusicUri(String name) {
        return Uri.parse(name);
    }

//    public Uri getMusicUri(String name) {
//        Uri uri = null;
//        if(Environment.DIRECTORY_MOVIES == Environment.getExternalStorageState()){
//            try{
//                File file = new File(Environment.getExternalStorageDirectory(),name);
//                uri = Uri.parse(file.getAbsolutePath());
//            }catch(Exception e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println("uri-->"+uri.toString());
//        return uri;
//    }
}

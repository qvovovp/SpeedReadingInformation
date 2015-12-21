package com.kaskys.speedreadinginformation.app.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.kaskys.speedreadinginformation.app.R;
import com.kaskys.speedreadinginformation.app.bean.MusicType;
import com.kaskys.speedreadinginformation.app.ui.widget.IMusicBackground;

import java.util.List;

/**
 * Created by 卡你基巴 on 2015/12/3.
 */
public class MusicTopListAdapter extends BaseAdapter{
    private Context mContext;
    private List<MusicType> mTypes;

    public MusicTopListAdapter(Context context, List<MusicType> types){
        mContext = context;
        mTypes = types;
    }

    @Override
    public int getCount() {
        return mTypes.size();
    }

    @Override
    public Object getItem(int i) {
        return mTypes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = View.inflate(mContext, R.layout.fragment_music_top_item,null);
        view.setTag(mTypes.get(i).id);
        IMusicBackground musicBg = (IMusicBackground) view.findViewById(R.id.music_top_bg);
        musicBg.setText(mTypes.get(i).name);
        musicBg.setBackgroundResource(mTypes.get(i).bgResId);
        return view;
    }
}

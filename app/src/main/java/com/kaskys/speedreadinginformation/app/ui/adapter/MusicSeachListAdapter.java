package com.kaskys.speedreadinginformation.app.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.kaskys.speedreadinginformation.app.R;
import com.kaskys.speedreadinginformation.app.bean.MusicBaiduTop;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by 卡你基巴 on 2015/12/20.
 */
public class MusicSeachListAdapter extends BaseAdapter{
    private Context mContext;
    private List<MusicBaiduTop.Detail> mMusics;

    public MusicSeachListAdapter(Context context, List<MusicBaiduTop.Detail> musics){
        mContext = context;
        mMusics = musics;
    }

    public void clear(){
        mMusics.clear();
    }

    public void addAll(List<MusicBaiduTop.Detail> musics){
        mMusics.addAll(musics);
    }

    @Override
    public int getCount() {
        return mMusics.size();
    }

    @Override
    public Object getItem(int i) {
        return mMusics.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View converView, ViewGroup viewGroup) {
        ViewHolder holder;
        if(converView == null){
            converView = View.inflate(mContext, R.layout.activity_music_seach_item, null);
            holder = new ViewHolder();
            holder.smallImg = (ImageView) converView.findViewById(R.id.small_img);
            holder.songName = (TextView) converView.findViewById(R.id.song_name);
            holder.specialName = (TextView) converView.findViewById(R.id.special_name);
            converView.setTag(holder);
        }else{
            holder = (ViewHolder) converView.getTag();
        }

        ImageLoader.getInstance().displayImage(mMusics.get(i).pic_small,holder.smallImg);
        holder.songName.setText(mMusics.get(i).album_title);
        holder.specialName.setText(mMusics.get(i).album_title+"-"+mMusics.get(i).author);
        return converView;
    }

    class ViewHolder{
        ImageView smallImg;
        TextView songName,specialName;
    }
}

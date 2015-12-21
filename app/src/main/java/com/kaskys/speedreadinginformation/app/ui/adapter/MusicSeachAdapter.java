package com.kaskys.speedreadinginformation.app.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.kaskys.speedreadinginformation.app.R;
import com.kaskys.speedreadinginformation.app.bean.MusicBaiduTop.Detail;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by 卡你基巴 on 2015/12/16.
 */
public class MusicSeachAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private List<Detail> mMusics;

    private OnItemClickListener mOnItemClickListener;

    public MusicSeachAdapter(Context context, List<Detail> musics){
        mContext = context;
        mMusics = musics;
    }

    public void clear(){
        mMusics.clear();
    }

    public void addAll(List<Detail> musics){
        mMusics.addAll(musics);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(View.inflate(mContext, R.layout.activity_music_seach_item,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        ImageLoader.getInstance().displayImage(mMusics.get(i).pic_small,holder.smallImg);
        holder.songName.setText(mMusics.get(i).album_title);
        holder.specialName.setText(mMusics.get(i).album_title+"-"+mMusics.get(i).author);
    }

    @Override
    public int getItemCount() {
        return mMusics.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView smallImg;
        TextView songName,specialName;
        public ViewHolder(View itemView) {
            super(itemView);
            smallImg = (ImageView) itemView.findViewById(R.id.small_img);
            songName = (TextView) itemView.findViewById(R.id.song_name);
            specialName = (TextView) itemView.findViewById(R.id.special_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(null != mOnItemClickListener){
                mOnItemClickListener.onItemClick(view,getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener=onItemClickListener;
    }
}

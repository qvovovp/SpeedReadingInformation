package com.kaskys.speedreadinginformation.app.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.kaskys.speedreadinginformation.app.R;
import com.kaskys.speedreadinginformation.app.bean.PictureData.Body.Detail;
import com.kaskys.speedreadinginformation.app.ui.widget.MyImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 卡你基巴 on 2015/11/21.
 */
public class PictureListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private List<Detail> mPictures;

    private OnItemClickListener mOnItemClickListener;

    public PictureListAdapter(Context context, List<Detail> pictures){
        mContext = context;
        mPictures = pictures;
    }

    public void clear(){
        mPictures.clear();
    }

    public void addAll(List<Detail> pictures){
        mPictures.addAll(pictures);
    }

    public List<Detail> getPictures(){
        return mPictures;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        return new ViewHolder(View.inflate(mContext,R.layout.fragment_picture_list_item,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        ImageLoader.getInstance().displayImage(mPictures.get(i).thumb,holder.imgContext);
    }

    @Override
    public int getItemCount() {
        return mPictures.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        MyImageView imgContext;
        public ViewHolder(View itemView) {
            super(itemView);
            imgContext = (MyImageView) itemView.findViewById(R.id.img_content);
            itemView.setTag(imgContext);
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

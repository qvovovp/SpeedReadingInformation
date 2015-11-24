package com.kaskys.speedreadinginformation.app.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.kaskys.speedreadinginformation.app.R;
import com.kaskys.speedreadinginformation.app.bean.NewsData.Body.Bean.Detail;
import com.kaskys.speedreadinginformation.app.ui.view.NewsListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 卡你基巴 on 2015/11/11.
 */
public class NewsListAdapter extends BaseAdapter{
    private Context mContext;
    private List<Detail> mNews;

    private NewsListView mNewsListView;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public NewsListAdapter(Context context, List<Detail> news, NewsListView newsListView){
        mContext = context;
        mNews = news;
        mNewsListView = newsListView;
    }

    public void clear(){
        mNews.clear();
    }

    public void addAll(List<Detail> news){
        mNews.addAll(news);
    }

    @Override
    public int getCount() {
        return mNews.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View converView, ViewGroup viewGroup) {
        ViewHolder holder;
        View view;

        if(converView == null) {
            view = View.inflate(mContext, R.layout.fragment_news_list_item, null);
            holder = new ViewHolder();
            holder.tvTitle = (TextView) view.findViewById(R.id.tv_title);
            holder.tvSource = (TextView) view.findViewById(R.id.tv_source);
            holder.tvTime = (TextView) view.findViewById(R.id.tv_time);
            holder.imgContent = (ImageView) view.findViewById(R.id.img_content);
            view.setTag(holder);
        }else{
            view = converView;
            holder = (ViewHolder) converView.getTag();
        }

        holder.tvTitle.setText(mNews.get(i).title);
        holder.tvSource.setText(mNews.get(i).source);
        holder.tvTime.setText(mNews.get(i).pubDate);
        if(null != mNews.get(i).imageurls && mNews.get(i).imageurls.size() > 0) {
            ImageLoader.getInstance().displayImage(mNews.get(i).imageurls.get(0).url,holder.imgContent,animateFirstListener);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNewsListView.onItemClick(mNews.get(i).link);
            }
        });
        return view;
    }

    class ViewHolder{
        TextView tvTitle,tvSource,tvTime;
        ImageView imgContent;
    }

    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if(loadedImage != null){
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if(firstDisplay){
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }
}

package com.kaskys.speedreadinginformation.app.ui.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
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
 * Created by 卡你基巴 on 2015/11/12.
 */
public class NewsTopAdapter extends PagerAdapter{
    private Context mContext;
    private List<Detail> mTopNews;

    private NewsListView mNewsListView;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public NewsTopAdapter(Context context, List<Detail> topNews, NewsListView newsListView){
        mContext = context;
        mTopNews = topNews;
        mNewsListView = newsListView;
    }

    public void clear(){
        mTopNews.clear();
    }

    public void addAll(List<Detail> news){
        mTopNews.addAll(news);
    }

    @Override
    public int getCount() {
        return mTopNews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTopNews.get(position).title;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = View.inflate(mContext,R.layout.fragment_news_header_item,null);

        ImageView img = (ImageView) view.findViewById(R.id.img_content);
        if(mTopNews.get(position).imageurls.size() > 0 && null != mTopNews.get(position).imageurls){
            ImageLoader.getInstance().displayImage(mTopNews.get(position).imageurls.get(0).url,img,animateFirstListener);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNewsListView.onItemClick(mTopNews.get(position).link);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
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

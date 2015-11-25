package com.kaskys.speedreadinginformation.app.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;
import com.kaskys.speedreadinginformation.app.ui.widget.MyImageView;

/**
 * Created by 卡你基巴 on 2015/11/25.
 */
public class MyImageLoader {
    private static MyImageLoader instance;

    private MyImageLoader(){}

    public static MyImageLoader getInstance(){
        if(null == instance){
            synchronized (MyImageLoader.class){
                if(null == instance){
                    instance = new MyImageLoader();
                }
            }
        }
        return instance;
    }

    public void display(Bitmap bitmap, ImageView imageView){
        imageView.setImageBitmap(bitmap);
    }
}

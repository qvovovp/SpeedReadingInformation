package com.kaskys.speedreadinginformation.app;

import android.app.Application;
import com.kaskys.speedreadinginformation.app.api.APIConstants;
import com.kaskys.speedreadinginformation.app.net.VolleyManager;
import com.kaskys.speedreadinginformation.app.utils.ImageLoaderHelper;
import com.kaskys.speedreadinginformation.app.utils.PreferenceManager;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by 卡你基巴 on 2015/11/9.
 */
public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        VolleyManager.getInstance().init(this);
        PreferenceManager.initialize(this);
        ImageLoader.getInstance().init(ImageLoaderHelper.getInstance(this).
                    getImageLoaderConfiguration(APIConstants.Paths.BASE_PATH+APIConstants.Paths.IMAGE_LOADER_CACHE_PATH));
    }
}

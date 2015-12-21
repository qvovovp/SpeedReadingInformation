package com.kaskys.speedreadinginformation.app.api;

import android.os.Environment;

/**
 * Created by 卡你基巴 on 2015/11/9.
 */
public class APIConstants {
    public static final class Urls{
        //机器人
        public static final String ROBOT_URL = "http://route.showapi.com/60-27";

        //新闻频道
        public static final String NEWS_CHANNE_URL = "http://route.showapi.com/109-34";
        //新闻内容
        public static final String NEWS_DATA_URL = "http://route.showapi.com/109-35";

        //地区新闻频道
        public static final String NEWS_AREA_CHANNE_URL = "http://route.showapi.com/170-48";
        //地区新闻内容
        public static final String NEWS_AREA_DATA_URL = "http://route.showapi.com/170-47";

        //图片
        public static final String PICTURE_DATA_URL = "http://route.showapi.com/819-1";

        //默认音乐
        public static final String MUSIC_DEFAULT_DATA_URL = "http://route.showapi.com/760-1";

        //QQ音乐排行
        public static final String MUSIC_QQ_TOP_URL = "http://route.showapi.com/213-4";

        //百度音乐排行榜
        public static final String MUSIC_BAIDU_TOP_URL = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=qianqian&version=2.1.0&method=baidu.ting.billboard.billList&format=json";

        //QQ音乐ID查询
        public static final String MUSIC_QQ_ID_DATA_URL = "http://route.showapi.com/213-2";
        //QQ音乐歌名人名查询
        public static final String MUSIC_QQ_DATA_URL = "http://route.showapi.com/213-1";

        //历史上的今天
        public static final String READ_HISTORY_DATA_URL = "http://route.showapi.com/119-42";
        //微信精选
        public static final String READ_CHAR_DATA_URL = "http://route.showapi.com/181-1";
    }

    public static final class Keys{
        public static final String SHOWAPI_APP_KEY = "6fe90941b8114c9b9e5d65e3d7de153c";
        public static final String SHOWAPI_APP_ID = "12070";
    }

    public static final class Paths {
        public static final String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
        public static final String IMAGE_LOADER_CACHE_PATH = "images";
    }
}

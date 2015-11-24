package com.kaskys.speedreadinginformation.app.net;

import android.net.Uri;
import com.kaskys.speedreadinginformation.app.api.APIConstants;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 卡你基巴 on 2015/11/9.
 */
public class UrlManager {
    private final static String PAGER_NUM = "20";
    private final static String PAGER_NUM_MIN = "10";
    private final static String MUSIC_KEY = "482dcc55534d629f559c4dd62f1d6a6e09bf61";

    private static UrlManager instance = null;

    private UrlManager(){};

    public static UrlManager getInstance(){
        if(null == instance){
            synchronized (UrlManager.class){
                if(null == instance){
                    instance = new UrlManager();
                }
            }
        }
        return instance;
    }

    /**
     * 获取机器人Url
     * @param info      对话信息
     * @param userId    用户Id
     * @return
     */
    public String getRobotUrl(String info, String userId){
        String url = APIConstants.Urls.ROBOT_URL;
        Uri.Builder builder = Uri.parse(url).buildUpon()
                .appendQueryParameter("showapi_appid",APIConstants.Keys.SHOWAPI_APP_ID)
                .appendQueryParameter("showapi_sign",APIConstants.Keys.SHOWAPI_APP_KEY)
                .appendQueryParameter("showapi_timestamp",getTimeStamp())
                .appendQueryParameter("info",info)
                .appendQueryParameter("userid",userId);
        return builder.build().toString();
    }

    /**
     * 获取新闻频道Url
     * @return
     */
    public String getNewsChanneListUrl(){
        String url = APIConstants.Urls.NEWS_CHANNE_URL;
        Uri.Builder builder = Uri.parse(url).buildUpon()
                .appendQueryParameter("showapi_appid",APIConstants.Keys.SHOWAPI_APP_ID)
                .appendQueryParameter("showapi_sign",APIConstants.Keys.SHOWAPI_APP_KEY)
                .appendQueryParameter("showapi_timestamp",getTimeStamp());
        return builder.build().toString();
    }

    /**
     * 获取新闻内容Url
     * @param channelId     频道Id
     * @param channeName    频道名称
     * @param title         标题
     * @param page          页数
     * @return
     */
    public String getNewsDataListUrl(String channelId, String channeName, String title, String page){
        String url = APIConstants.Urls.NEWS_DATA_URL;
        Uri.Builder builder = Uri.parse(url).buildUpon()
                .appendQueryParameter("showapi_appid",APIConstants.Keys.SHOWAPI_APP_ID)
                .appendQueryParameter("showapi_sign",APIConstants.Keys.SHOWAPI_APP_KEY)
                .appendQueryParameter("showapi_timestamp",getTimeStamp());
        if(null != channelId && !"".equals(channelId)){
            builder.appendQueryParameter("channelId",channelId);
        }
        if(null != channeName && !"".equals(channeName)){
            builder.appendQueryParameter("channeName",channeName);
        }
        if(null != title && !"".equals(title)){
            builder.appendQueryParameter("title",title);
        }
        if(null != page && !"".equals(page)){
            builder.appendQueryParameter("page",page);
        }
        return builder.build().toString();
    }

    /**
     * 获取地区新闻Url
     * @return
     */
    public String getNewsAreaChanneListUrl(){
        String url = APIConstants.Urls.NEWS_AREA_CHANNE_URL;
        Uri.Builder builder = Uri.parse(url).buildUpon()
                .appendQueryParameter("showapi_appid",APIConstants.Keys.SHOWAPI_APP_ID)
                .appendQueryParameter("showapi_sign",APIConstants.Keys.SHOWAPI_APP_KEY)
                .appendQueryParameter("showapi_timestamp",getTimeStamp());
        return builder.build().toString();
    }

    /**
     * 获取地区新闻内容Url
     * @param areaId        地区新闻Id
     * @param areaName      地区新闻名称
     * @param title         标题
     * @param page          页数
     * @return
     */
    public String getNewsAreaDataListUrl(String areaId, String areaName, String title, String page){
        String url = APIConstants.Urls.NEWS_AREA_DATA_URL;
        Uri.Builder builder = Uri.parse(url).buildUpon()
                .appendQueryParameter("showapi_appid",APIConstants.Keys.SHOWAPI_APP_ID)
                .appendQueryParameter("showapi_sign",APIConstants.Keys.SHOWAPI_APP_KEY)
                .appendQueryParameter("showapi_timestamp",getTimeStamp())
                .appendQueryParameter("areaId",areaId)
                .appendQueryParameter("areaName",areaName)
                .appendQueryParameter("title",title)
                .appendQueryParameter("page",page);
        return builder.build().toString();
    }

    /**
     * 获取图片Url
     * @param type      图片类型
     * @param page      页数
     * @return
     */
    public String getPictureListUrl(String type, String page){
        String url = APIConstants.Urls.PICTURE_DATA_URL;
        Uri.Builder builder = Uri.parse(url).buildUpon()
                .appendQueryParameter("showapi_appid",APIConstants.Keys.SHOWAPI_APP_ID)
                .appendQueryParameter("showapi_sign",APIConstants.Keys.SHOWAPI_APP_KEY)
                .appendQueryParameter("showapi_timestamp",getTimeStamp())
                .appendQueryParameter("type",type)
                .appendQueryParameter("num",PAGER_NUM_MIN)
                .appendQueryParameter("page",page);
        return builder.build().toString();
    }

    /**
     * 获取默认音乐Url
     * @param s         搜索关键字
     * @param limit     返回条数
     * @param page      页数
     * @return
     */
    public String getMusicDefaultListUrl(String s, String limit,String page){
        String url = APIConstants.Urls.MUSIC_DEFAULT_DATA_URL;
        Uri.Builder builder = Uri.parse(url).buildUpon()
                .appendQueryParameter("showapi_appid",APIConstants.Keys.SHOWAPI_APP_ID)
                .appendQueryParameter("showapi_sign",APIConstants.Keys.SHOWAPI_APP_KEY)
                .appendQueryParameter("showapi_timestamp",getTimeStamp())
                .appendQueryParameter("s",s)
                .appendQueryParameter("p",page)
                .appendQueryParameter("limit",limit)
                .appendQueryParameter("key",MUSIC_KEY);
        return builder.build().toString();
    }

    /**
     * 获取QQ音乐排行Url
     * @param topId     排行Id
     * @return
     */
    public String getMusicQQTopListUrl(String topId){
        String url = APIConstants.Urls.MUSIC_QQ_TOP_URL;
        Uri.Builder builder = Uri.parse(url).buildUpon()
                .appendQueryParameter("showapi_appid",APIConstants.Keys.SHOWAPI_APP_ID)
                .appendQueryParameter("showapi_sign",APIConstants.Keys.SHOWAPI_APP_KEY)
                .appendQueryParameter("showapi_timestamp",getTimeStamp())
                .appendQueryParameter("topid",topId);
        return builder.build().toString();
    }

    /**
     * 获取QQ音乐内容Url,根据歌曲ID
     * @param musicid
     * @return
     */
    public String getMusicDataByIDUrl(String musicid){
        String url = APIConstants.Urls.MUSIC_QQ_ID_DATA_URL;
        Uri.Builder builder = Uri.parse(url).buildUpon()
                .appendQueryParameter("showapi_appid",APIConstants.Keys.SHOWAPI_APP_ID)
                .appendQueryParameter("showapi_sign",APIConstants.Keys.SHOWAPI_APP_KEY)
                .appendQueryParameter("showapi_timestamp",getTimeStamp())
                .appendQueryParameter("musicid",musicid);
        return builder.build().toString();
    }

    /**
     * 获取QQ音乐内容Url,根据歌名人名
     * @param keyword   搜索关键字
     * @param page      页数
     * @return
     */
    public String getMusicQQDataListUrl(String keyword, String page){
        String url = APIConstants.Urls.MUSIC_QQ_DATA_URL;
        Uri.Builder builder = Uri.parse(url).buildUpon()
                .appendQueryParameter("showapi_appid",APIConstants.Keys.SHOWAPI_APP_ID)
                .appendQueryParameter("showapi_sign",APIConstants.Keys.SHOWAPI_APP_KEY)
                .appendQueryParameter("showapi_timestamp",getTimeStamp())
                .appendQueryParameter("keyword",keyword)
                .appendQueryParameter("page",page);
        return builder.build().toString();
    }

    /**
     * 获取历史上的今天内容Url
     * @param date      时间
     * @return
     */
    public String getHistoryListUrl(String date){
        String url = APIConstants.Urls.READ_HISTORY_DATA_URL;
        Uri.Builder builder = Uri.parse(url).buildUpon()
                .appendQueryParameter("showapi_appid",APIConstants.Keys.SHOWAPI_APP_ID)
                .appendQueryParameter("showapi_sign",APIConstants.Keys.SHOWAPI_APP_KEY)
                .appendQueryParameter("showapi_timestamp",getTimeStamp())
                .appendQueryParameter("date",date);
        return builder.build().toString();
    }

    /**
     * 获取微信精选内涵Url
     * @param rand      指定随机获取，值默认为0不随机，1为随机
     * @param word      搜索关键字
     * @param page      页数
     * @return
     */
    public String getCharListUrl(String rand, String word, String page){
        String url = APIConstants.Urls.READ_CHAR_DATA_URL;
        Uri.Builder builder = Uri.parse(url).buildUpon()
                .appendQueryParameter("showapi_appid",APIConstants.Keys.SHOWAPI_APP_ID)
                .appendQueryParameter("showapi_sign",APIConstants.Keys.SHOWAPI_APP_KEY)
                .appendQueryParameter("showapi_timestamp",getTimeStamp())
                .appendQueryParameter("num",PAGER_NUM)
                .appendQueryParameter("rand",rand)
                .appendQueryParameter("page",page);
        if(null == word || "".equals(word)){
            return builder.build().toString();
        }else{
            builder.appendQueryParameter("word",word);
            return builder.build().toString();
        }

    }

    public String getTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }
}

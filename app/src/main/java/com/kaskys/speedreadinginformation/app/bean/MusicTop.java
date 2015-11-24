package com.kaskys.speedreadinginformation.app.bean;

import com.kaskys.speedreadinginformation.app.bean.base.BaseBean;

import java.util.List;

/**
 * Created by 卡你基巴 on 2015/11/9.
 */
public class MusicTop extends BaseBean{
    public Body showapi_res_body;
    public class Body{
        public String ret_code;
        public Bean pagebean;
        public class Bean{
            public String totalpage;
            public String ret_code;
            public List<Detail> songlist;
            public class Detail{
               public String albumid;           //专辑id
               public String downUrl;           //mp3下载链接
               public String seconds;           //
               public String singerid;          //歌手id
               public String singername;        //歌手名
               public String songid;             //歌曲id
               public String songname;           //歌曲名称
               public String url;                 //歌曲地址
            }
        }
    }
}

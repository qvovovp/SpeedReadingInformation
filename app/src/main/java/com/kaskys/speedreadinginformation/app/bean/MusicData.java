package com.kaskys.speedreadinginformation.app.bean;

import com.kaskys.speedreadinginformation.app.bean.base.BaseBean;

import java.util.List;

/**
 * Created by 卡你基巴 on 2015/11/9.
 */
public class MusicData extends BaseBean{
    public Body showapi_res_body;
    public class Body{
        public String ret_code;
        public Bean pagebean;
        public class Bean{
            public String allNum;              //所有数量
            public String allPages;            //所有页
            public String currentPage;         //当前页
            public String maxResult;           //每页最大数量
            public String notice;
            public String ret_code;
            public List<Detail> contentlist;
            public class Detail{
                public String albumid;          //专辑id
                public String albummid;         //
                public String albumname;        //专辑名称
                public String albumpic_big;     //专辑大图片，高宽300
                public String albumpic_small;   //专辑小图片，高宽90
                public String downUrl;          //mp3下载链接
                public String m4a;               //流媒体地址
                public String media_mid;        //
                public String singerid;         //歌手id
                public String singername;       //歌手名
                public String songid;           //歌曲id
                public String songmid;          //
                public String songname;         //歌曲名称
            }
        }
    }
}

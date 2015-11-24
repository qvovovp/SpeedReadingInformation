package com.kaskys.speedreadinginformation.app.bean;

import com.kaskys.speedreadinginformation.app.bean.base.BaseBean;

import java.util.List;

/**
 * Created by 卡你基巴 on 2015/11/9.
 */
public class NewsData extends BaseBean{
    public Body showapi_res_body;
    public class Body{
        public String ret_code;
        public Bean pagebean;
        public class Bean{
            public String allNum;              //所有数量
            public String allPages;            //所有页
            public String currentPage;         //当前页
            public String maxResult;           //每页最大数量
            public List<Detail> contentlist;   //条目列表
            public class Detail{
                public String channelId;        //频道id
                public String channelName;      //频道名称
                public String title;             //新闻标题
                public String desc;              //新闻简要描述
                public String link;              //新闻详情链接
                public String source;           //来源网站
                public String pubDate;          //发布时间
                public List<Img> imageurls;     //图片列表
                public class Img{
                    public String url;          //图片url
                    public String width;        //高度，像素
                    public String height;       //高度，像素
                }
            }
        }
    }
}

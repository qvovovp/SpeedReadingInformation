package com.kaskys.speedreadinginformation.app.bean;

import com.kaskys.speedreadinginformation.app.bean.base.BaseBean;

/**
 * Created by 卡你基巴 on 2015/11/9.
 */
public class MusicDetail extends BaseBean{
    public Body showapi_res_body;
    public class Body{
        public String lyric;
        public String lyric_txt;
        public String ret_code;
    }
}

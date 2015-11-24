package com.kaskys.speedreadinginformation.app.bean;

import com.kaskys.speedreadinginformation.app.bean.base.BaseBean;

import java.util.List;

/**
 * Created by 卡你基巴 on 2015/11/9.
 */
public class NewsAreaChanne extends BaseBean{
    public Body showapi_res_body;
    public class Body{
        public String ret_code;
        public String totalNum;
        public List<Bean> cityList;
        public class Bean{
            public String areaId;
            public String areaName;
        }
    }
}

package com.kaskys.speedreadinginformation.app.bean;

import com.kaskys.speedreadinginformation.app.bean.base.BaseBean;

import java.util.List;

/**
 * Created by 卡你基巴 on 2015/11/9.
 */
public class HistoryData extends BaseBean{
    public Body showapi_res_body;
    public class Body {
        public String ret_code;
        public List<Detail> list;
        public class Detail{
            public String title;    //标题
            public String img;      //图片
            public String year;     //年
            public String month;    //月
            public String day;      //日
        }
    }
}

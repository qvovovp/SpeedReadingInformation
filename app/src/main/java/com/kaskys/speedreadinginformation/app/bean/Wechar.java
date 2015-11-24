package com.kaskys.speedreadinginformation.app.bean;

import com.google.gson.annotations.SerializedName;
import com.kaskys.speedreadinginformation.app.bean.base.BaseBean;

import java.util.List;

/**
 * Created by 卡你基巴 on 2015/11/9.
 */
public class Wechar extends BaseBean{
    public Body showapi_res_body;
    public class Body {
        public String code;
        public String msg;
        @SerializedName("0")
        public Detail bean0;
        @SerializedName("1")
        public Detail bean1;
        @SerializedName("2")
        public Detail bean2;
        @SerializedName("3")
        public Detail bean3;
        @SerializedName("4")
        public Detail bean4;
        @SerializedName("5")
        public Detail bean5;
        @SerializedName("6")
        public Detail bean6;
        @SerializedName("7")
        public Detail bean7;
        @SerializedName("8")
        public Detail bean8;
        @SerializedName("9")
        public Detail bean9;
        public class Detail{
            public String description;  //描述
            public String hottime;      //时间
            public String picUrl;       //图片
            public String title;        //标题
            public String url;          //连接
        }
    }
}

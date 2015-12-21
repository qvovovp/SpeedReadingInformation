package com.kaskys.speedreadinginformation.app.bean;

import java.util.List;

/**
 * Created by 卡你基巴 on 2015/12/16.
 */
public class MusicBaiduTop {
    public String error_code;
    public Billboard billboard;
    public class Billboard{
        public String billboard_no;
        public String billboard_type;
        public String comment;
        public String havemore;
        public String name;
        public String pic_s210;
        public String pic_s260;
        public String pic_s444;
        public String pic_s640;
        public String update_date;
        public String web_url;
    }
    public List<Detail> song_list;
    public class Detail{
        public String album_id;
        public String album_no;
        public String album_title; //歌名
        public String all_artist_id;
        public String all_artist_ting_uid;
        public String all_rate;
        public String area;
        public String artist_id;
        public String artist_name;
        public String author;       //歌手名
        public String charge;
        public String copy_type;
        public String country;
        public String del_status;
        public String file_duration;
        public String has_mv;
        public String has_mv_mobile;
        public String havehigh;
        public String hot;
        public String is_first_publish;
        public String is_new;
        public String korean_bb_song;
        public String language;
        public String learn;
        public String lrclink;
        public String mv_provider;
        public String piao_id;
        public String pic_big;
        public String pic_small;
        public String publishtime;
        public String rank;
        public String rank_change;
        public String relate_status;
        public String resource_type;
        public String resource_type_ext;
        public String song_id;
        public String song_source;
        public String sound_effect;
        public String style;
        public String ting_uid;
        public String title;
        public String toneid;
    }
}

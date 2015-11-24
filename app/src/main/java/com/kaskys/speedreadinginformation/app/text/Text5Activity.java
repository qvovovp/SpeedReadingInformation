
package com.kaskys.speedreadinginformation.app.text;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kaskys.speedreadinginformation.app.R;
import com.kaskys.speedreadinginformation.app.bean.*;
import com.kaskys.speedreadinginformation.app.net.UrlManager;
import com.kaskys.speedreadinginformation.app.net.VolleyWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abc on 2015/11/9.
 */
public class Text5Activity extends ListActivity {
    private ListView mList;
    private MyAdapter mAdapter;

    private Handler mHander;
    private HistoryData mHistoryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = getListView();
        mHander = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println("hander.ok-->"+mHistoryData);
                if(mHistoryData != null){
                    mAdapter = new MyAdapter(Text5Activity.this,mHistoryData.showapi_res_body.list);
                    mList.setAdapter(mAdapter);
                }
            }
        };

        initData();
    }

    private void initData() {
        VolleyWrapper wrapper = new VolleyWrapper(Request.Method.GET, UrlManager.getInstance().getHistoryListUrl("0918"), HistoryData.class,
                new Response.Listener<HistoryData>() {
                    @Override
                    public void onResponse(HistoryData historyData) {
                        mHistoryData = historyData;
                        mHander.sendEmptyMessage(0);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
        wrapper.sendRequest();
    }

    class MyAdapter extends BaseAdapter{
        private Context mContext;
        private List<HistoryData.Body.Detail> mBeans;

        public MyAdapter(Context context, List<HistoryData.Body.Detail> beans){
            mContext = context;
            mBeans = beans;
        }

        @Override
        public int getCount() {
            return mBeans.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View newView = View.inflate(mContext, R.layout.listvew_text5,null);
//            ((TextView)newView.findViewById(R.id.tv_id)).setText(mBeans.get(i).title);
            ((TextView)newView.findViewById(R.id.tv_name)).setText(mBeans.get(i).year);
            return newView;
        }
    }
}

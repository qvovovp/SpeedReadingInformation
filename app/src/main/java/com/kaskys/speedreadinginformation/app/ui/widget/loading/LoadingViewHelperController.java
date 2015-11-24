package com.kaskys.speedreadinginformation.app.ui.widget.loading;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.kaskys.speedreadinginformation.app.R;

/**
 * Created by 卡你基巴 on 2015/11/7.
 */
public class LoadingViewHelperController {
    private ILoadingViewHelper helper;

    public LoadingViewHelperController(View view){
        this(new LoadingViewHelper(view));
    }

    public LoadingViewHelperController(ILoadingViewHelper helper){
        super();
        this.helper = helper;
    }

    public void showLoading(String msg){
        View layout = helper.inflate(R.layout.loading);
        if(!msg.isEmpty()){
            TextView textView = (TextView) layout.findViewById(R.id.loading_msg);
            textView.setText(msg);
        }
        helper.showLayout(layout);
    }

    public void showEmpty(String msg, View.OnClickListener onClickListener){
        View layout = helper.inflate(R.layout.message);
        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        if(!msg.isEmpty()){
            textView.setText(msg);
        }else{
            textView.setText(helper.getContext().getResources().getString(R.string.common_empty_msg));
        }

        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        imageView.setImageResource(R.mipmap.ic_exception);

        if(null != onClickListener){
            layout.setOnClickListener(onClickListener);
        }
        helper.showLayout(layout);
    }

    public void showError(String msg, View.OnClickListener onClickListener){
        View layout = helper.inflate(R.layout.message);
        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        if (!msg.isEmpty()) {
            textView.setText(msg);
        } else {
            textView.setText(helper.getContext().getResources().getString(R.string.common_empty_msg));
        }

        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        imageView.setImageResource(R.mipmap.ic_exception);

        if (null != onClickListener) {
            layout.setOnClickListener(onClickListener);
        }

        helper.showLayout(layout);
    }

    public void showNetwoekError(View.OnClickListener onClickListener){
        View layout = helper.inflate(R.layout.message);
        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        textView.setText(helper.getContext().getResources().getString(R.string.common_no_network_msg));

        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        imageView.setImageResource(R.mipmap.ic_exception);

        if(null != onClickListener){
            layout.setOnClickListener(onClickListener);
        }

        helper.showLayout(layout);
    }


    public void restore(){
        helper.restoreView();
    }
}

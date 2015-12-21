package com.kaskys.speedreadinginformation.app.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.kaskys.speedreadinginformation.app.R;
import com.kaskys.speedreadinginformation.app.presenter.Presenter;
import com.kaskys.speedreadinginformation.app.presenter.impl.NewsDetailPresenterImpl;
import com.kaskys.speedreadinginformation.app.ui.activity.base.BaseActivity;
import com.kaskys.speedreadinginformation.app.ui.view.NewsDetailView;
import com.kaskys.speedreadinginformation.app.ui.widget.MyProgressBar;

/**
 * Created by 卡你基巴 on 2015/11/14.
 */
public class NewsDetailActivity extends BaseActivity implements NewsDetailView{
    private WebView webContent;
    private MyProgressBar pbLoading;

    private Presenter mNewsDetailPresenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected int getMenuViewLayoutID() {
        return R.menu.menu_detail;
    }

    @Override
    protected View getLoadingTargetView() {
        return this.findViewById(R.id.detail_container);
    }

    @Override
    protected String getTitleName() {
        return "新闻详情";
    }

    @Override
    protected void initViewsAndEvents() {
        if(null != mToolbar){
            getSupportActionBar().setHomeAsUpIndicator(0);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        toggleWeaher(false);

        String url = getIntent().getExtras().getString("url");
        webContent = (WebView) this.findViewById(R.id.web_container);
        pbLoading = (MyProgressBar) this.findViewById(R.id.pb_bar);

        mNewsDetailPresenter = new NewsDetailPresenterImpl(this,this);
        mNewsDetailPresenter.initialized();

        webContent.loadUrl(url);
        webContent.getSettings().setJavaScriptEnabled(true);
        webContent.setWebViewClient(new WebViewClient(){

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                toggleShowLoading(false,"");
                pbLoading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbLoading.setVisibility(View.GONE);
            }
        });
        toggleShowLoading(true,"正在加载...");
        webContent.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pbLoading.setProgerss(newProgress);
            }
        });
    }

    @Override
    protected void onActivityDestroy() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_size:
                showSizeChange();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private int mCurrentItem = 2;
    private int mCurrentChooseItem;
    private String[] items;

    private void showSizeChange() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择文字大小");
        builder.setSingleChoiceItems(items, mCurrentItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mCurrentChooseItem = i;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                WebSettings set = webContent.getSettings();
                switch (mCurrentChooseItem) {
                    case 0:
                        set.setTextSize(WebSettings.TextSize.LARGEST);
                        break;
                    case 1:
                        set.setTextSize(WebSettings.TextSize.LARGER);
                        break;
                    case 2:
                        set.setTextSize(WebSettings.TextSize.NORMAL);
                        break;
                    case 3:
                        set.setTextSize(WebSettings.TextSize.SMALLER);
                        break;
                    case 4:
                        set.setTextSize(WebSettings.TextSize.SMALLEST);
                        break;
                }

                mCurrentItem = mCurrentChooseItem;
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    @Override
    public void initializeSizeData(String[] items) {
        this.items = items;
    }
}

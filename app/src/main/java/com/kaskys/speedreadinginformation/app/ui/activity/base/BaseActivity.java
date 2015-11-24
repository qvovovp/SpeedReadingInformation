package com.kaskys.speedreadinginformation.app.ui.activity.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.kaskys.speedreadinginformation.app.R;
import com.kaskys.speedreadinginformation.app.ui.widget.loading.LoadingViewHelperController;

/**
 * Created 卡你基巴 abc on 2015/11/6.
 */
public abstract class BaseActivity extends AppCompatActivity{

    protected Toolbar mToolbar;
    protected TextView mBarTitle;
    protected LoadingViewHelperController mLoadingViewHelperController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(0 != getContentViewLayoutID()){
            setContentView(getContentViewLayoutID());
        }else{
            throw new IllegalArgumentException("You must return a contentView layout resource Id");
        }
        setTranslucentStatus(true);
        initViewsAndEvents();
    }

    public void setContentView(int layoutResID){
        super.setContentView(layoutResID);
        mToolbar = (Toolbar) this.findViewById(R.id.toolbar);
        mBarTitle = (TextView) this.findViewById(R.id.bar_title);
        if(null != mToolbar){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_weather);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if(null != getTitleName()){
                mBarTitle.setText(getTitleName());
            }
        }
        if(null != getLoadingTargetView()){
            mLoadingViewHelperController = new LoadingViewHelperController(getLoadingTargetView());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(0 != getMenuViewLayoutID()){
            setMenuView(menu,getMenuViewLayoutID());
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void setMenuView(Menu menu, int menuResID) {
        getMenuInflater().inflate(menuResID,menu);
    }

    public void readyGo(Class<?> clazz){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }

    public void readyGo(Class<?> clazz, int enterAnim, int exitAnim){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
        overridePendingTransition(enterAnim,exitAnim);
    }

    public void readyGO(Class<?> clazz, Bundle bundle){
        Intent intent = new Intent(this,clazz);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void readyGo(Class<?> clazz, Bundle bundle, int enterAnim, int exitAnim){
        Intent intent = new Intent(this,clazz);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(enterAnim,exitAnim);
    }

    public void readyGoKill(Class<?> clazz){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
        finish();
    }

    public void readyGoKill(Class<?> clazz, int enterAnim, int exitAnim){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
        overridePendingTransition(enterAnim,exitAnim);
        finish();
    }

    public void readyGoKill(Class<?> clazz, Bundle bundle){
        Intent intent = new Intent(this,clazz);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    public void readyGoKill(Class<?> clazz, Bundle bundle, int enterAnim, int exitAnim){
        Intent intent = new Intent(this,clazz);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(enterAnim,exitAnim);
        finish();
    }

    public void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    public void readyGoForResult(Class<?> clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, clazz);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    protected void showToast(String msg) {
        //防止遮盖虚拟按键
        if (null != msg && !msg.isEmpty()) {
            Snackbar.make(getLoadingTargetView(), msg, Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * 是否显示加载
     * @param toggle
     * @param msg
     */
    protected void toggleShowLoading(boolean toggle, String msg){
        if(mLoadingViewHelperController == null){
            throw new IllegalArgumentException("You must return a target view for loading");
        }
        if(toggle){
            mLoadingViewHelperController.showLoading(msg);
        }else{
            mLoadingViewHelperController.restore();
        }
    }

    protected void toggleShowEmpty(boolean toggle, String msg, View.OnClickListener onClickListener){
        if(mLoadingViewHelperController == null){
            throw new IllegalArgumentException("You must return a target view for loading");
        }
        if(toggle){
            mLoadingViewHelperController.showEmpty(msg, onClickListener);
        }else{
            mLoadingViewHelperController.restore();
        }
    }

    /**
     * 是否显示错误信息
     * @param toggle
     * @param msg
     */
    protected void toggleShowError(boolean toggle, String msg, View.OnClickListener onClickListener){
        if(mLoadingViewHelperController == null){
            throw new IllegalArgumentException("You must return a target view for loading");
        }
        if(toggle){
            mLoadingViewHelperController.showError(msg, onClickListener);
        }else{
            mLoadingViewHelperController.restore();
        }
    }

    /**
     * 是否显示网络错误信息
     * @param toggle
     * @param onClickListener
     */
    protected void toggleNetworkError(boolean toggle, View.OnClickListener onClickListener){
        if(mLoadingViewHelperController == null){
            throw new IllegalArgumentException("You must return a target view for loading");
        }
        if(toggle){
            mLoadingViewHelperController.showNetwoekError(onClickListener);
        }else{
            mLoadingViewHelperController.restore();
        }
    }

    protected  void setTranslucentStatus(boolean on){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window win = getWindow();
            WindowManager.LayoutParams params = win.getAttributes();
            int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if(on){
                params.flags |= bits;
            }else{
                params.flags &= ~bits;
            }
            win.setAttributes(params);
        }
    }

    protected abstract int getContentViewLayoutID();
    protected abstract int getMenuViewLayoutID();
    protected abstract View getLoadingTargetView();
    protected abstract String getTitleName();
    protected abstract void initViewsAndEvents();
}


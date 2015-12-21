package com.kaskys.speedreadinginformation.app.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.kaskys.speedreadinginformation.app.R;
import com.kaskys.speedreadinginformation.app.bean.MusicBaiduTop;
import com.kaskys.speedreadinginformation.app.presenter.Presenter;
import com.kaskys.speedreadinginformation.app.presenter.impl.MusicSeachPresenterImpl;
import com.kaskys.speedreadinginformation.app.ui.activity.base.BaseActivity;
import com.kaskys.speedreadinginformation.app.ui.adapter.MusicSeachAdapter;
import com.kaskys.speedreadinginformation.app.ui.adapter.MusicSeachListAdapter;
import com.kaskys.speedreadinginformation.app.ui.service.MusicService;
import com.kaskys.speedreadinginformation.app.ui.view.MusicSeachView;
import com.kaskys.speedreadinginformation.app.ui.widget.ExpandListView;

import java.util.List;

/**
 * Created by 卡你基巴 on 2015/12/15.
 */
public class MusicSeachActivity extends BaseActivity implements MusicSeachView{
    public static final int SEACCH_TYPE_TOP = 0x1;
    public static final int SEACCH_TYPE_DETAIIL = 0x2;

    private ExpandListView mListView;
    private MusicSeachListAdapter mSeachAdapter;
    private ImageView mHeaderImg;
    private TextView mHeaderTextComment;
    private TextView mHeaderTextName;
    private TextView mHeaderTextUpdate;
    private TextView mHeaderTextNumber;

    private Presenter mMusicSeachPresenter;

    private MusicService mMusicService;
    private boolean mBind;
    private boolean isSetupData,isSetupHeaderImg;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_music_seach;
    }

    @Override
    protected int getMenuViewLayoutID() {
        return 0;
    }

    @Override
    protected View getLoadingTargetView() {
        return this.findViewById(R.id.seach_container);
    }

    @Override
    protected String getTitleName() {
        return getIntent().getExtras().getString(HomeActivity.MUSIC_SEACH_TITLE);
    }

    @Override
    protected void initViewsAndEvents() {
        if(null != mToolbar){
            getSupportActionBar().setHomeAsUpIndicator(0);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        toggleWeaher(false);

        if(!mBind){
            bindService(new Intent(this, MusicService.class),connection, Context.BIND_AUTO_CREATE);
        }

        mListView = (ExpandListView) this.findViewById(R.id.list_container);
        mListView.setHeaderView(R.layout.activity_music_seach_top);

        mHeaderImg = (ImageView) mListView.getHeaderView().findViewById(R.id.music_seach_header_img);
        mHeaderTextComment = (TextView) mListView.getHeaderView().findViewById(R.id.music_seach_header_comment);
        mHeaderTextName = (TextView) mListView.getHeaderView().findViewById(R.id.music_seach_header_name);
        mHeaderTextUpdate = (TextView) mListView.getHeaderView().findViewById(R.id.music_seach_header_update);
        mHeaderTextNumber = (TextView) mListView.getHeaderView().findViewById(R.id.music_seach_header_number);

        mMusicSeachPresenter = new MusicSeachPresenterImpl(this,this,
                getIntent().getExtras().getString(HomeActivity.MUSIC_SEACH_KEY),
                getIntent().getExtras().getInt(HomeActivity.MUSIC_SEACH_TYPE));
        mMusicSeachPresenter.initialized();

        toggleShowLoading(true,"正在加载数据...");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            overridePendingTransition(0,R.anim.activity_music_seach_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityDestroy() {
        if(mBind){
            unbindService(connection);
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBind = true;
            mMusicService =  ((MusicService.MusicBinder)iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBind = false;
        }
    };

    @Override
    public void setupData(List<MusicBaiduTop.Detail> musics) {
        if(null == musics){
            toggleShowError(true,"网络异常...",new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    toggleShowLoading(true,"正在加载数据...");
                    mMusicSeachPresenter.initialized();
                }
            });
        }else{
            isSetupData = true;
            if(isSetupHeaderImg){
                toggleShowLoading(false,"");
            }
            if(null == mSeachAdapter){
                mSeachAdapter = new MusicSeachListAdapter(this,musics);
//                mSeachAdapter.setOnItemClickListener(new OnClickListener());
                mListView.setAdapter(mSeachAdapter);
            }else if(null != mSeachAdapter){
                mSeachAdapter.clear();
                mSeachAdapter.addAll(musics);
                mSeachAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void setupHeaderImg(Bitmap bitmap) {
        isSetupHeaderImg = true;
        if(isSetupData){
            toggleShowLoading(false,"");
        }
        mHeaderImg.setImageBitmap(bitmap);
    }

    @Override
    public void setupHeaderData(MusicBaiduTop.Billboard billboard) {
        mHeaderTextComment.setText(billboard.comment);
        mHeaderTextName.setText(billboard.name);
        mHeaderTextUpdate.setText("最近更新:"+billboard.update_date);
        mHeaderTextNumber.setText("编号:"+billboard.billboard_no);
    }

    /**
     * 点击事件监听
     */
    private class OnClickListener implements MusicSeachAdapter.OnItemClickListener {
        @Override
        public void onItemClick(View view, int position) {
            if(null != mSeachAdapter){

            }
        }
    }
}

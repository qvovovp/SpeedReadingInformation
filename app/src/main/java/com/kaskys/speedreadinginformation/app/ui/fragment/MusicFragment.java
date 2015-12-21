package com.kaskys.speedreadinginformation.app.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import com.kaskys.speedreadinginformation.app.R;
import com.kaskys.speedreadinginformation.app.bean.MusicType;
import com.kaskys.speedreadinginformation.app.presenter.Presenter;
import com.kaskys.speedreadinginformation.app.presenter.impl.MusicPresenterImpl;
import com.kaskys.speedreadinginformation.app.ui.activity.HomeActivity;
import com.kaskys.speedreadinginformation.app.ui.activity.MusicSeachActivity;
import com.kaskys.speedreadinginformation.app.ui.adapter.MusicTopListAdapter;
import com.kaskys.speedreadinginformation.app.ui.fragment.base.BaseFragment;
import com.kaskys.speedreadinginformation.app.ui.service.MusicService;
import com.kaskys.speedreadinginformation.app.ui.view.MusicView;
import com.kaskys.speedreadinginformation.app.ui.widget.IPlayButton;
import com.kaskys.speedreadinginformation.app.ui.widget.circle.IRotateCircle;
import com.kaskys.speedreadinginformation.app.ui.widget.circle.RoundImageView;

import java.util.List;

/**
 * Created by 卡你基巴 on 2015/11/9.
 */
public class MusicFragment extends BaseFragment implements MusicView,View.OnClickListener,MusicService.OnPlaybackListener,IPlayButton.IPlayOnClickListener{
    private static final String DEFAUL_MUSIC_NAME = "http://tsmusic24.tc.qq.com/100142.mp3";

    private GridView mGridView;
    private RoundImageView mRoundView;
    private IRotateCircle mRotateView;
    private ImageButton mPreBtn,mNextBtn;
    private IPlayButton mPlayBtn;

    private Presenter mMusicContainerPresenter;

    private MusicService mMusicService;
    private Handler mHander = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mRoundView.setProgerss(mMusicService.getPlayCurrentPosition());
            mHander.sendEmptyMessageDelayed(1,100);
        }
    };

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_music;
    }

    @Override
    protected View getLoadingTargetView() {
        return mRootView.findViewById(R.id.container);
    }

    @Override
    protected void initViewsAndEvents() {
        mGridView = (GridView) mRootView.findViewById(R.id.grid);
        mRoundView = (RoundImageView) mRootView.findViewById(R.id.round_img);
        mRotateView = (IRotateCircle) mRootView.findViewById(R.id.rotate_circle);

        mPreBtn = (ImageButton) mRootView.findViewById(R.id.music_pre);
        mPlayBtn = (IPlayButton) mRootView.findViewById(R.id.music_play_btn);
        mNextBtn = (ImageButton) mRootView.findViewById(R.id.music_next);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putInt(HomeActivity.MUSIC_SEACH_TYPE, MusicSeachActivity.SEACCH_TYPE_TOP);
                bundle.putString(HomeActivity.MUSIC_SEACH_KEY,String.valueOf(getResources().getIntArray(R.array.music_top_id_list)[i]));
                bundle.putString(HomeActivity.MUSIC_SEACH_TITLE,getResources().getStringArray(R.array.music_top_name_list)[i]);
                ((HomeActivity)mContext).readyGo(MusicSeachActivity.class,bundle,R.anim.activity_music_seach_in,0);
            }
        });

        mPreBtn.setOnClickListener(this);
        mNextBtn.setOnClickListener(this);
        mPlayBtn.setListener(this);
    }

    @Override
    protected void onFirstUserVisible() {
        mMusicContainerPresenter = new MusicPresenterImpl(mContext,this);
        mMusicContainerPresenter.initialized();
        ((HomeActivity)mContext).setMusicView(this);
    }



    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    public void initializeGridDatas(List<MusicType> typeList) {
        if(null != typeList && typeList.size() > 0){
            MusicTopListAdapter mAdapter = new MusicTopListAdapter(mContext,typeList);
            mGridView.setAdapter(mAdapter);
        }
    }

    @Override
    public void playMusic() {
        startPlayMusic();
    }

    @Override
    public void pauseMusic() {
        pausePlayMusic();
    }

    private void startPlayMusic() {
        if(null == mMusicService){
            mMusicService = ((HomeActivity)mContext).getMusicService();
            mMusicService.setOnPlaybackListener(this);
        }

        ((HomeActivity)mContext).IViewStart();
        mRoundView.playM();
        mRotateView.startM();

        if(mMusicService.getPalyState() == MusicService.STATE_PAUSED){
            mMusicService.resurtStartMusic();
        }else {
            mMusicService.startMusic(((MusicPresenterImpl) mMusicContainerPresenter).getMusicUri(DEFAUL_MUSIC_NAME));
        }
    }

    private void pausePlayMusic() {
        ((HomeActivity)mContext).IViewStop();
        mRoundView.stopM();
        mRotateView.stopM();

        mMusicService.pauseMusic();
    }

    @Override
    public void onPlayMusic() {
        startPlayMusic();
    }

    @Override
    public void onPauseMusic() {
        pausePlayMusic();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onStateChanged(Uri uri, int state) {
        switch (state){
            case MusicService.STATE_IDLE:
                mHander.removeCallbacksAndMessages(null);
                break;
            case MusicService.STATE_INITIALIZED:
                break;
            case MusicService.STATE_PREPARING:
                break;
            case MusicService.STATE_PREPARED:
                break;
            case MusicService.STATE_STARTED:
                mHander.sendEmptyMessageDelayed(1,100);
                break;
            case MusicService.STATE_STOPPED:
                break;
            case MusicService.STATE_COMPLETED:
                mHander.removeCallbacksAndMessages(null);
                mRoundView.reset();
//                stopPlayMusic();
                break;
            case MusicService.STATE_END:
                break;
            case MusicService.STATE_ERROR:
                mHander.removeCallbacksAndMessages(null);
                break;
        }
    }

    @Override
    public void onPlayDuration(int value) {
        mRoundView.setMax(value);
    }

    @Override
    public void onBufferingUpdata(int percent) {
        mRoundView.setCacheProgress(percent);
    }

}

package com.kaskys.speedreadinginformation.app.ui.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnTimedTextListener;
import android.media.TimedText;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.provider.MediaStore.Audio;

public class MusicService extends Service implements OnErrorListener,OnPreparedListener,OnCompletionListener,OnBufferingUpdateListener,OnInfoListener{
	public static final int STATE_IDLE = 0;      		//空闲
	public static final int STATE_INITIALIZED = 1;    //初始化
	public static final int STATE_PREPARED = 2;     	//装载好
	public static final int STATE_PREPARING = 3;      //装载中
	public static final int STATE_STARTED = 4;      	//开始
	public static final int STATE_PAUSED = 5;     	 	//暂停
	public static final int STATE_STOPPED = 6;       	//停止
	public static final int STATE_COMPLETED = 7;     	//播放完
	public static final int STATE_END = -1;      		//结束
	public static final int STATE_ERROR = -2;  	  	//错误

	private static final int MEDIA_INFO_NETWORK = 703;

	private MediaPlayer mPlay;
	private int mCurrentState;
	private Uri mUri;
	
	private OnPlaybackListener mPlaybackListener;
	
	@Override
	public IBinder onBind(Intent intent) {
		return new MusicBinder(this);
	}
	
	public void startMusic(Uri uri){
		mUri = uri;
		init();
		try{
			if(mCurrentState == STATE_IDLE){
				mPlay.setDataSource(this,uri);
			}
			changeState(STATE_INITIALIZED);
			if(mCurrentState != STATE_ERROR){
				mPlay.setAudioStreamType(AudioManager.STREAM_MUSIC);
			}
			if(mCurrentState == STATE_INITIALIZED || mCurrentState == STATE_STOPPED){
				mPlay.prepareAsync();
				changeState(STATE_PREPARING);
			}
		}catch(Exception e){
			
		}
	}
	
	public void pauseMusic(){
		if(mCurrentState == STATE_STARTED){
			mPlay.pause();
			changeState(STATE_PAUSED);
		}
	}

	public void resurtStartMusic(){
		if(mCurrentState == STATE_PAUSED && !mPlay.isPlaying()){
			mPlay.start();
			changeState(STATE_STARTED);
		}
	}

	public void preMusic(){
		
	}
	
	public void nextMusic(){
		
	}

	public int getPalyState(){
		return mCurrentState;
	}

	private void init(){
		if(mPlay == null){
			mPlay = new MediaPlayer();
			changeState(STATE_IDLE);
		}else{
			if(mCurrentState == STATE_IDLE || mCurrentState == STATE_INITIALIZED || mCurrentState == STATE_PREPARED
					|| mCurrentState == STATE_STARTED || mCurrentState == STATE_PAUSED
					|| mCurrentState == STATE_STOPPED || mCurrentState == STATE_COMPLETED
					|| mCurrentState == STATE_ERROR){
				mPlay.reset();
				changeState(STATE_IDLE);
			}
		}
		
		mPlay.setOnErrorListener(this);
		mPlay.setOnPreparedListener(this);
		mPlay.setOnCompletionListener(this);
		mPlay.setOnBufferingUpdateListener(this);
		mPlay.setOnInfoListener(this);
	}
	
	public int getPlayCurrentPosition(){
		return mPlay.getCurrentPosition();
	}
	
	private void changeState(int state) {
		mCurrentState = state;
		if(mPlaybackListener != null){
			mPlaybackListener.onStateChanged(mUri, mCurrentState);
		}
	}

	public static class MusicBinder extends Binder{
		private MusicService service = null;
		
		public MusicBinder(MusicService service){
			this.service = service;
		}
		
		public MusicService getService(){
			return this.service;
		}
	}
	
	public void setOnPlaybackListener(OnPlaybackListener listener){
		this.mPlaybackListener = listener;
	}
	
    public interface OnPlaybackListener {
        void onStateChanged(Uri uri, int state);
        void onPlayDuration(int value);
		void onBufferingUpdata(int percent);
    }
	
	@Override
	public void onCompletion(MediaPlayer mp) {
		changeState(STATE_COMPLETED);
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		doStart();
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void doStart() {
		System.out.println("start=->"+mUri.toString());
		mPlay.start();
		changeState(STATE_STARTED);
		if(mPlaybackListener != null){
			mPlaybackListener.onPlayDuration(mPlay.getDuration());
		}
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		if(mPlaybackListener != null){
			mPlaybackListener.onBufferingUpdata(percent);
		}
	}

	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		if(what == MEDIA_INFO_NETWORK){
			mPlay.reset();
			changeState(STATE_IDLE);
		}
		return true;
	}
}

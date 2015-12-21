package com.kaskys.speedreadinginformation.app.ui.widget.circle;

import android.os.SystemClock;

public class IAcceleratedSpeed {
	private final static float DETALUE_MULTIPLE = 1000f;
	
	private float a; 
	private int startX;
	private int distanceX;
	private long startTime;
	private boolean isFinish;
	
	private float initialV;
	
	private int duration;
	private float currX;
	
	public float getCurrX(){
		return currX;
	}
	
	public boolean isFinish(){
		return isFinish;
	}

	public void setDuration(int duration){
		this.duration = duration;
	}

	public void startScroller(int startX, int distanceX,float initialV) {
		this.startX = startX;
		this.distanceX = distanceX;
		this.initialV = initialV;
		this.startTime = SystemClock.uptimeMillis();
		this.a = (2 * ((distanceX - startX) - (initialV * (duration/DETALUE_MULTIPLE)))) / 
				((duration/DETALUE_MULTIPLE)*(duration/DETALUE_MULTIPLE)); 
		isFinish = false;
	}
	
	public boolean computeScrollOffset(){
		if(isFinish){
			return false;
		}
		
		long passTime = SystemClock.uptimeMillis() - startTime;
		if(passTime<duration){
			currX = startX + (initialV*(passTime/DETALUE_MULTIPLE)) + 
					((a*(passTime/DETALUE_MULTIPLE)*(passTime/DETALUE_MULTIPLE))/2);
		}else{
			currX = startX + distanceX;
			isFinish = true;
		}
		return true;
	}
}

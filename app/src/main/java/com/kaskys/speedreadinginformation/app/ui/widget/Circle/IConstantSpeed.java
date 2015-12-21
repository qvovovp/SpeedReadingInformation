package com.kaskys.speedreadinginformation.app.ui.widget.circle;

import android.os.SystemClock;

public class IConstantSpeed {
	private int startX;
	private int distanceX;
	private long startTime;
	private boolean isFinish;
	
	//CustomView --> 150;
	private int duration;
	private float currX;
	
	public float getCurrX(){
		return currX;
	}
	
	public boolean isFinish(){
		return isFinish;
	}
	
	public float getV(){
		return distanceX/(duration/1000f);
	}

	public void setDuration(int duration){
		this.duration = duration;
	}

	public void startScroller(int startX, int distanceX) {
		this.startX = startX;
		this.distanceX = distanceX;
		this.startTime = SystemClock.uptimeMillis();
		isFinish = false;
	}
	
	public boolean computeScrollOffset(){
		if(isFinish){
			return false;
		}
		
		long passTime = SystemClock.uptimeMillis() - startTime;
		if(passTime<duration){
			currX = startX + distanceX*passTime*1.0f/duration;
		}else{
			currX = startX + distanceX;
			isFinish = true;
		}
		return true;
	}
}

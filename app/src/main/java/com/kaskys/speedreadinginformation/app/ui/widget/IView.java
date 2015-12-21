package com.kaskys.speedreadinginformation.app.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class IView extends View{
	private Paint mPaint;
	private Path mOnePath,mTwoPath,mThreePath,mFourPath;
	private int mWidth,mHeight;
	
	private float mStartHeight1,mStartHeight2,mStartHeight3,mStartHeight4;
	private float minHeight,maxHeight;
	
	private int mImgWidth;
	private int mTWidth;
	private float addHeight;
	
	private boolean isPlay,isStartThread;
	private boolean isAdd;
	
	public IView(Context context) {
		this(context,null);
	}
	
	public IView(Context context, AttributeSet attrs) {
		this(context,attrs,0);
	}
	
	public IView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.GREEN);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setStrokeWidth(1.0f);
		
		mOnePath = new Path();
		mTwoPath = new Path();
		mThreePath = new Path();
		mFourPath = new Path();
		
		isPlay = false;
		isStartThread = false;
		isAdd = true;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mWidth = this.getMeasuredWidth();
		mHeight = this.getMeasuredHeight();

		initLayout();
		initPath();
	}

	private void initLayout() {
		mImgWidth = mWidth / 8;
		mTWidth = (mWidth - mImgWidth) / 3;
		
		minHeight = 3*mHeight*1.0f/4;
		maxHeight = mHeight*1.0f/6;
		
		addHeight = mHeight*1.0f/200;
		
		mStartHeight1 = 3*mHeight*1.0f/5;
		mStartHeight2 = maxHeight;
		mStartHeight3 = minHeight;
		mStartHeight4 = mHeight*1.0f/3;
	}

	private void initPath() {		
		if(!mOnePath.isEmpty()){
			mOnePath.reset();
		}
		if(!mTwoPath.isEmpty()){
			mTwoPath.reset();
		}
		if(!mThreePath.isEmpty()){
			mThreePath.reset();	
		}
		if(!mFourPath.isEmpty()){
			mFourPath.reset();	
		}
		
		mOnePath.moveTo(0, mHeight);
		mOnePath.lineTo(0, mStartHeight1);
		mOnePath.lineTo(mImgWidth,mStartHeight1);
		mOnePath.lineTo(mImgWidth, mHeight);
		mOnePath.close();
		
		mTwoPath.moveTo(mTWidth, mHeight);
		mTwoPath.lineTo(mTWidth, mStartHeight2);
		mTwoPath.lineTo(mTWidth+mImgWidth,mStartHeight2);
		mTwoPath.lineTo(mTWidth+mImgWidth, mHeight);
		mTwoPath.close();
		
		mThreePath.moveTo(2*mTWidth, mHeight);
		mThreePath.lineTo(2*mTWidth, mStartHeight3);
		mThreePath.lineTo((2*mTWidth)+mImgWidth,mStartHeight3);
		mThreePath.lineTo((2*mTWidth)+mImgWidth, mHeight);
		mThreePath.close();
		
		mFourPath.moveTo(3*mTWidth, mHeight);
		mFourPath.lineTo(3*mTWidth, mStartHeight4);
		mFourPath.lineTo((3*mTWidth)+mImgWidth,mStartHeight4);
		mFourPath.lineTo((3*mTWidth)+mImgWidth, mHeight);
		mFourPath.close();
	}
	
	public void startM(){
		isPlay = true;
		if(!isStartThread){
			new Thread(new Runnable() {
				public void run() {
					isStartThread = true;
					while(isPlay){
						try {
							addHeight();
							postInvalidate();
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
	}
	
	public void stopM(){
		isPlay = false;
		isStartThread  = false;
		initLayout();
		initPath();
		invalidate();
	}
	
	private void addHeight(){
		if(isAdd){
			mStartHeight1 = mStartHeight1 - addHeight;
			mStartHeight3 = mStartHeight3 - addHeight;
			mStartHeight2 = mStartHeight2 + addHeight;
			mStartHeight4 = mStartHeight4 + addHeight;
			if(mStartHeight4 > minHeight || mStartHeight1 < maxHeight){
				isAdd = false;
			}
		}else{
			mStartHeight1 = mStartHeight1 + addHeight;
			mStartHeight3 = mStartHeight3 + addHeight;
			mStartHeight2 = mStartHeight2 - addHeight;
			mStartHeight4 = mStartHeight4 - addHeight;	
			if(mStartHeight2 < maxHeight || mStartHeight3 > minHeight){
				isAdd = true;
			}
		}
		initPath();
	}
	
	protected void onDraw(Canvas canvas) {
		canvas.drawPath(mOnePath, mPaint);
		canvas.drawPath(mTwoPath, mPaint);
		canvas.drawPath(mThreePath, mPaint);
		canvas.drawPath(mFourPath, mPaint);
	}
	
	@Override
	public void setVisibility(int visibility) {
		super.setVisibility(visibility);
		if(visibility == View.INVISIBLE || visibility == View.GONE){
			this.stopM();
		}
	}
	
	public boolean isStart(){
		return isPlay;
	}
}

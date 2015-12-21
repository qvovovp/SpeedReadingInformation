package com.kaskys.speedreadinginformation.app.ui.widget.circle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

public class IRotateCircle extends View{
	private int mCircleNumber = 6;
	
	private Paint[] mPaints;
	private int[] colors = new int[]{Color.RED,Color.BLUE,Color.YELLOW,Color.GREEN,Color.GRAY,Color.BLACK};
	private int[] bagColors = new int[]{0x33ff0000,0x3300ff00,0x33ffff00,0x330000ff,0x3300ffff,0x33ff00ff};
	//旋转圆的半径
	private int mSmallCircleRadius = 10;

	//内切椭圆里的最大轴，正方形的半径
	private float mInCircleBigRadius;
	//内切椭圆里的最小轴
	private float mInCircleShortRadius;
	//内切椭圆里的最大和最小轴的差值
	private float mInCircleDiffRadius;

	//旋转圆的坐标
	private float[][] mSmallCircleCoord;
	//空间的宽高
	private int mWidth,mHeidht;
	//内切圆的上、左、右、下
	private int mCircleTop,mCircleLeft,mCircleRight,mCircleBottom;
	//内切圆的中心坐标
	private float mInCircleMiddleX,mInCircleMiddleY;

	//内切圆的周长
	private double mInCircleGirth;
	private Type mType;

	private boolean[] mThreads;
	private boolean[] mSpeedThreads;
	private boolean isPlay;

	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what == -1){
				setBackgroundColor(getResources().getColor(android.R.color.transparent));
				mHandler.removeCallbacksAndMessages(null);
			}else {
				setBackgroundColor(bagColors[msg.what]);
			}
		}
	};

	private enum Type{
		CIRCLE,ELLIPSEWIDTH,ELLIPSEHEIDHT;
	}
	
	public IRotateCircle(Context context) {
		this(context,null);
	}
	
	public IRotateCircle(Context context, AttributeSet attrs) {
		this(context,attrs,0);
	}
	
	public IRotateCircle(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		mThreads = new boolean[mCircleNumber];
		mSpeedThreads = new boolean[mCircleNumber];
		mPaints = new Paint[mCircleNumber];
		mSmallCircleCoord = new float[mCircleNumber][2];
		
		isPlay = false;
		
		for(int i=0;i<mCircleNumber;i++){
			Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mPaint.setStyle(Paint.Style.FILL);
			mPaint.setColor(colors[i]);
			mPaints[i] = mPaint;
			
			mThreads[i] = false;
			mSpeedThreads[i] = false;
		}
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mWidth = getMeasuredWidth();
		mHeidht = getMeasuredHeight();
		
		if(mWidth > 0 || mHeidht > 0){
			mCircleLeft = mSmallCircleRadius;
			mCircleTop = mSmallCircleRadius;
			mCircleRight = mWidth - mSmallCircleRadius;
			mCircleBottom = mHeidht - mSmallCircleRadius;
			
			mInCircleMiddleX = (mWidth * 1.0f / 2) - mCircleLeft;
			mInCircleMiddleY = (mHeidht * 1.0f / 2) - mCircleTop;
			
			if(mWidth == mHeidht){
				initCircle();
			}else{
				initEllipse();
			}
		}
	}

	/**
	 * 正方形
	 */
	private void initCircle() {
		mInCircleBigRadius = (mCircleRight - mCircleLeft + 0.0f) / 2;
		
		for(int i=0;i<mSmallCircleCoord.length;i++){
			mSmallCircleCoord[i][0] = mCircleRight * 1.0f / 2 + (mSmallCircleRadius / 2);
			mSmallCircleCoord[i][1] = mCircleTop * 1.0f;
		}
		mType = Type.CIRCLE;
		mInCircleGirth = 2 * mInCircleBigRadius * Math.PI;
	}

	/**
	 * 长方形
	 */
	private void initEllipse() {
		if(mWidth > mHeidht){
			mInCircleBigRadius = (mCircleRight - mCircleLeft + 0.0f) / 2;
			mInCircleShortRadius = (mCircleBottom - mCircleTop + 0.0f) / 2;
			
			mType = Type.ELLIPSEWIDTH;
			mInCircleGirth = 2 * Math.PI * mInCircleBigRadius + 4 * (mInCircleBigRadius - mInCircleShortRadius);
		}else{
			mInCircleBigRadius = (mCircleBottom - mCircleTop + 0.0f) / 2;
			mInCircleShortRadius = (mCircleRight - mCircleLeft + 0.0f) / 2;
			
			mType = Type.ELLIPSEHEIDHT;
			mInCircleGirth = 2 * Math.PI * mInCircleBigRadius + 4 * (mInCircleBigRadius - mInCircleShortRadius);
		}
		mInCircleDiffRadius = mInCircleBigRadius - mInCircleShortRadius;
		for(int i=0;i<mSmallCircleCoord.length;i++){
			mSmallCircleCoord[i][0] = mCircleRight * 1.0f / 2 + (mSmallCircleRadius / 2);
			mSmallCircleCoord[i][1] = mCircleTop * 1.0f;
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for(int i=0;i<mCircleNumber;i++){
			canvas.drawCircle(mSmallCircleCoord[i][0], mSmallCircleCoord[i][1], mSmallCircleRadius, mPaints[i]);	
		}
	}

	public void startM(){
		if(!isPlay){
			isPlay = true;
			startThread(0);
		}
	}

	public void stopM(){
		if(isPlay){
			isPlay = false;
		}
	}

	private void startSpeedThread(final float v,final int number){
		if(!mSpeedThreads[number]){
			mSpeedThreads[number] = true;
			new Thread(new Runnable() {
				public void run() {
					IAcceleratedSpeed speed = new IAcceleratedSpeed();
					speed.setDuration(3000);
					speed.startScroller(0, 270, v);
					try {
						while(speed.computeScrollOffset() && isPlay){
							if(mType == Type.CIRCLE){
								computeCircleCoord(90,speed.getCurrX(),number);
							}else if(mType == Type.ELLIPSEWIDTH){
								computeEllipseWidthCoord(90,speed.getCurrX(),number);
							}else if(mType == Type.ELLIPSEHEIDHT){
								computeEllipseHeightCoord(90,speed.getCurrX(),number);
							}else{
								return;
							}
							Thread.sleep(100);
						}	

						Thread.sleep(1000);
						if(isPlay) {
							if (0 == number) {
								startThread(number);
							}
							if(number >= (mCircleNumber - 1)){
								mHandler.sendEmptyMessage(-1);
							}
						}else{
							if(mType == Type.CIRCLE){
								computeCircleCoord(0,0,number);
							}else if(mType == Type.ELLIPSEWIDTH){
								computeEllipseWidthCoord(0,0,number);
							}else if(mType == Type.ELLIPSEHEIDHT){
								computeEllipseHeightCoord(0,0,number);
							}else{
								return;
							}
							mHandler.sendEmptyMessage(-1);
						}
						
						mSpeedThreads[number] = false;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}else{

		}
	}
	
	private void startThread(final int number) {
		if(!mThreads[number] && isPlay){
			mThreads[number] = true;
			mHandler.sendEmptyMessage(number);
			new Thread(new Runnable() {
				public void run() {
					IConstantSpeed scroller = new IConstantSpeed();
					scroller.setDuration(1000);
					scroller.startScroller(0, 90);
					try {
						while(scroller.computeScrollOffset() && isPlay){
							if(mType == Type.CIRCLE){
								computeCircleCoord(0,scroller.getCurrX(),number);
							}else if(mType == Type.ELLIPSEWIDTH){
								computeEllipseWidthCoord(0,scroller.getCurrX(),number);
							}else if(mType == Type.ELLIPSEHEIDHT){
								computeEllipseHeightCoord(0,scroller.getCurrX(),number);
							}else{
								return;
							}
							Thread.sleep(150);
						}		

						if(!isPlay){
							if(mType == Type.CIRCLE){
								computeCircleCoord(0,0,number);
							}else if(mType == Type.ELLIPSEWIDTH){
								computeEllipseWidthCoord(0,0,number);
							}else if(mType == Type.ELLIPSEHEIDHT){
								computeEllipseHeightCoord(0,0,number);
							}else{
								return;
							}
							mHandler.sendEmptyMessage(-1);
						}else{
							startSpeedThread(scroller.getV(),number);
							if(number < (mCircleNumber - 1)){
								startThread(number+1);
							}
						}
						mThreads[number] = false;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
	
	private void computeCircleCoord(double startAngle, float angle, int number) {
		double distanceX = mInCircleBigRadius * Math.sin((startAngle+angle) * Math.PI / 180);
		double distanceY = mInCircleBigRadius - (mInCircleBigRadius * Math.cos((startAngle+angle) * Math.PI / 180));
		double mInCircleX = mInCircleBigRadius + distanceX;
		double mInCircleY = distanceY;
		
		mSmallCircleCoord[number][0] = (float) (mSmallCircleRadius + mInCircleX);
		mSmallCircleCoord[number][1] = (float) (mSmallCircleRadius + mInCircleY);
		postInvalidate();
	}

	//判断tan的正负
	private double mPlusMinus;
	
	private void computeEllipseWidthCoord(float startAngle, float angle, int number){
		angle += startAngle;
		
		double distanceY; 
		double distanceX; 
		if(angle <= 90){
			mPlusMinus = 1;
		}else if(angle <= 180){
			mPlusMinus = -1;
		}else if(angle <= 270){
			mPlusMinus = -1;
		}else if(angle <= 360){
			mPlusMinus = 1;
		}
		
		if(angle == 0){
			distanceX = 0;
			distanceY = mInCircleShortRadius;
		}else if(angle == 90){
			distanceX = mInCircleBigRadius;
			distanceY = 0;
		}else if(angle == 180){
			distanceX = 0;
			distanceY = -mInCircleShortRadius;
		}else if(angle == 270){
			distanceX = -mInCircleBigRadius;
			distanceY = 0;
		}else if(angle == 360){
			distanceX = 0;
			distanceY = mInCircleShortRadius;
		}else{			
			distanceY = mPlusMinus * Math.sqrt((mInCircleBigRadius*mInCircleBigRadius*mInCircleShortRadius*mInCircleShortRadius)/
					((mInCircleBigRadius*mInCircleBigRadius)+
							(mInCircleShortRadius*mInCircleShortRadius*Math.tan(angle*Math.PI/180)*Math.tan(angle*Math.PI/180))));
			distanceX = distanceY * Math.tan(angle*Math.PI/180);
		}
		
		double mInCircleX = mInCircleBigRadius + distanceX;
		double mInCircleY = mInCircleShortRadius - distanceY;
		
		mSmallCircleCoord[number][0] = (float) (mSmallCircleRadius + mInCircleX);
		mSmallCircleCoord[number][1] = (float) (mSmallCircleRadius + mInCircleY);
		postInvalidate();
	}
	
	private void computeEllipseHeightCoord(float startAngle, float angle, int number){
		angle += startAngle;
		
		double distanceY; 
		double distanceX; 
		
		if(angle <= 90){
			mPlusMinus = 1;
		}else if(angle <= 180){
			mPlusMinus = -1;
		}else if(angle <= 270){
			mPlusMinus = -1;
		}else if(angle <= 360){
			mPlusMinus = 1;
		}
		
		if(angle == 0){
			distanceX = 0;
			distanceY = mInCircleBigRadius;
		}else if(angle == 90){
			distanceX = mInCircleShortRadius;
			distanceY = 0;
		}else if(angle == 180){
			distanceX = 0;
			distanceY = -mInCircleBigRadius;
		}else if(angle == 270){
			distanceX = -mInCircleShortRadius;
			distanceY = 0;
		}else if(angle == 360){
			distanceX = 0;
			distanceY = mInCircleBigRadius;
		}else{
			distanceY = mPlusMinus * Math.sqrt((mInCircleBigRadius*mInCircleBigRadius*mInCircleShortRadius*mInCircleShortRadius)/
					((mInCircleShortRadius*mInCircleShortRadius)+
							(mInCircleBigRadius*mInCircleBigRadius*Math.tan(angle*Math.PI/180)*Math.tan(angle*Math.PI/180))));
			distanceX = distanceY * Math.tan(angle*Math.PI/180);
		}
		
		double mInCircleX = mInCircleShortRadius + distanceX;
		double mInCircleY = mInCircleBigRadius - distanceY;
		
		mSmallCircleCoord[number][0] = (float) (mSmallCircleRadius + mInCircleX);
		mSmallCircleCoord[number][1] = (float) (mSmallCircleRadius + mInCircleY);
		postInvalidate();
	}
}

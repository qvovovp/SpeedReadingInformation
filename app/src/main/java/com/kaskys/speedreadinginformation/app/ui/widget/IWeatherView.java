package com.kaskys.speedreadinginformation.app.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

public class IWeatherView extends View implements View.OnClickListener{
	private Type mType = Type.FINE;
	
	private static final int DEFAULT_DURATION = 3000;
	private static final int FINE_DEFAULT_LINE_NUMBER = 12;
	private static final int CLOUD_DEFAULT_LINE_NUMBER = 8;
	
	private Paint mPaint;
	private RectF mCricleRect;
	private float mRotateAngle;
	
	private int mWidth,mHeight;
	private int mMin;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			long passTime = SystemClock.uptimeMillis() - startTime;
			if(passTime >= DEFAULT_DURATION) {
				if(mType == Type.FINE){
					mRotateAngle = 0;
				}else if(mType == Type.MCLOUD || mType == Type.SCLOUD){
					
				}else if(mType == Type.SRAIN || mType == Type.MRAIN){
					mRainLongLine = mMaxLine;
					mRainShortLine = mMinLine;
				}else if(mType == Type.SNOW){
					
				}
				startTime = 0;
				removeCallbacksAndMessages(null);
			}else{
				if(mType == Type.FINE){
					mRotateAngle += 10;
				}else if(mType == Type.MCLOUD || mType == Type.SCLOUD){
					
				}else if(mType == Type.SRAIN || mType == Type.MRAIN){
					updateRain();
				}else if(mType == Type.SNOW){
					updateSnow();
				}
				sendEmptyMessageDelayed(0, 100);
			}
			invalidate();
		};
	};
	
	//Fine
	private float mCLDis;
	private float mLineAngle;
	private int mLineNumber;
	private int mCricleRadius;
	private int mLongLine;
	private int mShortLine;
	
	//cloud
	private Paint mCloudPaint;
	private float mOffSet;
	private float mCloudRadius1,mCloudRadius2,mCloudRadius3,mCloudRadius4;
	
	//rain
	private Paint mRainPaint;
	private RectF mCloudRect;
	private RectF mRainRect;
	private int mRainRow = 6;
	private float mMaxLine,mMinLine;
	private float mRainRowOffset;
	private float mRainLongLine,mRainShortLine;
	private float mRaintOffset;
	
	//snow
	private RectF mSnowRect;
	private Path mSnowPath;
	private float mSide;
	private float mAngle;
	private float mAddHeight = 0;
	private float mPointWidth,mPointHeight;
	private float mSideWidth;
	private float mAddSide = 0;
	private float mPointX = 0,mPiontY = 0;
	private boolean isDrawSide;
	
	public IWeatherView(Context context) {
		this(context,null);
	}
	
	public IWeatherView(Context context, AttributeSet attrs) {
		this(context,attrs,0);
	}
	
	public IWeatherView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	
	private void init() {
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(2);
		mPaint.setColor(Color.WHITE);		
		
		setOnClickListener(this);
	}
	
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mWidth = getMeasuredWidth();
		mHeight = getMeasuredHeight();
		mMin = Math.min(mHeight, mWidth);
		setup();
	}
	
	private void setup() {
		if(mType == Type.FINE){
			setupFine();
		}else if(mType == Type.MCLOUD || mType == Type.SCLOUD){
			setupCloud();
		}else if(mType == Type.SRAIN){
			setupRain(6);
		}else if(mType == Type.MRAIN){
			setupRain(8);
		}else if(mType == Type.SNOW){
			setupSnow();
		}else{
			return;
		}
	}

	private void setupFine() {
		mCricleRect = new RectF();
		
		mLineNumber = FINE_DEFAULT_LINE_NUMBER;
		mLineAngle = 360 / mLineNumber;
		mRotateAngle = 0;
		
		mCLDis = (Math.min(mHeight, mWidth) / 2) * 1.0f / 10;
		
		mCricleRadius = Math.min(mWidth, mHeight) * 2 / 8;
		mLongLine = ((Math.min(mHeight, mWidth)/2) - mCricleRadius) * 2 / 3;
		mShortLine = mLongLine * 2 / 3;
		
		mCricleRect.set(mWidth/2 - mCricleRadius - mCLDis, mHeight/2 - mCricleRadius - mCLDis,
				mWidth/2 + mCricleRadius + mCLDis, mHeight/2 + mCricleRadius + mCLDis);
	}

	private void setupCloud() {
		mCloudPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mCloudPaint.setStyle(Paint.Style.FILL);
		mCloudPaint.setColor(Color.WHITE);
		
		mCricleRect = new RectF();
		
		mLineNumber = CLOUD_DEFAULT_LINE_NUMBER;
		mLineAngle = 360 / mLineNumber;
		
		mCLDis = (mMin / 2) * 1.0f / 10;
		mOffSet = (mMin / 2) * 1.0f / 4;
		
		mCricleRadius = mMin * 2 / 8;
		mLongLine = ((mMin/2) - mCricleRadius) * 2 / 3;
		mShortLine = mLongLine * 2 / 3;
		
		mCricleRect.set(mWidth/2 - mCricleRadius - mCLDis - mOffSet, mHeight/2 - mCricleRadius - mCLDis - mOffSet,
				mWidth/2 + mCricleRadius + mCLDis - mOffSet, mHeight/2 + mCricleRadius + mCLDis - mOffSet);
		
		mCloudRadius1 = mMin * 3.0f / 9; 
		mCloudRadius2 = mMin * 4.0f / 15;
		mCloudRadius3 = mMin * 3.0f / 15;
		mCloudRadius4 = mMin * 11.0f / 36;
	}

	private void setupRain(int row) {
		mCloudPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mCloudPaint.setStyle(Paint.Style.FILL);
		mCloudPaint.setColor(Color.WHITE);
		
		mRainPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mRainPaint.setStyle(Paint.Style.STROKE);
		mRainPaint.setStrokeWidth(3);
		mRainPaint.setColor(Color.BLUE);
		
		mCloudRect = new RectF();
		mRainRect = new RectF();
		
		mRainRow = row;
		
		mCloudRect.set(mWidth/2 - mWidth * 1.0f / 3, mHeight/2 - mHeight * 1.0f / 3,
				mWidth/2 + mWidth * 1.0f / 3, mHeight/2);
		mRainRect.set(mWidth/2 - mWidth * 1.0f / 3, mHeight/2,
				mWidth/2 + mWidth * 1.0f / 3, mHeight/2 + mHeight * 1.0f / 3);
		
		mMaxLine = mRainLongLine = mRainRect.height() * 3 / 5;
		mMinLine = mRainShortLine = mRainRect.height() * 2 / 5;
		mRaintOffset = mRainLongLine/10;
		
		mRainRowOffset = (mRainRect.right - mRainRect.left) / (mRainRow - 1);
		
		mCloudRadius1 = Math.min(mRainRect.width(), mRainRect.height()) * 3.0f / 5; 
		mCloudRadius2 = Math.min(mRainRect.width(), mRainRect.height()) * 4.0f / 7;
		mCloudRadius3 = Math.min(mRainRect.width(), mRainRect.height()) * 3.0f / 4;
		mCloudRadius4 = Math.min(mRainRect.width(), mRainRect.height()) * 10.0f / 19;
	}

	private void setupSnow() {
		mPaint.setStrokeWidth(6);
		
		mSnowRect = new RectF();
		mSnowPath = new Path();
		
		isDrawSide = true;
		
		mSide = Math.min(mWidth, mHeight) * 3.0f / 5;
		mAddHeight = mWidth/2 - mSide/2;
		
		mSnowRect.set(mWidth*1.0f/2 - mSide/2, mAddHeight, mWidth*1.0f/2 + mSide/2,mAddHeight + mSide);
		mSideWidth = mSnowRect.width() / 2;
		mPointWidth = mSnowRect.width() / 4;
		mPointHeight = mSnowRect.height() / 2;
		linePath();
	}
	
	private void linePath() {
		mSnowPath.reset();
		mSnowPath.moveTo(mSnowRect.left, mSnowRect.top+mSnowRect.height()/2);
		mSnowPath.lineTo(mSnowRect.left+mSnowRect.width()/4, mSnowRect.top);
		mSnowPath.lineTo(mSnowRect.left+mSnowRect.width()*3/4, mSnowRect.top);
		mSnowPath.lineTo(mSnowRect.left+mSnowRect.width(), mSnowRect.top+mSnowRect.height()/2);
		mSnowPath.lineTo(mSnowRect.left+mSnowRect.width()*3/4, mSnowRect.top+mSnowRect.height());
		mSnowPath.lineTo(mSnowRect.left+mSnowRect.width()*1/4, mSnowRect.top+mSnowRect.height());
		mSnowPath.close();
	}

	public enum Type{
		FINE,SCLOUD,MCLOUD,MRAIN,SRAIN,SNOW
	}
	
	public void setType(Type type){
		mType = type;
		setup();
		invalidate();
	}
	
	protected void onDraw(Canvas canvas) {
		if(mType == Type.FINE){
			drawFine(canvas);
		}else if(mType == Type.SCLOUD){
			drawCloud(canvas,false);
		}else if(mType == Type.MCLOUD){
			drawCloud(canvas,true);
		}else if(mType == Type.SRAIN || mType == mType.MRAIN){
			drawRain(canvas);
		}else if(mType == Type.SNOW){
			drawSnow(canvas);
		}else{
			return;
		}
	}

	private void drawFine(Canvas canvas) {
		canvas.rotate(mRotateAngle, mWidth/2, mHeight/2);
		canvas.drawCircle(mWidth/2, mHeight/2, mCricleRadius, mPaint);
		for(int i=0;i<mLineNumber;i++){
			if(i%2==0){
				canvas.drawLine(mWidth/2, mCricleRect.top, mWidth/2, mCricleRect.top - mLongLine, mPaint);
			}else{
				canvas.drawLine(mWidth/2, mCricleRect.top, mWidth/2, mCricleRect.top - mShortLine, mPaint);
			}
			canvas.rotate(mLineAngle, mWidth/2, mHeight/2);
		}
	}

	private void drawCloud(Canvas canvas,boolean isMore) {
		canvas.drawCircle(mWidth/2 - mOffSet, mHeight/2  - mOffSet, mCricleRadius, mPaint);
		for(int i=0;i<mLineNumber;i++){
			if(i%2==0){
				canvas.drawLine(mWidth/2 - mOffSet, mCricleRect.top, mWidth/2 - mOffSet, mCricleRect.top - mLongLine, mPaint);
			}else{
				canvas.drawLine(mWidth/2 - mOffSet, mCricleRect.top, mWidth/2 - mOffSet, mCricleRect.top - mShortLine, mPaint);
			}
			canvas.rotate(mLineAngle, mWidth/2 - mOffSet, mHeight/2 - mOffSet);
		}
		if(isMore){
			canvas.drawCircle(mWidth*1.0f/2, mHeight*1.0f/2 + mCloudRadius1, mCloudRadius1, mCloudPaint);
		}else{
			canvas.drawCircle(mWidth*1.0f/2, mHeight* 1.0f/4 + mCloudRadius1, mCloudRadius1, mCloudPaint);
		}
		canvas.drawCircle(mWidth*1.0f/4, mHeight - mCloudRadius2, mCloudRadius2, mCloudPaint);
		canvas.drawCircle(mWidth*1.0f/2, mHeight - mCloudRadius3, mCloudRadius3, mCloudPaint);
		canvas.drawCircle(mWidth*3.0f/4, mHeight - mCloudRadius4, mCloudRadius4, mCloudPaint);
		
	}

	private void drawRain(Canvas canvas) {
		canvas.drawCircle(mWidth*1.0f/2, mRainRect.top * 1.0f / 4 + mCloudRadius1, mCloudRadius1, mCloudPaint);
		canvas.drawCircle(mWidth*1.0f/4, mRainRect.top - mCloudRadius2, mCloudRadius2, mCloudPaint);
		canvas.drawCircle(mWidth*4.0f/7, mRainRect.top - mCloudRadius3, mCloudRadius3, mCloudPaint);
		canvas.drawCircle(mWidth*3.0f/4, mRainRect.top - mCloudRadius4, mCloudRadius4, mCloudPaint);
		
		for(int i=0;i<mRainRow;i++){
			if(i%2==0){
				canvas.drawLine(mRainRect.left + mRaintOffset, mRainRect.top, mRainRect.left, mRainRect.top + mRainLongLine, mRainPaint);
			}else{
				canvas.drawLine(mRainRect.left + mRaintOffset, mRainRect.top, mRainRect.left, mRainRect.top + mRainShortLine, mRainPaint);
			}
			canvas.translate(mRainRowOffset, 0);
		}
		
	}

	private void drawSnow(Canvas canvas) {
		canvas.rotate(mAngle, mSnowRect.left+mSnowRect.width()/2, mSnowRect.top+mSnowRect.height()/2);
		canvas.drawLine(mSnowRect.left, mSnowRect.top+mSnowRect.height()/2,
				mSnowRect.left+mSnowRect.width()/2, mSnowRect.top+mSnowRect.height()/2, mPaint);
		canvas.drawLine(mSnowRect.left+mSnowRect.width()/4, mSnowRect.top,
				mSnowRect.left+mSnowRect.width()/2, mSnowRect.top+mSnowRect.height()/2, mPaint);
		canvas.drawLine(mSnowRect.left+mSnowRect.width()*3/4,mSnowRect.top,
				mSnowRect.left+mSnowRect.width()/2, mSnowRect.top+mSnowRect.height()/2, mPaint);
		canvas.drawLine(mSnowRect.left+mSnowRect.width(), mSnowRect.top+mSnowRect.height()/2,
				mSnowRect.left+mSnowRect.width()/2, mSnowRect.top+mSnowRect.height()/2, mPaint);
		canvas.drawLine(mSnowRect.left+mSnowRect.width()*3/4, mSnowRect.top+mSnowRect.height(),
				mSnowRect.left+mSnowRect.width()/2, mSnowRect.top+mSnowRect.height()/2, mPaint);
		canvas.drawLine(mSnowRect.left+mSnowRect.width()*1/4, mSnowRect.top+mSnowRect.height(),
				mSnowRect.left+mSnowRect.width()/2, mSnowRect.top+mSnowRect.height()/2, mPaint);
		if(isDrawSide){
			canvas.drawLine(mSnowRect.left + mSnowRect.width()/2 - mAddSide, mSnowRect.top, 
					mSnowRect.left + mSnowRect.width()/2 + mAddSide, mSnowRect.top, mPaint);
			canvas.drawLine(mSnowRect.left + mSnowRect.width()/2 - mAddSide, mSnowRect.top+mSnowRect.height(), 
					mSnowRect.left + mSnowRect.width()/2 + mAddSide, mSnowRect.top+mSnowRect.height(), mPaint);
			canvas.drawLine(mSnowRect.left, mSnowRect.top+mSnowRect.height()/2, 
					mSnowRect.left+mPointX, mSnowRect.top+mSnowRect.height()/2+mPiontY, mPaint);
			canvas.drawLine(mSnowRect.left, mSnowRect.top+mSnowRect.height()/2, 
					mSnowRect.left+mPointX, mSnowRect.top+mSnowRect.height()/2-mPiontY, mPaint);
			
			canvas.drawLine(mSnowRect.left + mSnowRect.width(), mSnowRect.top+mSnowRect.height()/2, 
					mSnowRect.left+ mSnowRect.width() - mPointX, mSnowRect.top+mSnowRect.height()/2+mPiontY, mPaint);
			canvas.drawLine(mSnowRect.left + mSnowRect.width(), mSnowRect.top+mSnowRect.height()/2, 
					mSnowRect.left+ + mSnowRect.width() - mPointX, mSnowRect.top+mSnowRect.height()/2-mPiontY, mPaint);
		}
	}
	
	private boolean isAdd = true;
	private void updateRain() {
		if(isAdd){
			mRainLongLine -= 0.5f;
			mRainShortLine += 0.5f;
			if(mRainLongLine <= mMinLine || mRainShortLine >= mMaxLine){
				isAdd = false;
			}
		}else{
			mRainLongLine += 0.5f;
			mRainShortLine -= 0.5f;
			if(mRainShortLine <= mMinLine || mRainLongLine >= mMaxLine){
				isAdd = true;
			}
		}
	}
	
	private boolean isStart = true;
	private void updateSnow(){
		if(isStart){
			mAngle += 10;
			mAddHeight += 3;
			System.out.println("mAddHeight-->"+mAddHeight);
			System.out.println("mHeight-->"+mHeight);
			if(mAddHeight > mHeight){
				isStart = false;
				reset();
			}
		}else{
			System.out.println("isStart-->"+isStart);
			mAddSide += 2;
			mPointX += 1;
			mPiontY += 2;
			if(mAddSide >= (mSideWidth/2)){
				mAddSide = mSideWidth/2;
			}
			if(mPointX >= mPointWidth){
				mPointX = mPointWidth;
			} 
			if(mPiontY >= mPointHeight){
				mPiontY = mPointHeight;
			}	
			if(mAddSide == mSideWidth/2 && mPointX == mPointWidth && mPiontY == mPointHeight){
				
			}
		}
	}
	
	private void reset() {
		mAddHeight = mHeight/2 - mSide/2;
		mAngle = 0;
	}
	
	private long startTime = 0;
	public void onClick(View v) {
		if(0 == startTime){
			startTime = SystemClock.uptimeMillis();
			mHandler.sendEmptyMessage(0);
		}
	}
}

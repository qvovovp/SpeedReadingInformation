package com.kaskys.speedreadinginformation.app.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import com.kaskys.speedreadinginformation.app.ui.widget.circle.IConstantSpeed;

public class IPlayButton extends View implements View.OnClickListener{
	public static final int TYPE_PLAY = 0x1;
	public static final int TYPE_STOP = 0x2;
	public static final int TYPE_ERROR = 0x0;
	private static final int DEFLUAT_CIRCIE_WIDTH = 10;
	
	private int mWindowWidth,mWindowHeight;
	
	private Paint mCirclePaint,mViewPaint;
	private RectF mOneRect,mTwoRect;
	private Path mOnePath,mTwoPath;
	
	private int mWidth,mHeidht;
	private float mCircleRadius;
	private float mViewWidth = DEFLUAT_CIRCIE_WIDTH;
	private float mViewHeight;
	
	private float mOneAddWidth = 0;
	private float mOneAddHeight = 0;
	private float mOneResultHeight,mOneResultWidth;
	
	private float mTwoAddLeftWidth = 0, mTwoAddLeftHeight = 0;
	private float mTwoResultLeftWidth,mTwoResultLeftHeight;
	private float mTwoAddRightWidth = 0, mTwoAddRightHeight = 0;
	private float mTwoResultRightWidth,mTwoResultRightHeight;
	
	private int mCurrentType = TYPE_STOP;
	private boolean isPlaying = false;
	
	private IPlayOnClickListener mListener;
	
	private IConstantSpeed speed;
	private Handler mHander = new Handler(){
		public void handleMessage(Message msg) {
			float currX = 0;
			if(mCurrentType == TYPE_STOP){
				currX = speed.getCurrX();
			}else if(mCurrentType == TYPE_PLAY){
				currX = 100 - speed.getCurrX();
			}else{
				return;
			}
			
			mOneAddWidth = ((currX/100) * mOneResultWidth);
			mOneAddHeight = mOneResultHeight - ((currX/100) * mOneResultHeight);
			
			mTwoAddLeftWidth = ((currX/100) * mTwoResultLeftWidth);
			mTwoAddLeftHeight = mTwoResultLeftHeight - ((currX/100) * mTwoResultLeftHeight);
			
			mTwoAddRightWidth = ((currX/100) * mTwoResultRightWidth);
			mTwoAddRightHeight = mTwoResultRightHeight - ((currX/100) * mTwoResultRightHeight);

			System.out.println("mTwoAddLeftWidth-->"+mTwoAddLeftWidth);
			System.out.println("mTwoResultLeftWidth-->"+mTwoResultLeftWidth);
			System.out.println("<------------------------------>");
			System.out.println("mTwoAddRightWidth-->"+mTwoAddRightWidth);
			System.out.println("mTwoResultRightWidth-->"+mTwoResultRightWidth);

			setup();
			invalidate();
			if(!speed.computeScrollOffset()){
				mHander.removeCallbacksAndMessages(null);
				isPlaying = false;
				if(mCurrentType == TYPE_PLAY){
					mCurrentType = TYPE_STOP;
				}else{
					mCurrentType = TYPE_PLAY;
				}
			}else{
				mHander.sendEmptyMessageDelayed(1, 100);
			}
		};
	};
	
	public IPlayButton(Context context) {
		this(context,null);
	}
	
	public IPlayButton(Context context, AttributeSet attrs) {
		this(context,attrs,0);
	}
	
	public IPlayButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		setBackgroundColor(getResources().getColor(android.R.color.transparent));
		mWindowWidth = getResources().getDisplayMetrics().widthPixels;
		mWindowHeight = getResources().getDisplayMetrics().heightPixels;
		
		
		mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mCirclePaint.setStyle(Paint.Style.STROKE);
		mCirclePaint.setStrokeWidth(mViewWidth);
		mCirclePaint.setColor(Color.WHITE);
		
		mViewPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mViewPaint.setStyle(Paint.Style.FILL);
		mViewPaint.setColor(Color.WHITE);
		
		mOneRect = new RectF();
		mTwoRect = new RectF();
		
		mOnePath = new Path();
		mTwoPath = new Path();
		
		setOnClickListener(this);
	}
	
	@Override
	public void setOnClickListener(OnClickListener l) {
		super.setOnClickListener(this);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);	
		if(MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST || 
				MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST){
			int min = Math.min(mWindowWidth,mWindowHeight);
			setMeasuredDimension(min/4, min/4);
		}else{
			setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
		}
		
		mWidth = getMeasuredWidth();
		mHeidht = getMeasuredHeight();
		
		mViewWidth = Math.min(mWidth, mHeidht)/50;
		mViewHeight = (float) (Math.sqrt(3*1.0f/4)*mCircleRadius) - mViewWidth/2;
		
		init();
		setup();
		compute();
	}

	private void compute() {
		mOneResultHeight = (float) ((mWidth/2 - mOneRect.left) * 
				(mOneRect.height()/2)/((mWidth - mViewWidth) - mOneRect.left));
		mOneResultWidth = mWidth/2 - mOneRect.left - mViewWidth*2;

		mTwoResultLeftWidth = mWidth/4 - mViewWidth*2;
		mTwoResultLeftHeight = mOneResultHeight;

		mTwoResultRightWidth = mWidth/4;
		mTwoResultRightHeight = mTwoRect.height()/2;

		mOneAddHeight = mOneResultHeight;
		mTwoAddLeftHeight = mTwoResultLeftHeight;
		mTwoAddRightHeight = mTwoResultRightHeight;
	}

	private void setup() {
		mCircleRadius = (Math.min(mWidth, mHeidht) - mViewWidth)/2;

		mOneRect.set(mWidth*1.0f/4, mHeidht*1.0f/2 - mViewHeight , mWidth*1.0f/2 - mOneAddWidth, mHeidht*1.0f/2 + mViewHeight);
		mTwoRect.set(mWidth*1.0f/2 + mTwoAddLeftWidth, mHeidht*2.0f/4 - mViewHeight, mWidth*1.0f - mTwoAddRightWidth, mHeidht*2.0f/4 + mViewHeight);
		
		linePath();
	}
	
	private void linePath() {
		mOnePath.reset();
		mOnePath.moveTo(mOneRect.left, mOneRect.top);
		mOnePath.lineTo(mOneRect.right, mOneRect.top + mOneAddHeight);
		mOnePath.lineTo(mOneRect.right, mOneRect.bottom - mOneAddHeight);
		mOnePath.lineTo(mOneRect.left, mOneRect.bottom);
		mOnePath.close();
		
		mTwoPath.reset();
		mTwoPath.moveTo(mTwoRect.left, mTwoRect.top + mTwoAddLeftHeight);
		mTwoPath.lineTo(mTwoRect.right, mTwoRect.top + mTwoAddRightHeight);
		mTwoPath.lineTo(mTwoRect.right, mTwoRect.bottom - mTwoAddRightHeight);
		mTwoPath.lineTo(mTwoRect.left, mTwoRect.bottom - mTwoAddLeftHeight);
		mTwoPath.close();
		
//		mThreePath.reset();
//		mThreePath.moveTo(mOneRect.left, mOneRect.top);
//		mThreePath.lineTo(mWidth - DEFLUAT_CIRCIE_WIDTH, mTwoRect.top + mTwoRect.height()/2);
//		mThreePath.lineTo(mOneRect.left, mOneRect.bottom);
//		mThreePath.close();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawCircle(mWidth/2, mHeidht/2, mCircleRadius, mCirclePaint);
		canvas.drawPath(mOnePath, mViewPaint);
		canvas.drawPath(mTwoPath, mViewPaint);
	}
	
	public void setListener(IPlayOnClickListener listener){
		mListener = listener;
	} 
	
	public interface IPlayOnClickListener{
		void onPlayMusic();
		void onPauseMusic();
	}
	
	@Override
	public void onClick(View v) {
		if(!isPlaying){
			isPlaying = true;
			speed = new IConstantSpeed();
			speed.setDuration(1100);
			speed.startScroller(0, 100);
			mHander.sendEmptyMessageDelayed(1, 100);
			
			if(mCurrentType == TYPE_PLAY){
				if(mListener != null){
					mListener.onPauseMusic();
				}
			}else if(mCurrentType == TYPE_STOP){
				if(mListener != null){
					mListener.onPlayMusic();
				}				
			}
		}
	}
}

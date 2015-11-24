package com.kaskys.speedreadinginformation.app.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.kaskys.speedreadinginformation.app.R;

public class MyProgressBar extends View{
	private final static int PROGRESSBAR_MAX = 100;
	private final static int PROGRESSBAR_COLOR = 0xFF000000;
	
	private Context mContext;
	
	private int mProMax;
	private int mProColor;
	
	private Paint mPaint;
	private int mViewWidth;
	private int mViewHeight;
	
	private int mFillWidth;
	private int mProgerss = 0;
	
	public MyProgressBar(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}
	
	public MyProgressBar(Context context, AttributeSet attrs) {
		this(context,attrs,0);
		// TODO Auto-generated constructor stub
	}
	
	public MyProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init(context,attrs,defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		int proMax = PROGRESSBAR_MAX;
		int proColor = PROGRESSBAR_COLOR;
		
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.pb_MyProgressBar, defStyleAttr, 0);
		proMax = ta.getInteger(R.styleable.pb_MyProgressBar_pb_max, proMax);
		proColor = ta.getColor(R.styleable.pb_MyProgressBar_pb_color, proColor);
		
		mProMax = proMax;
		mProColor = proColor;
		
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(proColor);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mViewWidth = getMeasuredWidth();
		mViewHeight = getMeasuredHeight();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawRect(0, 0, mFillWidth, mViewHeight, mPaint);
	}
	
	public void setProgerss(int progress){
		if(progress <= mProMax ){
			mProgerss = progress;
			mFillWidth = (mProgerss * mViewWidth) / mProMax;
			postInvalidate();
		}
	}
}

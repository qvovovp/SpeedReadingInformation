package com.kaskys.speedreadinginformation.app.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class IMusicBackground extends ImageView{
	private Paint mPaint;
	private TextPaint mTextPaint;
	
	private int mWidth,mHeidht;
	
	private String mStr = "null";
	
	public IMusicBackground(Context context) {
		this(context,null);
	}
	
	public IMusicBackground(Context context, AttributeSet attrs) {
		this(context,attrs,0);
	}
	
	public IMusicBackground(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}
	
	private void init(Context context) {
		
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Paint.Style.FILL);
		
		mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);  
		mTextPaint.setColor(Color.WHITE);   
		mTextPaint.setTextAlign(Paint.Align.CENTER);  
		mTextPaint.setTypeface(Typeface.DEFAULT_BOLD); 
	}
  	
	public void setText(String value){
		mStr = value;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mWidth = getMeasuredWidth();
		mHeidht = getMeasuredHeight();
		initLayout();
	}
	
	private void initLayout() {
//		mPaint.setShader(new SweepGradient(mWidth/2, mHeidht/2, new int[] { Color.GREEN, Color.WHITE, Color.GREEN }, null));
		
		mTextPaint.setTextSize(mWidth * 2 / 10F); 
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawText(mStr, mWidth/2, mHeidht/2, mTextPaint);
//		canvas.drawText("new", mWidth/2, y, mTextPaint);
	}
}

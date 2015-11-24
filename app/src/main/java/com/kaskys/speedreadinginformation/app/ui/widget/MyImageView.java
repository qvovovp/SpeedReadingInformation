package com.kaskys.speedreadinginformation.app.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MyImageView extends ImageView{
	private Context mContext;
	private int mImgWidth,mImgHeight;
	private int mScreenHeight;
	
	public MyImageView(Context context) {
		this(context,null);
	}
	
	public MyImageView(Context context, AttributeSet attrs) {
		this(context,attrs,0);
	}
	
	public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
		init();
	}

	private void init() { 
		setScaleType(ScaleType.CENTER_INSIDE);
		mScreenHeight = mContext.getResources().getDisplayMetrics().heightPixels;
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		mImgWidth = bm.getWidth();
		mImgHeight = bm.getHeight();
		requestLayout();
		super.setImageBitmap(bm);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int mWidth = getMeasuredWidth();
		int mHeight = getMeasuredHeight();
		if(0 < mImgWidth && 0 < mImgHeight){
				int heightC = mWidth * mImgHeight / mImgWidth;
				if(heightC > mHeight){
					heightC = mHeight;
					mWidth = heightC * mImgWidth / mImgHeight;
					this.setScaleType(ScaleType.CENTER_CROP);
				}else{
					this.setScaleType(ScaleType.FIT_XY);
				}
				if(mImgHeight >= mScreenHeight/2){
					mHeight = (int) ((mImgHeight*1.0f/mScreenHeight) * (mScreenHeight/2));
				}
				setMeasuredDimension(mWidth, mHeight);
		}
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	}
}

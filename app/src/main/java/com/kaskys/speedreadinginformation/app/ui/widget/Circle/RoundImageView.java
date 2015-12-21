package com.kaskys.speedreadinginformation.app.ui.widget.circle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.view.View;
import com.kaskys.speedreadinginformation.app.R;

/**
 * 
 * @author Administrator
 * 
 */
public class RoundImageView extends ImageView implements View.OnClickListener{
	private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;

	private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
	private static final int COLORDRAWABLE_DIMENSION = 1;

	private static final int DEFAULT_BORDER_WIDTH = 0;
	private static final int DEFAULT_BORDER_COLOR = Color.BLACK;

	private static final int DEFAULT_MAX_PROGRESS = 100;
	private static final int DEFAULT_MAX_ANGLE = 360;
	private static final int DEFAULT_START_ANGLE = 270;
	
	private final RectF mDrawableRect = new RectF();
	private final RectF mBorderRect = new RectF();
	
	private final Matrix mShaderMatrix = new Matrix();
	private final Paint mBitmapPaint = new Paint();
	private final Paint mBorderPaint = new Paint();
	private final Paint mPointPaint = new Paint();
	private final Paint mCacheBorderPaint = new Paint();

	private float mBorderAngle = 0;
	private float mCacheBorderAngle = 0;

	private int mPointColor = DEFAULT_BORDER_COLOR;
	private int mPointWidth = DEFAULT_BORDER_WIDTH;
	
	private int mBorderColor = DEFAULT_BORDER_COLOR;
	private int mBorderWidth = DEFAULT_BORDER_WIDTH;

	private Bitmap mBitmap;
	private BitmapShader mBitmapShader;
	private int mBitmapWidth;
	private int mBitmapHeight;
	
	private float mPointX,mPointY;
	
	private float mDrawableRadius;
	private float mBorderRadius;
	private float mPointRadius;
	private float mInCircleRadius;
	
	private boolean mReady;
	private boolean mSetupPending;

	private float mPlayAngle = 0;
	private int max = 100;
	private boolean isPlay,mPlaying;

	public RoundImageView(Context context) {
		this(context, null);
	}

	public RoundImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		super.setScaleType(SCALE_TYPE);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.CircleImageView, defStyle, 0);

		mBorderWidth = a.getDimensionPixelSize(
				R.styleable.CircleImageView_border_width, DEFAULT_BORDER_WIDTH);
		mBorderColor = a.getColor(R.styleable.CircleImageView_border_color,
				DEFAULT_BORDER_COLOR);
		mPointWidth = a.getDimensionPixelSize(
				R.styleable.CircleImageView_point_width, DEFAULT_BORDER_WIDTH);
		mPointColor = a.getColor(R.styleable.CircleImageView_point_color, 
				DEFAULT_BORDER_COLOR);
		
		a.recycle();

		mReady = true;
		isPlay = false;

		if (mSetupPending) {
			setup();
			mSetupPending = false;
		}
		setOnClickListener(this);
	}

	private void setup() {
		if(!mReady){
			mSetupPending = true;
			return;
		}
		if(null == mBitmap){
			return;
		}
		
		mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
	    
		mBitmapPaint.setAntiAlias(true);
	    mBitmapPaint.setShader(mBitmapShader);
	    
	    mBorderPaint.setStyle(Paint.Style.STROKE);
	    mBorderPaint.setAntiAlias(true);
	    mBorderPaint.setColor(mBorderColor);
	    mBorderPaint.setStrokeWidth(mBorderWidth/2);
	    
	    mPointPaint.setStyle(Paint.Style.FILL);
	    mPointPaint.setAntiAlias(true);
	    mPointPaint.setColor(mPointColor);

		mCacheBorderPaint.setAntiAlias(true);
		mCacheBorderPaint.setStyle(Paint.Style.STROKE);
		mCacheBorderPaint.setColor(Color.GRAY);
		mCacheBorderPaint.setStrokeWidth(mBorderWidth/2);

	    mPointRadius = mPointWidth/2;
	    
	    mBitmapHeight = mBitmap.getHeight();
	    mBitmapWidth = mBitmap.getWidth();

	    mBorderRect.set(0, 0, getWidth(), getHeight());
	    mBorderRadius = Math.min((mBorderRect.height() - mBorderWidth) / 2, (mBorderRect.width() - mBorderWidth) / 2);
	    
	    mDrawableRect.set(mBorderWidth, mBorderWidth, mBorderRect.width() - mBorderWidth, mBorderRect.height() - mBorderWidth);
	    mDrawableRadius = Math.min(mDrawableRect.height() / 2, mDrawableRect.width() / 2);
	    mBorderRect.set(getWidth()/2 - mDrawableRadius, getHeight()/2 - mDrawableRadius,
	    		getWidth()/2 + mDrawableRadius, getHeight()/2 + mDrawableRadius);
	    
	    mPointX = mBorderRect.left + mBorderRect.width() / 2;
	    mPointY = mBorderRect.top;
	    mInCircleRadius = mDrawableRadius;
	    
	    updateShaderMatrix();
	    invalidate();
	}

	private void updateShaderMatrix() {
		float scale;
	    float dx = 0;
	    float dy = 0;

	    mShaderMatrix.set(null);

	    if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight) {
	      scale = mDrawableRect.height() / (float) mBitmapHeight;
	      dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f;
		} else {
	      scale = mDrawableRect.width() / (float) mBitmapWidth;
	      dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f;
		}

	    mShaderMatrix.setScale(scale, scale);
	    mShaderMatrix.postTranslate((int) (dx + 0.5f) + mBorderWidth, (int) (dy + 0.5f) + mBorderWidth);

	    mBitmapShader.setLocalMatrix(mShaderMatrix);
	}

	@Override
	public ScaleType getScaleType() {
		return SCALE_TYPE;
	}

	@Override
	public void setScaleType(ScaleType scaleType) {
		if (scaleType != SCALE_TYPE) {
			throw new IllegalArgumentException(String.format(
					"ScaleType is not supported.", scaleType));
		}
	}

	public int getBorderColor() {
		return mBorderColor;
	}

	public void setBorderColor(int borderColor) {
		if (borderColor == mBorderColor) {
			return;
		}

		mBorderColor = borderColor;
		mBorderPaint.setColor(mBorderColor);
		invalidate();
	}
	
	public void setBorderAngle(float angle){
		if(mBorderAngle == angle){
			return;
		}
		mBorderAngle = angle;
		computeCircleCoord(angle);
		postInvalidate();
	}

	public void setCacheBorderAngle(float angle){
		if(mCacheBorderAngle == angle){
			return;
		}
		mCacheBorderAngle = angle;
		postInvalidate();
	}

	public int getBorderWidth(){
		return mBorderWidth;
	}

	public void setBorderWidth(int borderWidth){
		if(mBorderWidth == borderWidth){
			return;
		}
		mBorderWidth = borderWidth;
		setup();
	}
	
	@Override
	public void setImageBitmap(Bitmap bm) {
		super.setImageBitmap(bm);
		mBitmap = bm;
		setup();
	}
	
	@Override
	public void setImageDrawable(Drawable drawable) {
		super.setImageDrawable(drawable);
		mBitmap = getBitmapFromDrawable(drawable);
		setup();
	}
	
	@Override
	public void setImageResource(int resId) {
		super.setImageResource(resId);
		mBitmap = getBitmapFromDrawable(getDrawable());
		setup();
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		setup();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (getDrawable() == null) {
			return;
		}
		canvas.save();
		canvas.rotate(mPlayAngle, getWidth() / 2, getHeight() / 2);
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, mDrawableRadius, mBitmapPaint);
		canvas.restore();
		canvas.drawArc(mBorderRect,DEFAULT_START_ANGLE,mCacheBorderAngle,false,mCacheBorderPaint);
		canvas.drawArc(mBorderRect, DEFAULT_START_ANGLE, mBorderAngle, false, mBorderPaint);
		canvas.drawCircle(mPointX, mPointY, mPointRadius, mPointPaint);
	}

	public boolean isPlay(){
		return isPlay;
	}

	public void playM(){
		if(!isPlay) {
			synchronized (RoundImageView.class) {
				if(!isPlay) {
					mPlaying = true;
					new Thread(new Runnable() {
						public void run() {
							try {
								while(mPlaying) {
									mPlayAngle += 0.1f;
									postInvalidate();
									Thread.sleep(10);
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}).start();
				}
			}
		}
	}

	public void pauseM(){
		isPlay = false;
		mPlaying = false;
	}

	public void stopM(){
		isPlay = false;
		mPlaying = false;
		reset();
	}

	private Bitmap getBitmapFromDrawable(Drawable drawable) {
	    if (drawable == null) {
	        return null;
	    }
	    
	    if(drawable instanceof BitmapDrawable){
	    	return ((BitmapDrawable)drawable).getBitmap();
	    }
	    try {
	        Bitmap bitmap;
		    if(drawable instanceof ColorDrawable){
		    	bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
		    }else{
		    	bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
		    }
		    
		    Canvas canvas = new Canvas(bitmap);
		    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		    drawable.draw(canvas);
		    return bitmap;
	    }catch(Exception e){
	    	return null;
	    }
	}
	
	private void computeCircleCoord(float angle){
		double distanceX = mInCircleRadius * Math.sin(angle * Math.PI / 180);
		double distanceY = mInCircleRadius - (mInCircleRadius * Math.cos(angle * Math.PI / 180));
		mPointX = (float) (mBorderRect.left  + mInCircleRadius + distanceX);
		mPointY = (float) (mBorderRect.top + distanceY);
	}
	
	@Override
	public void onClick(View v) {
		new Thread(new Runnable() {
			public void run() {
				try {
					for(int i=0;i<=100;i++){
						setProgerss(i);
						Thread.sleep(100);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		playM();
	}
	
	public void setProgerss(float value){
		float angle = ( value * 1.0f / max ) * DEFAULT_MAX_ANGLE;
		setBorderAngle(angle);
	}

	public void setCacheProgress(float value){
		float angle =  (value * 1.0f / 100 ) * DEFAULT_MAX_ANGLE;
		setCacheBorderAngle(angle);
	}

	public void setMax(int max){
		this.max = max;
	}

	public void reset(){
		mPlayAngle = 0;
		setBorderAngle(0);
		setCacheBorderAngle(0);
	}
}

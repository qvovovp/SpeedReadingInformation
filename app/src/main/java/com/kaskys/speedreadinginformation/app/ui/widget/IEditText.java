package com.kaskys.speedreadinginformation.app.ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.kaskys.speedreadinginformation.app.R;
import com.kaskys.speedreadinginformation.app.ui.widget.circle.IConstantSpeed;

public class IEditText extends EditText implements OnFocusChangeListener,TextWatcher{
	private Context mContext;
	private Drawable mClearDrawable;
	
	private int oldLeft;
	private int newLeft;
	private boolean isChange = false;
	private boolean isAdd;
	
	private OnListener onComput;
	
	public IEditText(Context context) {
		this(context,null);
	}
	
	public IEditText(Context context, AttributeSet attrs) {
		this(context,attrs,android.R.attr.editTextStyle);
	}
	
	public IEditText(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
		init();
	}
	
	public void setListener(OnListener onComput){
		this.onComput = onComput;
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,int bottom) {
		if(changed){
			if(isChange){
				isChange = false;
				newLeft = left;
				layout(oldLeft, top, right, bottom);
				setScroller();
				startAnim(left, top, right, bottom);
			}else{
				if(oldLeft == 0){
					oldLeft = left;
					isChange = true;
				}
//				if(newLeft == left ){
//					oldLeft =  newLeft;
//					if(isAdd){
//						onComput.onStop();
//					}
//					return;
//				}
				super.onLayout(changed, left, top, right, bottom);
			}
		}else{
			oldLeft = left;
			isChange = true;
			super.onLayout(changed, left, top, right, bottom);
		}
	}
	
	private void init() {
		mClearDrawable = getResources().getDrawable(R.mipmap.ic_close);
		mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
		setClearIconVisible(false);
		this.setOnFocusChangeListener(this);
//		this.addTextChangedListener(this);
	}

	private void setClearIconVisible(boolean visible) {
		Drawable right = visible ? mClearDrawable : null;
		setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		 if (getCompoundDrawables()[2] != null) {
	            if (event.getAction() == MotionEvent.ACTION_UP) {
	                boolean touchable = event.getX() > (getWidth()
	                        - getPaddingRight() - mClearDrawable.getIntrinsicWidth())
	                        && (event.getX() < ((getWidth() - getPaddingRight())));
	                if (touchable) {
	                    this.setText("");
	                }
	            }
		 }
		return super.onTouchEvent(event);
	}

	private void startAnim(int left, final int top, final int right,final int bottom){
		new Thread(new Runnable() {
			public void run() {
				if(newLeft > oldLeft){
//					scroller.startScroller(0, newLeft - oldLeft);
//					z = 1;
//					isAdd = true;
				}else{
					scroller.startScroller(0, oldLeft-newLeft);
					isAdd = false;
				}
				try {
					while(scroller.computeScrollOffset()){
						post(new Runnable() {
							public void run() {
								layout((oldLeft - (int)scroller.getCurrX()), top, right, bottom);
							}
						});	
						Thread.sleep(10);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	private IConstantSpeed scroller;
	private void setScroller(){
		isChange = false;
		scroller = new IConstantSpeed();
		scroller.setDuration(200);
	}

	public interface OnListener {
		void hasFocus(boolean hasFocus);
		void onStop();
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
			onComput.hasFocus(hasFocus);
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
			closeInputMethod();
        }
	}

	private void closeInputMethod(){
		InputMethodManager im = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		boolean isOpen = im.isActive();
		if(isOpen){
			im.hideSoftInputFromWindow(this.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	@Override
	public void onTextChanged(CharSequence text, int start,int lengthBefore, int lengthAfter) {
		 setClearIconVisible(text.length() > 0);
	}
	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}
}

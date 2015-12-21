package com.kaskys.speedreadinginformation.app.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import com.kaskys.speedreadinginformation.app.R;

public class ICancelButton extends Button{
	private Animation mButtonIn,mButtonOut;
	private int mVisibility;
	
	public ICancelButton(Context context) {
		this(context,null);
	}
	
	public ICancelButton(Context context, AttributeSet attrs) {
		this(context,attrs,android.R.attr.buttonStyle);
	}
	
	public ICancelButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}
	
	private void init(Context context) {
		mButtonIn = AnimationUtils.loadAnimation(context, R.anim.button_in);
		mButtonOut = AnimationUtils.loadAnimation(context, R.anim.button_out);
		
		mButtonOut.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				setVisibility(mVisibility);
			}
		});
	}

	public void setAnimVisibility(int visibility) {
		mVisibility = visibility;
		if(visibility == View.INVISIBLE || visibility == View.GONE){
			this.startAnimation(mButtonOut);
		}else if(visibility == View.VISIBLE){
			setVisibility(mVisibility);
			this.startAnimation(mButtonIn);
		}
	}
}

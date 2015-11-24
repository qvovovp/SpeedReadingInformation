package com.kaskys.speedreadinginformation.app.ui.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.kaskys.speedreadinginformation.app.R;

public class HeaderViewPager extends ViewPager{
	private int startX;
	private int startY;

	private View mPageIndicator;
	private ViewPager.OnPageChangeListener mListener;
	private TextView tvTitle,tvNum;
	private int mChildCount;

	private Handler mHander;

	public HeaderViewPager(Context context) {
		super(context);
	}

	public HeaderViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			getParent().requestDisallowInterceptTouchEvent(true);
			
			startX = (int) ev.getRawX();
			startY = (int) ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			int endX = (int) ev.getRawX();
			int endY = (int) ev.getRawY();
			
			if(Math.abs(endX - startX) > Math.abs(endY - startY)){
				
				if(endX > startX){
					if(getCurrentItem() == 0){
						getParent().requestDisallowInterceptTouchEvent(false);
					}
				}else{
					if(getCurrentItem() == getAdapter().getCount()-1){
						getParent().requestDisallowInterceptTouchEvent(false);
					}
				}
				
			}else{
				getParent().requestDisallowInterceptTouchEvent(false);
			}
			break;
		}
		
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public void addOnPageChangeListener(OnPageChangeListener listener) {
		mListener = listener;
		super.addOnPageChangeListener(new MyPageChangeListener());
	}

	public void setPageIndicator(View pageIndicator){
		mPageIndicator = pageIndicator;
		tvTitle = (TextView) mPageIndicator.findViewById(R.id.tv_title);
		tvNum = (TextView) mPageIndicator.findViewById(R.id.tv_num);
		mChildCount = this.getAdapter().getCount();

		tvTitle.setText(getAdapter().getPageTitle(0));
		tvNum.setText("1/"+mChildCount);
	}

	private class MyPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrolled(int i, float v, int i1) {
			if(null != mListener) {
				mListener.onPageScrolled(i, v, i1);
			}
		}

		@Override
		public void onPageSelected(int i) {
			tvNum.setText((i+1)+"/"+mChildCount);
			tvTitle.setText(getAdapter().getPageTitle(i));

			if(null != mListener) {
				mListener.onPageSelected(i);
			}
		}

		@Override
		public void onPageScrollStateChanged(int i) {
			if(null != mListener) {
				mListener.onPageScrollStateChanged(i);
			}
		}
	}

	public void startCarousel(){
		if(null == mHander){
			mHander = new Handler(){
				@Override
				public void handleMessage(Message msg) {
					int currentItem = getCurrentItem();
					if(currentItem < getAdapter().getCount() - 1){
						currentItem++;
					}else{
						currentItem = 0;
					}
					setCurrentItem(currentItem);
					mHander.sendEmptyMessageDelayed(0,3000);
				}
			};
		}else{
			mHander.removeCallbacksAndMessages(null);
		}
		mHander.sendEmptyMessageDelayed(0,3000);
	}

	public void stopCarousel(){
		if(null != mHander){
			mHander.removeCallbacksAndMessages(null);
		}
	}
}

package com.kaskys.speedreadinginformation.app.ui.widget;

import android.content.Context;
import android.gesture.Gesture;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;;

public class ExpandListView extends ListView implements OnScrollListener,GestureDetector.OnGestureListener{
	private View mHeaderView;
	private GestureDetector mGesture;
	
	private int mHeaderHeight;
	
	public ExpandListView(Context context) {
		this(context,null);
	}
	
	public ExpandListView(Context context, AttributeSet attrs) {
		this(context,attrs,0);
	}
	
	public ExpandListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	
	private void init() {
		mGesture = new GestureDetector(getContext(),this);
	}

	public void setHeaderView(int resLayoutId){
		mHeaderView = View.inflate(getContext(), resLayoutId, null);
		mHeaderView.measure(0, 0);
		mHeaderHeight = mHeaderView.getMeasuredHeight();
		this.addHeaderView(mHeaderView);
	}
	
	public void setHeaderView(View headerView){
		mHeaderView = headerView;
		mHeaderView.measure(0, 0);
		mHeaderHeight = mHeaderView.getMeasuredHeight();
		this.addHeaderView(mHeaderView);
	}

	public View getHeaderView(){
		return mHeaderView;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		mGesture.onTouchEvent(ev);
		return super.onTouchEvent(ev);
	}
	
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,float distanceY) {
		if(getFirstVisiblePosition() == 0){
			float value = 1 - Math.abs(getChildAt(0).getTop() * 1.0f) / mHeaderHeight; 
			mHeaderView.setAlpha(value);
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY) {
		if(velocityY >= 500){
			mHeaderView.setAlpha(1);
		}
		return false;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
		this.setItemsCanFocus(false);
	}
}

package com.kaskys.speedreadinginformation.app.ui.widget;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.kaskys.speedreadinginformation.app.R;


public class RefreshListView extends ListView implements OnScrollListener,OnItemClickListener{
	private final static int STATE_PULL_REFRESH=0;
	private final static int STATE_RELEASE_REFRESH=1;
	private final static int STATE_REFRESHING=2;
	
	private View mHeaderView;
	private int startY = -1;

	private int mHeaderHeight;
	private TextView tvTitle;
	private TextView tvTime;
	private ImageView ivArror;
	private ProgressBar pbBar;
	
	private int mCurrentState = STATE_PULL_REFRESH;
	private RotateAnimation upAnim;
	private RotateAnimation downAnim;
	
	private OnRefreshListener mListener;
	private View mFooterView;
	private int mFooterHeight;
	private boolean isLoadingMore = false;
	
	public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initHeaderView();
		initFooterView();
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeaderView();
		initFooterView();
	}

	public RefreshListView(Context context) {
		super(context);
		initHeaderView();
		initFooterView();
	}

	private void initHeaderView() {
		mHeaderView = View.inflate(getContext(), R.layout.refresh_header, null);
		
		tvTitle = (TextView) mHeaderView.findViewById(R.id.tv_title);
		tvTime = (TextView) mHeaderView.findViewById(R.id.tv_time);
		ivArror = (ImageView) mHeaderView.findViewById(R.id.iv_arr);
		pbBar = (ProgressBar) mHeaderView.findViewById(R.id.pb_bar);
		
		mHeaderView.measure(0, 0);
		mHeaderHeight = mHeaderView.getMeasuredHeight();
		mHeaderView.setPadding(0, mHeaderHeight, 0, 0);
		this.addHeaderView(mHeaderView);
		
		tvTime.setText("最后刷新时间:"+getCurrentTime());
		initArrowAnim();
	}
	
	private void initFooterView(){
		mFooterView = View.inflate(getContext(), R.layout.refresh_footer, null);
		this.addFooterView(mFooterView);
		
		mFooterView.measure(0, 0);
		mFooterHeight = mFooterView.getMeasuredHeight();
		mFooterView.setPadding(0, -mFooterHeight, 0, 0);
	}

	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startY  = (int) ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			if(startY == -1){
				startY = (int) ev.getRawY();
			}
			
			if(mCurrentState == STATE_REFRESHING){
				break;
			}
			
			int endY = (int) ev.getRawY();
			int dy = endY - startY;
			
			if(dy > 0 && getFirstVisiblePosition() == 0){
				int padding = dy - mHeaderHeight;
				mHeaderView.setPadding(0,padding,0,0);
				
				if(padding > 0 && mCurrentState != STATE_RELEASE_REFRESH){
					mCurrentState = STATE_RELEASE_REFRESH;
					refreshState();
				}else if(padding <= 0 && mCurrentState != STATE_PULL_REFRESH){
					mCurrentState = STATE_PULL_REFRESH;
					refreshState();
				}
				
				return true;
			}
			
			break;
		case MotionEvent.ACTION_UP:
			startY = -1;
			if(mCurrentState == STATE_RELEASE_REFRESH){
				mCurrentState = STATE_REFRESHING;
				mHeaderView.setPadding(0, 0, 0, 0);
				refreshState();
			}else if(mCurrentState == STATE_PULL_REFRESH){
				mHeaderView.setPadding(0, -mHeaderHeight, 0, 0);
			}
			
			break;
		}
		
		return super.onTouchEvent(ev);
	}

	private void refreshState() {
		switch (mCurrentState) {
		case STATE_PULL_REFRESH:
			tvTitle.setText("下拉刷新");
			ivArror.setVisibility(View.VISIBLE);
			pbBar.setVisibility(View.INVISIBLE);
			
			ivArror.startAnimation(downAnim);
			break;

		case STATE_REFRESHING:
			tvTitle.setText("正在刷新...");
			ivArror.clearAnimation();
			
			ivArror.setVisibility(View.INVISIBLE);
			pbBar.setVisibility(View.VISIBLE);
			
			if(mListener != null){
				mListener.onRefresh();
			}
			break;
			
		case STATE_RELEASE_REFRESH:
			tvTitle.setText("松开刷新");
			ivArror.setVisibility(View.VISIBLE);
			pbBar.setVisibility(View.INVISIBLE);
			
			ivArror.startAnimation(upAnim);
			break;
		}
		
	}
	
	private void initArrowAnim(){
		upAnim = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		upAnim.setDuration(200);
		upAnim.setFillAfter(true);
		
		downAnim = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		downAnim.setDuration(200);
		downAnim.setFillAfter(true);
		
	}
	
	public void setOnRefreshListener(OnRefreshListener mListener) {
		this.mListener = mListener;
	}
	
	public interface OnRefreshListener{
		public void onRefresh();
		public void onLoadMore();
	}
	
	public void onRefreshComplete(){
		if(isLoadingMore){
			mFooterView.setPadding(0, -mFooterHeight, 0, 0);
			isLoadingMore = false;
		}{
			mCurrentState = STATE_PULL_REFRESH;
			tvTitle.setText("下拉刷新");
			ivArror.setVisibility(View.VISIBLE);
			pbBar.setVisibility(View.INVISIBLE);
			
			mHeaderView.setPadding(0, -mHeaderHeight, 0, 0);
			
			tvTime.setText("最后刷新时间:"+getCurrentTime());
		}
	}
	
	public String getCurrentTime(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}

	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_TOUCH_SCROLL){
			if(getLastVisiblePosition() == getCount()-1 && isLoadingMore == false){
				mFooterView.setPadding(0, 0, 0, 0);
				setSelection(getCount() - 1);
				isLoadingMore = true;
				if(mListener!=null){
					mListener.onLoadMore();
				}
			}
		}
	}
	
	public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount){
		this.setItemsCanFocus(false);
	}
	
	OnItemClickListener mItemClickListener;
	
	public void setOnItemClickListener(OnItemClickListener listener) {
		mItemClickListener = listener;
		super.setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		if(mItemClickListener!=null){
			mItemClickListener.onItemClick(parent, view, position - getHeaderViewsCount(), id);
		}
	}
}

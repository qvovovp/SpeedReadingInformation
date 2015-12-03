package com.kaskys.speedreadinginformation.app.ui.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaskys.speedreadinginformation.app.R;

public class IRefreshView extends LinearLayout{
	private final static int HEADER_STATE_PULL_REFRESH = 0;
	private final static int HEADER_STATE_RELEASE_REFRESH = 1;
	private final static int HEADER_STATE_REFRESHING = 2;
	private final static int FOOT_STATE_PULL_REFRESH = 3;
	private final static int FOOT_STATE_RELEASE_REFRESH = 4;
	private final static int FOOT_STATE_REFRESHING = 5;
	
	private Context mContext;
	
	private View mHeaderView;
	private TextView title;
	private ImageView topImg,topImg2,topCircle,footCircle;
	
	private View mFootView;
	
	private RecyclerView mRecyclerView;
	private StaggeredGridLayoutManager mStaggered;
	
	private int mHeaderState = HEADER_STATE_PULL_REFRESH;
	private int mFootState = FOOT_STATE_PULL_REFRESH;
	private int[] visItem;
	
	private int startY = -1;
	private int headerY = -1;
	private int bottomY = -1;
	private int mHeaderHeight;
	private int mFootHeight;
	
	private int mHeight;
	private int mItemCount;
	
	private boolean isRefresh = false;
	private boolean isShowHeader = true;
	private boolean isLoadMore = false;
	private boolean isShowToast = false;
	
	private OnRefreshViewChange mOnRefreshViewChange;
	private OnRefreshListener mOnRefreshListener;
	
	public IRefreshView(Context context) {
		super(context);
		init(context);
	}
	
	public IRefreshView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public IRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		setOrientation(VERTICAL);
		
		mRecyclerView = new RecyclerView(mContext);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,0);
		params.weight = 1;
		mRecyclerView.setLayoutParams(params);
		
		visItem = new int[2];
		
		setHeaderView(R.layout.irefresh_header);
		setFootView(R.layout.irefresh_foot);
		setOnRefreshViewChange();
	}
	
	private void setOnRefreshViewChange(){
		mOnRefreshViewChange = new RefreshChanger();
	}
	
	private void setHeaderView(View headerView){
		if(null == headerView){
			return;
		}
		mHeaderView = headerView;
		title = (TextView) mHeaderView.findViewById(R.id.title);
		topImg = (ImageView) mHeaderView.findViewById(R.id.top_img);
		topImg2 = (ImageView) mHeaderView.findViewById(R.id.top_img2);
		topCircle = (ImageView) mHeaderView.findViewById(R.id.top_circle);
		
		this.addView(mHeaderView, 0);
		this.addView(mRecyclerView, 1);
		
		mHeaderView.measure(0, 0);
		mHeaderHeight = mHeaderView.getMeasuredHeight();
		mHeaderView.setPadding(0, -mHeaderHeight, 0, 0);
		
		mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			public void onGlobalLayout() {
				mRecyclerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				mHeight = mRecyclerView.getHeight();
			}
		});
	}

	private void setHeaderView(int layoutResId){
		if(0 == layoutResId){
			return;
		}
		View header = View.inflate(mContext, layoutResId, null);
		setHeaderView(header);
	}
	
	private void setFootView(View footView){
		if(null == footView){
			return;
		}
		mFootView = footView;
		footCircle = (ImageView) mFootView.findViewById(R.id.img_foot);
		
		this.addView(mFootView,2);
		
		mFootView.measure(0, 0);
		mFootHeight = mFootView.getMeasuredHeight();
		mFootView.setPadding(0, -mFootHeight, 0, 0);
	}
	
	private void setFootView(int layoutResId){
		if(0 == layoutResId){
			return;
		}
		View foot = View.inflate(mContext, layoutResId, null);
		setFootView(foot);
	}
	
	public void setStaggeredGridLayoutManager(StaggeredGridLayoutManager layoutManager){
		mStaggered = layoutManager;
		mRecyclerView.setLayoutManager(mStaggered);
	}
	
	public void setAdapter(RecyclerView.Adapter adapter){
		mItemCount = adapter.getItemCount();
		mRecyclerView.setAdapter(adapter);
	}
	
	public void setOnRefreshListener(OnRefreshListener listener){
		if(null != listener){
			mOnRefreshListener = listener;
		}
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startY = (int) event.getRawY();
			isShowToast = true;
			break;
		case MotionEvent.ACTION_MOVE:
			if(-1 == startY){
				startY = (int) event.getRawY();
			}
			
			int endY = (int) event.getRawY();
			int dy = endY - startY;
			mStaggered.findFirstVisibleItemPositions(visItem);
			
			if(mHeaderState == HEADER_STATE_REFRESHING){
				if(dy < 0 && mHeaderView.getPaddingTop() > -mHeaderHeight){
					mHeaderView.setPadding(0,dy,0,0);
					isShowHeader = false;
				}else if(dy >= 0 && dy <= mHeaderHeight && mHeaderView.getPaddingTop() < 0){
					int padding = dy - mHeaderHeight;
					mHeaderView.setPadding(0, padding, 0, 0);
					isShowHeader = true;
				}
				break;
			}
			
			if(mFootState != FOOT_STATE_REFRESHING){
				if(0 == visItem[0]){
					if(-1 == headerY){
						headerY = (int) event.getRawY();
					}
					endY = (int) event.getRawY();
					dy = endY - headerY;
					
					if(dy > 0 && dy <= (mHeaderHeight*3/2) && mStaggered.findViewByPosition(0).getTop() >= -10){
						isShowHeader = true;
						int padding = dy - mHeaderHeight;
						mHeaderView.setPadding(0,padding,0,0);
						
						if(null != mOnRefreshViewChange){
							if(padding <= 0){
								float offset = ((0-padding*1.0f)*100/mHeaderHeight);
								mOnRefreshViewChange.onHeaderChange(topImg,topImg2,(int)offset);
							}
						}
						
						if(padding > 0 && mHeaderState != HEADER_STATE_RELEASE_REFRESH){
							mHeaderState = HEADER_STATE_RELEASE_REFRESH;
							refreshState();
						}else if(padding <= 0 && mHeaderState != HEADER_STATE_PULL_REFRESH){
							mHeaderState = HEADER_STATE_PULL_REFRESH;
							refreshState();
						}
					}
					break;
				}
			}
			
			if(mFootState == FOOT_STATE_REFRESHING){
				if(isShowToast){
					Toast.makeText(mContext, "正在刷新...", Toast.LENGTH_SHORT).show();
					isShowToast = false;
				}
				return true;
			}
			
			if(mHeaderState != HEADER_STATE_REFRESHING){
				mStaggered.findLastVisibleItemPositions(visItem);
				if((mItemCount - 1) == visItem[1]){
					if(mHeight >= mStaggered.findViewByPosition((mItemCount-1)).getBottom()){
						isLoadMore = true;
						if(-1 == bottomY){
							bottomY = (int) event.getRawY();
						}
						endY = (int) event.getRawY();
						dy = endY - bottomY;
						int padding = dy + mFootHeight;
						if(padding >= -10){
							mFootView.setPadding(0, -padding, 0, 0);
						}
						if(null != mOnRefreshViewChange){
							if(padding >= 0){
								mOnRefreshViewChange.onFootChangee(padding);
							}
						}
						
						if(padding <= 0 && mFootState != FOOT_STATE_RELEASE_REFRESH){
							mFootState = FOOT_STATE_RELEASE_REFRESH;
						}else if(padding > 0 && mFootState != FOOT_STATE_PULL_REFRESH){
							mFootState = FOOT_STATE_PULL_REFRESH;
						}
					}
					break;
				}
			}
			
			break;
		case MotionEvent.ACTION_UP:
			startY = -1;
			headerY = -1;
			bottomY = -1;
			if(mHeaderState == HEADER_STATE_RELEASE_REFRESH || mHeaderState == HEADER_STATE_REFRESHING){
				mHeaderState = HEADER_STATE_REFRESHING;
				if(isShowHeader){
					mHeaderView.setPadding(0, 0, 0, 0);
				}else{
					mHeaderView.setPadding(0, -mHeaderHeight, 0, 0);
				}
				refreshState();
			}else if(mHeaderState == HEADER_STATE_PULL_REFRESH){
				mHeaderView.setPadding(0, -mHeaderHeight, 0, 0);
			}
			
			if(mFootState == FOOT_STATE_PULL_REFRESH){
				mFootView.setPadding(0, -mFootHeight, 0, 0);
			}else if(mFootState == FOOT_STATE_REFRESHING || mFootState == FOOT_STATE_RELEASE_REFRESH){
				mFootState = FOOT_STATE_REFRESHING;
				mFootView.setPadding(0, 0, 0, 0);
				footCircle.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.irefresh_circle));
				refreshState();
			}
			break;
		}
		return super.dispatchTouchEvent(event);
	}

	private void refreshState() {
		switch (mHeaderState) {
		case HEADER_STATE_PULL_REFRESH:
			title.setText("下拉刷新");
			topCircle.clearAnimation();
			topImg.setVisibility(View.VISIBLE);
			topImg2.setVisibility(View.VISIBLE);
			topCircle.setVisibility(View.GONE);
			break;
		case HEADER_STATE_RELEASE_REFRESH:
			title.setText("松开刷新");
			break;
		case HEADER_STATE_REFRESHING:
			title.setText("正在刷新...");
			topImg.setVisibility(View.GONE);
			topImg2.setVisibility(View.GONE);
			topCircle.setVisibility(View.VISIBLE);
			topCircle.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.irefresh_circle));
			if(null != mOnRefreshListener && !isRefresh){
				mOnRefreshListener.onRefresh();
				isRefresh = true;
			}
			break;
		}
		
		if(mFootState == FOOT_STATE_REFRESHING && null != mOnRefreshListener){
			mOnRefreshListener.onLoadMore();
		}
	}
	
	public interface OnRefreshViewChange{
		void onHeaderChange(View one, View two, int offset);
		void onFootChangee(int offset);
	}
	
	public interface OnRefreshListener{
		void onRefresh();
		void onLoadMore();
	}
	
	public void onRefreshComplete(){
		if(isLoadMore){
			isLoadMore = false;
			mFootState = FOOT_STATE_PULL_REFRESH;
			mFootView.setPadding(0, -mFootHeight, 0, 0);
			footCircle.clearAnimation();
		}else{
			mHeaderState = HEADER_STATE_PULL_REFRESH;
			mHeaderView.setPadding(0, -mHeaderHeight, 0, 0);
			isRefresh = false;
			refreshState();
		}
	}
	
	private class RefreshChanger implements OnRefreshViewChange{

		public void onHeaderChange(View one, View two, int offset) {
			System.out.println("-->"+(100 - offset+0.0f)/100);
			two.setScaleX((100 - offset+0.0f)/100);
			two.setScaleY((100 - offset+0.0f)/100);
			one.setPadding(0, offset, 0, 0);
		}

		public void onFootChangee(int offset) {
			
		}
		
	}
}

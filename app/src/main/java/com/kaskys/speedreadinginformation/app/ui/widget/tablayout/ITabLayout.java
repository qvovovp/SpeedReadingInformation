package com.kaskys.speedreadinginformation.app.ui.widget.tablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.kaskys.speedreadinginformation.app.R;

public class ITabLayout extends HorizontalScrollView{
    public interface TabColorizer {
        int getIndicatorColor(int position);
        int getDividerColor(int position);

    }
	
    private static final boolean DEFAULT_DISTRIBUTE_EVENLY = false;

    private static final int TITLE_OFFSET_DIPS = 24;
    private static final int TAB_VIEW_PADDING_DIPS = 16;
    private static final boolean TAB_VIEW_TEXT_ALL_CAPS = true;
    private static final int TAB_VIEW_TEXT_SIZE_SP = 12;
    private static final int TAB_VIEW_TEXT_COLOR = 0xFC000000;
    private static final int TAB_VIEW_TEXT_MIN_WIDTH = 0;
    
	private boolean mTabViewTextAllCaps;
	private boolean mDistributeEvenly;
	
	private int mTitleOffset;
	private int mTabViewTextColor;
	private int mTabViewTextHorizontalPadding;
	private int mTabViewTextMinWidth;
	private int mTabViewLayoutId;
	private int mTabViewTextViewId;
	
	private float mTabViewTextSize;
	
    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mViewPagerPageChangeListener;
	
	private ITabStrip mTabStrip;
	
	public ITabLayout(Context context) {
	this(context,null);
		// TODO Auto-generated constructor stub
	}
	
	public ITabLayout(Context context, AttributeSet attrs) {
		this(context,attrs,0);
		// TODO Auto-generated constructor stub
	}
	
	public ITabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		setHorizontalScrollBarEnabled(false);
		setFillViewport(true);
		setOverScrollMode(OVER_SCROLL_NEVER);
		final DisplayMetrics dm = getResources().getDisplayMetrics();
		final float density = dm.density;
		
		mTitleOffset = (int) (TITLE_OFFSET_DIPS * density);
		boolean textAllCaps = TAB_VIEW_TEXT_ALL_CAPS;
		int textColor = TAB_VIEW_TEXT_COLOR;
		float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, TAB_VIEW_TEXT_SIZE_SP, dm);
		int textHorizontalPadding = (int) (TAB_VIEW_PADDING_DIPS * density);
		int textMinWidth = (int) (TAB_VIEW_TEXT_MIN_WIDTH * density);
		boolean distributeEvenly = DEFAULT_DISTRIBUTE_EVENLY;
		int textLayoutId = NO_ID;
		int textViewId = NO_ID;
		
		TypedArray ta  = context.obtainStyledAttributes(attrs, R.styleable.stl_SmartTabLayout,defStyleAttr,0);
        textAllCaps = ta.getBoolean(R.styleable.stl_SmartTabLayout_stl_defaultTabTextAllCaps, textAllCaps);
        textColor = ta.getColor(R.styleable.stl_SmartTabLayout_stl_defaultTabTextColor, textColor);
        textSize = ta.getDimension(R.styleable.stl_SmartTabLayout_stl_defaultTabTextSize, textSize);
        textHorizontalPadding = ta.getDimensionPixelSize(R.styleable.stl_SmartTabLayout_stl_defaultTabTextHorizontalPadding, textHorizontalPadding);
        textMinWidth = ta.getDimensionPixelSize(R.styleable.stl_SmartTabLayout_stl_defaultTabTextMinWidth, textMinWidth);
        textLayoutId = ta.getResourceId(R.styleable.stl_SmartTabLayout_stl_customTabTextLayoutId, textLayoutId);
        textViewId = ta.getResourceId(R.styleable.stl_SmartTabLayout_stl_customTabTextViewId, textViewId);
        distributeEvenly = ta.getBoolean(R.styleable.stl_SmartTabLayout_stl_distributeEvenly, distributeEvenly);
		ta.recycle();
		
		mTitleOffset= (int)(TITLE_OFFSET_DIPS * density);
		mTabViewTextAllCaps = textAllCaps;
		mTabViewTextColor = textColor;
		mTabViewTextSize = textSize;
		mTabViewTextHorizontalPadding = textHorizontalPadding;
		mTabViewTextMinWidth = textMinWidth;
		mTabViewLayoutId = textLayoutId;
		mTabViewTextViewId = textViewId;
		mDistributeEvenly = distributeEvenly;
		
		mTabStrip = new ITabStrip(context,attrs);
		
		if(distributeEvenly && mTabStrip.isIndicatorAlwaysInCenter()){
            throw new UnsupportedOperationException(
                    "'distributeEvenly' and 'indicatorAlwaysInCenter' both use does not support");
		}
		
		addView(mTabStrip,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		if(mTabStrip.isIndicatorAlwaysInCenter() && getChildCount() > 0){
			int left = (w - mTabStrip.getChildMeasuredWidthAt(0)) / 2;
			int right = (w - mTabStrip.getChildMeasuredWidthAt(getChildCount() - 1)) / 2;
			setPadding(left, getPaddingTop(), right, getPaddingRight());
			setClipToPadding(false);
		}
	}
	
	public void setCustomTabColorizer(TabColorizer tabColorizer){
		mTabStrip.setCustomTabColorizer(tabColorizer);
	}
	
	public void setDistributeEvenly(boolean distributeEvenly){
		mDistributeEvenly = distributeEvenly;
	}
	
	public void setSelectedINdicatorColors(int ... colors){
		mTabStrip.setSelectedIndicatorColors(colors);
	}
	
	public void setDivideColors(int... colors){
		mTabStrip.setDividerColors(colors);
	}
	
	public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener){
		mViewPagerPageChangeListener = listener;
	}
	
	public void setCustomTabView(int layoutResId,int textViewResId){
		mTabViewLayoutId = layoutResId;
		mTabViewTextViewId = textViewResId;
	}
	
	public void setViewPager(ViewPager viewPager){
		mTabStrip.removeAllViews();
		
		mViewPager = viewPager;
		if(null != viewPager){
			viewPager.addOnPageChangeListener(new InternalViewPagerListener());
			populateTabStrip();
		}
	}
	
	protected TextView createDefaultTabView(Context context){
		TextView textView = new TextView(context);
		textView.setGravity(Gravity.CENTER);
		textView.setTextColor(mTabViewTextColor);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,mTabViewTextSize);
		textView.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			TypedValue outValue = new TypedValue();
			getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
			textView.setBackgroundResource(outValue.resourceId);
		}
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
			textView.setAllCaps(mTabViewTextAllCaps);
		}
		
		textView.setPadding(mTabViewTextHorizontalPadding, 0,
				mTabViewTextHorizontalPadding, 0);
		
		if(mTabViewTextMinWidth > 0){
			textView.setMinWidth(mTabViewTextMinWidth);
		}
		
		return textView;
	} 
	
	private void populateTabStrip(){
		final PagerAdapter adapter = mViewPager.getAdapter();
		final OnClickListener tabClickListener = new TabClickListener();
		
		final LayoutInflater inflater = LayoutInflater.from(getContext());
		for(int i=0;i<adapter.getCount();i++){
			View tabView = null;
			TextView tabTitleView = null;
			
			if(mTabViewLayoutId != NO_ID){
				tabView = inflater.inflate(mTabViewLayoutId, mTabStrip,false);
			}
			if(mTabViewTextViewId != NO_ID && tabView != null){
				tabTitleView = (TextView) tabView.findViewById(mTabViewTextViewId);
			}
			
			if(tabView == null){
				tabView = createDefaultTabView(getContext());	
			}
			if(tabTitleView == null && TextView.class.isInstance(tabView)){
				tabTitleView = (TextView) tabView;
			}
			
            if (tabTitleView == null) {
                throw new IllegalStateException("tabTitleView not found.");
            }
            
            if(mDistributeEvenly){
            	LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tabView.getLayoutParams();
            	lp.width = 0;
            	lp.weight = 1;
            }
            
            tabTitleView.setText(adapter.getPageTitle(i));
            tabView.setOnClickListener(tabClickListener);
            
            mTabStrip.addView(tabView);
            if(i == mViewPager.getCurrentItem()){
            	tabView.setSelected(true);
            }
		}
	}
	
	@Override
	protected void onAttachedToWindow() {
		// TODO Auto-generated method stub
		super.onAttachedToWindow();
		if(null != mViewPager){
			scrollToTab(mViewPager.getCurrentItem(),0);
		}
	}

	private void scrollToTab(int tabIndex, int positionOffset) {
		final int tabStripChildCount = mTabStrip.getChildCount();
		if(tabStripChildCount <= 0 || tabIndex < 0 || tabStripChildCount <= tabIndex){
			return;
		}
		
		View selectedChild = mTabStrip.getChildAt(tabIndex);
		if(null != selectedChild){
			int targetScrollX = selectedChild.getLeft() + positionOffset;
			if (mTabStrip.isIndicatorAlwaysInCenter()) {
				targetScrollX -= (mTabStrip.getChildWidthAt(0) - selectedChild.getWidth()) / 2;
			} else if (tabIndex > 0 || positionOffset > 0) {
                targetScrollX -= mTitleOffset;
            }
			scrollTo(targetScrollX, 0);
		}
	}
	
	private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {
		private int mScrollState;
		
		@Override
		public void onPageScrollStateChanged(int state) {
			// TODO Auto-generated method stub
			mScrollState = state;
			if(null != mViewPagerPageChangeListener){
				mViewPagerPageChangeListener.onPageScrollStateChanged(state);
			}
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			// TODO Auto-generated method stub
			int tabStripChildCount = mTabStrip.getChildCount();
            if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
                return;
            }
            
            mTabStrip.onViewPagerPageChanged(position,positionOffset);
            View selectTitle = mTabStrip.getChildAt(position);
            int extraOffset = (selectTitle != null) ? (int)(positionOffset*selectTitle.getWidth()) : 0;
            
            if(0 < positionOffset && positionOffset < 1f && mTabStrip.isIndicatorAlwaysInCenter()){
            	int current = mTabStrip.getChildWidthAt(position) / 2;
            	int next = mTabStrip.getChildWidthAt(position + 1) / 2;
            	extraOffset = Math.round(positionOffset * (current + next));
            }
            
            scrollToTab(position, extraOffset);
            
            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageScrolled(position, positionOffset,
                        positionOffsetPixels);
            }
		}

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			if(mScrollState == ViewPager.SCROLL_STATE_IDLE){
				mTabStrip.onViewPagerPageChanged(position,0);
				scrollToTab(position, 0);
			}
			
            for (int i = 0, size = mTabStrip.getChildCount(); i < size; i++) {
                mTabStrip.getChildAt(i).setSelected(position == i);
            }

            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageSelected(position);
            }
		}
		
	}
	
    private class TabClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                if (v == mTabStrip.getChildAt(i)) {
                    mViewPager.setCurrentItem(i);
                    return;
                }
            }
        }
    }
}

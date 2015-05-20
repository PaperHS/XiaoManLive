/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.insthub.ecmobile.component;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.insthub.ecmobile.adapter.MainPageAdapter;

/**
 * This is forked from com.astuetz.pagerslidingtabstrip.
 * 
 * @author Vincent Cheung
 * @since  Dec. 09, 2014
 */
public class PagerSlidingTabStrip extends HorizontalScrollView implements OnPageChangeListener {
	/** icon tab provider */
	public interface IconTabProvider {
		int getPageIconResId(int position);
	}
	
	/** text tab with number indicator provider */
	public interface NumTabProvider {
		int getNumber(int position);
	}
	
	/** system arrtributes */
	private static final int[] ATTRS = new int[] {
		android.R.attr.textSize,
		android.R.attr.textColor
    };
	
	private int mTabCount = 0;
	private int mCurrentPosition = 0;
	private float mCurrentPositionOffset = 0f;
	private boolean mShouldExpand = true;
	private int mScrollOffset = 52;
	private int mLastScrollX = 0;
	private int mNumPadding = 8;
	private int mTabPadding = 25;
	private int mTabTextSize = 16;
	private int mNumTextSize = 10;
	
	private int mTabTextColor = 0xFF232323;
	private int mIndicatorColor = 0xFFFF8050;
	private int mUnderlineColor = 0xFFDDDDDD;
	private int mNumTextColor = Color.WHITE;
	private int mNumBackground = 0xFFFF8050;

	private float mCornerRadius = 4f;
	private float mIndicatorHeight = 4f;
	private float mUnderlineHeight = 0f;
	
	private Typeface mTabTypeface = null;
	private int mTabTypefaceStyle = Typeface.NORMAL;
	
	private int mTabBackgroundResId = android.R.color.transparent;
	
	private Paint mRectPaint;
	
	/** default tab layout params */
	private LinearLayout.LayoutParams mDefaultTabLayoutParams;
	/** expanded tab layout params */
	private LinearLayout.LayoutParams mExpandedTabLayoutParams;
	/** number layout params in number tab */
	private LinearLayout.LayoutParams mNumLayoutParams;
	/** tabs container to put tabs */
	private LinearLayout mTabsContainer;
	/** view pager */
	private ViewPager mViewPager;
	
	/** page change listener */
	private OnPageChangeListener mOnPageChangeListener;
	
	public PagerSlidingTabStrip(Context context) {
		this(context, null);
	}

	public PagerSlidingTabStrip(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public PagerSlidingTabStrip(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		setFillViewport(true);
		setWillNotDraw(false);
		
		mTabsContainer = new LinearLayout(context);
		mTabsContainer.setOrientation(LinearLayout.HORIZONTAL);
		mTabsContainer.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		addView(mTabsContainer);
		
		DisplayMetrics dm = getResources().getDisplayMetrics();
		mIndicatorHeight = (float) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, mIndicatorHeight, dm);
		mTabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTabTextSize, dm);
		mNumTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mNumTextSize, dm);
		
		TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);
		mTabTextSize = a.getDimensionPixelSize(0, mTabTextSize);
		mNumTextSize = a.getDimensionPixelSize(0, mNumTextSize);
		mTabTextColor = a.getColor(1, mTabTextColor);
		a.recycle();
		
		mRectPaint = new Paint();
		mRectPaint.setAntiAlias(true);
		mRectPaint.setStyle(Style.FILL);

		mDefaultTabLayoutParams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		mExpandedTabLayoutParams = new LinearLayout.LayoutParams(
				0, LayoutParams.MATCH_PARENT, 1.0f);
		mNumLayoutParams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		mNumLayoutParams.setMargins(mNumPadding, 0, 0, 0);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (isInEditMode() || mTabCount == 0) {
			return;
		}

		final int height = getHeight();

		/* default: line below current tab */
		View currentTab = mTabsContainer.getChildAt(mCurrentPosition);
		float lineLeft = currentTab.getLeft();
		float lineRight = currentTab.getRight();

		/* if there is an offset, start interpolating left and right coordinates between current and next tab */
		if (mCurrentPositionOffset > 0f && mCurrentPosition < mTabCount - 1) {
			View nextTab = mTabsContainer.getChildAt(mCurrentPosition + 1);
			final float nextTabLeft = nextTab.getLeft();
			final float nextTabRight = nextTab.getRight();

			lineLeft = (mCurrentPositionOffset * nextTabLeft + (
					1f - mCurrentPositionOffset) * lineLeft);
			lineRight = (mCurrentPositionOffset * nextTabRight + (
					1f - mCurrentPositionOffset) * lineRight);
		}

		/* draw indicator line */
		mRectPaint.setColor(mIndicatorColor);
		canvas.drawRect(lineLeft, height - mIndicatorHeight, lineRight, height, mRectPaint);

		/* draw underline */
		if (mUnderlineHeight != 0) {
			mRectPaint.setColor(mUnderlineColor);
			canvas.drawRect(0, height - mUnderlineHeight, 
					mTabsContainer.getWidth(), height, mRectPaint);
		}
	}
	
	@Override
	public void onRestoreInstanceState(Parcelable state) {
		SavedState savedState = (SavedState) state;
		super.onRestoreInstanceState(savedState.getSuperState());
		mCurrentPosition = savedState.currentPosition;
		
		requestLayout();
	}

	@Override
	public Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();
		SavedState savedState = new SavedState(superState);
		savedState.currentPosition = mCurrentPosition;
		
		return savedState;
	}
	
	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		mCurrentPosition = position;
		mCurrentPositionOffset = positionOffset;

		scrollToChild(position, (int) (positionOffset * mTabsContainer.getChildAt(position).getWidth()));
		invalidate();

		/* if we have page listener, invoke it */
		if (mOnPageChangeListener != null) {
			mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
		}
	}
	
	@Override
	public void onPageScrollStateChanged(int state) {
		if (state == ViewPager.SCROLL_STATE_IDLE) {
			scrollToChild(mViewPager.getCurrentItem(), 0);
		}

		/* if we have page listener, invoke it */
		if (mOnPageChangeListener != null) {
			mOnPageChangeListener.onPageScrollStateChanged(state);
		}
	}
	
	@Override
	public void onPageSelected(int position) {
		updateTabStyles(position);
		
		/* if we have page listener, invoke it */
		if (mOnPageChangeListener != null) {
			mOnPageChangeListener.onPageSelected(position);
		}
	}
	
	/** saved state */
	static class SavedState extends BaseSavedState {
		int currentPosition;

		public SavedState(Parcelable superState) {
			super(superState);
		}

		private SavedState(Parcel in) {
			super(in);
			currentPosition = in.readInt();
		}

		@Override
		public void writeToParcel(@NonNull Parcel dest, int flags) {
			super.writeToParcel(dest, flags);
			dest.writeInt(currentPosition);
		}

		public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
			@Override
			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}

			@Override
			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}
		};
	}
	
	/** add one text tab */
	private void addTextTab(final int position, String title,int res) {
		TextView tab = new TextView(getContext());
		tab.setText(title);
		tab.setGravity(Gravity.CENTER);
		tab.setSingleLine();
		Drawable drawable = getResources().getDrawable(res);
		drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
		tab.setCompoundDrawables(drawable, null, null, null);
		addTab(position, tab);
	}
		/** add one text tab */
	private void addTextTab(final int position, String title) {
		TextView tab = new TextView(getContext());
		tab.setText(title);
		tab.setGravity(Gravity.CENTER);
		tab.setSingleLine();

		addTab(position, tab);
	}

	/** add one number indicator tab */
	private void addNumTab(final int position, String title, int num) {
		LinearLayout tab = new LinearLayout(getContext());
		TextView titleText = new TextView(getContext());
		TextView numText = new TextView(getContext());
		
		titleText.setText(title);
		titleText.setGravity(Gravity.CENTER);
		titleText.setSingleLine();

		numText.setText(Integer.toString(num));
		numText.setGravity(Gravity.CENTER);
		numText.setPadding(mNumPadding, 1, mNumPadding, 1);
		numText.setSingleLine();
		
		/* if the number is 0, set invisible */
		if (num == 0) {
			numText.setVisibility(View.GONE);
		} else {
			numText.setVisibility(View.VISIBLE);
		}
		
		tab.addView(titleText, 0, mDefaultTabLayoutParams);
		tab.addView(numText, 1, mNumLayoutParams);
		tab.setGravity(Gravity.CENTER);
		
		addTab(position, tab);
	}

	/** add one icon tab */
	private void addIconTab(final int position, int resId) {
		ImageButton tab = new ImageButton(getContext());
		tab.setImageResource(resId);
		
		addTab(position, tab);
	}

	/** add one tab into tab container */
	private void addTab(final int position, View tab) {
		tab.setFocusable(true);
		tab.setPadding(mTabPadding, 0, mTabPadding, 0);
		tab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mViewPager.setCurrentItem(position);
			}
		});

		mTabsContainer.addView(tab, position, mShouldExpand ?
				mExpandedTabLayoutParams : mDefaultTabLayoutParams);
	}

	/** 
	 * update tab styles, the tab at 'position' will be updated to indicator style, 
	 * and others will be updated to default style 
	 */
	private void updateTabStyles(int position) {
		int textColor;
		mTabsContainer.setBackgroundResource(mTabBackgroundResId);
		
		for (int i = 0; i < mTabCount; i++) {
			if (i == position) {
				textColor = mIndicatorColor;
			} else {
				textColor = mTabTextColor;
			}
			
			/* only change the style of title text */
			View v = mTabsContainer.getChildAt(i);
			if (v instanceof TextView) {
				TextView tab = (TextView) v;
				updateTabTextStyle(tab, textColor);
			} else if (v instanceof LinearLayout) {
				View tabTitle = ((LinearLayout) v).getChildAt(0);
				View tabNum = ((LinearLayout) v).getChildAt(1);
				
				/* update title */
				if (tabTitle instanceof TextView) {
					TextView titleText = (TextView) tabTitle;
					updateTabTextStyle(titleText, textColor);
				}
				
				/* update number */
				if (tabNum instanceof TextView) {
					TextView numText = (TextView) tabNum;
					updateNumTextStyle(numText);
				}
			}
		}
	}
	
	/** update the style of title text */
	private void updateTabTextStyle(TextView tab, int color) {
		tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabTextSize);
		tab.setTypeface(mTabTypeface, mTabTypefaceStyle);
		tab.setTextColor(color);
	}
	
	/** update the style of number text */
	private void updateNumTextStyle(TextView tab) {
		tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, mNumTextSize);
		tab.setTypeface(mTabTypeface, mTabTypefaceStyle);
		tab.setTextColor(mNumTextColor);
		
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
			setBackgroud(tab);
		} else {
			setBackgroundJelly(tab);
		}
	}
	
	@SuppressWarnings("deprecation")
	private void setBackgroud(TextView tab) {
		tab.setBackgroundDrawable(createDrawable());
	}
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void setBackgroundJelly(TextView tab) {
		tab.setBackground(createDrawable());
	}
	
	/** get background drawable */
	private GradientDrawable createDrawable() {
		GradientDrawable drawable = new GradientDrawable();
		drawable.setCornerRadius(mCornerRadius);
		drawable.setColor(mNumBackground);
		
		return drawable;
	}

	/** scroll to child with new position */
	private void scrollToChild(int position, int offset) {
		if (mTabCount == 0) {
			return;
		}

		int newScrollX = mTabsContainer.getChildAt(position).getLeft() + offset;

		if (position > 0 || offset > 0) {
			newScrollX -= mScrollOffset;
		}

		if (newScrollX != mLastScrollX) {
			mLastScrollX = newScrollX;
			scrollTo(newScrollX, 0);
		}
	}
	
	/**
	 * Set view pager used in strip.
	 * 
	 * @param viewPager view pager
	 */
	public void setViewPager(ViewPager viewPager) {
		mViewPager = viewPager;

		if (mViewPager.getAdapter() == null) {
			throw new IllegalStateException("ViewPager does not have adapter instance.");
		}
		
		mViewPager.setOnPageChangeListener(this);
		notifyDataSetChanged();
	}
	
	/**
	 * Set on page change listener.
	 * 
	 * @param l page change listener
	 */
	public void setOnPageChangeListener(OnPageChangeListener l) {
		mOnPageChangeListener = l;
	}
	
	/**
	 * Notify data set changed.
	 */
	public void notifyDataSetChanged() {
		mTabsContainer.removeAllViews();
		mTabCount = mViewPager.getAdapter().getCount();

		for (int i = 0; i < mTabCount; i++) {
			if (mViewPager.getAdapter() instanceof IconTabProvider) {
				addIconTab(i, ((IconTabProvider) mViewPager.getAdapter()).getPageIconResId(i));
			} else if (mViewPager.getAdapter() instanceof NumTabProvider) {
				int num = ((NumTabProvider) mViewPager.getAdapter()).getNumber(i);
				num = num < 0 ? 0 : num;
				addNumTab(i, mViewPager.getAdapter().getPageTitle(i).toString(), num);
			} else {
				if (mViewPager.getAdapter() instanceof MainPageAdapter)
					addTextTab(i, mViewPager.getAdapter().getPageTitle(i).toString(),((MainPageAdapter)mViewPager.getAdapter()).getPageIconRes(i));
				else addTextTab(i,mViewPager.getAdapter().getPageTitle(i).toString());
			}
		}
		
		updateTabStyles(mViewPager.getCurrentItem());

		getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
					removeLayoutListener(this);
				} else {
					removeLayoutListenerJelly(this);
				}

				mCurrentPosition = mViewPager.getCurrentItem();
				if (mCurrentPosition > mTabCount) {
					mCurrentPosition = mTabCount;
				}

				scrollToChild(mCurrentPosition, 0);
			}
		});
	}
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void removeLayoutListenerJelly(OnGlobalLayoutListener l) {
		getViewTreeObserver().removeOnGlobalLayoutListener(l);
	}
	
	@SuppressWarnings("deprecation")
	private void removeLayoutListener(OnGlobalLayoutListener l) {
		getViewTreeObserver().removeGlobalOnLayoutListener(l);
	}

	/**
	 * Set the padding of tab.
	 *
	 * @param padding padding
	 */
	public void setTabPadding(int padding) {
		mTabPadding = padding;
	}

	/**
	 * Should expand the tab or not.
	 * 
	 * @param shouldExpand true or false
	 */
	public void setShouldExpand(boolean shouldExpand) {
		mShouldExpand = shouldExpand;
		requestLayout();
	}
	
	/**
	 * Set backgroud of tab.
	 * 
	 * @param resId resource id
	 */
	public void setTabBackground(int resId) {
		mTabBackgroundResId = resId;
		invalidate();
	}
	
	/**
	 * Set the color of indicator.
	 * 
	 * @param indicatorColor color of indicator to set
	 */
	public void setIndicatorColor(int indicatorColor) {
		mIndicatorColor = indicatorColor;
		invalidate();
	}
	
	/**
	 * Set the color resource of indicator.
	 * 
	 * @param resId color resource id of indicator to set
	 */
	public void setIndicatorColorResource(int resId) {
		mIndicatorColor = getResources().getColor(resId);
		invalidate();
	}
	
	/**
	 * Set the height of indicator line.
	 * 
	 */
	public void setIndicatorHeight(float heightPx) {
		mIndicatorHeight = heightPx;
		invalidate();
	}

	/**
	 * Set text size in tab.
	 * 
	 * @param textSizeSp text size in sp
	 */
	public void setTextSize(int textSizeSp) {
		DisplayMetrics dm = getResources().getDisplayMetrics();
		mTabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSizeSp, dm);
	}
	
	/**
	 * Set the color of text in tab.
	 * 
	 * @param textColor text color
	 */
	public void setTextColor(int textColor) {
		mTabTextColor = textColor;
		updateTabStyles(mViewPager.getCurrentItem());
	}

	/**
	 * Set the color  of text in tab.
	 * 
	 * @param resId text color resource id
	 */
	public void setTextColorResource(int resId) {
		mTabTextColor = getResources().getColor(resId);
		updateTabStyles(mViewPager.getCurrentItem());
	}
	
	/**
	 * Set type face of text in tab.
	 * 
	 * @param typeface type face
	 * @param style    stype
	 */
	public void setTypeface(Typeface typeface, int style) {
		mTabTypeface = typeface;
		mTabTypefaceStyle = style;
		updateTabStyles(mViewPager.getCurrentItem());
	}
	
	/**
	 * Set the height of underline line.
	 * 
	 * @param heightPx height in pixel
	 */
	public void setUnderlineHeight(float heightPx) {
		mUnderlineHeight = heightPx;
		invalidate();
	}
	
	/**
	 * Set the color of underline.
	 * 
	 * @param underlineColor underline color
	 */
	public void setUnderlineColor(int underlineColor) {
		mUnderlineColor = underlineColor;
		invalidate();
	}
	
	/**
	 * Set the color resource of underline.
	 * 
	 * @param resId resource id
	 */
	public void setUnderlineColorResource(int resId) {
		mUnderlineColor = getResources().getColor(resId);
		invalidate();
	}
	
	/**
	 * Set the text color of number.
	 * 
	 * @param color text color
	 */
	public void setNumberTextColor(int color) {
		mNumTextColor = color;
		invalidate();
	}
	
	/**
	 * Set the color of number background.
	 * 
	 * @param color background color
	 */
	public void setNumberBackground(int color) {
		mNumBackground = color;
		invalidate();
	}
	
	/**
	 * Set the color resource of number background.
	 * 
	 * @param resId background color resource id
	 */
	public void setNumberBackgroundResource(int resId) {
		mNumBackground = getResources().getColor(resId);
		invalidate();
	}
	
	/**
	 * Set number showing in number tab.
	 * 
	 * @param number number to show
	 */
	public void setNumber(int position, int number) {
		if (position > mTabCount) {
			throw new IllegalArgumentException("the position is not valid");
		}
		
		if (!(mViewPager.getAdapter() instanceof NumTabProvider)) {
			throw new IllegalAccessError("the tab is not number tab, "
					+ "the adapter should implement NumTabProvider");
		}
		
		/* update the number text */
		View v = mTabsContainer.getChildAt(position);
		if (v instanceof LinearLayout) {
			View tabNum = ((LinearLayout) v).getChildAt(1);
			if (tabNum instanceof TextView) {
				TextView textNum = (TextView) tabNum;
				if (number == 0) {
					textNum.setVisibility(View.GONE);
				} else {
					textNum.setVisibility(View.VISIBLE);
					textNum.setText(Integer.toString(number));
				}
			}
		}
	}
}

package com.insthub.ecmobile.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.external.viewpagerindicator.PageIndicator;
import com.insthub.BeeFramework.view.MyListView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.B0_IndexAdapter;
import com.insthub.ecmobile.adapter.Bee_PageAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @InjectView(R.id.fragment_listView)
    MyListView fragmentListView;

    // TODO: Rename and change types of parameters
    private String mTitle;
    private int mIcon;
    FrameLayout bannerView;//广告栏
    private ViewPager bannerViewPager;
    private PageIndicator mIndicator;
    private MyListView mListView;
    private B0_IndexAdapter listAdapter;

    private ArrayList<View> bannerListView;
    private Bee_PageAdapter bannerPageAdapter;
    private View mTouchTarget;

    public String getTitle() {
        return getArguments().getString(ARG_PARAM1);
    }

    public int getIcon() {
        return getArguments().getInt(ARG_PARAM2, R.drawable.ic_shop1);
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopFragment newInstance(String param1, int param2) {
        ShopFragment fragment = new ShopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ShopFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_PARAM1);
            mIcon = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        ButterKnife.inject(this, view);
        init();
        return view;
    }

    private void init() {
     bannerView = (FrameLayout)LayoutInflater.from(getActivity()).inflate(R.layout.b0_index_banner, null);

        bannerViewPager = (ViewPager)bannerView.findViewById(R.id.banner_viewpager);

        ViewGroup.LayoutParams params1 = bannerViewPager.getLayoutParams();
		params1.width = getDisplayMetricsWidth();
		params1.height = (int) (params1.width*1.0/484*200);

		bannerViewPager.setLayoutParams(params1);

        bannerListView = new ArrayList<View>();


        bannerPageAdapter = new Bee_PageAdapter(bannerListView);

        bannerViewPager.setAdapter(bannerPageAdapter);
        bannerViewPager.setCurrentItem(0);

        bannerViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            private int mPreviousState = ViewPager.SCROLL_STATE_IDLE;
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // All of this is to inhibit any scrollable container from consuming our touch events as the user is changing pages
                if (mPreviousState == ViewPager.SCROLL_STATE_IDLE) {
                    if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                        mTouchTarget = bannerViewPager;
                    }
                } else {
                    if (state == ViewPager.SCROLL_STATE_IDLE || state == ViewPager.SCROLL_STATE_SETTLING) {
                        mTouchTarget = null;
                    }
                }

                mPreviousState = state;
            }
        });


        mIndicator = (PageIndicator)bannerView.findViewById(R.id.indicator);
        mIndicator.setViewPager(bannerViewPager);

        fragmentListView.addHeaderView(bannerView);
        if (mTitle.equals("微商优选")){
//            listAdapter = new B0_IndexAdapterNew(this,)
        }
    }

	//获取屏幕宽度
	public int getDisplayMetricsWidth() {
		int i = getActivity().getWindowManager().getDefaultDisplay().getWidth();
		int j = getActivity().getWindowManager().getDefaultDisplay().getHeight();
		return Math.min(i, j);
	}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}

package com.insthub.ecmobile.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.external.androidquery.callback.AjaxStatus;
import com.external.viewpagerindicator.PageIndicator;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.MyListView;
import com.insthub.ecmobile.EcmobileApp;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.B1_ProductListActivity;
import com.insthub.ecmobile.activity.B2_ProductDetailActivity;
import com.insthub.ecmobile.activity.BannerWebActivity;
import com.insthub.ecmobile.adapter.B0_IndexAdapterNew;
import com.insthub.ecmobile.adapter.Bee_PageAdapter;
import com.insthub.ecmobile.adapter.ShopAdapter2;
import com.insthub.ecmobile.model.ADModel;
import com.insthub.ecmobile.model.FirstLvModel;
import com.insthub.ecmobile.model.SearchModel;
import com.insthub.ecmobile.protocol.AD;
import com.insthub.ecmobile.protocol.ApiInterface;
import com.insthub.ecmobile.protocol.FILTER;
import com.insthub.ecmobile.protocol.PLAYER;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopFragment extends Fragment implements BusinessResponse{
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

    private B0_IndexAdapterNew listAdapter;

    private ArrayList<View> bannerListView;
    private Bee_PageAdapter bannerPageAdapter;
    private View mTouchTarget;

    private ADModel adModel;
    private FirstLvModel firstLvModel;
protected ImageLoader imageLoader = ImageLoader.getInstance();
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
        adModel = new ADModel(getActivity());
        adModel.addResponseListener(this);
        firstLvModel = new FirstLvModel(getActivity());
        firstLvModel.addResponseListener(this);

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
        if (mTitle.equals("富国超市")){
            listAdapter = new B0_IndexAdapterNew(getActivity(),firstLvModel,new SearchModel(getActivity()));
            adModel.fetchADs(1);
            firstLvModel.fetchFirstRecom(1, 10, 0);
            firstLvModel.fetchLvCategory(14);
        }else if (mTitle.equals("新马路菜市场")){
            listAdapter = new ShopAdapter2(getActivity(),firstLvModel,new SearchModel(getActivity()));
            adModel.fetchADs(2);
            firstLvModel.fetchFirstRecom(2,10,0);
        }else if (mTitle.equals("鲜果园")){
            adModel.fetchADs(3);
        }else if (mTitle.equals("微商优选")){
            adModel.fetchADs(4);
        }else if (mTitle.equals("福田及时送")){
            adModel.fetchADs(5);
        }
        fragmentListView.setAdapter(listAdapter);
        fragmentListView.setPullRefreshEnable(false);
        fragmentListView.setPullLoadEnable(false);



    }

     public void addBannerView()
    {
        bannerListView.clear();
        for (int i = 0; i < adModel.ads.size(); i++)
        {
            AD player = adModel.ads.get(i);
            ImageView viewOne =  (ImageView)LayoutInflater.from(getActivity()).inflate(R.layout.b0_index_banner_cell,null);

//            shared = getActivity().getSharedPreferences("userInfo", 0);
//    		editor = shared.edit();
//    		String imageType = shared.getString("imageType", "mind");
//
//    		if(imageType.equals("high")) {
//                imageLoader.displayImage(player.photo.thumb,viewOne, EcmobileApp.options);
//    		} else if(imageType.equals("low")) {
//                imageLoader.displayImage(player.photo.small,viewOne, EcmobileApp.options);
//    		} else {
//    			String netType = shared.getString("netType", "wifi");
//    			if(netType.equals("wifi")) {
//                    imageLoader.displayImage(player.photo.thumb,viewOne, EcmobileApp.options);
//    			} else {
//                    imageLoader.displayImage(player.photo.small,viewOne, EcmobileApp.options);
//    			}
//    		}
            imageLoader.displayImage(player.ad_code,viewOne, EcmobileApp.options);
            try
            {
                viewOne.setTag(player.toJson().toString());
            }
            catch (JSONException e)
            {

            }

            bannerListView.add(viewOne);

            viewOne.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    String playerJSONString = (String) v.getTag();

                    try {
                        JSONObject jsonObject = new JSONObject(playerJSONString);
                        PLAYER player1 = new PLAYER();
                         player1.fromJson(jsonObject);
                        if (null == player1.action)
                        {
                            if (null != player1.url) {
                                Intent intent = new Intent(getActivity(), BannerWebActivity.class);
                                intent.putExtra("url", player1.url);
                                startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.push_right_in,
                                        R.anim.push_right_out);
                            }
                        }
                        else
                        {
                            if (player1.action.equals("goods"))
                            {
                                Intent intent = new Intent(getActivity(), B2_ProductDetailActivity.class);
                                intent.putExtra("good_id", player1.action_id+"");
                                getActivity().startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.push_right_in,
                                        R.anim.push_right_out);
                            }
                            else if (player1.action.equals("category"))
                            {
                                Intent intent = new Intent(getActivity(), B1_ProductListActivity.class);
                                FILTER filter = new FILTER();
                                filter.category_id = String.valueOf(player1.action_id);
                                intent.putExtra(B1_ProductListActivity.FILTER,filter.toJson().toString());
                                startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.push_right_in,
                                        R.anim.push_right_out);
                            }
                            else if (null != player1.url)
                            {
                                Intent intent = new Intent(getActivity(), BannerWebActivity.class);
                                intent.putExtra("url", player1.url);
                                startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.push_right_in,
                                        R.anim.push_right_out);
                            }
                        }

                    } catch (JSONException e) {

                    }

                }
            });

        }

        mIndicator.notifyDataSetChanged();
//        mIndicator.setCurrentItem(0);
        bannerPageAdapter.mListViews = bannerListView;
        bannerViewPager.setAdapter(bannerPageAdapter);

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

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ApiInterface.HOME_AD)){
            addBannerView();
        }else if (url.endsWith(ApiInterface.PRODUCT_CATBESTLIST)){
            listAdapter.notifyDataSetChanged();
        }else if(url.endsWith(ApiInterface.PRODUCT_CATEGORY)){
            listAdapter.notifyDataSetChanged();
        }
    }
}

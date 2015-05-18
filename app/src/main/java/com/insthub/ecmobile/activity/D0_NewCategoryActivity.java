package com.insthub.ecmobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.external.viewpagerindicator.PageIndicator;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.ecmobile.EcmobileApp;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.B0_NewCategoryAdapter;
import com.insthub.ecmobile.adapter.Bee_PageAdapter;
import com.insthub.ecmobile.model.ADModel;
import com.insthub.ecmobile.model.CategoryProductsModel;
import com.insthub.ecmobile.protocol.AD;
import com.insthub.ecmobile.protocol.ApiInterface;
import com.insthub.ecmobile.protocol.FILTER;
import com.insthub.ecmobile.protocol.PLAYER;
import com.insthub.ecmobile.protocol.SIMPLEGOODS;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * ,==.              |~~~
 * /  66\             |
 * \c  -_)         |~~~
 * `) (           |
 * /   \       |~~~
 * /   \ \      |
 * ((   /\ \_ |~~~
 * \\  \ `--`|
 * / / /  |~~~
 * ___ (_(___)_|
 * <p/>
 * Created by Paper on 15-5-18 2015.
 */
public class D0_NewCategoryActivity extends BaseActivity implements BusinessResponse {

    @InjectView(R.id.listView)
    XListView listView;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    private FrameLayout bannerView;
    private ViewPager bannerViewPager;
    private List<View> bannerListView;
    private PageIndicator mIndicator;
    private View mTouchTarget;
    private Bee_PageAdapter bannerPageAdapter;
    private ADModel adModel;
    private CategoryProductsModel productsModel;
    private com.nostra13.universalimageloader.core.ImageLoader imageLoader = ImageLoader.getInstance();
    private ArrayList<SIMPLEGOODS> datalist = new ArrayList<SIMPLEGOODS>();
    String cat_name;
    int cat_id;
    B0_NewCategoryAdapter productsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d0_newcategory);
        ButterKnife.inject(this);
        cat_id = Integer.parseInt(getIntent().getStringExtra("cat_id"));
        cat_name = getIntent().getStringExtra("cat_name");
        setSupportActionBar(toolbar);
       getSupportActionBar().setTitle(cat_name);
        init();

        adModel = new ADModel(this);
        adModel.addResponseListener(this);
        adModel.fetchADs(1);
        productsModel = new CategoryProductsModel(this);
        productsModel.addResponseListener(this);
        productsModel.fetchGoods(cat_id);

    }

    private void init() {
        bannerView = (FrameLayout) LayoutInflater.from(this).inflate(R.layout.b0_index_banner, null);

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
        listView.addHeaderView(bannerView);
        productsAdapter = new B0_NewCategoryAdapter(this,datalist);
        listView.setAdapter(productsAdapter);
        listView.setPullRefreshEnable(false);
        listView.setPullLoadEnable(false);

    }
    public void addBannerView()
    {
        bannerListView.clear();
        for (int i = 0; i < adModel.ads.size(); i++)
        {
            AD player = adModel.ads.get(i);
            ImageView viewOne =  (ImageView)LayoutInflater.from(this).inflate(R.layout.b0_index_banner_cell, null);

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
                                Intent intent = new Intent(D0_NewCategoryActivity.this, BannerWebActivity.class);
                                intent.putExtra("url", player1.url);
                                startActivity(intent);
                                D0_NewCategoryActivity.this.overridePendingTransition(R.anim.push_right_in,
                                        R.anim.push_right_out);
                            }
                        }
                        else
                        {
                            if (player1.action.equals("goods"))
                            {
                                Intent intent = new Intent(D0_NewCategoryActivity.this, B2_ProductDetailActivity.class);
                                intent.putExtra("good_id", player1.action_id+"");
                                D0_NewCategoryActivity.this.startActivity(intent);
                                D0_NewCategoryActivity.this.overridePendingTransition(R.anim.push_right_in,
                                        R.anim.push_right_out);
                            }
                            else if (player1.action.equals("category"))
                            {
                                Intent intent = new Intent(D0_NewCategoryActivity.this, B1_ProductListActivity.class);
                                FILTER filter = new FILTER();
                                filter.category_id = String.valueOf(player1.action_id);
                                intent.putExtra(B1_ProductListActivity.FILTER,filter.toJson().toString());
                                startActivity(intent);
                                D0_NewCategoryActivity.this.overridePendingTransition(R.anim.push_right_in,
                                        R.anim.push_right_out);
                            }
                            else if (null != player1.url)
                            {
                                Intent intent = new Intent(D0_NewCategoryActivity.this, BannerWebActivity.class);
                                intent.putExtra("url", player1.url);
                                startActivity(intent);
                                D0_NewCategoryActivity.this.overridePendingTransition(R.anim.push_right_in,
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
		int i = this.getWindowManager().getDefaultDisplay().getWidth();
		int j = this.getWindowManager().getDefaultDisplay().getHeight();
		return Math.min(i, j);
	}

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ApiInterface.HOME_AD)){
            addBannerView();
        }else if (url.endsWith(ApiInterface.PRODUCT_CATEGORY_SECOND)){
           datalist.clear();
            datalist.addAll(productsModel.ads);
            productsAdapter.notifyDataSetChanged();
        }
    }


}

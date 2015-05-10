package com.insthub.ecmobile.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;

import butterknife.OnClick;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.external.viewpagerindicator.PageIndicator;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.ecmobile.EcmobileApp;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.B0_IndexAdapter;
import com.insthub.ecmobile.adapter.Bee_PageAdapter;
import com.insthub.ecmobile.adapter.CategoryAdapter;
import com.insthub.ecmobile.event.ItemClickEvent;
import com.insthub.ecmobile.model.HomeModel;
import com.insthub.ecmobile.model.ShoppingCartModel;
import com.insthub.ecmobile.protocol.*;
import com.nostra13.universalimageloader.core.ImageLoader;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/1/23.
 */
public class D0_Category1Activity extends BaseActivity implements BusinessResponse {

    @InjectView(R.id.category1_all_products)
    TextView mCategory1AllProducts;
    @InjectView(R.id.category1_choice)
    TextView mCategory1Choice;
    @InjectView(R.id.d0_category_listview)
    ListView mD0CategoryListview;
    @InjectView(R.id.products_counts)
    TextView mProductsCounts;
    @InjectView(R.id.products_total_price)
    TextView mProductsTotalPrice;
    private CategoryAdapter adapter;
    FILTER filter = new FILTER();
    private CATEGORY category;
    private String categoryStr;
    FrameLayout bannerView;
    private ViewPager bannerViewPager;
    private ArrayList<View> bannerListView;
    private Bee_PageAdapter bannerPageAdapter;
//    private PageIndicator mIndicator;
    private HomeModel dataModel ;
    private ShoppingCartModel shoppingCartModel;
    private View mTouchTarget;
    private float totalGoodsPrice =0;  //总价格
    private int totalGoodsCount =0;  //总价格


    private SharedPreferences shared;
    private SharedPreferences.Editor editor;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d0_activity_category1);
        ButterKnife.inject(this);
        EventBus.getDefault().register(this,"setOnItemClick", ItemClickEvent.class);
        init();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void init() {
        categoryStr = getIntent().getStringExtra("category");
        Log.e("hshs","categoryStr:"+categoryStr);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(categoryStr);
            category = new CATEGORY();
            category.fromJson(jsonObject);
            getActionBar().setTitle(category.name);
            adapter = new CategoryAdapter(category.children);
            mD0CategoryListview.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (null == shoppingCartModel) {
            shoppingCartModel = new ShoppingCartModel(this);
            shoppingCartModel.addResponseListener(this);
            shoppingCartModel.checkOrder();
        } else {
            setInfo();
        }

        if (!category.name.trim().equalsIgnoreCase("农贸市场")){
            return;
        }

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


//        mIndicator = (PageIndicator)bannerView.findViewById(R.id.indicator);
//        mIndicator.setViewPager(bannerViewPager);
        mD0CategoryListview.addHeaderView(bannerView);

        if (null == dataModel)
        {
            dataModel = new HomeModel(this);
            dataModel.addResponseListener(this);
            dataModel.fetchHotSelling();

        }
    }

    private void setInfo() {
        for (int i = 0; i < shoppingCartModel.balance_goods_list.size(); i++) {
            totalGoodsPrice += Float.valueOf(shoppingCartModel.balance_goods_list.get(i).subtotal);
            totalGoodsCount += Integer.valueOf(shoppingCartModel.balance_goods_list.get(i).goods_number);
        }
        mProductsTotalPrice.setText("￥"+totalGoodsPrice);
        mProductsCounts.setText(totalGoodsCount);
    }

    public void setOnItemClick(ItemClickEvent event) {
            Intent it = new Intent(this, D0_Category2Activity.class);


        it.putExtra("category", categoryStr);
            it.putExtra("position",event.getPosition());
            startActivity(it);
            overridePendingTransition(R.anim.push_right_in,R.anim.push_left_out);
    }

    @OnClick(R.id.products_cart_layout)
    public void onShopCart(){
        Intent it = new Intent(this,ShopCartActivity.class);

        startActivity(it);
        overridePendingTransition(R.anim.push_buttom_in,
                R.anim.anim_hold);
    }

    @OnClick(R.id.category1_choice)
    public void onChoiceCategory(){
//        try {
        Intent it = new Intent(this,D1_CategoryAllAcitivty.class);
//        it.putExtra("category", category.toJson().toString());
//        it.putExtra("category_name", category.name);
        startActivityForResult(it,100);
        overridePendingTransition(R.anim.push_up_in,
                R.anim.anim_hold);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home)
        {

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode ==0) {
            String categoryStr = data.getStringExtra("category");
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(categoryStr);
                category = new CATEGORY();
                category.fromJson(jsonObject);
                getActionBar().setTitle(category.name);
                adapter = new CategoryAdapter(category.children);
                mD0CategoryListview.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ApiInterface.HOME_DATA))
        {
            addBannerView();
        }else if(url.endsWith(ApiInterface.FLOW_CHECKORDER)){
            STATUS res_status = new STATUS();
            res_status.fromJson(jo.optJSONObject("status"));
            if (res_status.succeed == 1) {
                setInfo();
            }
        }
    }


    public void addBannerView()
    {
        bannerListView.clear();
        for (int i = 0; i < dataModel.playersList.size(); i++)
        {
            PLAYER player = dataModel.playersList.get(i);
            ImageView viewOne =  (ImageView)LayoutInflater.from(this).inflate(R.layout.b0_index_banner_cell,null);

            shared = this.getSharedPreferences("userInfo", 0);
            editor = shared.edit();
            String imageType = shared.getString("imageType", "mind");

            if(imageType.equals("high")) {
                imageLoader.displayImage(player.photo.thumb,viewOne, EcmobileApp.options);
            } else if(imageType.equals("low")) {
                imageLoader.displayImage(player.photo.small,viewOne, EcmobileApp.options);
            } else {
                String netType = shared.getString("netType", "wifi");
                if(netType.equals("wifi")) {
                    imageLoader.displayImage(player.photo.thumb,viewOne, EcmobileApp.options);
                } else {
                    imageLoader.displayImage(player.photo.small,viewOne, EcmobileApp.options);
                }
            }

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
                                Intent intent = new Intent(D0_Category1Activity.this, BannerWebActivity.class);
                                intent.putExtra("url", player1.url);
                                startActivity(intent);
                                D0_Category1Activity.this.overridePendingTransition(R.anim.push_right_in,
                                        R.anim.push_right_out);
                            }
                        }
                        else
                        {
                            if (player1.action.equals("goods"))
                            {
                                Intent intent = new Intent(D0_Category1Activity.this, B2_ProductDetailActivity.class);
                                intent.putExtra("good_id", player1.action_id+"");
                                D0_Category1Activity.this.startActivity(intent);
                                D0_Category1Activity.this.overridePendingTransition(R.anim.push_right_in,
                                        R.anim.push_right_out);
                            }
                            else if (player1.action.equals("category"))
                            {
                                Intent intent = new Intent(D0_Category1Activity.this, B1_ProductListActivity.class);
                                FILTER filter = new FILTER();
                                filter.category_id = String.valueOf(player1.action_id);
                                intent.putExtra(B1_ProductListActivity.FILTER,filter.toJson().toString());
                                startActivity(intent);
                                D0_Category1Activity.this.overridePendingTransition(R.anim.push_right_in,
                                        R.anim.push_right_out);
                            }
                            else if (null != player1.url)
                            {
                                Intent intent = new Intent(D0_Category1Activity.this, BannerWebActivity.class);
                                intent.putExtra("url", player1.url);
                                startActivity(intent);
                                D0_Category1Activity.this.overridePendingTransition(R.anim.push_right_in,
                                        R.anim.push_right_out);
                            }
                        }

                    } catch (JSONException e) {

                    }

                }
            });

        }

//        mIndicator.notifyDataSetChanged();
//        mIndicator.setCurrentItem(0);
        bannerPageAdapter.mListViews = bannerListView;
        bannerViewPager.setAdapter(bannerPageAdapter);

    }

    //获取屏幕宽度
    public int getDisplayMetricsWidth() {
        int i =this.getWindowManager().getDefaultDisplay().getWidth();
        int j = this.getWindowManager().getDefaultDisplay().getHeight();
        return Math.min(i, j);
    }
}
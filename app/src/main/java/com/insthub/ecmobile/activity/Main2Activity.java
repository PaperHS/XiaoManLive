package com.insthub.ecmobile.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.MainPageAdapter;
import com.insthub.ecmobile.component.PagerSlidingTabStrip;
import com.insthub.ecmobile.fragment.ShopFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Main2Activity extends ActionBarActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.main_indicator)
    PagerSlidingTabStrip mainIndicator;
    @InjectView(R.id.main_vierpager)
    ViewPager mainVierpager;
    private List<ShopFragment> mShopFragments;
    private MainPageAdapter mainPageAdapter;
private SharedPreferences shared;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
         shared = getSharedPreferences("userInfo", 0);
        String uid = shared.getString("uid", "");

         if (TextUtils.isEmpty(uid)){
            startActivity(new Intent(this,A0_SigninActivity.class));
            this.finish();
             return;
        }
        ButterKnife.inject(this);

        View tv= LayoutInflater.from(this).inflate(R.layout.layout_main_title,null);

        Toolbar.LayoutParams params = new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        toolbar.addView(tv, params);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setCustomView(tv);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        init();
    }

    private void init() {
        mShopFragments = new ArrayList<>();
        mShopFragments.add(ShopFragment.newInstance("乐购超市",R.drawable.ic_shop1));
        mShopFragments.add(ShopFragment.newInstance("新马路菜市场",R.drawable.ic_shop2));
        mShopFragments.add(ShopFragment.newInstance("鲜果园",R.drawable.ic_shop3));
        mShopFragments.add(ShopFragment.newInstance("微商优选",R.drawable.ic_shop4));
        mainPageAdapter = new MainPageAdapter(getSupportFragmentManager(),mShopFragments);
        mainVierpager.setAdapter(mainPageAdapter);
        mainIndicator.setIndicatorColorResource(R.color.main_color);
        mainIndicator.setTextSize(18);
        mainIndicator.setViewPager(mainVierpager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

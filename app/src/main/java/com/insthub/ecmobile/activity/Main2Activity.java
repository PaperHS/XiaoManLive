package com.insthub.ecmobile.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.AddressChoiceDialog;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.MainPageAdapter;
import com.insthub.ecmobile.component.PagerSlidingTabStrip;
import com.insthub.ecmobile.fragment.ShopFragment;
import com.insthub.ecmobile.model.AddressModel;
import com.insthub.ecmobile.protocol.ApiInterface;
import com.insthub.ecmobile.protocol.STATUS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class Main2Activity extends ActionBarActivity implements BusinessResponse{

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.main_indicator)
    PagerSlidingTabStrip mainIndicator;
    @InjectView(R.id.main_vierpager)
    ViewPager mainVierpager;
    TextView titleAddress;
    ImageView titlePerson;
    private List<ShopFragment> mShopFragments;
    private MainPageAdapter mainPageAdapter;
    private SharedPreferences shared;
    AddressChoiceDialog addressChoiceDialog;
    AddressModel addressModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        shared = getSharedPreferences("userInfo", 0);
//        String uid = shared.getString("uid", "");
//
//        if (TextUtils.isEmpty(uid)) {
//            startActivity(new Intent(this, A0_SigninActivity.class));
//            this.finish();
//            return;
//        }
        ButterKnife.inject(this);

        View tv = LayoutInflater.from(this).inflate(R.layout.layout_main_title, null);
        titleAddress = (TextView)tv.findViewById(R.id.title_address);
        titlePerson = (ImageView)tv.findViewById(R.id.title_person);
        titlePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Main2Activity.this,S1_SelfInfoAcitivity.class);
                startActivity(it);
            }
        });
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        toolbar.addView(tv, params);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setCustomView(tv);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        init();
    }

    @OnClick(R.id.main_shopcart_btn)
    public void onToShopcart(){
        Intent it = new Intent(this,C0_ShoppingCartActivity.class);
        startActivity(it);
    }

    private void init() {
        mShopFragments = new ArrayList<>();
        mShopFragments.add(ShopFragment.newInstance("富国超市", R.drawable.ic_shop1));
        mShopFragments.add(ShopFragment.newInstance("新马路菜市场", R.drawable.ic_shop2));
        mShopFragments.add(ShopFragment.newInstance("鲜果园", R.drawable.ic_shop3));
        mShopFragments.add(ShopFragment.newInstance("微商优选", R.drawable.ic_shop4));
        mShopFragments.add(ShopFragment.newInstance("福田及时送", R.drawable.ic_shop4));
        mainPageAdapter = new MainPageAdapter(getSupportFragmentManager(), mShopFragments);
        mainVierpager.setAdapter(mainPageAdapter);
        mainIndicator.setIndicatorColorResource(R.color.text_red);
        mainIndicator.setTextSize(14);
        mainIndicator.setViewPager(mainVierpager);
        addressModel = new AddressModel(this);
        addressModel.addResponseListener(this);
        addressModel.getAddressList();
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


    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ApiInterface.ADDRESS_LIST)) {
            STATUS s = new STATUS();
            s.fromJson(jo.optJSONObject("status"));
            if (s.succeed == 1) {
                //TODO dialog show

                if (addressChoiceDialog == null) {
                    addressChoiceDialog = new AddressChoiceDialog(this, "请选择送货地址", addressModel.addressList);
                    addressChoiceDialog.positive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addressChoiceDialog.dismiss();
                            Intent intent = new Intent(Main2Activity.this, F1_NewAddressActivity.class);
                            startActivity(intent);
                        }
                    });
                }

            }
        }
    }
    }

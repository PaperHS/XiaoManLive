package com.insthub.ecmobile.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.AddressChoiceDialog;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.MainPageAdapter;
import com.insthub.ecmobile.component.PagerSlidingTabStrip;
import com.insthub.ecmobile.event.AddressItemClickEvent;
import com.insthub.ecmobile.fragment.ShopFragment;
import com.insthub.ecmobile.model.AddressModel;
import com.insthub.ecmobile.model.IndexStoreModel;
import com.insthub.ecmobile.protocol.ApiInterface;
import com.insthub.ecmobile.protocol.HOMESTORE;
import com.insthub.ecmobile.protocol.STATUS;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class Main2Activity extends AppCompatActivity implements BusinessResponse, TencentLocationListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
        @InjectView(R.id.main_indicator)
        PagerSlidingTabStrip mainIndicator;
    TextView titleAddress;
    ImageView titlePerson;
    @InjectView(R.id.main_vierpager)
    ViewPager mainVierpager;
//    @InjectView(R.id.main_indicator)
//    TabPageIndicator mainIndicator;
    private List<ShopFragment> mShopFragments;
    private MainPageAdapter mainPageAdapter;
    private SharedPreferences shared;
    AddressChoiceDialog addressChoiceDialog;
    AddressModel addressModel;
    private TencentLocationManager locationManager;
    IndexStoreModel indexStoreModel;


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
        titleAddress = (TextView) tv.findViewById(R.id.title_address);
        titlePerson = (ImageView) tv.findViewById(R.id.title_person);
        titlePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Main2Activity.this, S1_SelfInfoAcitivity.class);
                startActivity(it);
            }
        });
        titleAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addressChoiceDialog.show();
            }
        });
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        toolbar.addView(tv, params);
        setSupportActionBar(toolbar);

//        getSupportActionBar().setCustomView(tv);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        init();
    }

    @OnClick(R.id.main_shopcart_btn)
    public void onToShopcart() {
        Intent it = new Intent(this, C0_ShoppingCartActivity.class);
        startActivity(it);
    }

    private void init() {
        EventBus.getDefault().register(this, "onAddressClick", AddressItemClickEvent.class);
        indexStoreModel = new IndexStoreModel(this);
        indexStoreModel.addResponseListener(this);
        indexStoreModel.fetchStores(3430);
        mShopFragments = new ArrayList<>();

        mainPageAdapter = new MainPageAdapter(getSupportFragmentManager(), mShopFragments);
        mainVierpager.setAdapter(mainPageAdapter);
        mainIndicator.setIndicatorColorResource(R.color.text_red);
        mainIndicator.setTextSize(14);
        mainIndicator.setShouldExpand(false);
//        mainIndicator.setViewPager(mainVierpager);

        addressModel = new AddressModel(this);
        addressModel.addResponseListener(this);
//        addressModel.getAddressList();
        //请求gps
        TencentLocationRequest request = TencentLocationRequest.create();
        locationManager = TencentLocationManager.getInstance(this);
        int error = locationManager.requestLocationUpdates(request, this, getMainLooper());
        if (error == 1) {
            Log.e("location", "tenlent less");
        } else if (error == 2) {
            Log.e("location", "tenlent key error");
        }
        Log.e("location", "error:" + error);
        addressModel.getAddressList();
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

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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

//                if (addressChoiceDialog == null) {
//                } else addressChoiceDialog.show();

            }
        } else if (url.endsWith(ApiInterface.HOME_INDEX)) {
            for (HOMESTORE store : indexStoreModel.stores) {
                ShopFragment fragment = ShopFragment.newInstance(store.suppliers_name, Integer.parseInt(store.theme), Integer.parseInt(store.suppliers_id));
                mShopFragments.add(fragment);
            }
//            mainPageAdapter.notifyDataSetChanged();
            mainPageAdapter.updateList(mShopFragments);
            mainIndicator.setViewPager(mainVierpager);
//            mainIndicator.notifyDataSetChanged();
//            mainVierpager.setAdapter(mainPageAdapter);

        }
    }

    public void onAddressClick(AddressItemClickEvent event) {
        addressChoiceDialog.dismiss();
        titleAddress.setText(event.getPosition());
//        getActivity().getActionBar().setTitle("送至："+event.getPosition());
    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
        if (TencentLocation.ERROR_OK == i) {
            // 定位成功
            titleAddress.setText(tencentLocation.getAddress());
            Log.e("location", "   " + tencentLocation.getAddress());
        } else {
            // 定位失败
            Log.e("location", " error  " + i + ":" + s);
        }
        locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {
        Log.e("location", s + "   " + s1);
    }
}

package com.insthub.ecmobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

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
 * Created by Paper on 15-5-14 2015.
 */
public class S1_SelfInfoAcitivity extends BaseActivity {

    @InjectView(R.id.toobar)
    Toolbar toobar;
    @InjectView(R.id.selfinfo_login)
    Button selfinfoLogin;
    @InjectView(R.id.selfinfo_address_man)
    LinearLayout selfinfoAddressMan;
    @InjectView(R.id.selfinfo_myorder)
    LinearLayout selfinfoMyorder;
    @InjectView(R.id.selfinfo_papermoney)
    LinearLayout selfinfoPapermoney;
    @InjectView(R.id.selfinfo_msgcenter)
    LinearLayout selfinfoMsgcenter;
    @InjectView(R.id.selfinfo_wallet)
    TextView selfinfoWallet;
    @InjectView(R.id.selfinfo_scan)
    TextView selfinfoScan;
    @InjectView(R.id.selfinfo_custom_server)
    LinearLayout selfinfoCustomServer;
    @InjectView(R.id.selfinfo_share)
    LinearLayout selfinfoShare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s1_selfinfoacitivity);
        ButterKnife.inject(this);
        toobar.setTitle("我的小满");
        setSupportActionBar(toobar);
        toobar.setNavigationIcon(R.drawable.ic_arrow_left);
        init();
    }


    private void init() {

    }

    @OnClick(R.id.selfinfo_address_man)
    public void onAddress(){
        Intent toAddr = new Intent(this,F0_AddressListActivity.class);
        startActivity(toAddr);
    }

    @OnClick(R.id.selfinfo_papermoney)
    public void onPaperMoney(){
        Intent toMoney = new Intent(this,E3_MyPromotionAcitivity.class);
        startActivity(toMoney);
    }
    @OnClick(R.id.selfinfo_msgcenter)
    public void onMsgCenter(){
        Intent toMsg = new Intent(this,M0_MessageCenterAcitivity.class);
        startActivity(toMsg);
    }
}

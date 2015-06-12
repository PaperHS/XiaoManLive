package com.insthub.ecmobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.model.UserInfoModel;
import com.insthub.ecmobile.protocol.ApiInterface;
import com.insthub.ecmobile.protocol.STATUS;

import org.json.JSONException;
import org.json.JSONObject;

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
public class S1_SelfInfoAcitivity extends BaseActivity implements BusinessResponse {

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
    UserInfoModel userInfoModel;
    @InjectView(R.id.selfinfo_right)
    ImageView selfinfoRight;
    @InjectView(R.id.selfinfo_name)
    TextView selfinfoName;
    @InjectView(R.id.selfinfo_selfinfo)
    RelativeLayout selfinfoSelfinfo;
    @InjectView(R.id.selfinfo_phone)
    TextView selfinfoPhone;

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
        userInfoModel = new UserInfoModel(this);
        userInfoModel.addResponseListener(this);
        userInfoModel.getUserInfo();

    }

    @OnClick(R.id.selfinfo_selfinfo)
    public void onSelfInfoClick(){
        startActivity(new Intent(this,E1_SelfInfoAcitvity.class));
    }


    @OnClick(R.id.selfinfo_address_man)
    public void onAddress() {
        Intent toAddr = new Intent(this, F0_AddressListActivity.class);
        startActivity(toAddr);
    }

    @OnClick(R.id.selfinfo_papermoney)
    public void onPaperMoney() {
        Intent toMoney = new Intent(this, E3_MyPromotionAcitivity.class);
        startActivity(toMoney);
    }

    @OnClick(R.id.selfinfo_msgcenter)
    public void onMsgCenter() {
        Intent toMsg = new Intent(this, M0_MessageCenterAcitivity.class);
        startActivity(toMsg);
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ApiInterface.USER_INFO)) {
            STATUS status1 = new STATUS();
            status1.fromJson(jo);
            if (status1.succeed == 1) {
                selfinfoName.setText(userInfoModel.user.name);
//                selfinfoPhone.setText(userInfoModel.user.);
            } else {
                selfinfoLogin.setVisibility(View.VISIBLE);
                selfinfoSelfinfo.setVisibility(View.GONE);
            }
        }
    }
}

package com.insthub.ecmobile.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.R;

/**
 * Created by Administrator on 2015/2/27.
 */
public class E5_SettingAcitivity extends BaseActivity {
    @InjectView(R.id.e5_setting_about)
    RelativeLayout mE5SettingAbout;
    @InjectView(R.id.e5_setting_help)
    RelativeLayout mE5SettingHelp;
    @InjectView(R.id.e5_setting_opption)
    RelativeLayout mE5SettingOpption;
    @InjectView(R.id.e5_setting_update)
    RelativeLayout mE5SettingUpdate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e5_setting_activity);
        ButterKnife.inject(this);
        getActionBar().setTitle("设置");
    }
}
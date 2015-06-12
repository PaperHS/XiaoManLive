package com.insthub.ecmobile.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2015/2/27.
 */
public class E1_SelfInfoAcitvity extends BaseActivity {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.e1_self_phone_num)
    TextView e1SelfPhoneNum;
    @InjectView(R.id.e1_self_name_edit)
    TextView e1SelfNameEdit;
    @InjectView(R.id.e1_self_name)
    RelativeLayout e1SelfName;
    @InjectView(R.id.e1_self_gender_edit)
    TextView e1SelfGenderEdit;
    @InjectView(R.id.e1_self_gender)
    RelativeLayout e1SelfGender;
    @InjectView(R.id.e1_self_exit)
    RelativeLayout e1SelfExit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e1_selfinfo_acitivty);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("修个信息");
    }


}
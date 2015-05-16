package com.insthub.ecmobile.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.E3_PromotionAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2015/2/27.
 */
public class E3_MyPromotionAcitivity extends BaseActivity {
    @InjectView(R.id.e3_promotion_scan)
    RelativeLayout mE3PromotionScan;
    @InjectView(R.id.e3_promotion_listview)
    ListView mE3PromotionListview;
    @InjectView(R.id.e3_promotion_none)
    LinearLayout mE3PromotionNone;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private E3_PromotionAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e3_mypromotion_acitivty);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        adapter = new E3_PromotionAdapter(this);
        mE3PromotionListview.setAdapter(adapter);
        getSupportActionBar().setTitle("我的优惠劵");
    }
}
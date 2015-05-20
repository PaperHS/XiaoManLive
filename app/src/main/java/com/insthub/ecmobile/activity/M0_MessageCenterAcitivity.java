package com.insthub.ecmobile.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.MsgPageAdapter;
import com.insthub.ecmobile.component.PagerSlidingTabStrip;
import com.insthub.ecmobile.fragment.ItemFragment;

import java.util.ArrayList;

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
 * Created by Paper on 15-5-19 2015.
 */
public class M0_MessageCenterAcitivity extends BaseActivity implements ItemFragment.OnFragmentInteractionListener{

    @InjectView(R.id.msgcenter_indicator)
    PagerSlidingTabStrip msgcenterIndicator;
    @InjectView(R.id.msgcenter_vp)
    ViewPager msgcenterVp;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private MsgPageAdapter mainPageAdapter;
    private ArrayList<ItemFragment> pagerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m0_messagecenter);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("消息中心");
        init();
    }

    private void init() {
        pagerList = new ArrayList<>();
        pagerList.add(ItemFragment.newInstance("","系统信息"));
        pagerList.add(ItemFragment.newInstance("","我的账单"));
        pagerList.add(ItemFragment.newInstance("","退款信息"));
        mainPageAdapter = new MsgPageAdapter(getSupportFragmentManager(),pagerList);
        msgcenterVp.setAdapter(mainPageAdapter);
        msgcenterIndicator.setIndicatorColorResource(R.color.text_red);
        msgcenterIndicator.setTextSize(14);
        msgcenterIndicator.setViewPager(msgcenterVp);
    }

    @Override
    public void onFragmentInteraction(String id) {

    }
}

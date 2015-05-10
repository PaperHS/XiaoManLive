package com.insthub.ecmobile.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.R;

/**
 * Created by Administrator on 2015/2/27.
 */
public class E1_SelfInfoAcitvity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e1_selfinfo_acitivty);
        getActionBar().setTitle("个人信息");
    }


}
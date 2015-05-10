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
public class E4_ShareAcitivty extends BaseActivity {
    @InjectView(R.id.e4_share_weixin)
    RelativeLayout mE4ShareWeixin;
    @InjectView(R.id.e0_share_friend)
    RelativeLayout mE0ShareFriend;
    @InjectView(R.id.e0_share_weibo)
    RelativeLayout mE0ShareWeibo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e4_share_activity);
        ButterKnife.inject(this);
        getActionBar().setTitle("分享");
    }
}
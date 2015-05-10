package com.insthub.ecmobile.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.RelativeLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.*;

/**
 * Created by peggy on 15-2-14.
 */
public class E0_ProfileFragment_new extends Fragment {
    @InjectView(R.id.e0_selfinfo)
    RelativeLayout mE0Selfinfo;
    @InjectView(R.id.e0_address)
    RelativeLayout mE0Address;
    @InjectView(R.id.e0_redpaper)
    RelativeLayout mE0Redpaper;
    @InjectView(R.id.e0_share)
    RelativeLayout mE0Share;
    @InjectView(R.id.e0_setting)
    RelativeLayout mE0Setting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.e0_profilefragment_new, container, false);
        getActivity().getActionBar().setTitle("我的小满");
        ButterKnife.inject(this,view);
        return view;
    }

    @OnClick(R.id.e0_selfinfo)
    public void onClickSelfinfo(){
        Intent it = new Intent(getActivity(), E1_SelfInfoAcitvity.class);
        startActivity(it);
        getActivity().overridePendingTransition(R.anim.push_right_in,R.anim.anim_hold);
    }

    @OnClick(R.id.e0_address)
    public  void onClickAddress(){
        //地址管理
        Intent intent = new Intent(getActivity(), F0_AddressListActivity.class);
        intent.putExtra("flag", 1);
        startActivityForResult(intent, 2);
        getActivity().overridePendingTransition(R.anim.push_right_in,R.anim.anim_hold);
    }

    @OnClick(R.id.e0_redpaper)
    public void onRedPaper(){
        //优惠卷
        Intent it = new Intent(getActivity(), E3_MyPromotionAcitivity.class);
        startActivity(it);
        getActivity().overridePendingTransition(R.anim.push_right_in,R.anim.anim_hold);
    }

    @OnClick(R.id.e0_share)
    public void onShare(){
        //分享
        Intent it = new Intent(getActivity(), E4_ShareAcitivty.class);
        startActivity(it);
        getActivity().overridePendingTransition(R.anim.push_right_in,R.anim.anim_hold);
    }
    @OnClick(R.id.e0_setting)
    public void onSetting(){
        //设置
        Intent it = new Intent(getActivity(), E5_SettingAcitivity.class);
        startActivity(it);
        getActivity().overridePendingTransition(R.anim.push_right_in,R.anim.anim_hold);
    }




}
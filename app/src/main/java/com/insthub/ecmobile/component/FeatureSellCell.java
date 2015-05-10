package com.insthub.ecmobile.component;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.insthub.ecmobile.EcmobileApp;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.BannerWebActivity;
import com.insthub.ecmobile.protocol.SIMPLEGOODS;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/1/22.
 */
public class FeatureSellCell extends LinearLayout implements View.OnClickListener{
    private ImageView btn1;
    private ImageView btn2;
    private ImageView btn3;
    private ImageView btn4;
    public ArrayList<SIMPLEGOODS> simplegoodsList;
    Handler mHandler;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private SharedPreferences shared;
    private SharedPreferences.Editor editor;
    private Context context;
    public FeatureSellCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                bindDataDelay();
            }

			
        };
        this.context=context;
	}
	private void bindDataDelay() {
		init();
        shared = context.getSharedPreferences("userInfo", 0);
        editor = shared.edit();
        String imageType = shared.getString("imageType", "mind");

//        if(imageType.equals("high")) {
//            imageLoader.displayImage(simplegoodsList.get(0).img.thumb,btn1, EcmobileApp.options);
//            imageLoader.displayImage(simplegoodsList.get(1).img.thumb,btn2, EcmobileApp.options);
//            imageLoader.displayImage(simplegoodsList.get(2).img.thumb,btn3, EcmobileApp.options);
//            imageLoader.displayImage(simplegoodsList.get(3).img.thumb,btn4, EcmobileApp.options);
//        } else if(imageType.equals("low")) {
            imageLoader.displayImage(simplegoodsList.get(0).img.small,btn1, EcmobileApp.options);
            imageLoader.displayImage(simplegoodsList.get(1).img.small,btn4, EcmobileApp.options);
            imageLoader.displayImage(simplegoodsList.get(2).img.small,btn2, EcmobileApp.options);
            imageLoader.displayImage(simplegoodsList.get(3).img.small,btn3, EcmobileApp.options);
//        } else {
//            String netType = shared.getString("netType", "wifi");
//            if(netType.equals("wifi")) {
//                imageLoader.displayImage(simplegoodsList.get(0).img.thumb,btn1, EcmobileApp.options);
//                imageLoader.displayImage(simplegoodsList.get(1).img.thumb,btn2, EcmobileApp.options);
//                imageLoader.displayImage(simplegoodsList.get(2).img.thumb,btn3, EcmobileApp.options);
//                imageLoader.displayImage(simplegoodsList.get(3).img.thumb,btn4, EcmobileApp.options);
//            } else {
//                imageLoader.displayImage(simplegoodsList.get(0).img.small,btn1, EcmobileApp.options);
//                imageLoader.displayImage(simplegoodsList.get(1).img.small,btn2, EcmobileApp.options);
//                imageLoader.displayImage(simplegoodsList.get(2).img.small,btn3, EcmobileApp.options);
//                imageLoader.displayImage(simplegoodsList.get(3).img.small,btn4, EcmobileApp.options);
//            }
//        }
	}
    public void bindData(ArrayList<SIMPLEGOODS> simplegoodsList)
    {
        this.simplegoodsList = simplegoodsList;
        mHandler.removeMessages(0);
        mHandler.sendEmptyMessageDelayed(0,30);

    	
    }
    private void init() {
        btn1 = (ImageView)findViewById(R.id.feature_btn1);
        btn2 = (ImageView)findViewById(R.id.feature_btn2);
        btn3 = (ImageView)findViewById(R.id.feature_btn3);
        btn4 = (ImageView)findViewById(R.id.feature_btn4);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String url="";
        switch (v.getId()){
            case R.id.feature_btn4:
                url = simplegoodsList.get(0).brief;
                break;
            case R.id.feature_btn3:
                url = simplegoodsList.get(1).brief;
                break;
            case R.id.feature_btn2:
                url = simplegoodsList.get(2).brief;
                break;
            case R.id.feature_btn1:
                url = simplegoodsList.get(3).brief;
                break;
            default:
                break;
        }
        Intent intent1 = new Intent(context, BannerWebActivity.class);
        intent1.putExtra("url", url);
        context.startActivity(intent1);
        ((Activity) context).overridePendingTransition(R.anim.push_right_in,
                R.anim.push_left_out);

    }
}

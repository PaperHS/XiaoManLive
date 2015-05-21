package com.insthub.ecmobile.component;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.LastBuyAcitivity;
import com.insthub.ecmobile.activity.S2_SearchAcitivity;

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
 * Created by Paper on 15-5-8 2015.
 */
public class SearchCell extends LinearLayout implements View.OnClickListener{
    @InjectView(R.id.searchcell_et)
    TextView mSearchCell;
    @InjectView(R.id.searchcell_lastbuy)
    TextView mLastBuy;
    @InjectView(R.id.searchcell_allproducts)
    TextView mAllProducts;
    @InjectView(R.id.searchcell_hotcell)
    TextView mHotSell;

    Handler mHandler;
    Context mContext;
    int cat_id;

    public SearchCell(Context context, AttributeSet attrs) {
        super(context, attrs);
          mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                bindDataDelay();
            }


        };
        this.mContext = context;

    }

    private void bindDataDelay() {
        init();
    }


    public void init(){
        ButterKnife.inject(this);
    }

    public void bindData(int cat_id){
        this.cat_id=cat_id;
        mHandler.removeMessages(0);
        mHandler.sendEmptyMessageDelayed(0,30);

    }


    @OnClick({R.id.searchcell_lastbuy,R.id.searchcell_allproducts,R.id.searchcell_hotcell,R.id.searchcell_et})
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.searchcell_lastbuy:
                Intent toLastBuy = new Intent(mContext, LastBuyAcitivity.class);
                mContext.startActivity(toLastBuy);
                break;
            case R.id.searchcell_hotcell:
                break;
            case R.id.searchcell_allproducts:
                break;
            case R.id.searchcell_et:
                Intent toSearch = new Intent(mContext, S2_SearchAcitivity.class);
                mContext.startActivity(toSearch);
                break;
            default:
                break;
        }
    }

}

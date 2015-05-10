package com.insthub.ecmobile.component;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.insthub.ecmobile.R;

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
public class SearchCell extends LinearLayout{
    @InjectView(R.id.searchcell_et)
    EditText mSearchCell;
    @InjectView(R.id.searchcell_lastbuy)
    TextView mLastBuy;
    @InjectView(R.id.searchcell_allproducts)
    TextView mAllProducts;

    Handler mHandler;
    Context mContext;
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

    @OnClick({R.id.searchcell_lastbuy,R.id.searchcell_allproducts,R.id.searchcell_hotcell})
    public void onC(View view){
        switch (view.getId()){
            case R.id.searchcell_lastbuy:
                break;
            case R.id.searchcell_hotcell:
                break;
            case R.id.searchcell_allproducts:
                break;
            default:
                break;
        }
    }
}

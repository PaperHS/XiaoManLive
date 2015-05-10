package com.insthub.ecmobile.component;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.CategorySecondAdapter;

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
 * Created by Paper on 15-5-8 2015.
 */
public class ProductsCell extends LinearLayout{
    @InjectView(R.id.cell_products_title)
    TextView mCellTitle;
    @InjectView(R.id.cell_products_gv)
    CustomGridView mGv;

    CategorySecondAdapter adapter;

    Handler mHandler;
    Context mContext;
    public ProductsCell(Context context, AttributeSet attrs) {
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
        adapter = new CategorySecondAdapter(new ArrayList());
        mGv.setAdapter(adapter);
    }


    public void init(){
        ButterKnife.inject(this);
    }


}

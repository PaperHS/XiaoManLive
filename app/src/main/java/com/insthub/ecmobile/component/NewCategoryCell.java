package com.insthub.ecmobile.component;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.CategoryNewAdapter;
import com.insthub.ecmobile.protocol.CATEGORYNEW;

import java.util.ArrayList;
import java.util.List;

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
public class NewCategoryCell extends LinearLayout{
    @InjectView(R.id.cell_products_title)
    TextView mCellTitle;
    @InjectView(R.id.cell_products_gv)
    CustomGridView mGv;

    CategoryNewAdapter adapter;
    private ArrayList<CATEGORYNEW> datalist = new ArrayList<>();
    Handler mHandler;
    Context mContext;
    public NewCategoryCell(Context context, AttributeSet attrs) {
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
        adapter = new CategoryNewAdapter(datalist);
        mGv.setAdapter(adapter);
    }


    public void init(){
        ButterKnife.inject(this);
    }

    public void bindDate(List<CATEGORYNEW> datalist){
        if (datalist !=null &&datalist.size()>0){
            this.datalist.clear();
            this.datalist.addAll(datalist);
            mHandler.removeMessages(0);
            mHandler.sendEmptyMessageDelayed(0,30);
        }
    }
}

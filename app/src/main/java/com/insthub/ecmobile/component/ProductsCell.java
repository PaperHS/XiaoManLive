package com.insthub.ecmobile.component;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.CategorySecondAdapter;
import com.insthub.ecmobile.protocol.SIMPLEGOODS;

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
public class ProductsCell extends LinearLayout{
    @InjectView(R.id.cell_products_title)
    TextView mCellTitle;
    @InjectView(R.id.cell_products_gv)
    CustomGridView mGv;

    private String title;

    CategorySecondAdapter adapter;
    private ArrayList<SIMPLEGOODS> datalist = new ArrayList<>();
    Handler mHandler;
    Context mContext;
    public ProductsCell(Context context, AttributeSet attrs) {
        super(context, attrs);
          mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                bindDataDelay(msg.what);
            }


        };
        this.mContext = context;

    }

    private void bindDataDelay(int type) {
        init();

        switch (type){
            case 0:
                break;
            case 1:

                mCellTitle.setText(title);
                mCellTitle.setBackgroundResource(R.color.translate);
                mCellTitle.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                break;
            case 2:
                mCellTitle.setVisibility(GONE);
                break;
            default:
                break;
        }
        adapter = new CategorySecondAdapter(datalist);
        mGv.setAdapter(adapter);
    }


    public void init(){
        ButterKnife.inject(this);
    }

    public void bindDate(List<SIMPLEGOODS> datalist,int type,String title){

        if (title !=null)
            this.title = title;

        if (datalist !=null &&datalist.size()>0){
            this.datalist.clear();
            this.datalist.addAll(datalist);


        }
        mHandler.removeMessages(type);
        mHandler.sendEmptyMessageDelayed(type,30);
    }
}

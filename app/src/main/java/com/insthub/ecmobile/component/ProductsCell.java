package com.insthub.ecmobile.component;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.Pair;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activityoptions.ActivityCompatICS;
import com.activityoptions.ActivityOptionsCompatICS;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.B2_ProductDetailActivity;
import com.insthub.ecmobile.adapter.CategorySecondAdapter;
import com.insthub.ecmobile.protocol.SIMPLEGOODS;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;

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

    @OnItemClick(R.id.cell_products_gv)
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){

        SIMPLEGOODS simplegoods = datalist.get(i);

        if (simplegoods!=null) {
            ActivityOptionsCompatICS options = ActivityOptionsCompatICS.
                    makeSceneTransitionAnimation((Activity) mContext, Pair.create(view,R.id.good_detail_photo_vp));
            Intent it = new Intent(mContext, B2_ProductDetailActivity.class);
            it.putExtra("good_id", "25");
//            it.putExtra("good_id", simplegoods.goods_id);

            ActivityCompatICS.startActivity((Activity) mContext, it, options.toBundle());
        }
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

package com.insthub.ecmobile.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.component.CustomGridView;

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
 * Created by Paper on 15/6/12 2015.
 */
public class B2_ProductDetailNewActivity extends BaseActivity {

    @InjectView(R.id.b2_viewpager)
    ViewPager b2Viewpager;
    @InjectView(R.id.b2_name)
    TextView b2Name;
    @InjectView(R.id.b2_price)
    TextView b2Price;
    @InjectView(R.id.b2_shopprice)
    TextView b2Shopprice;
    @InjectView(R.id.b2_sellnum)
    TextView b2Sellnum;
    @InjectView(R.id.b2_otherword)
    TextView b2Otherword;
    @InjectView(R.id.b2_detail)
    TextView b2Detail;
    @InjectView(R.id.b2_gv)
    CustomGridView b2Gv;
    @InjectView(R.id.product_detail_minus)
    ImageView productDetailMinus;
    @InjectView(R.id.product_detail_count)
    TextView productDetailCount;
    @InjectView(R.id.product_detail_add)
    ImageView productDetailAdd;
    @InjectView(R.id.add_to_cart)
    Button addToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b2_productdetailnew);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
    }
}

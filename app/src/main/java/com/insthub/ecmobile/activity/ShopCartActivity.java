package com.insthub.ecmobile.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.fragment.C0_ShoppingCartFragment;

/**
 * Created by Administrator on 2015/2/4.
 */
public class ShopCartActivity extends BaseActivity {
    @InjectView(R.id.contaner)
    FrameLayout mContaner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopcart);
        ButterKnife.inject(this);
        getActionBar().setTitle("购物车");
        getSupportFragmentManager().beginTransaction().replace(R.id.contaner,new C0_ShoppingCartFragment()).commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home)
        {
            setResult(-1);
            finish();
            overridePendingTransition(R.anim.anim_hold,R.anim.push_buttom_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
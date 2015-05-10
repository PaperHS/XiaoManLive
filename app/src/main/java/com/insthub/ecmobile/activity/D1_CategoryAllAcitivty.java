package com.insthub.ecmobile.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.fragment.D0_CategoryFragment;

/**
 * Created by Administrator on 2015/2/3.
 */
public class D1_CategoryAllAcitivty extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d1_categoryall_activity);
        getActionBar().setTitle("返回");

        getSupportFragmentManager().beginTransaction().replace(R.id.categoryall_framelayout,new D0_CategoryFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home)
        {
            setResult(-1);
            finish();
            overridePendingTransition(R.anim.anim_hold,R.anim.push_up_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(-1);
        finish();
        overridePendingTransition(R.anim.anim_hold,R.anim.push_up_out);
    }
}
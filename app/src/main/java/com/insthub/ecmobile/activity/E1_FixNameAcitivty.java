package com.insthub.ecmobile.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.R;

/**
 * Created by Administrator on 2015/2/27.
 */
public class E1_FixNameAcitivty extends BaseActivity {
    @InjectView(R.id.fixname_name)
    EditText mFixnameName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e1_fixname_activity);
        ButterKnife.inject(this);
        getActionBar().setTitle("修改姓名");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.e1_fixname_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home)
        {

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
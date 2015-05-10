package com.insthub.ecmobile.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.D0_CategoryAdapter;
import com.insthub.ecmobile.protocol.CATEGORY;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/2/4.
 */
public class D1_CategoryAll2Activity extends BaseActivity {
    @InjectView(R.id.categoryall_listview)
    ListView mCategoryallListview;
    private D0_CategoryAdapter parentListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d1_categoryall2_activity);
        ButterKnife.inject(this);
        getActionBar().setTitle("返回");
        String categoryStr = getIntent().getStringExtra("category");
        CATEGORY category = new CATEGORY();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(categoryStr);
            category.fromJson(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        parentListAdapter = new D0_CategoryAdapter(this,category.children);
        mCategoryallListview.setAdapter(parentListAdapter);
        mCategoryallListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setResult(position);
                D1_CategoryAll2Activity.this.finish();
                overridePendingTransition(R.anim.anim_hold,R.anim.push_up_out);
            }
        });
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
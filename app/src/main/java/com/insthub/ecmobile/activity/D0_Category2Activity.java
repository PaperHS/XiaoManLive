package com.insthub.ecmobile.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.CategorySecond2Adapter;
import com.insthub.ecmobile.model.GoodsListModel;
import com.insthub.ecmobile.protocol.CATEGORY;
import com.insthub.ecmobile.protocol.FILTER;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/1/23.
 */
public class D0_Category2Activity extends BaseActivity {


    @InjectView(R.id.category1_all_products)
    TextView mCategory1AllProducts;
    @InjectView(R.id.category1_choice)
    TextView mCategory1Choice;
    @InjectView(R.id.d0_category_listview)
    GridView mD0CategoryListview;
    @InjectView(R.id.products_counts)
    TextView mProductsCounts;
    @InjectView(R.id.products_total_price)
    TextView mProductsTotalPrice;
    CategorySecond2Adapter adapter;
    FILTER filter = new FILTER();
    GoodsListModel dataModel;
    int position;
    private String categoryStr;
    private CATEGORY category;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d0_activity_category2);
        ButterKnife.inject(this);

        init();
    }

    private void init() {

        dataModel = new GoodsListModel(this);
        categoryStr = getIntent().getStringExtra("category");

        Log.e("hshs","categoryStr222222:"+categoryStr);
        position = getIntent().getIntExtra("position",0);
        category = new CATEGORY();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(categoryStr);
            category.fromJson(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getActionBar().setTitle(category.name);
        mCategory1AllProducts.setText(category.children.get(position).name);
        filter.category_id = category.children.get(position).id;
        filter.sort_by = GoodsListModel.PRICE_DESC;
        adapter = new CategorySecond2Adapter(dataModel.simplegoodsList);
        mD0CategoryListview.setAdapter(adapter);
        mD0CategoryListview.setSelector(new ColorDrawable());
        mD0CategoryListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(D0_Category2Activity.this, B2_ProductDetailActivity.class);
                it.putExtra("good_id",dataModel.simplegoodsList.get(position).goods_id);
                D0_Category2Activity.this.startActivity(it);
                D0_Category2Activity.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

            }
        });
        dataModel.addResponseListener(new BusinessResponse() {
            @Override
            public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
                adapter.notifyDataSetChanged();
            }
        });
        dataModel.fetchPreSearch(filter);
    }

    @OnClick(R.id.products_cart_layout)
    public void onShopCart(){
        Intent it = new Intent(this,ShopCartActivity.class);

        startActivity(it);
        overridePendingTransition(R.anim.push_buttom_in,
                R.anim.anim_hold);
    }

    @OnClick(R.id.category1_choice)
    public void onClickCategory(){
        Intent it = new Intent(this,D1_CategoryAll2Activity.class);
        it.putExtra("category",categoryStr);
        startActivityForResult(it,100);
        overridePendingTransition(R.anim.push_up_in,
                R.anim.anim_hold);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode >=0) {
            position = resultCode;
            mCategory1AllProducts.setText(category.children.get(position).name);
            filter.category_id = category.children.get(position).id;
            filter.sort_by = GoodsListModel.PRICE_DESC;
            adapter = new CategorySecond2Adapter(dataModel.simplegoodsList);
            mD0CategoryListview.setAdapter(adapter);
            dataModel.addResponseListener(new BusinessResponse() {
                @Override
                public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
                    adapter.notifyDataSetChanged();
                }
            });
            dataModel.fetchPreSearch(filter);
        }
        super.onActivityResult(requestCode, resultCode, data);
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
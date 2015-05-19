package com.insthub.ecmobile.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.CategorySecondAdapter;
import com.insthub.ecmobile.model.LastBuyModel;
import com.insthub.ecmobile.protocol.ApiInterface;
import com.insthub.ecmobile.protocol.SESSION;
import com.insthub.ecmobile.protocol.SIMPLEGOODS;
import com.insthub.ecmobile.protocol.STATUS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LastBuyAcitivity extends BaseActivity implements BusinessResponse {

    @InjectView(R.id.lastbuy_gv)
    GridView lastbuyGv;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    LastBuyModel lastBuyModel;
    CategorySecondAdapter adapter;
    ArrayList<SIMPLEGOODS> simplegoodses = new ArrayList<SIMPLEGOODS>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_buy);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("最近购买");
        init();
    }

    private void init() {
        lastBuyModel = new LastBuyModel(this);
        lastBuyModel.addResponseListener(this);
        adapter = new CategorySecondAdapter(simplegoodses);
        lastbuyGv.setAdapter(adapter);
        SESSION session = SESSION.getInstance();
        lastBuyModel.fetchGoods(1,session.uid,session.sid);

    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ApiInterface.PRODUCT_LASTBUY)){
            STATUS status1 = new STATUS();
            status1.fromJson(jo.optJSONObject("status"));
            if (status1.succeed == 1){
                simplegoodses.clear();
                simplegoodses.addAll(lastBuyModel.simplegoodses);
                adapter.notifyDataSetChanged();
            }
        }
    }
}

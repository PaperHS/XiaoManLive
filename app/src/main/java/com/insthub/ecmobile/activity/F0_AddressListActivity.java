package com.insthub.ecmobile.activity;
//
//                       __
//                      /\ \   _
//    ____    ____   ___\ \ \_/ \           _____    ___     ___
//   / _  \  / __ \ / __ \ \    <     __   /\__  \  / __ \  / __ \
//  /\ \_\ \/\  __//\  __/\ \ \\ \   /\_\  \/_/  / /\ \_\ \/\ \_\ \
//  \ \____ \ \____\ \____\\ \_\\_\  \/_/   /\____\\ \____/\ \____/
//   \/____\ \/____/\/____/ \/_//_/         \/____/ \/___/  \/___/
//     /\____/
//     \/___/
//
//  Powered by BeeFramework
//

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.external.activeandroid.query.Select;
import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.EcmobileManager;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.F0_AddressListAdapter;
import com.insthub.ecmobile.model.AddressModel;
import com.insthub.ecmobile.protocol.ADDRESS;
import com.insthub.ecmobile.protocol.ApiInterface;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 收货地址管理
 */
public class F0_AddressListActivity extends BaseActivity implements BusinessResponse {

    @InjectView(R.id.toolbar)
    Toolbar toobar;
    //	private ImageView back;
//	private ImageView add;
    private ListView listView;
    private RelativeLayout bg;
    private F0_AddressListAdapter addressManageAdapter;
    private AddressModel addressModel;
    public Handler messageHandler;
    public int flag;
    private View footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f0_address_list);
        ButterKnife.inject(this);
        setSupportActionBar(toobar);
        getSupportActionBar().setTitle("地址管理");
        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", 0);

//		back = (ImageView) findViewById(R.id.address_manage_back);
//		back.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//	            finish();
//			}
//		});
//
//		add = (ImageView) findViewById(R.id.address_manage_add);
        listView = (ListView) findViewById(R.id.address_manage_list);
        bg = (RelativeLayout) findViewById(R.id.address_list_bg);
        footer = LayoutInflater.from(this).inflate(R.layout.f0_addresslist_footer, null);
        listView.addFooterView(footer);
        footer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(F0_AddressListActivity.this, F1_NewAddressActivity.class);
                startActivity(intent);
            }
        });
//		add.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(F0_AddressListActivity.this, F1_NewAddressActivity.class);
//				startActivity(intent);
//			}
//		});

        addressModel = new AddressModel(this);
        addressModel.addResponseListener(this);

        messageHandler = new Handler() {

            public void handleMessage(Message msg) {

                if (msg.what == 1) {
                    Integer address_id = (Integer) msg.arg1;
                    addressModel.addressDefault(address_id + "");
                }

            }
        };

    }

    @OnClick(R.id.address_null_add)
    public void onAddclick(){
        Intent toAdd = new Intent(this,F1_SearchAddressActivity.class);
        startActivity(toAdd);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (item.getItemId() == android.R.id.home) {

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setAddress() {

        if (addressModel.addressList.size() == 0) {
            listView.setVisibility(View.GONE);
            Resources resource = (Resources) getBaseContext().getResources();
            String non = resource.getString(R.string.non_address);
            ToastView toast = new ToastView(this, non);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            bg.setVisibility(View.VISIBLE);
        } else {
            bg.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            addressManageAdapter = new F0_AddressListAdapter(this, addressModel.addressList, flag);
            listView.setAdapter(addressManageAdapter);

            addressManageAdapter.parentHandler = messageHandler;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        addressModel.getAddressList();
        if (EcmobileManager.getUmengKey(this) != null) {
            MobclickAgent.onPageStart("AddressManage");
            MobclickAgent.onResume(this, EcmobileManager.getUmengKey(this), "");
        }
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        if (url.endsWith(ApiInterface.ADDRESS_LIST)) {
            setAddress();
        } else if (url.endsWith(ApiInterface.ADDRESS_SETDEFAULT)) {
            Intent intent = new Intent();
            intent.putExtra("address", "address");
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }

    public static List<ADDRESS> getAll() {
        return new Select().from(ADDRESS.class).execute();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (EcmobileManager.getUmengKey(this) != null) {
            MobclickAgent.onPageEnd("AddressManage");
            MobclickAgent.onPause(this);
        }
    }
}

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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.service.NetworkStateService;
import com.insthub.ecmobile.event.HomeClickEvent;
import com.insthub.ecmobile.event.ItemClickEvent;
import com.insthub.ecmobile.fragment.D0_CategoryFragment;
import com.insthub.ecmobile.fragment.TabsFragment;
import com.insthub.ecmobile.model.AddressModel;
import com.insthub.ecmobile.protocol.ApiInterface;
import com.insthub.ecmobile.protocol.STATUS;
import com.tencent.android.tpush.XGPushManager;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.KeyEvent;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.insthub.BeeFramework.BeeFrameworkApp;
import com.insthub.BeeFramework.model.BeeQuery;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.EcmobileManager;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.protocol.FILTER;
import com.umeng.analytics.MobclickAgent;

public class EcmobileMainActivity extends FragmentActivity implements BusinessResponse
{

    public static final String RESPONSE_METHOD = "method";
    public static final String RESPONSE_CONTENT = "content";
    public static final String RESPONSE_ERRCODE = "errcode";
    protected static final String ACTION_LOGIN = "com.baidu.pushdemo.action.LOGIN";
    public static final String ACTION_MESSAGE = "com.baiud.pushdemo.action.MESSAGE";
    public static final String ACTION_RESPONSE = "bccsclient.action.RESPONSE";
    public static final String ACTION_PUSHCLICK = "bccsclient.action.PUSHCLICK";
    public static final String ACTION_SHOW_MESSAGE = "bccsclient.action.SHOW_MESSAGE";
    protected static final String EXTRA_ACCESS_TOKEN = "access_token";
    public static final String EXTRA_MESSAGE = "message";
    public static final String CUSTOM_CONTENT ="CustomContent";

    // 在百度开发者中心查询应用的API Key
    public static String API_KEY ;



    public void onCreate(Bundle savedInstanceState)
    {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
        Context context = getApplicationContext();
        XGPushManager.registerPush(context);
//	    Intent intent = new Intent();
//		intent.setAction("com.BeeFramework.NetworkStateService");
        Intent intent = new Intent(NetworkStateService.class.getName());
		intent.setPackage("com.insthub.BeeFramework.service");
		startService(intent);
		
		if(getIntent().getStringExtra(CUSTOM_CONTENT) != null) {
			pushIntent(getIntent().getStringExtra(CUSTOM_CONTENT));
		}
        getActionBar().setDisplayHomeAsUpEnabled(false);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        // 如果要统计Push引起的用户使用应用情况，请实现本方法，且加上这一个语句
        setIntent(intent);
        handleIntent(intent);
        Log.e("hshs","toshopcart:"+intent.getBooleanExtra("toShopCart",false)+"");
        if (intent.getBooleanExtra("toShopCart",false)){
            ((TabsFragment)(getSupportFragmentManager().findFragmentById(R.id.tabs_fragment))).OnTabSelected("tab_two");
        }
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();

        if (ACTION_RESPONSE.equals(action))
        {

        } else if (ACTION_LOGIN.equals(action))
        {

        }
        else if (ACTION_MESSAGE.equals(action))
        {

        }
        else if (ACTION_PUSHCLICK.equals(action))
        {
            String message = intent.getStringExtra(CUSTOM_CONTENT);
            pushIntent(message);
        }
    }
    
    public void pushIntent(String message) {
    	if(message != null) {
        	try
            {
                JSONObject jsonObject = new JSONObject(message);
                String actionString = jsonObject.optString("a");
                if (0 == actionString.compareTo("s")) {
                    String parameter = jsonObject.optString("k");
                    if (null != parameter && parameter.length() > 0) {
                    	try {
							parameter = URLDecoder.decode(parameter,"UTF-8");
						} catch (UnsupportedEncodingException e1) {
							 
							e1.printStackTrace();
						} 
                        Intent it = new Intent(this, B1_ProductListActivity.class);
                        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        FILTER filter = new FILTER();
                        filter.keywords =parameter;
                        try {
                            it.putExtra(B1_ProductListActivity.FILTER,filter.toJson().toString());
                        } catch (JSONException e) {
                        	e.printStackTrace();
                        }
                        startActivity(it);
                    }
                } else if(0 == actionString.compareTo("w")) {
                	String parameter = jsonObject.optString("u");
                	if (null != parameter && parameter.length() > 0) {
                		try {
							parameter = URLDecoder.decode(parameter,"UTF-8");
						} catch (UnsupportedEncodingException e1) {
							 
							e1.printStackTrace();
						}
                        Intent it = new Intent(this, BannerWebActivity.class);
                        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        it.putExtra("url",parameter);
                        startActivity(it);
                    }
                }
            } catch (JSONException e) {

            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        
         {
        	API_KEY = EcmobileManager.getPushKey(this);
            PushManager.activityStarted(this);
            PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, API_KEY);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
//        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(EcmobileManager.getUmengKey(this)!=null){
            MobclickAgent.onResume(this, EcmobileManager.getUmengKey(this), "");
        }


    }

    private boolean isExit = false;
    //退出操作
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {        
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(isExit==false){
                isExit=true;
                Resources resource = (Resources) getBaseContext().getResources();
                String exit=resource.getString(R.string.again_exit);                
                ToastView toast = new ToastView(getApplicationContext(), exit);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                handler.sendEmptyMessageDelayed(0, 3000);
                if(BeeQuery.environment() == BeeQuery.ENVIROMENT_DEVELOPMENT)
                {
                    BeeFrameworkApp.getInstance().showBug(this);
                }

                return true;
            } else {
            	Intent intent = new Intent();
        		intent.setAction("com.BeeFramework.NetworkStateService");
        		stopService(intent);
                finish();
                ToastView.cancel();
                return false;
            }
        }
        return true;
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit=false;
        }
    };


    @Override
    protected void onStop() {
        super.onStop();
        PushManager.activityStoped(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(EcmobileManager.getUmengKey(this)!=null){
            MobclickAgent.onPause(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                EventBus.getDefault().post(new HomeClickEvent());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ApiInterface.ADDRESS_LIST)){
            STATUS s = new STATUS();
            s.fromJson(jo.optJSONObject("status"));
            if (s.succeed == 1){

            }
        }
    }
}

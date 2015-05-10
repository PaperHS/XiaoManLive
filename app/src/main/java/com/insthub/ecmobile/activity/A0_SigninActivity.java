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
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.fragment.E0_ProfileFragment;
import com.insthub.ecmobile.model.LoginModel;
import com.insthub.ecmobile.protocol.ApiInterface;
import com.sina.weibo.sdk.utils.LogUtil;
import org.json.JSONException;
import org.json.JSONObject;


public class A0_SigninActivity extends BaseActivity implements OnClickListener, BusinessResponse {

    @InjectView(R.id.login_phone_num)
    EditText mLoginPhoneNum;
    @InjectView(R.id.login_code_et)
    EditText mLoginCodeEt;
    @InjectView(R.id.login_getcode)
    Button mLoginGetcode;
    @InjectView(R.id.login_comfirm)
    Button mLoginComfirm;
    @InjectView(R.id.a0_signin_protrol)
    TextView mA0SigninProtrol;

//	private ImageView back;
//	private Button login;

//	private EditText userName;
//	private EditText password;
//	private TextView register;

    private String name;
    private String psd;
    private int endTime = 60;

    private LoginModel loginModel;
    private final static int REQUEST_SIGN_UP = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a0_signin);
        ButterKnife.inject(this);

        getActionBar().setTitle("一键登录");
        getActionBar().setDisplayHomeAsUpEnabled(false);
        loginModel = new LoginModel(A0_SigninActivity.this);
        loginModel.addResponseListener(this);
        mA0SigninProtrol.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
//		userName = (EditText) findViewById(R.id.login_name);
//		password = (EditText) findViewById(R.id.login_password);
//		register = (TextView) findViewById(R.id.login_register);
//        register.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//        login = (Button)findViewById(R.id.login_login);
//		register.setOnClickListener(this);
//		login.setOnClickListener(this);
    }

    Handler hd = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            endTime--;
            if (endTime < 0) {
                mLoginGetcode.setEnabled(true);
                mLoginGetcode.setTextColor(getResources().getColor(R.color.main_color));
                mLoginGetcode.setText("发送验证码");
                endTime = 60;
            } else {
                mLoginGetcode.setText(endTime + "秒再次发送");
                hd.sendEmptyMessageDelayed(0, 1000);
            }
        }
    };

    @Override
    @OnClick({R.id.login_getcode, R.id.login_comfirm,R.id.a0_signin_protrol})
    public void onClick(View v) {
        Resources resource = (Resources) getBaseContext().getResources();
        String usern = resource.getString(R.string.user_name_cannot_be_empty);
        String pass = resource.getString(R.string.password_cannot_be_empty);
        Intent intent;
        switch (v.getId()) {
            case R.id.a0_signin_protrol://阅读协议
                Intent intent1 = new Intent(this, BannerWebActivity.class);
                intent1.putExtra("url", "http://yw.hetnet.cn/agreement.php");
                startActivity(intent1);
                overridePendingTransition(R.anim.push_right_in,
                        R.anim.push_left_out);
                break;
            case R.id.login_getcode:
                String ph_hum = mLoginPhoneNum.getText().toString().trim();
                if (ph_hum.equals("") && ph_hum.length() != 11) {
                    Toast.makeText(this, "请填写正确的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                mLoginGetcode.setEnabled(false);
                mLoginGetcode.setTextColor(Color.WHITE);

                loginModel.getCode(mLoginPhoneNum.getText().toString().trim());
                hd.sendEmptyMessage(0);
                break;
            case R.id.login_comfirm:
                name = mLoginPhoneNum.getText().toString();
                psd = mLoginCodeEt.getText().toString();
//                name = "测试";
//                psd = "123456";
                if (name.length() < 2) {
                    ToastView toast = new ToastView(this, resource.getString(R.string.username_too_short));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                if (name.length() > 20) {
                    ToastView toast = new ToastView(this, resource.getString(R.string.username_too_long));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                if (psd.length() < 6) {
                    ToastView toast = new ToastView(this, resource.getString(R.string.password_too_short));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                if (psd.length() > 20) {
                    ToastView toast = new ToastView(this, resource.getString(R.string.password_too_long));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                if ("".equals(name)) {
                    ToastView toast = new ToastView(this, usern);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if ("".equals(psd)) {
                    ToastView toast = new ToastView(this, pass);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {

                    loginModel.login(name, psd);
                    CloseKeyBoard();

                }
                break;

//		case R.id.login_back:
//			finish();
//			CloseKeyBoard();
//			overridePendingTransition(R.anim.push_up_in,R.anim.push_up_out);
//			break;
//            case R.id.login_login:
//                name = userName.getText().toString();
//                psd = password.getText().toString();
//                if (name.length() < 2) {
//                    ToastView toast = new ToastView(this, resource.getString(R.string.username_too_short));
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//                }
//                if (name.length() > 20) {
//                    ToastView toast = new ToastView(this, resource.getString(R.string.username_too_long));
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//                }
//                if (psd.length() < 6) {
//                    ToastView toast = new ToastView(this, resource.getString(R.string.password_too_short));
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//                }
//                if (psd.length() > 20) {
//                    ToastView toast = new ToastView(this, resource.getString(R.string.password_too_long));
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//                }
//                if ("".equals(name)) {
//                    ToastView toast = new ToastView(this, usern);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//                } else if ("".equals(psd)) {
//                    ToastView toast = new ToastView(this, pass);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//                } else {
//                    loginModel = new LoginModel(A0_SigninActivity.this);
//                    loginModel.addResponseListener(this);
//                    loginModel.login(name, psd);
//                    CloseKeyBoard();
//
//                }
//                break;
//            case R.id.login_register:
//                intent = new Intent(this, A1_SignupActivity.class);
//                startActivityForResult(intent, REQUEST_SIGN_UP);
//                break;
        }

    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        Log.e("hshs","login:"+jo.toString());
        if (loginModel.responseStatus.succeed == 1) {
            if (url.endsWith(ApiInterface.USER_SMS_SIGNIN)) {
                Intent intent = new Intent();
                intent.putExtra("login", true);
                setResult(Activity.RESULT_OK, intent);
                finish();
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }
        } else {
            Log.e("hshs", "return error!");
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SIGN_UP) {
            if (data != null) {
                Intent intent = new Intent();
                intent.putExtra("login", true);
                setResult(Activity.RESULT_OK, intent);
                finish();
                E0_ProfileFragment.isRefresh = true;
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        }
        return true;
    }

    // 关闭键盘
    public void CloseKeyBoard() {
        mLoginPhoneNum.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mLoginPhoneNum.getWindowToken(), 0);
    }
}

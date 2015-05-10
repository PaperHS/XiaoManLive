package com.insthub.ecmobile.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.model.UserInfoModel;
import com.insthub.ecmobile.protocol.ApiInterface;
import com.insthub.ecmobile.protocol.STATUS;
import org.json.JSONException;
import org.json.JSONObject;


public class F0_WalletFragment extends Fragment implements BusinessResponse{

    View rootView;
    @InjectView(R.id.f0_wallet_balance)
    TextView mF0WalletBalance;
    private UserInfoModel userInfoModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (View) inflater.inflate(R.layout.f0_wallet_fragment, null);
        ButterKnife.inject(this,rootView);
        init();
        return rootView;
    }

    private void init() {
        (getActivity()).getActionBar().setTitle("钱包");
        if (userInfoModel == null){
            userInfoModel = new UserInfoModel(getActivity());
            userInfoModel.addResponseListener(this);
            userInfoModel.getUserInfo();
        }
    }


    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.equalsIgnoreCase(ApiInterface.USER_INFO)){
            STATUS status1 = new STATUS();
            status1.fromJson(jo.optJSONObject("status"));
            if (status1.succeed==1){
                mF0WalletBalance.setText("￥"+userInfoModel.user.user_money);
            }
        }
    }
}

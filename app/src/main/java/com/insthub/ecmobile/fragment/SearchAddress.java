package com.insthub.ecmobile.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.model.AddressModel;
import com.insthub.ecmobile.protocol.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * ,==.              |~~~
 * /  66\             |
 * \c  -_)         |~~~
 * `) (           |
 * /   \       |~~~
 * /   \ \      |
 * ((   /\ \_ |~~~
 * \\  \ `--`|
 * / / /  |~~~
 * ___ (_(___)_|
 * <p/>
 * Created by Paper on 15-6-1 2015.
 */
public class SearchAddress extends Fragment implements BusinessResponse{

    @InjectView(R.id.searchaddress_et)
    EditText searchaddressEt;
    @InjectView(R.id.searchaddress_btn)
    TextView searchaddressBtn;
    @InjectView(R.id.searchaddress_lv)
    XListView searchaddressLv;
    AddressModel addressModel ;

    public void setmCityId(int mCityId) {
        this.mCityId = mCityId;
    }

    private int mCityId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f1_fragment_searchaddress, null);

        ButterKnife.inject(this, view);
        addressModel =new AddressModel(getActivity());
        addressModel.addResponseListener(this);

        return view;
    }

    @OnClick(R.id.searchaddress_btn)
    public void  onSearch(){
        if (TextUtils.isEmpty(searchaddressEt.getEditableText())){
            ToastView toastView = new ToastView(getActivity(),"请输入搜索地址");
            toastView.show();
            return;
        }
        addressModel.searchAddress(mCityId,searchaddressEt.getEditableText().toString().trim());
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ApiInterface.ADDRESS_SEARCH)){

        }
    }


}

package com.insthub.ecmobile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.model.AddressModel;
import com.insthub.ecmobile.protocol.ApiInterface;
import com.insthub.ecmobile.protocol.SEARCHADDRESS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;

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
public class SearchAddress extends Fragment implements BusinessResponse {

    @InjectView(R.id.searchaddress_et)
    EditText searchaddressEt;
    @InjectView(R.id.searchaddress_btn)
    Button searchaddressBtn;
    @InjectView(R.id.searchaddress_lv)
    ListView searchaddressLv;
    AddressModel addressModel;

    public void setmCityName(String mCityName) {
        this.mCityName = mCityName;
    }

    private String mCityName;
    private AddressAdapter addressAdapter;
    private List<SEARCHADDRESS> datalist = new ArrayList<>();

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
        addressModel = new AddressModel(getActivity());
        addressModel.addResponseListener(this);
        addressAdapter = new AddressAdapter();
        searchaddressLv.setAdapter(addressAdapter);
        return view;
    }

    @OnClick(R.id.searchaddress_btn)
    public void onSearch() {
        if (TextUtils.isEmpty(searchaddressEt.getEditableText())) {
            ToastView toastView = new ToastView(getActivity(), "请输入搜索地址");
            toastView.show();
            return;
        }
        addressModel.searchAddress(mCityId, searchaddressEt.getEditableText().toString().trim());
    }

    @OnItemClick(R.id.searchaddress_lv)
    public void onIclick(int p){
        Intent result = new Intent();
        result.putExtra("address", mCityName+datalist.get(p).region_name);
        getActivity().setResult(Integer.parseInt(datalist.get(p).region_id), result);
        getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ApiInterface.ADDRESS_SEARCH)) {
            datalist.clear();
            datalist.addAll(addressModel.searchaddress);
            addressAdapter.notifyDataSetChanged();
//            Intent result = new Intent();
//            result.putExtra("address", addressModel.searchaddress.region_name);
//            getActivity().setResult(Integer.parseInt(addressModel.searchaddress.region_id),result);
//            getActivity().finish();
        }
    }
    class AddressAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datalist.size();
        }

        @Override
        public Object getItem(int i) {
            return datalist.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_search_address, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }else {
                holder = (ViewHolder)view.getTag();
            }
            holder.itemSearchName.setText(datalist.get(i).region_name);
            return view;
        }
    }
    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_search_address.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @InjectView(R.id.item_search_name)
        TextView itemSearchName;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}

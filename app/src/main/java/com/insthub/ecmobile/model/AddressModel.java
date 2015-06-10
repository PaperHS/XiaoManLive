package com.insthub.ecmobile.model;

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

import android.content.Context;
import android.util.Log;

import com.external.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;
import com.insthub.BeeFramework.view.MyProgressDialog;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.protocol.ADDRESS;
import com.insthub.ecmobile.protocol.ADDRESSNEW;
import com.insthub.ecmobile.protocol.ApiInterface;
import com.insthub.ecmobile.protocol.CommonResponse;
import com.insthub.ecmobile.protocol.REGIONS;
import com.insthub.ecmobile.protocol.REGION_DATA;
import com.insthub.ecmobile.protocol.SEARCHADDRESS;
import com.insthub.ecmobile.protocol.SESSION;
import com.insthub.ecmobile.protocol.SearchAddrResponse;
import com.insthub.ecmobile.protocol.addressaddRequest;
import com.insthub.ecmobile.protocol.addressaddResponse;
import com.insthub.ecmobile.protocol.addressdeleteRequest;
import com.insthub.ecmobile.protocol.addressdeleteResponse;
import com.insthub.ecmobile.protocol.addressinfoRequest;
import com.insthub.ecmobile.protocol.addressinfoResponse;
import com.insthub.ecmobile.protocol.addresslistRequest;
import com.insthub.ecmobile.protocol.addresslistResponse;
import com.insthub.ecmobile.protocol.addresssetDefaultRequest;
import com.insthub.ecmobile.protocol.addresssetDefaultResponse;
import com.insthub.ecmobile.protocol.addressupdateRequest;
import com.insthub.ecmobile.protocol.addressupdateResponse;
import com.insthub.ecmobile.protocol.regionRequest;
import com.insthub.ecmobile.protocol.regionResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressModel extends BaseModel {

    public ArrayList<ADDRESS> addressList = new ArrayList<ADDRESS>();
    public ArrayList<REGIONS> regionsList0 = new ArrayList<REGIONS>();
    public ADDRESS address;
    public List<SEARCHADDRESS> searchaddress = new ArrayList<>();
    public List<CityModel> citys ;
    public AddressModel(Context context) {
        super(context);

    }

    // 获取地址列表
    public void getAddressList() {

        final addresslistRequest request = new addresslistRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    AddressModel.this.callback(url, jo, status);
                    if (jo != null) {
                        addresslistResponse response = new addresslistResponse();
                        response.fromJson(jo);
                        if (response.status.succeed == 1) {
                           addressList.clear();
                           ArrayList<ADDRESS> data = response.data;
                            if (null != data && data.size() > 0) {
                                addressList.addAll(data);
                            }
                        }
                       AddressModel.this.OnMessageResponse(url, jo, status);
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }

        };
        request.session=SESSION.getInstance();
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            // TODO: handle exception
        }
        cb.url(ApiInterface.ADDRESS_LIST).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

    // 添加地址
    public void addAddress(String consignee, String region_id, String address, String mobile) {
       addressaddRequest request=new addressaddRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                AddressModel.this.callback(url, jo, status);
                try {
                    addressaddResponse response = new addressaddResponse();
                    response.fromJson(jo);
                    if (response.status.succeed == 1) {
                            AddressModel.this.OnMessageResponse(url, jo, status);
                    } else {
                        AddressModel.this.OnMessageResponse(url, jo, status);
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        };

        SESSION session = SESSION.getInstance();
//        ADDRESS add = new ADDRESS();
//        add.consignee = consignee;
//        add.tel = tel;
//        add.email = email;
//        add.mobile = mobile;
//        add.zipcode = zipcode;
//        add.address = address;
//        add.country = country;
//        add.province = province;
//        add.city = city;
//        add.district = district;
        ADDRESSNEW addressnew = new ADDRESSNEW();
        addressnew.address=address;
        addressnew.consignee =consignee;
        addressnew.mobile=mobile;
        addressnew.region_id = region_id;
        request.session =session;
        request.address=addressnew;
//       request.session=session;
//       request.address=add;

        Map<String, String> params = new HashMap<String, String>();
        try {
//            JSONObject jsonObject = new JSONObject();
//            JSONObject re_address = new JSONObject();
//            re_address.put("consignee",consignee);
//            re_address.put("region_id",region_id);
//            re_address.put("address",address);
//            re_address.put("mobile",mobile);
//            jsonObject.put("address", re_address.toString());
//            jsonObject.put("session", session.toJson().toString());
            params.put("json", request.toJson().toString());
            Log.e("http", "add address:" + request.toJson().toString());
        } catch (JSONException e) {
            // TODO: handle exception
        }

        cb.url(ApiInterface.ADDRESS_ADD).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

    // 获取地区城市
    public void region(int parent_id) {
        regionRequest request=new regionRequest();
        request.parent_id=parent_id;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                AddressModel.this.callback(url, jo, status);

                try {
                    regionResponse response = new regionResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            REGION_DATA data = response.data;
                            ArrayList<REGIONS> regionses = data.regions;
                            regionsList0.clear();
                            if (null != regionses && regionses.size() > 0) {
                                regionsList0.clear();
                                for (int i = 0; i < regionses.size(); i++) {
                                    REGIONS regions = regionses.get(i);
                                    regionsList0.add(regions);
                                }
                            }
                            AddressModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            AddressModel.this.OnMessageResponse(url, jo, status);
                        }
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        };

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());

        } catch (JSONException e) {
            // TODO: handle exception
        }

        cb.url(ApiInterface.REGION).type(JSONObject.class).params(params);
        aq.ajax(cb);

    }


    // 获取地址详细信息
    public void getAddressInfo(String address_id) {
        addressinfoRequest request=new addressinfoRequest();
        request.address_id=address_id;
        request.session=SESSION.getInstance();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                AddressModel.this.callback(url, jo, status);

                try {
                    addressinfoResponse response = new addressinfoResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            address=response.data;
                            AddressModel.this.OnMessageResponse(url, jo, status);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());

        } catch (JSONException e) {
            // TODO: handle exception
        }
        cb.url(ApiInterface.ADDRESS_INFO).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

    // 设置默认地址
    public void addressDefault(String address_id) {

        addresssetDefaultRequest request=new addresssetDefaultRequest();
        request.address_id=address_id;
        request.session=SESSION.getInstance();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                AddressModel.this.callback(url, jo, status);

                try {
                    addresssetDefaultResponse response = new addresssetDefaultResponse();
                    response.fromJson(jo);
                    if(jo!=null){
                        if (response.status.succeed == 1) {
                            AddressModel.this.OnMessageResponse(url, jo, status);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());

        } catch (JSONException e) {
            // TODO: handle exception
        }
        cb.url(ApiInterface.ADDRESS_SETDEFAULT).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

    // 删除地址
    public void addressDelete(String address_id) {
        addressdeleteRequest request=new addressdeleteRequest();
        request.address_id=address_id;
        request.session=SESSION.getInstance();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                AddressModel.this.callback(url, jo, status);

                try {
                    addressdeleteResponse response = new addressdeleteResponse();
                    response.fromJson(jo);
                    if(jo!=null) {
                        if (response.status.succeed == 1) {
                            AddressModel.this.OnMessageResponse(url, jo, status);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());

        } catch (JSONException e) {
            // TODO: handle exception
        }
        cb.url(ApiInterface.ADDRESS_DELETE).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

    // 修改地址
    public void addressUpdate(String address_id, String consignee, String tel, String email, String mobile, String zipcode, String address, String country, String province, String city, String district) {
       addressupdateRequest request=new addressupdateRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                AddressModel.this.callback(url, jo, status);
                try {
                    addressupdateResponse response = new addressupdateResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            AddressModel.this.OnMessageResponse(url, jo, status);
                        }
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        };
        ADDRESS add = new ADDRESS();
        add.consignee = consignee;
        add.tel = tel;
        add.email = email;
        add.mobile = mobile;
        add.zipcode = zipcode;
        add.address = address;
        add.country = country;
        add.province = province;
        add.city = city;
        add.district = district;
        request.address=add;
        request.session=SESSION.getInstance();
        request.address_id=address_id;

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cb.url(ApiInterface.ADDRESS_UPDATE).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext,mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

    public void searchAddress(int keyword,String keywords){
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                AddressModel.this.callback(url, jo, status);
                try {
                    SearchAddrResponse response = new SearchAddrResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            searchaddress = response.data;
                            AddressModel.this.OnMessageResponse(url, jo, status);
                        }
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        };
        Map<String, String> params = new HashMap<String, String>();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("city_id",keyword);
            jsonObject.put("keywords",keywords);
            params.put("json", jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cb.url(ApiInterface.ADDRESS_SEARCH).type(JSONObject.class).params(params);
        aq.ajax(cb);
    }

    public void fetchCitys(){
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                AddressModel.this.callback(url, jo, status);
                try {
                    CommonResponse response = new CommonResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<CityModel>>(){}.getType();

                            citys = gson.fromJson(response.data,type);
                            AddressModel.this.OnMessageResponse(url, jo, status);
                        }
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        };
        cb.url(ApiInterface.ADDRESS_CITY).type(JSONObject.class);
        aq.ajax(cb);
    }

}

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

import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.model.BaseModel;
import com.insthub.BeeFramework.model.BeeCallback;
import com.insthub.BeeFramework.view.MyProgressDialog;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.protocol.ApiInterface;
import com.insthub.ecmobile.protocol.CATEGORYNEW;
import com.insthub.ecmobile.protocol.CateProducts;
import com.insthub.ecmobile.protocol.FILTER;
import com.insthub.ecmobile.protocol.FirstCategory;
import com.insthub.ecmobile.protocol.PAGINATED;
import com.insthub.ecmobile.protocol.PAGINATION;
import com.insthub.ecmobile.protocol.SIMPLEGOODS;
import com.insthub.ecmobile.protocol.categoryResponseNew;
import com.insthub.ecmobile.protocol.firstLvRecomRequest;
import com.insthub.ecmobile.protocol.firstLvRecomResponse;
import com.insthub.ecmobile.protocol.searchRequest;
import com.insthub.ecmobile.protocol.searchResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirstLvModel extends BaseModel {

    public ArrayList<SIMPLEGOODS> simplegoodsList = new ArrayList<SIMPLEGOODS>();
    public ArrayList<CATEGORYNEW> categoryList = new ArrayList<CATEGORYNEW>();
    public ArrayList<CateProducts> cateGoodsList= new ArrayList<CateProducts>();
    public static String PRICE_DESC = "price_desc";
    public static String PRICE_ASC = "price_asc";
    public static String IS_HOT = "is_hot";

    public static final int PAGE_COUNT = 6;

    public FirstLvModel(Context context) {
        super(context);
    }

    public void fetchFirstRecom(int suppliers_id,int num,int offset) {
        firstLvRecomRequest request = new firstLvRecomRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                FirstLvModel.this.callback(url, jo, status);
                try {
                    firstLvRecomResponse response = new firstLvRecomResponse();
                    response.fromJson(jo);
                    if (jo != null) {

                        if (response.status.succeed == 1) {
                            ArrayList<SIMPLEGOODS> data = response.data;

                            simplegoodsList.clear();
                            if (null != data && data.size() > 0) {
                                simplegoodsList.clear();
                                simplegoodsList.addAll(data);

                            }

                            FirstLvModel.this.OnMessageResponse(url, jo, status);
                        }
                    }

                } catch (JSONException e) {
                    // TODO: handle exception
                }

            }
        };



        request.suppliers_id = suppliers_id;
        request.number = num;
        request.offset = offset;
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cb.url(ApiInterface.PRODUCT_RECOMMEND).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.ajax(cb);

    }

    public void fetchBuyRecord(){

    }

    public void fetchLvCategory(int cat_id){
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                FirstLvModel.this.callback(url, jo, status);
                try {
                    categoryResponseNew response = new categoryResponseNew();
                    response.fromJson(jo);
                    if (jo != null) {

                        if (response.status.succeed == 1) {
                            ArrayList<CATEGORYNEW> data = response.data;

                            categoryList.clear();
                            if (null != data && data.size() > 0) {
                                categoryList.clear();
                                categoryList.addAll(data);

                            }

                            FirstLvModel.this.OnMessageResponse(url, jo, status);
                        }
                    }

                } catch (JSONException e) {
                    // TODO: handle exception
                }

            }
        };


      Map<String, String> params = new HashMap<String, String>();
        try {
            JSONObject object = new JSONObject();
            object.put("suppliers_id",cat_id);
//            params.put("suppliers_id",Integer.toString(cat_id));
            params.put("json", object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cb.url(ApiInterface.PRODUCT_CATEGORY).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.ajax(cb);


    }
    public  void fetchCatGoods(int suppliers_id){
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                FirstLvModel.this.callback(url, jo, status);
                try {
                    FirstCategory response = new FirstCategory();
                    response.fromJson(jo);
                    if (jo != null) {

                        if (response.status.succeed == 1) {
                            ArrayList<CateProducts> data = response.data;

                            cateGoodsList.clear();
                            if (null != data && data.size() > 0) {
                                cateGoodsList.clear();
                                cateGoodsList.addAll(data);

                            }

                            FirstLvModel.this.OnMessageResponse(url, jo, status);
                        }
                    }

                } catch (JSONException e) {
                    // TODO: handle exception
                }

            }
        };


      Map<String, String> params = new HashMap<String, String>();
        try {
            JSONObject object = new JSONObject();
            object.put("suppliers_id",suppliers_id);
            params.put("json", object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cb.url(ApiInterface.PRODUCT_CATGOODS).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.ajax(cb);

    }

    public void fetchPreSearchMore(FILTER filter) {
        searchRequest request = new searchRequest();


        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                FirstLvModel.this.callback(url, jo, status);
                try {
                    searchResponse response = new searchResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        PAGINATED paginated = response.paginated;
                        if (response.status.succeed == 1) {
                            ArrayList<SIMPLEGOODS> data = response.data;

                            if (null != data && data.size() > 0) {
                                simplegoodsList.addAll(data);
                            }

                            FirstLvModel.this.OnMessageResponse(url, jo, status);

                        }
                    }
                } catch (JSONException e) {
                    // TODO: handle exception
                }

            }

        };

        PAGINATION pagination = new PAGINATION();
        pagination.page = (int) Math.ceil((double) simplegoodsList.size() * 1.0 / PAGE_COUNT) + 1;
        pagination.count = PAGE_COUNT;

        request.filter = filter;
        request.pagination = pagination;
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cb.url(ApiInterface.SEARCH).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.ajax(cb);

    }

}

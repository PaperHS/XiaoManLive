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
import com.insthub.ecmobile.protocol.AD;
import com.insthub.ecmobile.protocol.ADResponse;
import com.insthub.ecmobile.protocol.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ADModel extends BaseModel {

    public ArrayList<AD> ads = new ArrayList<AD>();

    public static String PRICE_DESC = "price_desc";
    public static String PRICE_ASC = "price_asc";
    public static String IS_HOT = "is_hot";

    public static final int PAGE_COUNT = 6;

    public ADModel(Context context) {
        super(context);
    }

    public void fetchADs(int postId) {

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                ADModel.this.callback(url, jo, status);
                try {
                    ADResponse response = new ADResponse();
                    response.fromJson(jo);
                    if (jo != null) {

                        if (response.status.succeed == 1) {
                            ArrayList<AD> data = response.data;

                            ads.clear();
                            if (null != data && data.size() > 0) {
                                ads.clear();
                                ads.addAll(data);

                            }

                            ADModel.this.OnMessageResponse(url, jo, status);
                        }
                    }

                } catch (JSONException e) {
                    // TODO: handle exception
                }

            }
        };




        JSONObject object = new JSONObject();


        Map<String, String> params = new HashMap<String, String>();
        try {
            object.put("pos_id",postId);
            params.put("json", object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cb.url(ApiInterface.HOME_AD).type(JSONObject.class).params(params);

        aq.ajax(cb);

    }



}

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
import com.insthub.ecmobile.protocol.ApiInterface;
import com.insthub.ecmobile.protocol.SIMPLEGOODS;
import com.insthub.ecmobile.protocol.firstLvRecomResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LastBuyModel extends BaseModel {

    public ArrayList<SIMPLEGOODS> simplegoodses = new ArrayList<SIMPLEGOODS>();

    public LastBuyModel(Context context) {
        super(context);
    }

    public void fetchGoods(int catid,String uid,String sid) {

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                LastBuyModel.this.callback(url, jo, status);
                try {
                    firstLvRecomResponse response = new firstLvRecomResponse();
                    response.fromJson(jo);
                    if (jo != null) {

                        if (response.status.succeed == 1) {
                            ArrayList<SIMPLEGOODS> data = response.data;

                            simplegoodses.clear();
                            if (null != data && data.size() > 0) {
                                simplegoodses.clear();
                                simplegoodses.addAll(data);

                            }

                            LastBuyModel.this.OnMessageResponse(url, jo, status);
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
            object.put("cat_id",catid);
            object.put("uid",uid);
            object.put("sid",sid);
            params.put("json", object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cb.url(ApiInterface.PRODUCT_LASTBUY).type(JSONObject.class).params(params);

        aq.ajax(cb);

    }



}

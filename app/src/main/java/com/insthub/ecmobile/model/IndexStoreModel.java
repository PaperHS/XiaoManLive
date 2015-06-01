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
import com.insthub.ecmobile.protocol.HOMESTORE;
import com.insthub.ecmobile.protocol.HomeIndexResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class IndexStoreModel extends BaseModel {



   public ArrayList<HOMESTORE> stores = new ArrayList<HOMESTORE>();

    public IndexStoreModel(Context context) {
        super(context);
    }

    public void fetchStores() {

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                IndexStoreModel.this.callback(url, jo, status);
                try {
                    HomeIndexResponse response = new HomeIndexResponse();
                    response.fromJson(jo);
                    if (jo != null) {

                        if (response.status.succeed == 1) {
                            ArrayList<HOMESTORE> data = response.data;

                            stores.clear();
                            if (null != data && data.size() > 0) {
                                stores.clear();
                                stores.addAll(data);

                            }

                            IndexStoreModel.this.OnMessageResponse(url, jo, status);
                        }
                    }

                } catch (JSONException e) {
                    // TODO: handle exception
                }

            }
        };





        cb.url(ApiInterface.HOME_INDEX).type(JSONObject.class);

        aq.ajax(cb);

    }



}

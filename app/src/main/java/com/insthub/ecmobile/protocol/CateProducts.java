package com.insthub.ecmobile.protocol;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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
 * Created by Paper on 15-6-11 2015.
 */
@Table(name = "CATEPRODUCTS")
public class CateProducts extends Model {
    @Column(name = "cat_id")
    public String  cat_id;
    @Column(name = "cat_name")
    public String cat_name;

    public List<SIMPLEGOODS> goods;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        this.cat_id = jsonObject.optString("cat_id");
        this.cat_name = jsonObject.optString("cat_name");
        JSONArray jsonArray = new JSONArray();
        jsonArray = jsonObject.optJSONArray("goods");
        for (int i = 0;i<jsonArray.length();i++){
            JSONObject object = jsonArray.getJSONObject(i);
            SIMPLEGOODS simplegoods = new SIMPLEGOODS();
            simplegoods.fromJson(object);
            goods.add(simplegoods);
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("cat_id", cat_id);
        localItemObject.put("cat_name", cat_name);

        for(int i =0; i< goods.size(); i++)
        {
            SIMPLEGOODS itemData =goods.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("children", itemJSONArray);
        return localItemObject;
    }

}

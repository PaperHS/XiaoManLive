
package com.insthub.ecmobile.protocol;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "SEARCHADDRESS")
public class SEARCHADDRESS extends Model {
    @Column(name = "region_id")
    public String region_id;
    @Column(name = "parent_id")
    public String parent_id;
    @Column(name = "region_name")
    public String region_name;
    @Column(name = "region_type")
    public String region_type;
    @Column(name = "agency_id")
    public String agency_id;
    @Column(name = "address")
    public String address;
    @Column(name = "keywords")
    public String keywords;
    @Column(name = "delivery_fee")
    public String delivery_fee;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.region_id = jsonObject.optString("region_id");
        this.parent_id = jsonObject.optString("parent_id");
        this.region_name = jsonObject.optString("region_name");

        this.region_type = jsonObject.optString("region_type");
        this.agency_id = jsonObject.optString("agency_id");
        this.address = jsonObject.optString("address");
        this.keywords = jsonObject.optString("keywords");

        this.delivery_fee = jsonObject.optString("delivery_fee");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("region_id", this.region_id);
        localItemObject.put("parent_id", this.parent_id);
        localItemObject.put("region_name", this.region_name);
        localItemObject.put("region_type", this.region_type);
        localItemObject.put("agency_id", this.agency_id);
        localItemObject.put("address", this.address);
        localItemObject.put("keywords", this.keywords);
        localItemObject.put("delivery_fee", this.delivery_fee);
        return localItemObject;
    }

}

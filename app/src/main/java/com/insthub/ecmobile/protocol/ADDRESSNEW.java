
package com.insthub.ecmobile.protocol;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "ADDRESSNEW")
public class ADDRESSNEW extends Model
{








     @Column(name = "consignee")
     public String consignee;








     @Column(name = "address")
     public String address;

     @Column(name = "region_id")
     public String region_id;

     @Column(name = "mobile")
     public String mobile;

 public void fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return ;
      }


     JSONArray subItemArray;


     this.consignee = jsonObject.optString("consignee");

     this.address = jsonObject.optString("address");

     this.region_id = jsonObject.optString("region_id");

     this.mobile = jsonObject.optString("mobile");
     return ;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("consignee", consignee);
     localItemObject.put("address", address);
     localItemObject.put("region_id", region_id);
     localItemObject.put("mobile", mobile);
     return localItemObject;
 }

}

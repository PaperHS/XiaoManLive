
package com.insthub.ecmobile.protocol;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "AD")
public class AD extends Model
{
    @Column(name = "ad_link")
    public String ad_link;
    @Column(name = "ad_name")
    public String ad_name;
    @Column(name = "ad_code")
    public String ad_code;
 public void fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return ;
      }
     this.ad_code = jsonObject.optString("ad_code");
     this.ad_link = jsonObject.optString("ad_name");
     this.ad_name = jsonObject.optString("ad_name");



     return ;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     localItemObject.put("ad_code",this.ad_code);
     localItemObject.put("ad_link",this.ad_link);
     localItemObject.put("ad_name",this.ad_name);

     return localItemObject;
 }

}

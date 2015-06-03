
package com.insthub.ecmobile.protocol;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "firstlvrecomresponse")
public class SearchAddrResponse extends Model
{

     @Column(name = "status")
     public STATUS   status;

     public SEARCHADDRESS  data = new SEARCHADDRESS();


     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONObject subItemArray;
          STATUS  status = new STATUS();
          status.fromJson(jsonObject.optJSONObject("status"));
          this.status = status;

          subItemArray = jsonObject.optJSONObject("data");
         this.data.fromJson(subItemArray);


          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONObject itemJSONArray = new JSONObject();
          if(null != status)
          {
            localItemObject.put("status", status.toJson());
          }


          localItemObject.put("data", this.data.toJson());

          return localItemObject;
     }

}

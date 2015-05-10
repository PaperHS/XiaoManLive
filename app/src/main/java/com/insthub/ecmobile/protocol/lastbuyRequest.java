
package com.insthub.ecmobile.protocol;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "firstlvrecomrequest")
public class lastbuyRequest extends Model
{

     @Column(name = "session")
     public SESSION   session;

     @Column(name = "cat_id")
     public int   cat_id;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;
            SESSION session = new SESSION();
            session.fromJson(jsonObject.optJSONObject("session"));
            this.session = session;

          this.cat_id = jsonObject.optInt("cat_id");

          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
            if (null != session){
                localItemObject.put("session",session.toJson());
            }
          localItemObject.put("cat_id",this.cat_id);
          return localItemObject;
     }

}

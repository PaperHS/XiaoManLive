
package com.insthub.ecmobile.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

@Table(name = "usersigninRequest")
public class usersigninRequest  extends Model
{

     @Column(name = "telephone")
     public String   telephone;

     @Column(name = "sms_password")
     public String   sms_password;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;

          this.telephone = jsonObject.optString("telephone");

          this.sms_password = jsonObject.optString("sms_password");
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          localItemObject.put("telephone", telephone);
          localItemObject.put("sms_password", sms_password);
          return localItemObject;
     }

}

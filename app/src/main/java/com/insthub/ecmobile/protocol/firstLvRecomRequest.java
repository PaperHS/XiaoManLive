
package com.insthub.ecmobile.protocol;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "firstlvrecomrequest")
public class firstLvRecomRequest extends Model
{

     @Column(name = "number")
     public int   number;

     @Column(name = "offset")
     public int offset;
     @Column(name = "suppliers_id")
     public int   suppliers_id;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;


          this.number = jsonObject.optInt("number");
          this.offset = jsonObject.optInt("offset");
          this.suppliers_id = jsonObject.optInt("suppliers_id");

          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
         localItemObject.put("number",this.number);
          localItemObject.put("offset",this.offset);
          localItemObject.put("suppliers_id",this.suppliers_id);
          return localItemObject;
     }

}

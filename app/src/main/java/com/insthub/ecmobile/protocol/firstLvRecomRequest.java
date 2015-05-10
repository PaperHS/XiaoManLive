
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
     @Column(name = "cat_id")
     public int   cat_id;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;


          this.number = jsonObject.optInt("number");
          this.offset = jsonObject.optInt("offset");
          this.cat_id = jsonObject.optInt("cat_id");

          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
         localItemObject.put("number",this.number);
          localItemObject.put("offset",this.offset);
          localItemObject.put("cat_id",this.cat_id);
          return localItemObject;
     }

}

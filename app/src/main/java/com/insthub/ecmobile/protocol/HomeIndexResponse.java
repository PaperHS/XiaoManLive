
package com.insthub.ecmobile.protocol;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@Table(name = "firstlvrecomresponse")
public class HomeIndexResponse extends Model
{

     @Column(name = "status")
     public STATUS   status;
    public ArrayList<HOMESTORE> data = new ArrayList<>();

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;
          STATUS  status = new STATUS();
          status.fromJson(jsonObject.optJSONObject("status"));
          this.status = status;
         JSONObject jo = jsonObject.optJSONObject("data");
         subItemArray  = jo.optJSONArray("supplier_list");
          if(null != subItemArray)
           {
              for(int i = 0;i < subItemArray.length();i++)
               {
                  JSONObject subItemObject = subItemArray.getJSONObject(i);
                  HOMESTORE subItem = new HOMESTORE();
                  subItem.fromJson(subItemObject);
                  this.data.add(subItem);
               }
           }


          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          if(null != status)
          {
            localItemObject.put("status", status.toJson());
          }

          for(int i =0; i< data.size(); i++)
          {
              HOMESTORE itemData =data.get(i);
              JSONObject itemJSONObject = itemData.toJson();
              itemJSONArray.put(itemJSONObject);
          }
          localItemObject.put("data", itemJSONArray);

          return localItemObject;
     }

}

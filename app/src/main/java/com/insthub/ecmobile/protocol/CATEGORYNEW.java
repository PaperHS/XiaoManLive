
package com.insthub.ecmobile.protocol;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@Table(name = "CATEGORYNEW")
public class CATEGORYNEW extends Model
{

     @Column(name = "cat_id")
     public String cat_id;

     @Column(name = "cat_name")
     public String cat_name;

    @Column(name =  "app_logo")
    public String app_logo;

    @Column(name = "cat_desc")
    public String cat_desc;

    @Column(name = "send_least_money")
    public String send_least_money;



     public ArrayList<SIMPLEGOODS>   children = new ArrayList<SIMPLEGOODS>();

 public void fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return ;
      }


     JSONArray subItemArray;

     this.cat_id = jsonObject.optString("cat_id");

     this.cat_name = jsonObject.optString("cat_name");
    this.app_logo= jsonObject.optString("app_logo");
    this.cat_desc = jsonObject.optString("cat_desc");
     this.send_least_money = jsonObject.optString("send_least_money");

     subItemArray = jsonObject.optJSONArray("goods");
     if(null != subItemArray)
      {
         for(int i = 0;i < subItemArray.length();i++)
          {
             JSONObject subItemObject = subItemArray.getJSONObject(i);
             SIMPLEGOODS subItem = new SIMPLEGOODS();
             subItem.fromJson(subItemObject);
             this.children.add(subItem);
         }
     }

     return ;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     JSONArray itemJSONArray = new JSONArray();
     localItemObject.put("cat_id", cat_id);
     localItemObject.put("cat_name", cat_name);
     localItemObject.put("app_logo",app_logo);
     localItemObject.put("cat_desc",cat_desc);
     localItemObject.put("send_least_money",send_least_money);
     for(int i =0; i< children.size(); i++)
     {
         SIMPLEGOODS itemData =children.get(i);
         JSONObject itemJSONObject = itemData.toJson();
         itemJSONArray.put(itemJSONObject);
     }
     localItemObject.put("goods", itemJSONArray);
     return localItemObject;
 }

}

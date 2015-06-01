
package com.insthub.ecmobile.protocol;

import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "HOMESTORE")
public class HOMESTORE extends Model
{

     @Column(name = "suppliers_id")
     public String suppliers_id;

     @Column(name = "short_name")
     public String short_name;

     @Column(name = "suppliers_name")
     public String suppliers_name;

     @Column(name = "theme")
     public String   theme;



 public void  fromJson(JSONObject jsonObject)  throws JSONException
 {
     if(null == jsonObject){
       return ;
      }

     this.short_name = jsonObject.optString("short_name");
     this.suppliers_id=jsonObject.optString("suppliers_id");
     this.suppliers_name=jsonObject.optString("suppliers_name");
     this.theme=jsonObject.optString("theme");

















     return ;
 }

 public JSONObject  toJson() throws JSONException 
 {
     JSONObject localItemObject = new JSONObject();
     localItemObject.put("short_name",this.short_name);

     localItemObject.put("suppliers_id",this.suppliers_id);
     localItemObject.put("suppliers_name",this.suppliers_name);
     localItemObject.put("theme",this.theme);
     return localItemObject;
 }

}

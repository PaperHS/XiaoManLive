
package com.insthub.ecmobile.protocol;
import com.external.activeandroid.Model;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "USER")
public class USER  extends Model
{

    @Column(name = "collection_num")
    public String   collection_num;

    @Column(name = "USER_id",unique = true)
    public String   id;

    @Column(name = "rank_level")
    public int rank_level;

    @Column(name = "order_num")
    public ORDER_NUM   order_num;

    @Column(name = "name")
    public String   name;

    @Column(name = "rank_name")
    public String   rank_name;

    @Column(name="user_money")
    public String user_money;

    @Column(name="frozen_money")
    public String frozen_money;

    @Column(name="email")
    public String email;



    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        JSONArray subItemArray;

        this.collection_num = jsonObject.optString("collection_num");

        this.id = jsonObject.optString("id");

        this.rank_level = jsonObject.optInt("rank_level");
        ORDER_NUM  order_num = new ORDER_NUM();
        order_num.fromJson(jsonObject.optJSONObject("order_num"));
        this.order_num = order_num;

        this.name = jsonObject.optString("name");

        this.rank_name = jsonObject.optString("rank_name");
        this.email=jsonObject.optString("email");
        this.user_money=jsonObject.optString("user_money");
        this.frozen_money=jsonObject.optString("frozen_money");
        return ;
    }

    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("collection_num", collection_num);
        localItemObject.put("id", id);
        localItemObject.put("rank_level", rank_level);
        localItemObject.put("user_money", user_money);
        localItemObject.put("frozen_money", frozen_money);
        if(null != order_num)
        {
            localItemObject.put("order_num", order_num.toJson());
        }
        localItemObject.put("name", name);
        localItemObject.put("rank_name", rank_name);
        localItemObject.put("email",email);
        return localItemObject;
    }

}

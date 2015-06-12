package com.insthub.ecmobile.protocol;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * ,==.              |~~~
 * /  66\             |
 * \c  -_)         |~~~
 * `) (           |
 * /   \       |~~~
 * /   \ \      |
 * ((   /\ \_ |~~~
 * \\  \ `--`|
 * / / /  |~~~
 * ___ (_(___)_|
 * <p/>
 * Created by Paper on 15/6/11 2015.
 */
public class OrderNum  {
    public String await_pay;
    public String await_ship;
    public String shipped;
    public String finished;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        this.await_pay = jsonObject.getString("await_pay");
        this.await_ship = jsonObject.getString("await_ship");
        this.shipped = jsonObject.getString("shipped");
        this.finished = jsonObject.getString("finished");
    }

    public JSONObject toJson() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("await_pay",await_pay);
        object.put("await_ship",await_ship);
        object.put("shipped",shipped);
        object.put("finished",finished);
        return object;
    }

}

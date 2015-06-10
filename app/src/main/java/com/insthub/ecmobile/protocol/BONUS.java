package com.insthub.ecmobile.protocol;

/*
 *	 ______    ______    ______
 *	/\  __ \  /\  ___\  /\  ___\
 *	\ \  __<  \ \  __\_ \ \  __\_
 *	 \ \_____\ \ \_____\ \ \_____\
 *	  \/_____/  \/_____/  \/_____/
 *
 *
 *	Copyright (c) 2013-2014, {Bee} open source community
 *	http://www.bee-framework.com
 *
 *
 *	Permission is hereby granted, free of charge, to any person obtaining a
 *	copy of this software and associated documentation files (the "Software"),
 *	to deal in the Software without restriction, including without limitation
 *	the rights to use, copy, modify, merge, publish, distribute, sublicense,
 *	and/or sell copies of the Software, and to permit persons to whom the
 *	Software is furnished to do so, subject to the following conditions:
 *
 *	The above copyright notice and this permission notice shall be included in
 *	all copies or substantial portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *	FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 *	IN THE SOFTWARE.
 */

import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "BONUS")
public class BONUS
{
    @Column(name = "type_id")
    public int type_id;

    @Column(name = "type_name")
    public String type_name;

    @Column(name = "type_money")
    public String type_money;

    @Column(name = "bonus_id")
    public String bonus_id;
    @Column(name = "use_start_date")
    public long use_start_date;
    @Column(name = "use_end_date")
    public  long use_end_date;
    @Column(name = "min_goods_amount")
    public String min_goods_amount;

    @Column(name = "bonus_sn")
    public String bonus_sn;

    @Column(name = "bonus_money_formated")
    public String bonus_money_formated;

    public void fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        JSONArray subItemArray;

        this.type_id = jsonObject.optInt("type_id");
        this.type_name = jsonObject.optString("type_name");
        this.type_money = jsonObject.optString("type_money");
        this.bonus_id   = jsonObject.optString("bonus_id");
        this.bonus_money_formated = jsonObject.optString("bonus_money_formated");
        this.bonus_sn = jsonObject.optString("bonus_sn");
        this.use_end_date = jsonObject.optLong("use_end_date");
        this.use_start_date =jsonObject.optLong("use_start_date");
        this.min_goods_amount=jsonObject.getString("min_goods_amount");
        return ;
    }

    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("type_id", type_id);
        localItemObject.put("type_name", type_name);
        localItemObject.put("type_money", type_money);
        localItemObject.put("bonus_id", bonus_id);
        localItemObject.put("bonus_money_formated", bonus_money_formated);
        localItemObject.put("use_start_date",use_start_date);
        localItemObject.put("use_end_date",use_end_date);
        localItemObject.put("bonus_sn",bonus_sn);
        localItemObject.put("min_goods_amount",min_goods_amount);
        return localItemObject;
    }


}

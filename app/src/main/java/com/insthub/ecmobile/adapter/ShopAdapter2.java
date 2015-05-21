package com.insthub.ecmobile.adapter;

import android.content.Context;

import com.insthub.ecmobile.model.FirstLvModel;
import com.insthub.ecmobile.model.SearchModel;

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
 * Created by Paper on 15-5-14 2015.
 */
public class ShopAdapter2 extends B0_IndexAdapterNew {

    public ShopAdapter2(Context c, FirstLvModel firstLvModel, SearchModel searchModel) {
        super(c, firstLvModel, searchModel);
    }

    @Override
    public int getItemViewRealType(int position) {
        if (position == 1)
            return TYPE_SEARCH;
        else if (position ==2)
            return TYPE_NEWCATEGORY_SIG;
        else
            return TYPE_PRODUCTS;
    }
}

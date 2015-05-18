package com.insthub.ecmobile.adapter;

import android.content.Context;

import com.insthub.ecmobile.model.FirstLvModel;

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
 * Created by Paper on 15-5-17 2015.
 */
public class B0_indexAdapter2 extends B0_IndexAdapterNew {

    public B0_indexAdapter2(Context c, FirstLvModel firstLvModel) {
        super(c, firstLvModel);
    }

    @Override
    public int getItemViewRealType(int position) {
        return TYPE_NEWCATEGORY_SIG;
    }
}

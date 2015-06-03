package com.insthub.ecmobile.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.model.CityModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;

public class F1_SearchAddressActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.fragment_container)
    FrameLayout fragmentContainer;

    ImageView mToolbarBack;
    TextView mToolbarTitle;
    @InjectView(R.id.fragment_cities)
    ListView fragmentCities;
    List<CityModel> cities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f1__search_address);
        ButterKnife.inject(this);
        View tv = LayoutInflater.from(this).inflate(R.layout.toolbar_search_address, null);
        mToolbarBack = (ImageView) tv.findViewById(R.id.toolbar_back);
        mToolbarTitle = (TextView) tv.findViewById(R.id.toolbar_title);

        Toolbar.LayoutParams params = new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        toolbar.addView(tv, params);
        setSupportActionBar(toolbar);
        fragmentCities.setAdapter(new AddressAdapter());



    }

    @OnItemClick(R.id.searchaddress_lv)
    public void onAddressClick(int position){
        mToolbarTitle.setText(cities.get(position).region_name);
    }

    class AddressAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return cities.size();
        }

        @Override
        public Object getItem(int i) {
            return cities.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_search_address, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }else {
                holder = (ViewHolder)view.getTag();
            }
            holder.itemSearchName.setText(cities.get(i).region_name);
            return view;
        }
    }
        /**
         * This class contains all butterknife-injected Views & Layouts from layout file 'item_search_address.xml'
         * for easy to all layout elements.
         *
         * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
         */
        static class ViewHolder {
            @InjectView(R.id.item_search_name)
            TextView itemSearchName;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_f1__search_address, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}

package com.insthub.ecmobile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.insthub.ecmobile.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

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
 * Created by Paper on 15-5-20 2015.
 */
public class M0_MsgCenterAdapger extends BaseAdapter {

    ArrayList datalist;

    public M0_MsgCenterAdapger(ArrayList datalist) {
        this.datalist = datalist;

    }

    @Override
    public int getCount() {
        return datalist.size();
    }

    @Override
    public Object getItem(int i) {
        return datalist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_msgcenter, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder)view.getTag();
        }

        return view;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_msgcenter.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @InjectView(R.id.item_msg_content)
        TextView itemMsgContent;
        @InjectView(R.id.item_msg_time)
        TextView itemMsgTime;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}

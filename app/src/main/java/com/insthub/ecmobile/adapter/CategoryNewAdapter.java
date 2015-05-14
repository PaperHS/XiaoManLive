package com.insthub.ecmobile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.insthub.ecmobile.EcmobileApp;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.protocol.CATEGORYNEW;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2015/1/29.
 */
public class CategoryNewAdapter extends BaseAdapter {

    ArrayList datalist;

    public CategoryNewAdapter(ArrayList list) {
        datalist = list;
    }

    @Override
    public int getCount() {
        return datalist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.b1_index_category_cell, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (datalist.size() > position) {
            CATEGORYNEW categorynew = (CATEGORYNEW) datalist.get(position);
            viewHolder.categoryDesc.setText(categorynew.cat_desc);
            viewHolder.categoryTitle.setText(categorynew.cat_name);
            ImageLoader.getInstance().displayImage(categorynew.app_logo,viewHolder.categoryName, EcmobileApp.options);

        }
        return convertView;
    }


    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'b1_index_category_cell.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @InjectView(R.id.category_name)
        ImageView categoryName;
        @InjectView(R.id.category_title)
        TextView categoryTitle;
        @InjectView(R.id.category_desc)
        TextView categoryDesc;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}

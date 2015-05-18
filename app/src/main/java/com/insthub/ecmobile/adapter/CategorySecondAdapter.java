package com.insthub.ecmobile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.insthub.ecmobile.EcmobileApp;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.protocol.SIMPLEGOODS;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2015/1/29.
 */
public class CategorySecondAdapter extends BaseAdapter {

    ArrayList datalist;
    public CategorySecondAdapter(ArrayList list) {
        datalist = list;
    }

    @Override
    public int getCount() {
        if (datalist.size()<6)
            return 6;
        if (datalist.size()%3 !=0){
            return datalist.size()+3-datalist.size()%3;
        }else
            return datalist.size();
    }

    @Override
    public Object getItem(int position) {
        return datalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.d0_goods_cell, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (datalist.size()>position){
            SIMPLEGOODS simplegoods = (SIMPLEGOODS)datalist.get(position);
            viewHolder.mD0GoodsName.setText(simplegoods.name);
            ImageLoader.getInstance().displayImage(simplegoods.img.url,viewHolder.mD0GoodsPic, EcmobileApp.options);
            viewHolder.mD0GoodsPrice.setText(simplegoods.shop_price);
        }
        return convertView;
    }


    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'd0_goods_cell.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Inmite Developers (http://inmite.github.io)
     */
    static class ViewHolder {
        @InjectView(R.id.d0_goods_pic)
        ImageView mD0GoodsPic;
        @InjectView(R.id.d0_goods_name)
        TextView mD0GoodsName;
        @InjectView(R.id.d0_goods_price)
        TextView mD0GoodsPrice;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}

package com.insthub.ecmobile.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.insthub.ecmobile.EcmobileApp;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.protocol.ORDER_GOODS_LIST;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by peggy on 15-4-5.
 */
public class H1_OrderDetailAdapter extends BaseAdapter {

    ArrayList<ORDER_GOODS_LIST> goods_list;
    private SharedPreferences shared;
    private SharedPreferences.Editor editor;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    Context context;

    public H1_OrderDetailAdapter(ArrayList<ORDER_GOODS_LIST> list, Context context) {
        this.goods_list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return goods_list.size();
    }

    @Override
    public Object getItem(int position) {
        return goods_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.trade_body, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        shared = context.getSharedPreferences("userInfo", 0);
				editor = shared.edit();
				String imageType = shared.getString("imageType", "mind");

				if(imageType.equals("high")) {
                    imageLoader.displayImage(goods_list.get(position).img.thumb,holder.mTradeBodyImage, EcmobileApp.options);
				} else if(imageType.equals("low")) {
                    imageLoader.displayImage(goods_list.get(position).img.small,holder.mTradeBodyImage, EcmobileApp.options);
				} else {
					String netType = shared.getString("netType", "wifi");
					if(netType.equals("wifi")) {
                        imageLoader.displayImage(goods_list.get(position).img.thumb,holder.mTradeBodyImage, EcmobileApp.options);
					} else {
                        imageLoader.displayImage(goods_list.get(position).img.small,holder.mTradeBodyImage, EcmobileApp.options);
					}
				}
            holder.mTradeBodyText.setText(goods_list.get(position).name);
            holder.mTradeBodyPrice.setText(goods_list.get(position).formated_shop_price);
            holder.mTradeBodyNum.setText("X"+goods_list.get(position).goods_number);
        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'trade_body.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Inmite Developers (http://inmite.github.io)
     */
    static class ViewHolder {
        @InjectView(R.id.trade_body_image)
        ImageView mTradeBodyImage;
        @InjectView(R.id.trade_body_text)
        TextView mTradeBodyText;
        @InjectView(R.id.trade_body_price)
        TextView mTradeBodyPrice;
        @InjectView(R.id.trade_body_total)
        TextView mTradeBodyTotal;
        @InjectView(R.id.trade_body_num)
        TextView mTradeBodyNum;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}

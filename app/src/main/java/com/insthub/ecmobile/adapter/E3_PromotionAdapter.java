package com.insthub.ecmobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.insthub.ecmobile.R;

/**
 * Created by Administrator on 2015/2/27.
 */
public class E3_PromotionAdapter extends BaseAdapter {
    private Context mContext;

    public E3_PromotionAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.e3_promotion_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'e3_promotion_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Inmite Developers (http://inmite.github.io)
     */
    static class ViewHolder {
        @InjectView(R.id.e3_promotion_item_price)
        TextView mE3PromotionItemPrice;
        @InjectView(R.id.e3_promotion_item_type)
        TextView mE3PromotionItemType;
        @InjectView(R.id.e3_promotion_item_start)
        TextView mE3PromotionItemStart;
        @InjectView(R.id.e3_promotion_item_end)
        TextView mE3PromotionItemEnd;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}

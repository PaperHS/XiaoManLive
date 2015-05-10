package com.insthub.ecmobile.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.B2_ProductDetailActivity;
import com.insthub.ecmobile.activity.D0_Category2Activity;
import com.insthub.ecmobile.activity.EcmobileMainActivity;
import com.insthub.ecmobile.component.CustomGridView;
import com.insthub.ecmobile.event.ItemClickEvent;
import com.insthub.ecmobile.model.GoodsListModel;
import com.insthub.ecmobile.protocol.CATEGORY;
import com.insthub.ecmobile.protocol.FILTER;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/1/29.
 */
public class CategoryAdapter extends BaseAdapter {

    ArrayList dataList ;
    public CategoryAdapter(ArrayList list) {
        dataList = list;
    }

    @Override
    public int getCount() {
        return dataList.size();
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.d0_item_category, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
                viewHolder = (ViewHolder)convertView.getTag();
        }


        final CATEGORY categoryItem = (CATEGORY)dataList.get(position);
        final GoodsListModel dataModel = new GoodsListModel(parent.getContext());
        final CategorySecondAdapter adapter = new CategorySecondAdapter(dataModel.simplegoodsList);
        viewHolder.mD0ItemGridview.setAdapter(adapter);
        FILTER filter = new FILTER();
        filter.category_id = String.valueOf(categoryItem.id);
        filter.sort_by = GoodsListModel.PRICE_DESC;
        dataModel.addResponseListener(new BusinessResponse() {
            @Override
            public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
                adapter.notifyDataSetChanged();
            }
        });
        dataModel.fetchPreSearch(filter);
        viewHolder.mD0ItemGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(parent.getContext(), B2_ProductDetailActivity.class);
                it.putExtra("good_id", dataModel.simplegoodsList.get(position).goods_id);
                parent.getContext().startActivity(it);
                ((Activity) parent.getContext()).overridePendingTransition(R.anim.my_scale_action, R.anim.anim_hold);
            }
        });
        viewHolder.mD0ItemGridview.setSelector(new ColorDrawable());
        viewHolder.mD0ItemName.setText(categoryItem.name);
        viewHolder.mD0ItemGetmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventBus.getDefault().post(new ItemClickEvent(position));

//                try {
//                    Intent it = new Intent(parent.getContext(),D0_Category2Activity.class);
//                    it.putExtra("category",categoryItem.toJson().toString());
//                    parent.getContext().startActivity(it);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }


            }
        });

        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.d0_item_getmore)
        TextView mD0ItemGetmore;
        @InjectView(R.id.d0_item_gridview)
        CustomGridView mD0ItemGridview;
        @InjectView(R.id.d0_item_categoryname)
        TextView mD0ItemName;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}

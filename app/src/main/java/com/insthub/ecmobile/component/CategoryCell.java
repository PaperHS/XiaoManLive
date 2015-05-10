package com.insthub.ecmobile.component;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.D0_Category1Activity;
import com.insthub.ecmobile.activity.D1_CategoryActivity;
import com.insthub.ecmobile.model.HomeModel;
import com.insthub.ecmobile.model.SearchModel;
import com.insthub.ecmobile.protocol.CATEGORY;
import com.insthub.ecmobile.protocol.CATEGORYGOODS;
import org.json.JSONException;

/**
 * Created by Administrator on 2015/1/21.
 */
public class CategoryCell extends LinearLayout implements View.OnClickListener{
    private LinearLayout btn1;
    private LinearLayout btn2;
    private LinearLayout btn3;
    private LinearLayout btn4;

    SearchModel model;
    Handler mHandler;
    public CategoryCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                bindDataDelay();
            }

			
        };

	}
	private void bindDataDelay() {
		init();
		
	}
    public void bindData(SearchModel datamodel)
    {
        model = datamodel;
        mHandler.removeMessages(0);
        mHandler.sendEmptyMessageDelayed(0,30);

    	
    }
    private void init() {
        btn1 = (LinearLayout)findViewById(R.id.category_btn1);
        btn2 = (LinearLayout)findViewById(R.id.category_btn2);
        btn3 = (LinearLayout)findViewById(R.id.category_btn3);
        btn4 = (LinearLayout)findViewById(R.id.category_btn4);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.category_btn1:
                Intent it1 = new Intent(getContext(), D0_Category1Activity.class);
//                it1.putExtra("category",category.toJson().toString());
//                it1.putExtra("category_name", searchModel.categoryArrayList.get(position-1).name);
                if (model.categoryArrayList.size()>0) {
                    CATEGORY category = model.categoryArrayList.get(0);
                    try {
                        it1.putExtra("category", category.toJson().toString());
                        it1.putExtra("category_name", category.name);
                        getContext().startActivity(it1);
                        ((Activity) getContext()).overridePendingTransition(R.anim.push_right_in,
                                R.anim.push_left_out);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.category_btn2:
                Intent it2 = new Intent(getContext(), D0_Category1Activity.class);
//                it2.putExtra("category",category.toJson().toString());
//                it2.putExtra("category_name", searchModel.categoryArrayList.get(position-1).name);
                if (model.categoryArrayList.size()>0) {
                    CATEGORY category = model.categoryArrayList.get(1);
                    try {
                        it2.putExtra("category", category.toJson().toString());
                        it2.putExtra("category_name", category.name);
                        getContext().startActivity(it2);
                        ((Activity) getContext()).overridePendingTransition(R.anim.push_right_in,
                                R.anim.push_left_out);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
//                getContext().startActivity(it2);
                break;
            case R.id.category_btn3:
                Intent it3 = new Intent(getContext(), D0_Category1Activity.class);
//                it3.putExtra("category",category.toJson().toString());
//                it3.putExtra("category_name", searchModel.categoryArrayList.get(position-1).name);
                if (model.categoryArrayList.size()>0) {
                    CATEGORY category = model.categoryArrayList.get(2);
                    try {
                        it3.putExtra("category", category.toJson().toString());
                        it3.putExtra("category_name", category.name);
                        getContext().startActivity(it3);
                        ((Activity) getContext()).overridePendingTransition(R.anim.push_right_in,
                                R.anim.push_left_out);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
//                getContext().startActivity(it3);
                break;
            case R.id.category_btn4:
                Intent it4 = new Intent(getContext(), D0_Category1Activity.class);
//                it4.putExtra("category",category.toJson().toString());
//                it4.putExtra("category_name", searchModel.categoryArrayList.get(position-1).name);

                if (model.categoryArrayList.size()>0) {
                    CATEGORY category = model.categoryArrayList.get(3);
                    try {
                        it4.putExtra("category", category.toJson().toString());
                        it4.putExtra("category_name", category.name);
                        getContext().startActivity(it4);
                        ((Activity) getContext()).overridePendingTransition(R.anim.push_right_in,
                                R.anim.push_left_out);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
//                getContext().startActivity(it4);
                break;
            default:
                break;
        }
    }
}

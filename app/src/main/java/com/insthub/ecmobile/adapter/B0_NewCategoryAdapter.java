package com.insthub.ecmobile.adapter;
//
//                       __
//                      /\ \   _
//    ____    ____   ___\ \ \_/ \           _____    ___     ___
//   / _  \  / __ \ / __ \ \    <     __   /\__  \  / __ \  / __ \
//  /\ \_\ \/\  __//\  __/\ \ \\ \   /\_\  \/_/  / /\ \_\ \/\ \_\ \
//  \ \____ \ \____\ \____\\ \_\\_\  \/_/   /\____\\ \____/\ \____/
//   \/____\ \/____/\/____/ \/_//_/         \/____/ \/___/  \/___/
//     /\____/
//     \/___/
//
//  Powered by BeeFramework
//

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.insthub.BeeFramework.adapter.BeeBaseAdapter;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.component.ProductsCell;
import com.insthub.ecmobile.model.FirstLvModel;
import com.insthub.ecmobile.model.SearchModel;

import java.util.ArrayList;

public class B0_NewCategoryAdapter extends BeeBaseAdapter
{

	public B0_NewCategoryAdapter(Context c, ArrayList dataList) {
		super(c, dataList);
	}

	public B0_NewCategoryAdapter(Context c, FirstLvModel firstLvModel, SearchModel searchModel) {
		super(c, firstLvModel.simplegoodsList);
	}



	@Override
	protected BeeCellHolder createCellHolder(View cellView) {		
		return null;
	}
	
    @Override
    public int getViewTypeCount() {
        
        return 1000;
    }
    
    @Override
    public int getCount() 
    {

//        int count = 3  + dataModel.categorygoodsList.size();
        int count = 4;
    	return 1;
    }
    
    public int getItemViewType(int position)
    {    	
        return position;
    }


    @Override
    public long getItemId(int position) {
        return position;
//        if (position < 3)
//        {
//            return position;
//        }
//        else  if (position < 3 + dataModel.categorygoodsList.size())
//        {
//            return position - 3;
//        }
//        else
//        {
//            return position - (int)Math.ceil(dataModel.simplegoodsList.size()*1.0/2)- dataModel.categorygoodsList.size();
//        }
    }

    @Override
	protected View bindData(int position, View cellView, ViewGroup parent,
			BeeCellHolder h) {		
		return null;
	}

	@Override
	public View createCellView() {		
		return null;
	}
    @Override
    public View getView(final int position, View cellView, ViewGroup parent) {

        BeeCellHolder holder = null;
        if (cellView == null || cellView.getClass()!= ProductsCell.class){

            cellView = (ProductsCell) LayoutInflater.from(mContext).inflate(R.layout.b1_index_products_cell,null);
        }
        ((ProductsCell)cellView).bindDate(dataList,2,null);

        return cellView;
    }

}

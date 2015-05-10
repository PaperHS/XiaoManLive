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
import com.insthub.ecmobile.component.CategoryCell;
import com.insthub.ecmobile.component.CategorySellingCell;
import com.insthub.ecmobile.component.FeatureSellCell;
import com.insthub.ecmobile.component.FunctionCell;
import com.insthub.ecmobile.component.HotSellingCell;
import com.insthub.ecmobile.component.ProductsCell;
import com.insthub.ecmobile.component.SearchCell;
import com.insthub.ecmobile.component.SepcialCagegoryCell;
import com.insthub.ecmobile.model.HomeModel;
import com.insthub.ecmobile.model.SearchModel;
import com.insthub.ecmobile.protocol.CATEGORY;
import com.insthub.ecmobile.protocol.CATEGORYGOODS;
import com.insthub.ecmobile.protocol.SIMPLEGOODS;

import java.util.ArrayList;
import java.util.List;

public class B0_IndexAdapterNew extends BeeBaseAdapter
{
	protected static final int TYPE_HOTSELL = 0;
	protected static final int TYPE_CATEGORYSELL = 1;
	protected static final int TYPE_HELPSELL = 2;
	protected static final int TYPE_FUNCTION = 3;
	protected static final int TYPE_CATEGORY= 4;
	protected static final int TYPE_CATEGORY_SPECIAL= 6;
	protected static final int TYPE_FEATURE = 5;
    protected static final int TYPE_SEARCH = 7;
    protected static final int TYPE_PRODUCTS = 8;

	private HomeModel dataModel;
	private SearchModel searchModel;
	public B0_IndexAdapterNew(Context c, ArrayList dataList) {
		super(c, dataList);
	}

	public B0_IndexAdapterNew(Context c, HomeModel dataModel, SearchModel searchModel) {
		super(c, dataModel.playersList);
		this.dataModel = dataModel;
        this.searchModel = searchModel;
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

        int count = 3  + dataModel.categorygoodsList.size();
    	return count;
    }
    
    public int getItemViewType(int position)
    {    	
        return position;
    }


    public int getItemViewRealType(int position)
    {
//        if (position < (int)Math.ceil(dataModel.simplegoodsList.size()*1.0/2))
//        {
//            return TYPE_HOTSELL;
//        }
//        else  if (position < (int)Math.ceil(dataModel.simplegoodsList.size()*1.0/2) + dataModel.categorygoodsList.size())
//        {
//            return TYPE_CATEGORYSELL;
//        }
//
//        else
//        {
//            return TYPE_HELPSELL;
//        }
        if(position<1){
            return TYPE_SEARCH;
        }else if (position <2){
            return TYPE_CATEGORY_SPECIAL;
        }else if (position<4) {
            return TYPE_PRODUCTS;
        }else {
            return  TYPE_CATEGORYSELL;
        }

    }

    @Override
    public long getItemId(int position) {
        if (position < 3)
        {
            return position;
        }
        else  if (position < 3 + dataModel.categorygoodsList.size())
        {
            return position - 3;
        }
        else
        {
            return position - (int)Math.ceil(dataModel.simplegoodsList.size()*1.0/2)- dataModel.categorygoodsList.size();
        }
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

        if (TYPE_HOTSELL== getItemViewRealType(position))
        {

            if (null == cellView || cellView.getClass()!= HotSellingCell.class)
            {
                cellView = (HotSellingCell)LayoutInflater.from(mContext).inflate(R.layout.b0_index_hot_cell, null);
            }
            else
            {
                return cellView;
            }

            List<SIMPLEGOODS> itemList = null;

            int distance =  dataModel.simplegoodsList.size() - position*2;
            int cellCount = distance >= 2? 2:distance;

            itemList = dataModel.simplegoodsList.subList(position*2,position*2+cellCount);
            ((HotSellingCell)cellView).bindData(itemList);

		}    
        else if (TYPE_CATEGORYSELL == getItemViewRealType(position))
        {
            if (null == cellView || cellView.getClass()!= CategorySellingCell.class)
            {
                cellView = (CategorySellingCell)LayoutInflater.from(mContext).inflate(R.layout.b0_index_category_cell, null);
            }
            else
            {
                return cellView;
            }

            List<SIMPLEGOODS> itemList = null;


//            int realPosition = position -  (int)Math.ceil(dataModel.simplegoodsList.size()*1.0/2);
            int realPosition = position -  3;

            CATEGORYGOODS categorygoods =  dataModel.categorygoodsList.get(realPosition);
            CATEGORY category = new CATEGORY();

            if (searchModel.categoryArrayList.size()>0){
                category = searchModel.categoryArrayList.get(realPosition);
            }
            ((CategorySellingCell) cellView).bindData(categorygoods,category);
            
		}else if (TYPE_FUNCTION == getItemViewRealType(position)){
            if (null == cellView || cellView.getClass()!= FunctionCell.class){
                cellView = (FunctionCell)LayoutInflater.from(mContext).inflate(R.layout.b0_index_funcion_cell,null);
            }else {
                return cellView;
            }
            ((FunctionCell)cellView).bindData();
        }else if (TYPE_CATEGORY == getItemViewRealType(position)){
            if (null == cellView || cellView.getClass()!= CategoryCell.class){
                cellView = (CategoryCell)LayoutInflater.from(mContext).inflate(R.layout.b0_index_category,null);
            }else {
                return cellView;
            }
            ((CategoryCell)cellView).bindData(searchModel);
        }else if (TYPE_CATEGORY_SPECIAL == getItemViewRealType(position)){
            if (null == cellView || cellView.getClass()!= SepcialCagegoryCell.class){
                cellView = (SepcialCagegoryCell)LayoutInflater.from(mContext).inflate(R.layout.b0_index_special_category_cell,null);
            }else {
                return cellView;
            }
            List<SIMPLEGOODS> itemList = null;


//            int realPosition = position -  (int)Math.ceil(dataModel.simplegoodsList.size()*1.0/2);
            int realPosition = position -  3;

            CATEGORYGOODS categorygoods =  dataModel.categorygoodsList.get(realPosition);
            CATEGORY category = new CATEGORY();

            if (searchModel.categoryArrayList.size()>2){
                category = searchModel.categoryArrayList.get(realPosition);
                ((SepcialCagegoryCell) cellView).bindData(categorygoods, category,searchModel);

            }
        }else if (TYPE_FEATURE == getItemViewRealType(position)){
            if (null == cellView || cellView.getClass()!= FeatureSellCell.class){
                cellView = (FeatureSellCell)LayoutInflater.from(mContext).inflate(R.layout.b0_index_feature,null);
            }else {
                return cellView;
            }
            ((FeatureSellCell)cellView).bindData(dataModel.simplegoodsList);
            
        }else if(TYPE_PRODUCTS == getItemViewRealType(position)){
            if (null == cellView || cellView.getClass() != ProductsCell.class){
                cellView = (ProductsCell)LayoutInflater.from(mContext).inflate(R.layout.b1_index_products_cell,null);
            }else {
                return cellView;
            }
//            ((ProductsCell)cellView).bin
        }else if (TYPE_SEARCH == getItemViewRealType(position)){
            if (null == cellView || cellView.getClass() != SearchCell.class){
                cellView = (SearchCell)LayoutInflater.from(mContext).inflate(R.layout.b1_index_search_cell,null);
            }else return cellView;

            //TODO
        }

        return cellView;
    }

}

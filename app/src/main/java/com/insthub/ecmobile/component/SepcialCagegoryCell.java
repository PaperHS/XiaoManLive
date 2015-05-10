package com.insthub.ecmobile.component;
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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.insthub.ecmobile.EcmobileApp;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.B2_ProductDetailActivity;
import com.insthub.ecmobile.activity.D0_Category1Activity;
import com.insthub.ecmobile.activity.D0_Category2Activity;
import com.insthub.ecmobile.activity.EcmobileMainActivity;
import com.insthub.ecmobile.model.HomeModel;
import com.insthub.ecmobile.model.SearchModel;
import com.insthub.ecmobile.protocol.CATEGORY;
import com.insthub.ecmobile.protocol.CATEGORYGOODS;
import com.insthub.ecmobile.protocol.SIMPLEGOODS;
import com.nostra13.universalimageloader.core.ImageLoader;
import org.json.JSONException;

import java.util.ArrayList;

public class SepcialCagegoryCell extends LinearLayout
{
	Context mContext;
    private TextView good_category_name;
    private ImageView good_cell_photo_one;
    private ImageView good_cell_photo_two;
    private ImageView good_cell_photo_three;
    private ImageView good_cell_photo_four;
    private ImageView good_cell_photo_five;
    private ImageView good_cell_photo_six;
    private ImageView good_cell_photo_seven;
    private ImageView good_cell_photo_eight;
    private TextView good_cell_name_one;
    private TextView     good_cell_name_two;
    private TextView     good_cell_name_three;
    private TextView     good_cell_name_four;
    private TextView     good_cell_name_five;
    private TextView     good_cell_name_six;
    private TextView     good_cell_name_seven;
    private TextView     good_cell_name_eight;
    private TextView good_cell_price_one;
    private TextView good_cell_price_two;
    private TextView good_cell_price_three;
    private TextView good_cell_price_four;
    private TextView good_cell_price_five;
    private TextView good_cell_price_six;
    private TextView good_cell_price_seven;
    private TextView good_cell_price_eight;
    private TextView getmore;
    private LinearLayout good_cell_one;
    private LinearLayout  good_cell_two;
    private LinearLayout  good_cell_three;
    private LinearLayout  good_cell_four;
    private LinearLayout  good_cell_five;
    private LinearLayout  good_cell_six;
    private LinearLayout  good_cell_seven;
    private LinearLayout  good_cell_eight;
    CATEGORYGOODS categorygoods;
    CATEGORY category;
    private  SearchModel searchModel;
    Handler mHandler;

    private SharedPreferences shared;
	private SharedPreferences.Editor editor;
    protected ImageLoader imageLoader = ImageLoader.getInstance();

	public SepcialCagegoryCell(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                bindDataDelay();
            }
        };
	}

    void init()
    {
        getmore = (TextView)findViewById(R.id.category1_choice);

        good_category_name = (TextView)findViewById(R.id.good_cell_name);
        if (null == good_cell_one)
        {
            good_cell_one = (LinearLayout)findViewById(R.id.good_cell_one);
        }

        if (null == good_cell_two)
        {
            good_cell_two = (LinearLayout)findViewById(R.id.good_cell_two);
        }

        if (null == good_cell_three)
        {
            good_cell_three = (LinearLayout)findViewById(R.id.good_cell_three);
        }
        if (null == good_cell_four)
        {
            good_cell_four = (LinearLayout)findViewById(R.id.good_cell_four);
        }
        if (null == good_cell_six)
        {
            good_cell_six = (LinearLayout)findViewById(R.id.good_cell_six);
        }
        if (null == good_cell_seven)
        {
            good_cell_seven = (LinearLayout)findViewById(R.id.good_cell_seven);
        }
        if (null == good_cell_five)
        {
            good_cell_five = (LinearLayout)findViewById(R.id.good_cell_five);
        }
        if (null == good_cell_eight)
        {
            good_cell_eight = (LinearLayout)findViewById(R.id.good_cell_eight);
        }

        if (null == good_cell_photo_one)
        {
            good_cell_photo_one = (ImageView)findViewById(R.id.good_cell_photo_one);
        }

        if (null == good_cell_photo_two)
        {
            good_cell_photo_two = (ImageView)findViewById(R.id.good_cell_photo_two);
        }

        if (null == good_cell_photo_three)
        {
            good_cell_photo_three = (ImageView)findViewById(R.id.good_cell_photo_three);
        }
        if (null == good_cell_photo_four)
        {
            good_cell_photo_four = (ImageView)findViewById(R.id.good_cell_photo_four);
        }
        if (null == good_cell_photo_five)
        {
            good_cell_photo_five = (ImageView)findViewById(R.id.good_cell_photo_five);
        }
        if (null == good_cell_photo_six)
        {
            good_cell_photo_six = (ImageView)findViewById(R.id.good_cell_photo_six);
        }
        if (null == good_cell_photo_seven)
        {
            good_cell_photo_seven = (ImageView)findViewById(R.id.good_cell_photo_seven);
        }
        if (null == good_cell_photo_eight)
        {
            good_cell_photo_eight = (ImageView)findViewById(R.id.good_cell_photo_eight);
        }

        if (null == good_cell_name_one)
        {
            good_cell_name_one = (TextView)findViewById(R.id.good_cell_name_one);
        }

        if (null == good_cell_name_two)
        {
            good_cell_name_two = (TextView)findViewById(R.id.good_cell_name_two);
        }

        if (null == good_cell_name_three)
        {
            good_cell_name_three = (TextView)findViewById(R.id.good_cell_name_three);
        }
        if (null == good_cell_name_four)
        {
            good_cell_name_four = (TextView)findViewById(R.id.good_cell_name_four);
        }
        if (null == good_cell_name_five)
        {
            good_cell_name_five = (TextView)findViewById(R.id.good_cell_name_five);
        }
        if (null == good_cell_name_six)
        {
            good_cell_name_six = (TextView)findViewById(R.id.good_cell_name_six);
        }
        if (null == good_cell_name_seven)
        {
            good_cell_name_seven = (TextView)findViewById(R.id.good_cell_name_seven);
        }
        if (null == good_cell_name_eight)
        {
            good_cell_name_eight = (TextView)findViewById(R.id.good_cell_name_eight);
        }

        if(null == good_cell_price_one) {
        	good_cell_price_one = (TextView)findViewById(R.id.good_cell_price_one);
        }if(null == good_cell_price_two) {
        	good_cell_price_two = (TextView)findViewById(R.id.good_cell_price_two);
        }
        
        if(null == good_cell_price_three) {
        	good_cell_price_three = (TextView)findViewById(R.id.good_cell_price_three);
        }
        if(null == good_cell_price_four) {
        	good_cell_price_four = (TextView)findViewById(R.id.good_cell_price_four);
        }
        if(null == good_cell_price_five) {
        	good_cell_price_five = (TextView)findViewById(R.id.good_cell_price_five);
        }
        if(null == good_cell_price_six) {
        	good_cell_price_six = (TextView)findViewById(R.id.good_cell_price_six);
        }
        if(null == good_cell_price_seven) {
        	good_cell_price_seven = (TextView)findViewById(R.id.good_cell_price_seven);
        }
        if(null == good_cell_price_eight) {
        	good_cell_price_eight = (TextView)findViewById(R.id.good_cell_price_eight);
        }

    }
    int count = 0;

    public void bindDataDelay()
    {
        init();
        ArrayList<SIMPLEGOODS> listData = categorygoods.goods;
        
        
        shared = mContext.getSharedPreferences("userInfo", 0); 
		editor = shared.edit();
		String imageType = shared.getString("imageType", "mind");
		

        if (null != categorygoods.name)
        {
            good_category_name.setText(categorygoods.name);
            count++;
            
//            good_cell_one.setOnClickListener(new OnClickListener() {
//
//    			@Override
//    			public void onClick(View v) {
//
//    				Intent it = new Intent(mContext, B1_ProductListActivity.class);
//                    FILTER filter = new FILTER();
//                    filter.category_id = String.valueOf(categorygoods.id);
//                    try
//                    {
//                        it.putExtra(B1_ProductListActivity.FILTER,filter.toJson().toString());
//                    }
//                    catch (JSONException e)
//                    {
//
//                    }
//
//                    mContext.startActivity(it);
//                    ((Activity) mContext).overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
//    			}
//    		});
            getmore.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(mContext, D0_Category1Activity.class);


                    try
                    {
                        it.putExtra("category", category.toJson().toString());
                        it.putExtra("category_name", category.name);
                    }
                    catch (JSONException e)
                    {

                    }

                    mContext.startActivity(it);
                    ((Activity) mContext).overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);

                }
            });
        }
        
        if (listData.size() >0)
        {
            final SIMPLEGOODS goodOne = listData.get(0);
            
            good_cell_photo_one.setVisibility(View.VISIBLE);
            
            if(imageType.equals("high")) {
                imageLoader.displayImage(goodOne.img.thumb,good_cell_photo_one, EcmobileApp.options);
    		} else if(imageType.equals("low")) {
                imageLoader.displayImage(goodOne.img.small,good_cell_photo_one, EcmobileApp.options);
    		} else {
    			String netType = shared.getString("netType", "wifi");
    			if(netType.equals("wifi")) {
                    imageLoader.displayImage(goodOne.img.thumb,good_cell_photo_one, EcmobileApp.options);
    			} else {
                    imageLoader.displayImage(goodOne.img.small,good_cell_photo_one, EcmobileApp.options);
    			}
    		}
            good_cell_one.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
//                    Intent it = new Intent(mContext, B2_ProductDetailActivity.class);
//                    it.putExtra("good_id",goodOne.id);
//                    mContext.startActivity(it);
//                    ((EcmobileMainActivity)mContext).overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
                    Intent it = new Intent(mContext, D0_Category2Activity.class);

                    try {
                        it.putExtra("category", searchModel.categoryArrayList.get(1).toJson().toString());
                    } catch (JSONException e) {
                        return;
                    }
                    it.putExtra("position",0);
                    mContext.startActivity(it);
                    ((Activity)mContext).overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
                }
            });

            good_cell_name_one.setText(goodOne.name);
            good_cell_price_one.setText(goodOne.brief);

            if (listData.size() >1)
            {
                good_cell_two.setVisibility(View.VISIBLE);
                final SIMPLEGOODS goodTwo = listData.get(1);
                if (null != goodTwo && null != goodTwo.img && null != goodTwo.img.thumb && null != goodTwo.img.small)
                {
                	if(imageType.equals("high")) {
                        imageLoader.displayImage(goodTwo.img.thumb,good_cell_photo_two, EcmobileApp.options);
            		} else if(imageType.equals("low")) {
                        imageLoader.displayImage(goodTwo.img.small,good_cell_photo_two, EcmobileApp.options);
            		} else {
            			String netType = shared.getString("netType", "wifi");
            			if(netType.equals("wifi")) {
                            imageLoader.displayImage(goodTwo.img.thumb,good_cell_photo_two, EcmobileApp.options);
            			} else {
                            imageLoader.displayImage(goodTwo.img.small,good_cell_photo_two, EcmobileApp.options);
            			}
            		}
                	
                    good_cell_two.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {

                            Intent it = new Intent(mContext, D0_Category2Activity.class);

                            try {
                                it.putExtra("category", searchModel.categoryArrayList.get(1).toJson().toString());
                            } catch (JSONException e) {
                                return;
                            }
                            it.putExtra("position", 2);
                            mContext.startActivity(it);
                            ((Activity)mContext).overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);	}
					});
                    
                }
                good_cell_name_two.setText(goodTwo.name);
                good_cell_price_two.setText(goodTwo.brief);

                if (listData.size() > 2)
                {
                    good_cell_three.setVisibility(View.VISIBLE);
                    final SIMPLEGOODS goodThree = listData.get(2);
                    if (null != goodThree && null != goodThree.img && null != goodThree.img.thumb && null != goodThree.img.small)
                    {
                    	
                    	if(imageType.equals("high")) {
                            imageLoader.displayImage(goodThree.img.thumb,good_cell_photo_three, EcmobileApp.options);
                		} else if(imageType.equals("low")) {
                            imageLoader.displayImage(goodThree.img.small,good_cell_photo_three, EcmobileApp.options);
                		} else {
                			String netType = shared.getString("netType", "wifi");
                			if(netType.equals("wifi")) {
                                imageLoader.displayImage(goodThree.img.thumb,good_cell_photo_three, EcmobileApp.options);
                			} else {
                                imageLoader.displayImage(goodThree.img.small,good_cell_photo_three, EcmobileApp.options);
                			}
                		}
                    	
                        good_cell_three.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
                                Intent it = new Intent(mContext, D0_Category2Activity.class);

                                try {
                                    it.putExtra("category", searchModel.categoryArrayList.get(1).toJson().toString());
                                } catch (JSONException e) {
                                    return;
                                }
                                it.putExtra("position",2);
                                mContext.startActivity(it);
                                ((Activity)mContext).overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
							}
						});
                       
                    }
                    good_cell_name_three.setText(goodThree.name);
                    good_cell_price_three.setText(goodThree.brief);
                }
                else
                {
                    good_cell_three.setVisibility(View.INVISIBLE);
                }

                if (listData.size()>3){
                    final SIMPLEGOODS goodfour = listData.get(3);
                    if (null != goodfour && null != goodfour.img && null != goodfour.img.thumb && null != goodfour.img.small)
                    {

                        if(imageType.equals("high")) {
                            imageLoader.displayImage(goodfour.img.thumb,good_cell_photo_four, EcmobileApp.options);
                        } else if(imageType.equals("low")) {
                            imageLoader.displayImage(goodfour.img.small,good_cell_photo_four, EcmobileApp.options);
                        } else {
                            String netType = shared.getString("netType", "wifi");
                            if(netType.equals("wifi")) {
                                imageLoader.displayImage(goodfour.img.thumb,good_cell_photo_four, EcmobileApp.options);
                            } else {
                                imageLoader.displayImage(goodfour.img.small,good_cell_photo_four, EcmobileApp.options);
                            }
                        }

                        good_cell_four.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Intent it = new Intent(mContext, D0_Category2Activity.class);

                                try {
                                    it.putExtra("category", searchModel.categoryArrayList.get(1).toJson().toString());
                                } catch (JSONException e) {
                                    return;
                                }
                                it.putExtra("position",3);
                                mContext.startActivity(it);
                                ((Activity)mContext).overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
                            }
                        });

                    }
                    good_cell_name_four.setText(goodfour.name);
                    good_cell_price_four.setText(goodfour.brief);

//five pic
                    final SIMPLEGOODS goodfive = listData.get(4);
                    if (null != goodfive && null != goodfive.img && null != goodfive.img.thumb && null != goodfive.img.small)
                    {

                        if(imageType.equals("high")) {
                            imageLoader.displayImage(goodfour.img.thumb,good_cell_photo_five, EcmobileApp.options);
                        } else if(imageType.equals("low")) {
                            imageLoader.displayImage(goodfour.img.small,good_cell_photo_five, EcmobileApp.options);
                        } else {
                            String netType = shared.getString("netType", "wifi");
                            if(netType.equals("wifi")) {
                                imageLoader.displayImage(goodfour.img.thumb,good_cell_photo_five, EcmobileApp.options);
                            } else {
                                imageLoader.displayImage(goodfour.img.small,good_cell_photo_five, EcmobileApp.options);
                            }
                        }

                        good_cell_five.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Intent it = new Intent(mContext, D0_Category2Activity.class);

                                try {
                                    it.putExtra("category", searchModel.categoryArrayList.get(1).toJson().toString());
                                } catch (JSONException e) {
                                    return;
                                }
                                it.putExtra("position",4);
                                mContext.startActivity(it);
                                ((Activity)mContext).overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
                            }
                        });

                    }
                    good_cell_name_five.setText(goodfive.name);
                    good_cell_price_five.setText(goodfive.brief);

//six
                    final SIMPLEGOODS goodsix = listData.get(5);
                    if (null != goodsix && null != goodsix.img && null != goodsix.img.thumb && null != goodsix.img.small)
                    {

                        if(imageType.equals("high")) {
                            imageLoader.displayImage(goodfour.img.thumb,good_cell_photo_six, EcmobileApp.options);
                        } else if(imageType.equals("low")) {
                            imageLoader.displayImage(goodfour.img.small,good_cell_photo_six, EcmobileApp.options);
                        } else {
                            String netType = shared.getString("netType", "wifi");
                            if(netType.equals("wifi")) {
                                imageLoader.displayImage(goodfour.img.thumb,good_cell_photo_six, EcmobileApp.options);
                            } else {
                                imageLoader.displayImage(goodfour.img.small,good_cell_photo_six, EcmobileApp.options);
                            }
                        }

                        good_cell_six.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Intent it = new Intent(mContext, D0_Category2Activity.class);

                                try {
                                    it.putExtra("category", searchModel.categoryArrayList.get(1).toJson().toString());
                                } catch (JSONException e) {
                                    return;
                                }
                                it.putExtra("position",5);
                                mContext.startActivity(it);
                                ((Activity)mContext).overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
                            }
                        });

                    }
                    good_cell_name_six.setText(goodsix.name);
                    good_cell_price_six.setText(goodsix.brief);

//seven
                    final SIMPLEGOODS goodseven = listData.get(6);
                    if (null != goodseven && null != goodseven.img && null != goodseven.img.thumb && null != goodseven.img.small)
                    {

                        if(imageType.equals("high")) {
                            imageLoader.displayImage(goodfour.img.thumb,good_cell_photo_seven, EcmobileApp.options);
                        } else if(imageType.equals("low")) {
                            imageLoader.displayImage(goodfour.img.small,good_cell_photo_seven, EcmobileApp.options);
                        } else {
                            String netType = shared.getString("netType", "wifi");
                            if(netType.equals("wifi")) {
                                imageLoader.displayImage(goodfour.img.thumb,good_cell_photo_seven, EcmobileApp.options);
                            } else {
                                imageLoader.displayImage(goodfour.img.small,good_cell_photo_seven, EcmobileApp.options);
                            }
                        }

                        good_cell_seven.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Intent it = new Intent(mContext, D0_Category2Activity.class);

                                try {
                                    it.putExtra("category", searchModel.categoryArrayList.get(1).toJson().toString());
                                } catch (JSONException e) {
                                    return;
                                }
                                it.putExtra("position",6);
                                mContext.startActivity(it);
                                ((Activity)mContext).overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
                            }
                        });

                    }
                    good_cell_name_seven.setText(goodseven.name);
                    good_cell_price_seven.setText(goodseven.brief);

//eight
                    final SIMPLEGOODS goodeight = listData.get(7);
                    if (null != goodeight && null != goodeight.img && null != goodeight.img.thumb && null != goodeight.img.small)
                    {

                        if(imageType.equals("high")) {
                            imageLoader.displayImage(goodfour.img.thumb,good_cell_photo_seven, EcmobileApp.options);
                        } else if(imageType.equals("low")) {
                            imageLoader.displayImage(goodfour.img.small,good_cell_photo_seven, EcmobileApp.options);
                        } else {
                            String netType = shared.getString("netType", "wifi");
                            if(netType.equals("wifi")) {
                                imageLoader.displayImage(goodfour.img.thumb,good_cell_photo_seven, EcmobileApp.options);
                            } else {
                                imageLoader.displayImage(goodfour.img.small,good_cell_photo_seven, EcmobileApp.options);
                            }
                        }

                        good_cell_seven.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Intent it = new Intent(mContext, D0_Category2Activity.class);

                                try {
                                    it.putExtra("category", searchModel.categoryArrayList.get(1).toJson().toString());
                                } catch (JSONException e) {
                                    return;
                                }
                                it.putExtra("position",7);
                                mContext.startActivity(it);
                                ((Activity)mContext).overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
                            }
                        });

                    }
                    good_cell_name_eight.setText(goodeight.name);
                    good_cell_price_eight.setText(goodeight.brief);

                }else {
                    good_cell_four.setVisibility(View.INVISIBLE);
                }
            }
            else
            {
                good_cell_two.setVisibility(View.INVISIBLE);
                good_cell_three.setVisibility(View.INVISIBLE);
                good_cell_four.setVisibility(INVISIBLE);
                good_cell_five.setVisibility(INVISIBLE);
                good_cell_six.setVisibility(INVISIBLE);
                good_cell_seven.setVisibility(INVISIBLE);
                good_cell_eight.setVisibility(INVISIBLE);
            }
        } else {
            good_cell_photo_one.setVisibility(View.INVISIBLE);
            good_cell_two.setVisibility(View.INVISIBLE);
            good_cell_three.setVisibility(View.INVISIBLE);
            good_cell_four.setVisibility(INVISIBLE);
            good_cell_five.setVisibility(INVISIBLE);
            good_cell_six.setVisibility(INVISIBLE);
            good_cell_seven.setVisibility(INVISIBLE);
            good_cell_eight.setVisibility(INVISIBLE);
        }
    }

    public void bindData(CATEGORYGOODS categorygoods,CATEGORY category,SearchModel searchmodel)
    {
        this.categorygoods = categorygoods;
        this.searchModel = searchmodel;
        this.category = category;
        mHandler.removeMessages(0);
        mHandler.sendEmptyMessageDelayed(0,30);

    	
    }
}

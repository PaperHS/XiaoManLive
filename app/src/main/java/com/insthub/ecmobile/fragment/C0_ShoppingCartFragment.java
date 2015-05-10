package com.insthub.ecmobile.fragment;

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

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListViewCart;
import com.external.maxwin.view.XListViewCart.IXListViewListenerCart;
import com.insthub.BeeFramework.fragment.BaseFragment;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.activity.*;
import com.insthub.ecmobile.adapter.C0_ShoppingCartAdapter;
import com.insthub.ecmobile.model.AddressModel;
import com.insthub.ecmobile.model.OrderModel;
import com.insthub.ecmobile.model.ShoppingCartModel;
import com.insthub.ecmobile.protocol.ApiInterface;
import com.insthub.ecmobile.protocol.STATUS;
import com.sina.weibo.sdk.utils.LogUtil;
import com.umeng.analytics.MobclickAgent;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 购物车
 */
public class C0_ShoppingCartFragment extends BaseFragment implements BusinessResponse, IXListViewListenerCart {

    @InjectView(R.id.c0_shopping_cart_username)
    TextView mC0ShoppingCartUsername;
    @InjectView(R.id.c0_shopping_cart_address)
    TextView mC0ShoppingCartAddress;
    @InjectView(R.id.c0_shopping_cart_phonenum)
    TextView mC0ShoppingCartPhonenum;
    private View view;
//	private View footerView;

    private TextView footer_total;
    //	private FrameLayout footer_balance;
    private FrameLayout shop_car_null;
    private FrameLayout shop_car_isnot;
    private Button cart_icon;

    private XListViewCart xlistView;
    private C0_ShoppingCartAdapter shopCarAdapter;
    private ArrayList<String> items = new ArrayList<String>();

    private ShoppingCartModel shoppingCartModel;
    public Handler messageHandler;
//    private ImageView back;

    private AddressModel addressModel;
    private OrderModel orderModel;

    private SharedPreferences shared;
    private SharedPreferences.Editor editor;

    private RelativeLayout addressLayout;
    private boolean isShopCartEmpty = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Resources resource = this.getResources();

        view = inflater.inflate(R.layout.c0_shopping_cart, null);
        ButterKnife.inject(this,view);

        shared = getActivity().getSharedPreferences("userInfo", 0);
        editor = shared.edit();

        addressLayout = (RelativeLayout) view.findViewById(R.id.c0_shopping_cart_address_layout);
        addressLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), F0_AddressListActivity.class);
                intent.putExtra("flag", 1);
                startActivityForResult(intent, 2);
                getActivity().overridePendingTransition(R.anim.push_right_in,
                        R.anim.push_left_out);

            }
        });

        shop_car_null = (FrameLayout) view.findViewById(R.id.shop_car_null);
        shop_car_isnot = (FrameLayout) view.findViewById(R.id.shop_car_isnot);

        xlistView = (XListViewCart) view.findViewById(R.id.shop_car_list);
        xlistView.setPullLoadEnable(false);
        xlistView.setRefreshTime();
        xlistView.setXListViewListener(this, 1);

//		footerView = LayoutInflater.from(getActivity()).inflate(R.layout.c0_shopping_car_footer, null);
        footer_total = (TextView) view.findViewById(R.id.c0_shopping_cart_totalprice);
//		footer_balance = (FrameLayout) footerView.findViewById(R.id.shop_car_footer_balance);
        cart_icon = (Button) view.findViewById(R.id.c0_shopping_cart_comfirmorder);

//		xlistView.addFooterView(footerView);

        addressModel = new AddressModel(getActivity());
        addressModel.addResponseListener(this);

        cart_icon.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {



                addressModel.getAddressList();


            }
        });

        if (null == shoppingCartModel) {
            shoppingCartModel = new ShoppingCartModel(getActivity());
            shoppingCartModel.addResponseListener(this);
            shoppingCartModel.checkOrder();
        }

        String uid = shared.getString("uid", "");
        if (uid.equals("")) {
            shop_car_null.setVisibility(View.VISIBLE);
            shop_car_isnot.setVisibility(View.GONE);
        } else {

            shoppingCartModel.cartList();
        }

        messageHandler = new Handler() {

            public void handleMessage(Message msg) {

                if (msg.what == C0_ShoppingCartAdapter.CART_CHANGE_REMOVE) {
                    Integer rec_id = (Integer) msg.arg1;
                    shoppingCartModel.deleteGoods(rec_id);
                }
                if (msg.what == C0_ShoppingCartAdapter.CART_CHANGE_MODIFY) {
                    int rec_id = msg.arg1;
                    int new_number = msg.arg2;
                    shoppingCartModel.updateGoods(rec_id, new_number);
                }
                if (msg.what == C0_ShoppingCartAdapter.CART_CHANGE_CHANGE1) {

//                	footer_balance.setEnabled(false);
//                	footer_balance.setBackgroundResource(R.drawable.item_info_add_cart_desabled_btn_red_b);
//                	cart_icon.setImageResource(R.drawable.shopping_cart_acc_cart_icon);
                }
                if (msg.what == C0_ShoppingCartAdapter.CART_CHANGE_CHANGE2) {
//                	footer_balance.setEnabled(true);
//                	footer_balance.setBackgroundResource(R.drawable.button_red);
//                	cart_icon.setImageResource(R.drawable.shopping_cart_acc_cart_icon);
                }

            }
        };

//        back = (ImageView) view.findViewById(R.id.top_view_back);
//        back.setVisibility(View.INVISIBLE);


        orderModel = new OrderModel(getActivity());
        orderModel.addResponseListener(this);
        (getActivity()).getActionBar().setTitle("购物车");
        return view;
    }

    @SuppressLint("NewApi")
    public void setShopCart() {

        if (shoppingCartModel.goods_list.size() == 0) {
            shop_car_null.setVisibility(View.VISIBLE);
            shop_car_isnot.setVisibility(View.GONE);
            cart_icon.setEnabled(false);

        } else {
            cart_icon.setEnabled(true);
            footer_total.setText(shoppingCartModel.total.goods_price);

            shop_car_isnot.setVisibility(View.VISIBLE);
            shop_car_null.setVisibility(View.GONE);
            if (null == shopCarAdapter) {
                shopCarAdapter = new C0_ShoppingCartAdapter(getActivity(), shoppingCartModel.goods_list);
            }

            xlistView.setAdapter(shopCarAdapter);
            shopCarAdapter.notifyDataSetChanged();

//            footer_balance.setEnabled(true);
//        	footer_balance.setBackgroundResource(R.drawable.button_red);
//        	cart_icon.setImageResource(R.drawable.shopping_cart_acc_cart_icon);

            shopCarAdapter.parentHandler = messageHandler;
        }

    }

    public void updataCar() {
        shoppingCartModel.goods_list.clear();
        shoppingCartModel.cartList();
    }

    @SuppressLint("NewApi")
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) {
        if (url.endsWith(ApiInterface.CART_LIST)) {
            xlistView.stopRefresh();
            xlistView.setRefreshTime();
            setShopCart();
            TabsFragment.setShoppingcartNum();
        } else if (url.endsWith(ApiInterface.CART_DELETE)) {
            updataCar();
        } else if (url.endsWith(ApiInterface.CART_UPDATE)) {
            updataCar();
        } else if (url.endsWith(ApiInterface.ADDRESS_LIST)) {
            if (addressModel.addressList.size() == 0) {
                Intent intent = new Intent(getActivity(), F1_NewAddressActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_right_in,
                        R.anim.push_left_out);
            } else {
                Intent intent = new Intent(getActivity(), C1_CheckOutActivity1.class);
                startActivityForResult(intent, 1);
                getActivity().overridePendingTransition(R.anim.push_right_in,
                        R.anim.push_left_out);
            }

        } else if (url.endsWith(ApiInterface.ORDER_PAY)) {
            Intent intent = new Intent(getActivity(), PayWebActivity.class);
            String data = null;
            try {
                data = jo.getString("data").toString();
                intent.putExtra("html", data);
            } catch (JSONException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.push_right_in,
                    R.anim.push_right_out);
        } else if (url.endsWith(ApiInterface.FLOW_CHECKORDER)) {
            STATUS res_status = new STATUS();

            try {
                res_status.fromJson(jo.optJSONObject("status"));
                if(res_status.succeed == 1)
                {
                    mC0ShoppingCartPhonenum.setText(shoppingCartModel.address.tel);
                    mC0ShoppingCartUsername.setText(shoppingCartModel.address.consignee);
                    StringBuffer sbf = new StringBuffer();
                    sbf.append(shoppingCartModel.address.province_name+" ");
                    sbf.append(shoppingCartModel.address.city_name+" ");
                    sbf.append(shoppingCartModel.address.district_name+" ");
                    sbf.append(shoppingCartModel.address.address);
                    mC0ShoppingCartAddress.setText(sbf.toString());
                }
                else if(res_status.error_code == 10001)
                {
                    Intent intent = new Intent(getActivity(), F1_NewAddressActivity.class);
                    intent.putExtra("balance", 1);
                    startActivityForResult(intent, 3);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void onRefresh(int id) {

        shoppingCartModel.cartList();
    }

    @Override
    public void onLoadMore(int id) {


    }


    @SuppressLint("NewApi")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            shoppingCartModel.cartList();
        } else if (requestCode == 2) {
            shoppingCartModel.checkOrder();
        }
    }

    @Override
    public void onResume() {

        super.onResume();
        MobclickAgent.onPageStart("ShopCart");
    }

    @Override
    public void onPause() {

        super.onPause();
        MobclickAgent.onPageEnd("ShopCart");
    }


}

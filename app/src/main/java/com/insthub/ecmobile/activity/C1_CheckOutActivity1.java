package com.insthub.ecmobile.activity;
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

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.external.androidquery.callback.AjaxStatus;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.BeeFramework.view.MyDialog;
import com.insthub.BeeFramework.view.ShipTimeDialog;
import com.insthub.BeeFramework.view.ToastView;
import com.insthub.ecmobile.EcmobileManager;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.model.OrderModel;
import com.insthub.ecmobile.model.ShoppingCartModel;
import com.insthub.ecmobile.protocol.*;
import com.sina.weibo.sdk.utils.LogUtil;
import com.umeng.analytics.MobclickAgent;
import org.json.JSONException;
import org.json.JSONObject;

public class C1_CheckOutActivity1 extends BaseActivity implements BusinessResponse {


    private final static int REQUEST_ADDRESS_LIST = 1;
    private final static int REQUEST_PAYMENT = 2;
    private final static int REQUEST_Distribution = 3;
    private final static int REQUEST_BONUS = 4;
    private final static int REQUEST_INVOICE = 5;
    private final static int REQUEST_RedEnvelope = 6;
    private final static int REQUEST_ALIPAY = 7;
    private final static int REQUEST_Pay_Web = 8;
    private final static int REQUEST_UPPay = 10;
    @InjectView(R.id.checkout_time_icon)
    ImageView mCheckoutTimeIcon;
    @InjectView(R.id.checkout_promotion_icon)
    ImageView mCheckoutPromotionIcon;
    @InjectView(R.id.balance_goods_total)
    TextView mBalanceGoodsTotal;
    @InjectView(R.id.balance_fees)
    TextView mBalanceFees;
    @InjectView(R.id.balance_bonus)
    TextView mBalanceBonus;
    @InjectView(R.id.balance_total)
    TextView mBalanceTotal;
    @InjectView(R.id.checkout_promotion_layout)
    RelativeLayout mCheckoutPromotionLayout;
    @InjectView(R.id.checkout_submit_btn)
    Button mCheckoutSubmitBtn;
    @InjectView(R.id.payment_ali)
    ImageView mPaymentAli;
    @InjectView(R.id.payment_tenent)
    ImageView mPaymentTenent;
    @InjectView(R.id.checkout_take_order)
    Button mCheckoutTakeOrder;
    @InjectView(R.id.checkout_pay_layout)
    RelativeLayout mCheckoutPayLayout;
    @InjectView(R.id.checkout_ship_time)
    RelativeLayout mCheckoutShipTime;
    @InjectView(R.id.checkout_ship_time_str)
    TextView mShipTimeStr;
    @InjectView(R.id.pay_total_price)
    TextView mPayTotalPrice;
    @InjectView(R.id.pay_total_price_final)
    TextView mPayTotalPriceFinal;

    private ShipTimeDialog mShipTimeDialog;


    private ShoppingCartModel shoppingCartModel;
    private OrderModel orderModel;
    private float totalGoodsPrice;  //总价格
    private String paymentJSONString;
    private ORDER_INFO order_info;

    private SHIPPING shipping;//配送方式
    private PAYMENT payment; //支付方式

    private int inv_type = -1; //发票类型
    private int inv_content = -1; //发票内容
    private String inv_payee = null; //发票抬头

    private String scoreNum = null; //兑换的积分数
    private String scoreChangedMoney = null; //积分兑换的钱
    private String scoreChangedMoneyFormated = null; //积分兑换的钱

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c1_checkout_activity);
        ButterKnife.inject(this);

//		title = (TextView) findViewById(R.id.top_view_text);
        Resources resource = (Resources) getBaseContext().getResources();
        String set = resource.getString(R.string.shopcarfooter_settleaccounts);


        if (null == shoppingCartModel) {
            shoppingCartModel = new ShoppingCartModel(this);
            shoppingCartModel.addResponseListener(this);
            shoppingCartModel.checkOrder();
        } else {
            setInfo();
        }

        orderModel = new OrderModel(this);
        orderModel.addResponseListener(this);
        payment = new PAYMENT();
        shipping = new SHIPPING();
        getActionBar().setTitle("下单");
        mShipTimeDialog = new ShipTimeDialog(this, "请选择送货时间");
        mShipTimeDialog.negative.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mShipTimeDialog.dismiss();

            }
        });

        mShipTimeDialog.positive.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mShipTimeDialog.dismiss();
                mShipTimeStr.setText(mShipTimeDialog.getTime());
            }
        });
    }

    @OnClick(R.id.checkout_promotion_layout)
    public void onPromotionClick() {
        //优惠卷选择
    }

    @OnClick(R.id.checkout_submit_btn)
    public void onSubmit() {
        //提交按钮

        Resources resourc = (Resources) getBaseContext().getResources();
//      支付方式
//        if (null == payment)
//        {
//            ToastView toast1 = new ToastView(this, resourc.getString(R.string.warn_no_pay));
//            toast1.setGravity(Gravity.CENTER, 0, 0);
//            toast1.show();
//            return;
//        }
//        this.finish();
//        if (null == shipping) {
//            ToastView toast1 = new ToastView(this, resourc.getString(R.string.warn_no_shipping));
//            toast1.setGravity(Gravity.CENTER, 0, 0);
//            toast1.show();
//            return;
//        }


//        if (checkCashOnDeliverOk(payment, shipping))
//        {
//            if (null != selectedBONUS)
//            {
//                shoppingCartModel.flowDone(payment.pay_id, shipping.shipping_id, selectedBONUS.bonus_id, scoreNum, inv_type+"", inv_payee, inv_content+"");
//            }
//            else
//            {
//                shoppingCartModel.flowDone(payment.pay_id, shipping.shipping_id, null, scoreNum, inv_type+"", inv_payee, inv_content+"");
//            }
//
//        }
//        else
//        {
//            ToastView toast1 = new ToastView(C1_CheckOutActivity.this, "该配送方式不支持货到付款");
//            toast1.setGravity(Gravity.CENTER, 0, 0);
//            toast1.show();
//        }
        if (mShipTimeStr.getText() == null || mShipTimeStr.getText().toString().trim().equalsIgnoreCase("")) {
            ToastView toast = new ToastView(C1_CheckOutActivity1.this, "请选择配送时间");
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            mCheckoutPayLayout.setVisibility(View.VISIBLE);
            getActionBar().setTitle("立即支付");

        }

    }

    @OnClick(R.id.checkout_take_order)
    public void onTakeOrder() {

        shoppingCartModel.flowDone(null, null, null, scoreNum, inv_type + "", inv_payee, inv_content + "", mShipTimeStr.getText().toString());


    }

    @OnClick(R.id.checkout_ship_time)
    public void onClickTime() {


        //送货时间
        mShipTimeDialog.show();
    }

    public boolean checkCashOnDeliverOk(PAYMENT payment, SHIPPING shipping) {
        if (null != payment && null != shipping) {
            if (payment.is_cod.equals("1") && shipping.support_cod.equals("0")) {
                return false;
            }
        }
        return true;
    }

    public void setInfo() {

        totalGoodsPrice = 0;

        paymentJSONString = shoppingCartModel.orderInfoJsonString;


        for (int i = 0; i < shoppingCartModel.balance_goods_list.size(); i++) {
            totalGoodsPrice += Float.valueOf(shoppingCartModel.balance_goods_list.get(i).subtotal);
        }


        mBalanceGoodsTotal.setText("￥" + totalGoodsPrice);
        mBalanceTotal.setText("￥" + totalGoodsPrice);
        mPayTotalPrice.setText("￥" + totalGoodsPrice);
        mPayTotalPriceFinal.setText("￥" + totalGoodsPrice);

//优惠获取
//        try {
//            JSONObject jo = new JSONObject(shoppingCartModel.orderInfoJsonString);
//            flowcheckOrderResponse response = new flowcheckOrderResponse();
//            response.fromJson(jo);
//            int bonus = response.data.allow_use_bonus;
//            ArrayList<BONUS> bonuses = response.data.bonus;
//            if (bonus == 1 && bonuses.size() > 0) {
//                redPaper.setEnabled(true);
//            } else {
//                redPaper.setEnabled(false);
//                text_balance_redPaper.setTextColor(Color.parseColor("#9B9B9B"));
//                arrow_balance_redpocket.setVisibility(View.INVISIBLE);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

//积分
//        try {
//            JSONObject jo = new JSONObject(paymentJSONString);
//            flowcheckOrderResponse response = new flowcheckOrderResponse();
//            response.fromJson(jo);
//            String your_score = response.data.your_integral;
//            String order_max_score = response.data.order_max_integral + "";
//            int min_score = Math.min(Integer.valueOf(your_score), Integer.valueOf(order_max_score));
//            if (min_score == 0) {
//                score.setEnabled(false);
//                text_balance_score.setTextColor(Color.parseColor("#9B9B9B"));
//                arrow_balance_score.setVisibility(View.INVISIBLE);
//            } else {
//                score.setEnabled(true);
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        if (url.endsWith(ApiInterface.FLOW_CHECKORDER)) {
            STATUS res_status = new STATUS();
            res_status.fromJson(jo.optJSONObject("status"));
            if (res_status.succeed == 1) {
                setInfo();
            } else if (res_status.error_code == 10001) {
                Intent intent = new Intent(this, F1_NewAddressActivity.class);
                intent.putExtra("balance", 1);
                startActivityForResult(intent, REQUEST_ADDRESS_LIST);
            }

        } else if (url.endsWith(ApiInterface.FLOW_DONE)) {
            LogUtil.e("hshs", jo.toString());
            //获取订单信息
            JSONObject json = jo.getJSONObject("data");
            JSONObject orderObject = json.optJSONObject("order_info");
            order_info = new ORDER_INFO();
            order_info.fromJson(orderObject);

            Resources resource = (Resources) getBaseContext().getResources();
            String suc = resource.getString(R.string.successful_operation);
            String pay = resource.getString(R.string.pay_or_not);
            final String per = resource.getString(R.string.personal_center);

            this.finish();
//            if (payment.is_cod.equals("1")) {
//                ToastView toast1 = new ToastView(C1_CheckOutActivity1.this, getString(R.string.check_orders));
//                toast1.setGravity(Gravity.CENTER, 0, 0);
//                toast1.show();
//                finish();
//            } else {
//                mDialog = new MyDialog(this, suc, pay);
//                mDialog.show();
//                mDialog.positive.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mDialog.dismiss();
//
//                        if (EcmobileManager.getAlipayCallback(getApplicationContext()) != null
//                                && EcmobileManager.getAlipayParterId(getApplicationContext()) != null
//                                && EcmobileManager.getAlipaySellerId(getApplicationContext()) != null
//                                && EcmobileManager.getRsaAlipayPublic(getApplicationContext()) != null
//                                && EcmobileManager.getRsaPrivate(getApplicationContext()) != null) {
//                            if (0 == order_info.pay_code.compareTo("alipay")) {
//                                showAlipayDialog();
//                            } else if (0 == order_info.pay_code.compareTo("upop")) {
//                                orderModel.orderPay(order_info.order_id);
//                            } else if (0 == order_info.pay_code.compareTo("tenpay")) {
//                                orderModel.orderPay(order_info.order_id);
//                            } else {
//                                orderModel.orderPay(order_info.order_id);
//                            }
//                        } else {
//                            orderModel.orderPay(order_info.order_id);
//                        }
//
//                    }
//
//
//                });
//                mDialog.negative.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mDialog.dismiss();
//                        ToastView toast = new ToastView(C1_CheckOutActivity1.this, per);
//                        toast.setGravity(Gravity.CENTER, 0, 0);
//                        toast.show();
//                        finish();
//                    }
//                });
//            }
        }
//        else if (url.endsWith(ApiInterface.ORDER_PAY)) {
//            String pay_wap = orderModel.pay_wap;
//            String pay_online = orderModel.pay_online;
//            String upop_tn = orderModel.upop_tn;
//
//            if (upop_tn != null && !"".equals(upop_tn)) {
//                //银联sdk支付
//                UPPayAssistEx.startPayByJAR(C1_CheckOutActivity1.this, PayActivity.class, null, null,
//                        upop_tn, UPPay_mMode);
//            } else if (pay_wap != null && !"".equals(pay_wap)) {
//                //wap支付
//                Intent intent = new Intent(this, PayWebActivity.class);
//                intent.putExtra(PayWebActivity.PAY_URL, pay_wap);
//                startActivityForResult(intent, REQUEST_Pay_Web);
//            } else if (pay_online != null && !"".equals(pay_online)) {
//                //其他方式
//                Intent intent = new Intent(this, OtherPayWebActivity.class);
//                intent.putExtra("html", pay_online);
//                startActivity(intent);
//                finish();
//            }
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADDRESS_LIST) {
            if (data != null) {
                shoppingCartModel.checkOrder();
            }
        } else if (requestCode == REQUEST_PAYMENT) {
//            支付选择
//            if (data != null) {
//                String paymentString = data.getStringExtra("payment");
//                try {
//                    JSONObject paymentJSONObject = new JSONObject(paymentString);
//                    payment = new PAYMENT();
//                    payment.fromJson(paymentJSONObject);
//                    pay_type.setText(payment.pay_name);
//                } catch (JSONException e) {
//
//                }
//
//            }
        } else if (requestCode == REQUEST_Distribution) {
//      地区选择？
//            if (data != null) {
//                String shippingString = data.getStringExtra("shipping");
//                try {
//                    JSONObject shippingJSONObject = new JSONObject(shippingString);
//                    shipping = new SHIPPING();
//                    shipping.fromJson(shippingJSONObject);
//                    dis_type.setText(shipping.shipping_name);
//                    fees.setText(shipping.format_shipping_fee);
//                    refreshTotalPrice();
//                } catch (JSONException e) {
//
//                }
//            }
        } else if (requestCode == REQUEST_BONUS) {
//            红包选择
//            if (data != null) {
//                scoreNum = data.getStringExtra("input");
//                Resources resource = (Resources) getBaseContext().getResources();
//                String use = resource.getString(R.string.use);
//                String inte = resource.getString(R.string.score);
//                score_num.setText(use + scoreNum + inte);
//
//                scoreChangedMoney = data.getStringExtra("bonus");
//                scoreChangedMoneyFormated = data.getStringExtra("bonus_formated");
//
//                coupon.setText("-" + scoreChangedMoneyFormated);
//                refreshTotalPrice();
//            }
//        } else if (requestCode == REQUEST_INVOICE) {
//            if (data != null) {
//                inv_type = data.getIntExtra("inv_type", 0);
//                inv_content = data.getIntExtra("inv_content", 0);
//                inv_payee = data.getStringExtra("inv_payee");
//                invoice_message.setText(inv_payee);
//            }
//        } else if (requestCode == REQUEST_RedEnvelope) {
//            if (data != null) {
//                String bonusJSONString = data.getStringExtra("bonus");
//
//                if (null != bonusJSONString) {
//                    try {
//                        JSONObject jsonObject = new JSONObject(bonusJSONString);
//                        selectedBONUS = new BONUS();
//                        selectedBONUS.fromJson(jsonObject);
//                        redPaper_name.setText(selectedBONUS.type_name + "[" + selectedBONUS.bonus_money_formated + "]");
//                        bonus_text.setText("-" + selectedBONUS.bonus_money_formated);
//                        refreshTotalPrice();
//                    } catch (JSONException e) {
//
//                    }
//                }
//
//            }
        } else if (requestCode == REQUEST_UPPay) {
            if (data == null) {
                return;
            }
        /*
         * 支付控件返回字符串:success、fail、cancel
         *      分别代表支付成功，支付失败，支付取消
         */
            String str = data.getExtras().getString("pay_result");
            if (str.equalsIgnoreCase("success")) {
                Resources resource = getResources();
                String exit = resource.getString(R.string.pay_success);
                String exiten = resource.getString(R.string.continue_shopping_or_not);
                final MyDialog mDialog = new MyDialog(C1_CheckOutActivity1.this, exit, exiten);
                mDialog.show();
                mDialog.positive.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        Intent it = new Intent(C1_CheckOutActivity1.this, EcmobileMainActivity.class);
                        startActivity(it);
                        finish();

                    }
                });
                mDialog.negative.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        Intent intent = new Intent(C1_CheckOutActivity1.this, E4_HistoryActivity.class);
                        intent.putExtra("flag", "await_ship");
                        startActivity(intent);
                        finish();

                    }
                });
            } else if (str.equalsIgnoreCase("fail") || str.equals("cancel")) {
                ToastView toast = new ToastView(C1_CheckOutActivity1.this, getResources().getString(R.string.pay_failed));
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Intent intent = new Intent(C1_CheckOutActivity1.this, E4_HistoryActivity.class);
                intent.putExtra("flag", "await_pay");
                startActivity(intent);
                finish();
            }
        } else if (requestCode == REQUEST_ALIPAY) {
            if (data == null) {
                return;
            }
            String str = data.getExtras().getString("pay_result");
            if (str.equalsIgnoreCase("success")) {
                Resources resource = getResources();
                String exit = resource.getString(R.string.pay_success);
                String exiten = resource.getString(R.string.continue_shopping_or_not);
                final MyDialog mDialog = new MyDialog(C1_CheckOutActivity1.this, exit, exiten);
                mDialog.show();
                mDialog.positive.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        Intent it = new Intent(C1_CheckOutActivity1.this, EcmobileMainActivity.class);
                        startActivity(it);
                        finish();

                    }
                });
                mDialog.negative.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        Intent intent = new Intent(C1_CheckOutActivity1.this, E4_HistoryActivity.class);
                        intent.putExtra("flag", "await_ship");
                        startActivity(intent);
                        finish();

                    }
                });
            } else if (str.equalsIgnoreCase("fail")) {
                ToastView toast = new ToastView(C1_CheckOutActivity1.this, getResources().getString(R.string.pay_failed));
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Intent intent = new Intent(C1_CheckOutActivity1.this, E4_HistoryActivity.class);
                intent.putExtra("flag", "await_pay");
                startActivity(intent);
                finish();
            }
        } else if (requestCode == REQUEST_Pay_Web) {
            if (data == null) {
                return;
            }
            String str = data.getExtras().getString("pay_result");
            if (str.equalsIgnoreCase("success")) {
                Resources resource = getResources();
                String exit = resource.getString(R.string.pay_success);
                String exiten = resource.getString(R.string.continue_shopping_or_not);
                final MyDialog mDialog = new MyDialog(C1_CheckOutActivity1.this, exit, exiten);
                mDialog.show();
                mDialog.positive.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        Intent it = new Intent(C1_CheckOutActivity1.this, EcmobileMainActivity.class);
                        startActivity(it);
                        finish();

                    }
                });
                mDialog.negative.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        Intent intent = new Intent(C1_CheckOutActivity1.this, E4_HistoryActivity.class);
                        intent.putExtra("flag", "await_ship");
                        startActivity(intent);
                        finish();

                    }
                });
            } else if (str.equalsIgnoreCase("fail")) {
                ToastView toast = new ToastView(C1_CheckOutActivity1.this, getResources().getString(R.string.pay_failed));
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Intent intent = new Intent(C1_CheckOutActivity1.this, E4_HistoryActivity.class);
                intent.putExtra("flag", "await_pay");
                startActivity(intent);
                finish();
            } else {

                Resources resource = getResources();
                String exit = resource.getString(R.string.pay_finished);
                String exiten = resource.getString(R.string.is_pay_success);
                final MyDialog mDialog = new MyDialog(C1_CheckOutActivity1.this, exit, exiten);
                mDialog.show();
                mDialog.positive.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        Intent it = new Intent(C1_CheckOutActivity1.this, EcmobileMainActivity.class);
                        startActivity(it);
                        finish();

                    }
                });
                mDialog.negative.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        Intent intent = new Intent(C1_CheckOutActivity1.this, E4_HistoryActivity.class);
                        intent.putExtra("flag", "await_pay");
                        startActivity(intent);
                        finish();

                    }
                });

            }
        }

    }

    void refreshTotalPrice() {
        float total_price_show = totalGoodsPrice;

        if (null != shipping && 0 != shipping.shipping_fee) {
            total_price_show += Float.valueOf(shipping.shipping_fee);
        }
//
//        if (null != scoreChangedMoney) {
//            total_price_show -= Float.valueOf(scoreChangedMoney);
//        }

//        红包扣除
//        if (null != selectedBONUS && null != selectedBONUS.type_money) {
//            total_price_show -= Float.valueOf(selectedBONUS.type_money);
//        }
        mBalanceTotal.setText("￥" + total_price_show);
        mBalanceGoodsTotal.setText("￥" + total_price_show);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (EcmobileManager.getUmengKey(this) != null) {
            MobclickAgent.onPageStart("BalancePage");
            MobclickAgent.onResume(this, EcmobileManager.getUmengKey(this), "");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (EcmobileManager.getUmengKey(this) != null) {
            MobclickAgent.onPageEnd("BalancePage");
            MobclickAgent.onPause(this);
        }
    }

    private void showAlipayDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.alipay_dialog, null);
        final Dialog dialog = new Dialog(this, R.style.dialog);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        LinearLayout alipayLayout = (LinearLayout) view.findViewById(R.id.alipay);
        LinearLayout alipayWapLayout = (LinearLayout) view.findViewById(R.id.alipay_wap);

        alipayLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(C1_CheckOutActivity1.this, AlixPayActivity.class);
                intent.putExtra(AlixPayActivity.ORDER_INFO, order_info);
                startActivityForResult(intent, REQUEST_ALIPAY);
            }
        });

        alipayWapLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                orderModel.orderPay(order_info.order_id);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (item.getItemId() == android.R.id.home) {

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

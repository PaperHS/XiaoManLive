package com.insthub.ecmobile.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.BeeFramework.model.BusinessResponse;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.H1_OrderDetailAdapter;
import com.insthub.ecmobile.model.OrderModel;
import com.insthub.ecmobile.protocol.ApiInterface;
import com.insthub.ecmobile.protocol.GOODORDER;
import com.insthub.ecmobile.protocol.STATUS;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class H1_OrderDetailActivity extends BaseActivity implements BusinessResponse {

    @InjectView(R.id.trade_item_status)
    TextView mTradeItemStatus;
    @InjectView(R.id.trade_item_time)
    TextView mTradeItemTime;
    @InjectView(R.id.trade_item_count)
    TextView mTradeItemCount;
    @InjectView(R.id.trade_item_total)
    TextView mTradeItemTotal;
    @InjectView(R.id.trade_item_redPaper)
    TextView mTradeItemRedPaper;
    @InjectView(R.id.h1_orderdetail_listview)
    ListView mH1OrderdetailListview;
    @InjectView(R.id.h1_orderdetail_cancel)
    Button mOrderCancel;

    private OrderModel orderModel;
    private int mPosition;
    private GOODORDER mCurOrder;
    private H1_OrderDetailAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h1_orderdetail);
        ButterKnife.inject(this);

        orderModel = new OrderModel(this);
        orderModel.addResponseListener(this);
        orderModel.getOrder("await_pay");

        mPosition = getIntent().getIntExtra("position", 0);

    }

    @OnClick(R.id.h1_orderdetail_cancel)
    public void OnclickCancel(){
        orderModel.orderCancle(Integer.parseInt(mCurOrder.order_id));
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ApiInterface.ORDER_LIST)) {
            mCurOrder = orderModel.order_list.get(mPosition);
            adapter = new H1_OrderDetailAdapter(mCurOrder.goods_list,this);
            setOrder();
        }else if (url.endsWith(ApiInterface.ORDER_CANCLE)){
            STATUS res_status = new STATUS();
            res_status.fromJson(jo.optJSONObject("status"));
            if(res_status.succeed == 1){
                this.finish();
            }
        }
    }

    private void setOrder() {
        mH1OrderdetailListview.setAdapter(adapter);
        getActionBar().setTitle("订单编号："+mCurOrder.order_sn);
        mTradeItemCount.setText("10个");
        mTradeItemRedPaper.setText("-"+mCurOrder.formated_bonus);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentTime = new Date(mCurOrder.order_time);
        mTradeItemTime.setText(format.format(currentTime));
        mTradeItemTotal.setText(mCurOrder.total_fee);
        mOrderCancel.setEnabled(true);
    }
}

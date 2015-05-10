package com.insthub.ecmobile.component;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.insthub.ecmobile.R;
import com.insthub.ecmobile.protocol.CATEGORYGOODS;

public class FunctionCell extends LinearLayout implements View.OnClickListener{


	private DrawableCenterTextView btn1;
	private DrawableCenterTextView btn2;
	private DrawableCenterTextView btn3;
	Handler mHandler;
	public FunctionCell(Context context, AttributeSet attrs) {
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
    public void bindData()
    {

        mHandler.removeMessages(0);
        mHandler.sendEmptyMessageDelayed(0,30);

    	
    }

	private void init() {
		btn1 = (DrawableCenterTextView)findViewById(R.id.funtion_freshbirdguaid);
		btn2 =(DrawableCenterTextView)findViewById(R.id.funtion_freshbirdguaid2);
		btn3 =(DrawableCenterTextView)findViewById(R.id.funtion_freshbirdguaid3);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.funtion_freshbirdguaid:
				break;
			case R.id.funtion_freshbirdguaid2:
				break;
			case R.id.funtion_freshbirdguaid3:
				break;
			default:
				break;
		}
	}
}

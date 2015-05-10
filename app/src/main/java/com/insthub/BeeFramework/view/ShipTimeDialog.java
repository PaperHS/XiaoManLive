package com.insthub.BeeFramework.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.insthub.ecmobile.R;

public class ShipTimeDialog {

	private Dialog mDialog;
	private TextView dialog_title;

	public TextView positive;
	public TextView negative;
    public Spinner mLeftSpinner;
    public Spinner mRightSpinner;

	public ShipTimeDialog(Context context, String title) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.ship_dialog_layout, null);

		mDialog = new Dialog(context, R.style.dialog);
		mDialog.setContentView(view);
		mDialog.setCanceledOnTouchOutside(false);
        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_BACK){
                    return true;
                }
                return false;
            }
        });
		dialog_title = (TextView) view.findViewById(R.id.dialog_title);

		dialog_title.setText(title);
		mLeftSpinner = (Spinner)view.findViewById(R.id.ship_dialog_day);
        mRightSpinner = (Spinner)view.findViewById(R.id.ship_dialog_time);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context,R.layout.spinner_item_layout,R.id.spinner_tv,context.getResources().getStringArray(R.array.ship_day));
        //设置下拉样式  android里面给大家提供了丰富的样式和功能图片
//        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //为下拉列表设置适配器
        mLeftSpinner.setAdapter(adapter1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(context,R.layout.spinner_item_layout,R.id.spinner_tv,context.getResources().getStringArray(R.array.ship_time));        //设置下拉样式  android里面给大家提供了丰富的样式和功能图片
//        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //为下拉列表设置适配器
        mRightSpinner.setAdapter(adapter2);
		
		positive = (TextView) view.findViewById(R.id.yes);
		negative = (TextView) view.findViewById(R.id.no);
		
	}

    public String getTime(){
        String str1 =         (String)(mLeftSpinner.getSelectedItem());
        String str2 =         (String)(mRightSpinner.getSelectedItem());


        return  str1+str2;
    }
	
	public void show() {
		mDialog.show();
	}
	
	public void dismiss() {
		mDialog.dismiss();
	}

}

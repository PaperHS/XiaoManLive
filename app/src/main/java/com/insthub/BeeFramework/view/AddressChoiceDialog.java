package com.insthub.BeeFramework.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.adapter.F0_AddressListAdapter;
import com.insthub.ecmobile.protocol.ADDRESS;

import java.util.List;

public class AddressChoiceDialog {

	private Dialog mDialog;
	private TextView dialog_title;

	public TextView positive;

    private ListView addressListView;


    F0_AddressListAdapter addressListAdapter;
    List<ADDRESS> addressesList;

	public AddressChoiceDialog(Context context, String title ,List<ADDRESS> list) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.dialog_address_choice, null);

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
        addressListView = (ListView)view.findViewById(R.id.address_listview);
		dialog_title.setText(title);
        addressesList = list;
        addressListAdapter = new F0_AddressListAdapter(context,addressesList,2);
		addressListView.setAdapter(addressListAdapter);
		positive = (TextView) view.findViewById(R.id.yes);

		
	}

	
	public void show() {
		mDialog.show();
	}
	
	public void dismiss() {
		mDialog.dismiss();
	}

}

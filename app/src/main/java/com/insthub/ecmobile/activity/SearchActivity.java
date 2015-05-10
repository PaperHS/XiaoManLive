package com.insthub.ecmobile.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.insthub.BeeFramework.activity.BaseActivity;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.model.SearchModel;

/**
 * Created by peggy on 15-4-1.
 */
public class SearchActivity extends BaseActivity {
    @InjectView(R.id.search_et)
    EditText mSearchEt;
    @InjectView(R.id.search_cancel)
    TextView mSearchCancel;
    @InjectView(R.id.search_listview)
    ListView mSearchListview;
    SearchModel searchModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_search);
        ButterKnife.inject(this);
        mSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.search_cancel)
    public void OnCancel(View v){
        this.finish();
    }
}

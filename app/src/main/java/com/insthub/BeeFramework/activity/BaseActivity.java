package com.insthub.BeeFramework.activity;

/*
 *	 ______    ______    ______
 *	/\  __ \  /\  ___\  /\  ___\
 *	\ \  __<  \ \  __\_ \ \  __\_
 *	 \ \_____\ \ \_____\ \ \_____\
 *	  \/_____/  \/_____/  \/_____/
 *
 *
 *	Copyright (c) 2013-2014, {Bee} open source community
 *	http://www.bee-framework.com
 *
 *
 *	Permission is hereby granted, free of charge, to any person obtaining a
 *	copy of this software and associated documentation files (the "Software"),
 *	to deal in the Software without restriction, including without limitation
 *	the rights to use, copy, modify, merge, publish, distribute, sublicense,
 *	and/or sell copies of the Software, and to permit persons to whom the
 *	Software is furnished to do so, subject to the following conditions:
 *
 *	The above copyright notice and this permission notice shall be included in
 *	all copies or substantial portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *	FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 *	IN THE SOFTWARE.
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.insthub.BeeFramework.model.ActivityManagerModel;
import com.insthub.BeeFramework.model.BusinessMessage;
import com.insthub.ecmobile.R;

import org.json.JSONException;

@SuppressLint("NewApi")
public class BaseActivity extends ActionBarActivity implements Handler.Callback
{
    public Handler mHandler;

    public BaseActivity()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         
        super.onCreate(savedInstanceState);
        mHandler = new Handler(this);
        ActivityManagerModel.addLiveActivity(this);
        com.insthub.ecmobile.Utils.ChangeActionBarHomeUpDrawable(this,R.drawable.ic_arraw_left);

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        ActivityManagerModel.addVisibleActivity(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        ActivityManagerModel.removeVisibleActivity(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ActivityManagerModel.addForegroundActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityManagerModel.removeForegroundActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManagerModel.removeLiveActivity(this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    public void OnMessageResponse(BusinessMessage response)
            throws JSONException
    {
        if (response.messageState == BusinessMessage.FAILURE_MESSAGE)
        {
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }
    public void startActivityForResult(Intent intent, int requestCode)
    {
        super.startActivityForResult(intent,requestCode);
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_left_in,R.anim.push_right_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home)
        {
            setResult(-1);
            finish();
            overridePendingTransition(R.anim.anim_hold,R.anim.push_right_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

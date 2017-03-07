package com.crc.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.crc.demo.service.CallService;
import com.crcement.com.mystudydemo.R;


public class CallActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

    }

    public void startListener(View view){
        startService(new Intent(this,CallService.class));


    }

    public void stopListener(View view){
        stopService(new Intent(this,CallService.class));

    }
}

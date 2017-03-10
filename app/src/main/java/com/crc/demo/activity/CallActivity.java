package com.crc.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.crc.demo.service.CallService;
import com.crcement.com.mystudydemo.R;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CallActivity extends Activity {

    //EditText et_number=null;
    String et=null;

    @BindView(R.id.et_number)
    EditText et_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        //et_number=(EditText) findViewById(R.id.et_number);

        ButterKnife.bind(this);



    }

    public void startListener(View view){
        startService(new Intent(this,CallService.class));
    }

    public void stopListener(View view){
        stopService(new Intent(this,CallService.class));

    }

    @OnClick(R.id.bt_addnumber) void submit() {
        et=et_number.getText().toString();
        Log.i("mytag","--------callActivity"+et);
        Intent intent=new Intent(this,CallService.class);
        intent.putExtra("et",et);
        startService(intent);
    }



//    public void addNumber(View view){
//        et=et_number.getText().toString();
//        Log.i("mytag","--------callActivity"+et);
//        Intent intent=new Intent(this,CallService.class);
//        intent.putExtra("et",et);
//        startService(intent);
//
//    }
}

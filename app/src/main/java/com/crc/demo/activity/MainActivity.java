package com.crc.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.crcement.com.mystudydemo.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void  baseListView(View view){
        //显示跳转
        Intent intent = new Intent(this, BaseListViewActivity.class);

        startActivity(intent);
    }

    public void checkListView(View view){
        // 显示跳转,唯一性
        Intent intent = new Intent("canway.hr.action.CHECKBOX");
        // 如果调用第三方的Activity则用隐式(多个字符串相同,则会出现选择框)
        startActivity(intent);
    }

    public void  displayImage(View view){
        //显示跳转
        Intent intent = new Intent(this, DownLoadImageActivity.class);

        startActivity(intent);
    }



}

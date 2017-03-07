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
        //显示跳转,基础Listview
        Intent intent = new Intent(this, BaseListViewActivity.class);

        startActivity(intent);
    }

    public void checkListView(View view){
        //带复选框的listview
        Intent intent = new Intent("canway.hr.action.CHECKBOX");
        // 如果调用第三方的Activity则用隐式(多个字符串相同,则会出现选择框)
        startActivity(intent);
    }

    public void  downloadImage(View view){
        //下载图片
        Intent intent = new Intent(this, DownLoadImageActivity.class);
        startActivity(intent);
    }

    public void  viewUtilsvDemo(View view){
        //HttpUtils框架实现网络访问
        Intent intent = new Intent(this, ViewUtilsActivity.class);
        startActivity(intent);
    }

    public void  serviceDemo(View view){
        //显示跳转
        Intent intent = new Intent(this, ServiceDemoActivity.class);
        startActivity(intent);
    }

    public void  myMusicDemo(View view){
        //播放音乐
        Intent intent = new Intent(this, MyMusicDemoActivity.class);
        startActivity(intent);
    }

    public void  myPhoneDemo(View view){
        //拨号拦截功能
        Intent intent = new Intent(this, CallActivity.class);
        startActivity(intent);
    }




}

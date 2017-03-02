package com.crc.demo.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.crc.demo.service.MyService;
import com.crcement.com.mystudydemo.R;

public class ServiceDemo extends Activity {

    //service 的内部类，
    private MyService.MyServiceProxy serviceProxy = null;

    private MyServiceConn myServiceConn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_demo);

    }


    //启动服务
    public void startService(View view) {
        startService(new Intent(this,MyService.class));

    }

    //停止服务
    public void stopService(View view) {
        stopService(new Intent(this,MyService.class));

    }

    //绑定
    public void bindService(View view) {
        if (myServiceConn == null) {
            myServiceConn = new MyServiceConn();
            // stopService(new Intent(this, MyService.class));
            bindService(new Intent(this, MyService.class), myServiceConn,
                    Context.BIND_AUTO_CREATE); // 如果服务没有创建,则在绑定自动创建
        }

    }

    //解除绑定
    public void unbindService(View view) {
        unbindService(myServiceConn);

    }

    //palyMusic
    public void palyMusic(View view) {
        serviceProxy.playMusicProxy("敢问路在何方");

    }


    private class MyServiceConn implements ServiceConnection {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("jxy", "onServiceDisconnected-->" + name);
        }

        @Override
        // 如果与Service绑定成功(onBind有正确返回值),那么此方法会被调用
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("jxy", this.getClass().getName() + "onServiceConnected");
            // service 就是onBind返回的对象
            Log.i("jxy", "onServiceConnected:myServiceProxy-->" + service);
            serviceProxy = (MyService.MyServiceProxy) service;
        }
    }

}
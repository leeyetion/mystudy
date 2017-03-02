package com.crc.demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("mytag","服务已经启动");
    }

    @Override
    public void onDestroy() {
        Log.i("mytag","服务已经停止");
        super.onDestroy();

    }

    @Override
    public IBinder onBind(Intent intent) {
        MyServiceProxy myServiceProxy = new MyServiceProxy();
        Log.i("jxy", "onBind:myServiceProxy-->" + myServiceProxy);
        return myServiceProxy;
    }

    public  class MyServiceProxy extends Binder {
        // 以后都要通过代理来完成操作

        public void playMusicProxy(String name) {
            playMusic(name);
        }
    }

    private void playMusic(String name) {
        Log.i("jxy", this.getClass().getName() + ":正在播放的歌曲是:" + name);
    }

    @Override
    // 主要用来执行服务启动后的代码(服务只创建一次,但是次方法可以执行多次),此方法后面会实现播放器的按钮功能
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("jxy", this.getClass().getName() + ":onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

}

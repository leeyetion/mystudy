package com.crc.demo.activity;

import android.app.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.crc.demo.service.MyMusicService;

import com.crcement.com.mystudydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyMusicDemoActivity extends Activity implements View.OnClickListener {


    @BindView(R.id.play) ImageView play;

    @BindView(R.id.pause) ImageView pause;

    @BindView(R.id.last) ImageView last;

    @BindView(R.id.next) ImageView next;

    @BindView(R.id.music_seekbar) SeekBar music_bar;

    String flag="play";

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:
                    //更新进度
                    int position = serviceProxy.getCurrentPosition();

                    int time = serviceProxy.getDuration();
                    int max = music_bar.getMax();

                    music_bar.setProgress(position*max/time);
                    break;
                default:
                    break;
            }

        }
    };


    MyMusicService.MyServiceProxy serviceProxy=null;
    MyMusicServiceConn myServiceConn =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymusic_demo);
        ButterKnife.bind(this);

        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        last.setOnClickListener(this);
        next.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(this, MyMusicService.class);
        if(play == view){
            intent.putExtra("command","play");
            startService(intent);

            bindService();
            if(serviceProxy.isPlaying()){
                final int milliseconds = 100;

                new Thread(){
                    @Override
                    public void run(){
                        while(true){
                            try {
                                sleep(milliseconds);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            mHandler.sendEmptyMessage(0);
                        }
                    }
                }.start();

            }


            music_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    //手动调节进度
                    // TODO Auto-generated method stub
                    int dest = seekBar.getProgress();
                    int time = serviceProxy.getDuration();
                    int max = seekBar.getMax();

                    serviceProxy.seekTo(time*dest/max);
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                    // TODO Auto-generated method stub

                }
            });


        }else if(pause==view) {
            Log.i("mytag","播放时长"+serviceProxy.getDuration());
            Log.i("mytag","播放时长"+serviceProxy.getCurrentPosition());
            intent.putExtra("command","pause");
            startService(intent);
        }else if(last==view){
            intent.putExtra("command","last");
            startService(intent);
        }else{
            intent.putExtra("command","next");
            startService(intent);
        }

    }


    //绑定
    public void bindService() {

        if (myServiceConn == null) {
            myServiceConn = new MyMusicDemoActivity.MyMusicServiceConn();
            // stopService(new Intent(this, MyService.class));
            bindService(new Intent(this, MyMusicService.class), myServiceConn, Context.BIND_AUTO_CREATE); // 如果服务没有创建,则在绑定自动创建
        }

    }



    private class MyMusicServiceConn implements ServiceConnection {
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
            serviceProxy = (MyMusicService.MyServiceProxy) service;
        }
    }
}
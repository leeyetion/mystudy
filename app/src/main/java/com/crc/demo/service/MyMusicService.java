package com.crc.demo.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import android.util.Log;

import com.crc.demo.until.OperateOFile;
import java.util.List;

public class MyMusicService extends Service {

    MediaPlayer mediaPlayer = null;
    List<String> fList = null;
    int number = 0;

    String path = "mnt/sdcard/netease/cloudmusic/Music/";

    public MyMusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        fList = OperateOFile.getFileName(path);

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }

        try {
            mediaPlayer.setDataSource(path + fList.get(number));
            mediaPlayer.prepare();//缓冲
        } catch (Exception e) {
            e.printStackTrace();
        }


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {//播出完毕事件
            @Override
            public void onCompletion(MediaPlayer arg0) {
                number++;
                initplay();
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {// 错误处理事件
            @Override
            public boolean onError(MediaPlayer player, int arg1, int arg2) {
                mediaPlayer.release();
                return false;
            }
        });

        Log.i("mytag", "服务已经启动");
    }

    @Override
    public void onDestroy() {
        Log.i("mytag", "服务已经停止");
        super.onDestroy();
    }

    public  class MyServiceProxy extends Binder {
        // 以后都要通过代理来完成操作

        public void playMusicProxy(String name) {
            playMusic(name);
        }

        public int getDuration(){
            if(mediaPlayer!=null){
                return mediaPlayer.getDuration();
            }else{
                return 0;
            }

        }

        public int getCurrentPosition(){
            if(mediaPlayer!=null){
                return mediaPlayer.getCurrentPosition();
            }else{
                return 0;
            }
        }

        public  void seekTo(int nn){
            if(mediaPlayer!=null)
            mediaPlayer.seekTo(nn);
        }
        public boolean isPlaying(){
            if(mediaPlayer!=null){
                return mediaPlayer.isPlaying();
            }
            else {
                return false;
            }

        }
    }

    private void playMusic(String name) {
        Log.i("jxy", this.getClass().getName() + ":正在播放的歌曲是:" + name);
    }





    @Override
    public IBinder onBind(Intent intent) {
        MyMusicService.MyServiceProxy myServiceProxy = new MyMusicService.MyServiceProxy();
        Log.i("jxy", "onBind:myServiceProxy-->" + myServiceProxy);
        return myServiceProxy;
    }

    @Override
    // 主要用来执行服务启动后的代码(服务只创建一次,但是次方法可以执行多次),此方法后面会实现播放器的按钮功能
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Log.i("jxy", this.getClass().getName() + ":onStartCommand");
        String flag = intent.getStringExtra("command");
        if (flag.equals("play")) {
            play();
        } else if (flag.equals("pause")) {
            pause();
        } else if (flag.equals("last")) {
            last();
        } else if (flag.equals("next")) {
            next();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void play() {


        mediaPlayer.start();//开始或恢复播放
    }

    private void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    private void last() {
        number--;
        initplay();
        Log.i("mytag", "last");
    }

    private void next() {
        number++;
        initplay();
        Log.i("mytag", "next");

    }

    private void initplay() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.reset();//重置为初始状态
        }
        try {

            if (number < 0) {
                number = fList.size() - 1;
            }
            if (number >= fList.size()) {
                number = 0;
            }
            mediaPlayer.setDataSource(path + fList.get(number));
            mediaPlayer.prepare();//缓冲
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        }
        mediaPlayer.start();//开始或恢复播放
        Log.i("mytag", mediaPlayer.toString());
    }
}

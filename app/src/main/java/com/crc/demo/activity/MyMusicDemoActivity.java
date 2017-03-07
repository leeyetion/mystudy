package com.crc.demo.activity;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;

import com.crc.demo.service.MyMusicService;
import com.crcement.com.mystudydemo.R;

public class MyMusicDemoActivity extends Activity {



    ImageView playorpause=null;
    ImageView last=null;
    ImageView next=null;
    String flag="play";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymusic_demo);

        playorpause=(ImageView)findViewById(R.id.playorpause);
        last=(ImageView)findViewById(R.id.last);
        next=(ImageView)findViewById(R.id.next);

        playorpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyMusicDemoActivity.this, MyMusicService.class);
                ImageView vv=(ImageView)v;
                String flag=v.getTag().toString();
                if(flag.equals("play")){
                    intent.putExtra("command","play");
                    startService(intent);
                    v.setTag("pause");
                    vv.setImageResource(R.drawable.stop_open);

                }
                if (flag.equals("pause")){

                    intent.putExtra("command","pause");
                    startService(intent);
                    vv.setImageResource(R.drawable.play_open);
                    v.setTag("play");
                }

            }
        });
        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyMusicDemoActivity.this, MyMusicService.class);
                intent.putExtra("command","last");
                startService(intent);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyMusicDemoActivity.this, MyMusicService.class);
                intent.putExtra("command","next");
                startService(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        if (flag.equals("pause")){
            stopService(new Intent(MyMusicDemoActivity.this, MyMusicService.class));
        }
        super.onDestroy();
    }
}
package com.crc.demo.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CallService extends Service {
    TelephonyManager tm = null;
    PhoneStateListener phoneStateListener = new PhoneStateListener();
    List<String> numberList=new ArrayList<String>();

    public CallService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        numberList.add("11O");
        numberList.add("18038054208");

        Log.i("jxy", "CallService....onCreate");
        tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        //PhoneStateListener是用来监听电话状态的监听器,此类的 onCallStateChanged需要重写处理电话状态切换之后的操作
        tm.listen(new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);
                Log.i("jxy", "电话号码："+incomingNumber);
                switch (state) {
                    case TelephonyManager.CALL_STATE_IDLE://空闲
                        Log.i("jxy", "空闲.....");
                        break;
                    case TelephonyManager.CALL_STATE_RINGING://响铃
                        Log.i("jxy", "响铃....."+incomingNumber);
                        for(int i=0;i<numberList.size();i++){
                            Log.i("mytag","--------"+numberList.get(i));
                        }
                        if(numberList.contains(incomingNumber))
                            CallService.endCall();
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK://正在通话
                        Log.i("jxy","通话.....");
                        break;
                }
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
    }
    public static void endCall() {
        //ServiceManager发现查询不到.查看SDK-15的版本我们会发现@Hide因此我们要用则需要通过反射
        Class clazz = null;
        try {
            clazz = Class.forName("android.os.ServiceManager");
            //得到方法所对应的Mehod对象
            Method method = clazz.getMethod("getService", String.class);
            //系统的服务都在Context中,包括Wifi的服务
            IBinder iBinder = (IBinder) method.invoke(null, Context.TELEPHONY_SERVICE);
            ITelephony iTelephony = ITelephony.Stub.asInterface(iBinder);
            iTelephony.endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String et=intent.getStringExtra("et");
        Log.i("mytag","onStartCommand"+et);
        numberList.add(et);

        return super.onStartCommand(intent, flags, startId);
    }
}
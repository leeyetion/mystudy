package com.crc.demo.breceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by crcement on 17/3/11.
 */

public class PhoneBroadcastReceiver extends BroadcastReceiver {
    @Override // 接受广播的方法
    public void onReceive(Context context, Intent intent) {
        Log.i("jxy","用户正在拨号111...........");
        // intent: 四大组件的桥梁,并且可以用来传数据
        String num = getResultData();
        setResultData("17951" + num);
    }
}

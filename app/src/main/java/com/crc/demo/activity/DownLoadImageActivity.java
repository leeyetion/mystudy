package com.crc.demo.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.crcement.com.mystudydemo.R;
import com.loopj.android.image.SmartImageView;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownLoadImageActivity extends Activity {

    // 主线程创建一个Handler对象,子线程通过主线程的Handler对象把Message交给主线程MessageQueue
    private Handler handler = new Handler() {
        @Override
        // 从子线程传入的Message
        public void handleMessage(Message msg) {
            Log.i("jxy", "当前线程名称:" + Thread.currentThread().getName());
            Bitmap bitmap = (Bitmap) msg.obj;
            // 获取ImageView,然后赋值即可(此处是主线程)
            ImageView iv = (ImageView) findViewById(R.id.iv);
            iv.setImageBitmap(bitmap);
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load_image);

        // 此处是主线程
        Log.i("jxy", "mainLooper:" + Looper.getMainLooper() + ",myLooper:"
                + Looper.myLooper() + Looper.myQueue());


    }

    public void testLooper(View view) {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                // 子线程的Looper需要自己创建
                Log.i("jxy", "mainLooper:" + Looper.getMainLooper()
                        + ",myLooper:" + Looper.myLooper() + Looper.myQueue());
            }
        }.start();
    }

    // 使用第三方空间 smart-image来实现图片的下载操作
    public void downImage03(View view) {
        // 默认支持 Https协议, 支持内存、硬盘级缓存(一级，二级缓存),下载的时候必须开启网络访问权限
        String imageUrl = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
        SmartImageView siv = (SmartImageView) findViewById(R.id.siv);
        siv.setImageUrl(imageUrl);
    }

    // 通过子线程加载图片,但是赋值必须要通过主线程来完成,如果通过子线程赋值则会出现线程安全问题
    public void downImage02(View view) {
        new Thread() {
            @Override
            public void run() {
                String imageUrl = "http://www.baidu.com/img/bd_logo1.png";
                HttpURLConnection httpConn = null;
                try {
                    URL url = new URL(imageUrl);
                    // 创建连接对象,注意：此时并未真正连接
                    httpConn = (HttpURLConnection) url.openConnection();
                    httpConn.setRequestMethod("POST");
                    httpConn.setReadTimeout(3000);
                    // 真正连接,注意Android中如果要访问网络,则需要配置权限
                    httpConn.connect();
                    // 获取访问的状态码,根据状态码来操作数据
                    int code = httpConn.getResponseCode();
                    if (code == 200) {
                        // 获取输入流下载图片信息
                        InputStream in = httpConn.getInputStream();
                        ImageView iv = (ImageView) findViewById(R.id.iv);
                        // android提供了一个工具类来InputStream --> BitMap之间的转化
                        Bitmap bitMap = BitmapFactory.decodeStream(in);
                        Log.i("jxy", "bitMap:" + bitMap);
                        // iv.setImageBitmap(bitMap); // 必须要通过主线程赋值
                        Message msg = handler.obtainMessage(); // 此方法可以使用缓存中的message对象
                        msg.obj = bitMap;
                        handler.sendMessage(msg);
                    } else {
                        // 网络访问异常
                        Toast.makeText(DownLoadImageActivity.this, "网络访问异常:" + code,
                                Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    if (httpConn != null) {
                        // 断开连接释放资源
                        httpConn.disconnect();
                    }
                }
            }

        }.start();
    }

    // 通过网络下载图片数据,通过主线程下载图片,会导致APP假死,用户体验非常不好
    public void downImage01(View view) {
        Log.i("jxy", "当前线程名称:" + Thread.currentThread().getName());
        String imageUrl = "http://www.baidu.com/img/bd_logo1.png";
        HttpURLConnection httpConn = null;
        try {
            URL url = new URL(imageUrl);
            // 创建连接对象,注意：此时并未真正连接
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setReadTimeout(3000);
            // 真正连接,注意Android中如果要访问网络,则需要配置权限
            httpConn.connect();
            // 获取访问的状态码,根据状态码来操作数据
            int code = httpConn.getResponseCode();
            if (code == 200) {
                // 获取输入流下载图片信息
                InputStream in = httpConn.getInputStream();
                ImageView iv = (ImageView) findViewById(R.id.iv);
                // android提供了一个工具类来InputStream --> BitMap之间的转化
                Bitmap bitMap = BitmapFactory.decodeStream(in);
                iv.setImageBitmap(bitMap);
            } else {
                // 网络访问异常
                Toast.makeText(this, "网络访问异常:" + code, Toast.LENGTH_LONG)
                        .show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (httpConn != null) {
                // 断开连接释放资源
                httpConn.disconnect();
            }
        }
    }
}

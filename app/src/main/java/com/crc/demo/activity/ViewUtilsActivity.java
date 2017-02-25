package com.crc.demo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.crc.demo.model.Person;
import com.crcement.com.mystudydemo.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ViewUtilsActivity extends AppCompatActivity {

    List<Person> p_list = new ArrayList<Person>();
    ListView lv_person = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veiw_utils);


    }

    public void httpReq(View view) {
        // 显示listview
        startActivity(new Intent(this, ListViewActivity.class));
    }

    private HttpHandler<File> handler = null;

    public void downSoft01(View view) {
        // 获取文本框
        final TextView txt_bar = (TextView) findViewById(R.id.txt_bar);
        final ProgressBar pbar = (ProgressBar) findViewById(R.id.progressBar);
        // 创建http请求对象
        HttpUtils http = new HttpUtils();
        String sdpath = Environment.getExternalStorageDirectory().getPath();
        // 根据tag状态来执行相应操作
        if (view.getTag().equals("start")) {
            // 修改tag与文本框提示
            view.setTag("stop");
            Button btn = (Button) view;
            btn.setText("停止下载");
            // 发送request请求
            handler = http.download(
                    "https://dl.google.com/dl/android/" +
                            "studio/ide-zips/2.0.0.0/android-studio-ide-143.2443734-windows.zip",
                    sdpath + "/android-studio-ide-143.2443734-windows.zip", true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
                    true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
                    new RequestCallBack<File>() { // 下载完毕的回调函数
                        @Override // 开始下载的时候调用
                        public void onStart() {
                            txt_bar.setText("conn...");
                        }

                        @Override
                        // 加载的时候此方法触发
                        public void onLoading(long total, long current,
                                              boolean isUploading) {
                            txt_bar.setText(current + "/" + total);
                            pbar.setMax((int) total);
                            pbar.setProgress((int) current);
                        }

                        @Override
                        public void onSuccess(ResponseInfo<File> responseInfo) {
                            // result:加载之后服务器返回的结果,类型取决T类型
                            txt_bar.setText("downloaded:"
                                    + responseInfo.result.getPath());
                        }

                        @Override
                        public void onFailure(HttpException error, String msg) {
                            txt_bar.setText(msg);
                        }
                    });
        } else {
            // 修改tag与文本框提示
            view.setTag("start");
            Button btn = (Button) view;
            btn.setText("开始下载");
            handler.cancel();
        }

    }


}

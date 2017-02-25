package com.crc.demo.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.crc.demo.model.Student;
import com.crcement.com.mystudydemo.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.loopj.android.image.SmartImageView;

public class ListViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_list_view);
		// 发送http请求获取网络数据(需要配置权限和导入jar包)
		HttpUtils http = new HttpUtils();

		http.send(HttpRequest.HttpMethod.GET,
				"http://www.jxy-edu.com/data.txt",
				new RequestCallBack<String>() {

					@Override
					// 从服务器返回了json字符串
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// 获取返回的json数据
						String json = responseInfo.result;
						Gson gson = new Gson();
						final List<Student> stuList = gson.fromJson(json,
								new TypeToken<ArrayList<Student>>() {
								}.getType());
						// ListView通过适配器模式加载数据
						ListView lv = (ListView) findViewById(R.id.lv);
						lv.setAdapter(new BaseAdapter() {

							@Override
							// 返回item布局文件
							public View getView(int position, View convertView,
									ViewGroup parent) {
								Student student = (Student) getItem(position);
								View view = null;
								// 如果有缓存item则不需要新建
								if (convertView != null) {
									view = convertView;
								} else {
									view = View.inflate(ListViewActivity.this,
											R.layout.image_list_view_item, null);
								}
								// 给View里面的控件赋值,如果是图片地址则下载且缓存
								SmartImageView siv = (SmartImageView) view
										.findViewById(R.id.siv);
//								siv.setScaleType(ImageView.ScaleType.CENTER);
								TextView txt_name = (TextView) view
										.findViewById(R.id.txt_name);
								TextView txt_address = (TextView) view
										.findViewById(R.id.txt_address);
								siv.setImageUrl(student.getIcon());
								txt_name.setText(student.getName());
								txt_address.setText(student.getAddress());
								return view;
							}

							@Override
							public long getItemId(int position) {
								// TODO Auto-generated method stub
								return 0;
							}

							@Override
							public Object getItem(int position) {
								// TODO Auto-generated method stub
								return stuList.get(position);
							}

							@Override
							public int getCount() {
								// TODO Auto-generated method stub
								return stuList.size();
							}
						});
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						Toast.makeText(ListViewActivity.this, "请求失败!",
								Toast.LENGTH_LONG).show();
					}
				});
	}

}

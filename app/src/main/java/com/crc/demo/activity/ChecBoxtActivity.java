package com.crc.demo.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import com.crcement.com.mystudydemo.R;


import com.crc.demo.model.Person;

// servlet doPost(request)
public class ChecBoxtActivity extends Activity {

	private List<Person> pList = new ArrayList<Person>();
	private TextView txt_count = null;
	private int count = 0;

    RadioButton rb_all=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkbox);
		// 1:从网络获取数据 json数组
		for (int i = 1; i <= 20; i++) {
			pList.add(new Person("小强" + i, false));
		}
		// 2:通过适配器进行数据适配
		ListView lv = (ListView) findViewById(R.id.lv);
		txt_count= (TextView) findViewById(R.id.txt_count);
		// 继承AdapterView的控件都必须使用适配器模式(提升性能)
		// lv.addView(child);
		lv.setAdapter(new MyBaseAdapter());
	}
	
	// 声明了一个内部类:1: 此类只能被当前父类使用 2: 可以共享外部类的资源
	private class MyBaseAdapter extends BaseAdapter {

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return pList.size();
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return pList.get(position);
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				Log.i("jxy","person:" + position + ",convertView:" + convertView + ",parent:" + parent);
				// 获取数据,然后交给list_item的组件显示
				final Person person = (Person) getItem(position);
				View item = null;
				if(convertView ==null){
					// Recycle中没有list_item，则创建
					item = (View) View.inflate(ChecBoxtActivity.this,R.layout.list_item2, null);
				}else{
					// item可以使用以前的但是数据,要重新赋值
					item = convertView;
				}
				TextView txt_name = (TextView) item.findViewById(R.id.txt_name);
				CheckBox chk_box = (CheckBox)item.findViewById(R.id.chk_box);
				 //单击复选框的时候,要同步当前person的boolean值
				/*chk_box.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						Log.i("jxy","buttonView:" + buttonView + ",isChecked:" + isChecked);
						person.setCheck(isChecked);
					}
				});*/
				chk_box.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						CheckBox chk_box = (CheckBox) view;
						person.setCheck(chk_box.isChecked());
						count = chk_box.isChecked() ? count+1 : count-1;
						txt_count.setText(String.format("已选中%d项",count));
						notifyDataSetChanged();
					}
				});
				 //person的值交给item(回显)
				txt_name.setText(person.getName());
				chk_box.setChecked(person.isCheck());
				return item;
			}

		}
}

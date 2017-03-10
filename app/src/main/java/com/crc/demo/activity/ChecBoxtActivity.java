package com.crc.demo.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.crc.demo.model.Person;
import com.crcement.com.mystudydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

// servlet doPost(request)
public class ChecBoxtActivity extends Activity {

	private List<Person> pList = new ArrayList<Person>();
	//private TextView txt_count = null;
	@BindView(R.id.txt_count) TextView txt_count;

	@BindView(R.id.lv) ListView lv;

	@BindView(R.id.txt_name) TextView txt_name;

	@BindView(R.id.chk_box) CheckBox chk_box;
	// 声明一个int类型用来存储被选中的记录数
	private int count = 0;
	
	private MyBaseAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkbox_list_view);

		ButterKnife.bind(this);
		//txt_count = (TextView) findViewById(R.id.txt_count);
		// 1:从网络获取数据 json数组
		for (int i = 1; i <= 20; i++) {
			pList.add(new Person("小强" + i, false));
		}
		// 2:通过适配器进行数据适配
		//ListView lv = (ListView) findViewById(R.id.lv);
		// 继承AdapterView的控件都必须使用适配器模式(提升性能)
		// lv.addView(child);
		mAdapter = new MyBaseAdapter();
		lv.setAdapter(mAdapter);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Person person = (Person) mAdapter.getItem(position);

				CheckBox chk_box = (CheckBox)view.findViewById(R.id.chk_box);
				Log.i("mytag","-------"+person.toString()+chk_box.isChecked());
				person.setCheck(!chk_box.isChecked());

				count = chk_box.isChecked() ? count-1 : count+1;
				txt_count.setText(String.format("已选中%d项", count));

				chk_box.setChecked(person.isCheck());
				mAdapter.notifyDataSetChanged();
			}
		});
	}

	// 全选功能
	public void btnAll(View view) {
		for (Person temp : pList) {
			temp.setCheck(true);
		}
		count = pList.size();
		// txt_count.setText("已选中"+count+"项");
		txt_count.setText(String.format("已选中%d项", count));
		// 数据源更新同步listview则需要使用观察者模式
		MyBaseAdapter mAdapter = this.mAdapter;
		//更新UI
		mAdapter.notifyDataSetChanged();
	}

	// 取消选择
	public void btnCancel(View view) {
		for (Person temp : pList) {
			temp.setCheck(false);
		}
		count = 0;
		txt_count.setText(String.format("已选中%d项", count));
		mAdapter.notifyDataSetChanged();
	}

	// 反选
	public void btnReverse(View view) {
		for (Person temp : pList) {
			temp.setCheck(!temp.isCheck());
		}
		count = pList.size() - count;
		txt_count.setText(String.format("已选中%d项", count));
		mAdapter.notifyDataSetChanged();
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
			Log.i("mytag", "person:" + position + ",convertView:" + convertView
					+ ",parent:" + parent);
			// 获取数据,然后交给list_item的组件显示
			final Person person = (Person) getItem(position);
			View item = null;
			if (convertView == null) {
				// Recycle中没有list_item，则创建
				item = (View) View.inflate(ChecBoxtActivity.this,
						R.layout.checkbox_list_view_item, null);
			} else {
				// item可以使用以前的但是数据,要重新赋值
				item = convertView;
			}
			ButterKnife.bind(item);
			txt_name.setText(person.getName());
			chk_box.setChecked(person.isCheck());
			return item;

		}

	}
}

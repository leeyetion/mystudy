package com.crc.demo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.crc.demo.model.Person;
import com.crcement.com.mystudydemo.R;

import java.util.ArrayList;
import java.util.List;

public class BaseListViewActivity extends AppCompatActivity {

    List<Person> p_list =new ArrayList<Person>();
    ListView lv_person=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baselistview);

        ListView lv_person=(ListView)findViewById(R.id.lv_person);
        initdate();
        lv_person.setAdapter(new MyAdapter());
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return p_list.size();
        }

        @Override
        public Object getItem(int i) {
            return p_list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = View.inflate(BaseListViewActivity.this,R.layout.activity_baselistview_item,null);

            }
            Person p=(Person)getItem(i);
            TextView tv_name=(TextView) view.findViewById(R.id.tv_name);
            tv_name.setText(p.getName());
            TextView tv_address=(TextView) view.findViewById(R.id.tv_address);
            tv_address.setText(p.getAdress());
            TextView tv_phone=(TextView) view.findViewById(R.id.tv_phone);
            tv_phone.setText(p.getPhone());

            return view;

        }
    }

    private void initdate() {
        for(int i=0;i<30;i++){
            p_list.add(new Person("admin"+i,"12345678901","中国广东深圳"));
        }
    }
}

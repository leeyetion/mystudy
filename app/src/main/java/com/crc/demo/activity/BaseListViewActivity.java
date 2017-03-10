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

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseListViewActivity extends AppCompatActivity {

    List<Person> p_list =new ArrayList<Person>();
    @BindView(R.id.lv_person) ListView lv_person;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_list_view);

        ButterKnife.bind(this);
        initdate();
        lv_person.setAdapter(new MyAdapter());
    }

    //定义适配器
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

            ViewHolder viewHolder;

            if (view == null) {
                view = View.inflate(BaseListViewActivity.this,R.layout.base_list_view_item,null);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) view.getTag();
            }
            Person p=(Person)getItem(i);
            //设置Item的值

            viewHolder.tv_name.setText(p.getName());

            viewHolder.tv_address.setText(p.getAdress());

            viewHolder.tv_phone.setText(p.getPhone());

            return view;
        }
    }

    static class ViewHolder{
        @BindView(R.id.tv_name) TextView tv_name;

        @BindView(R.id.tv_address) TextView tv_address;

        @BindView(R.id.tv_phone) TextView tv_phone;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private void initdate() {
        for(int i=0;i<30;i++){
            p_list.add(new Person("admin"+i,"12345678901","中国广东深圳"));
        }
    }
}

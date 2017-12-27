package com.example.xinghuacheng.howrelax.switchActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.xinghuacheng.howrelax.Activity.mainDisplayActivity;
import com.example.xinghuacheng.howrelax.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Xinghua Cheng on 17/06/2017.
 */

public class information extends mainDisplayActivity {

    private ListView listView1;
    private ListView listView2;

    private ImageButton tab_publish_img;
    private LinearLayout add_activity;
    private LinearLayout add_together;
    private boolean isVisible = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.information);

        listView1 = (ListView) findViewById(R.id.list_a);
        //步骤1 一个列表项的内容，就是一个item
        Map<String, Object> item1 = new HashMap<String, Object>();
        item1.put("img_a", R.drawable.message1);
        item1.put("title_a", "通知消息");
        item1.put("info_a", "自由的艺术画展进入倒计时");
        //步骤2：把这些Map放到List当中
        List<Map<String, Object>> data1 = new ArrayList<Map<String, Object>>();
        data1.add(item1);
        //注意：第四个参数和第五个参数要一一对应
        SimpleAdapter simpleAdapter1 = new SimpleAdapter(this, data1,
                R.layout.infor_list1, new String[] { "img_a", "title_a","info_a" },
                new int[] { R.id.img_a, R.id.title_a , R.id.info_a});
        //步骤3：将List中的内容填充到listView里面去
        listView1.setAdapter(simpleAdapter1);

        listView2 = (ListView) findViewById(R.id.list_b);
        //步骤1 一个列表项的内容，就是一个item
        Map<String, Object> item2 = new HashMap<String, Object>();
        item2.put("img_b", R.drawable.message3);
        item2.put("title_b", "小明");
        item2.put("info_b", "这活动下午还举行吗？");
        //步骤1 一个列表项的内容，就是一个item
        Map<String, Object> item3 = new HashMap<String, Object>();
        item3.put("img_b", R.drawable.message4);
        item3.put("title_b", "南师大学生会");
        item3.put("info_b", "有问题可以咨询我");
        //步骤2：把这些Map放到List当中
        List<Map<String, Object>> data2 = new ArrayList<Map<String, Object>>();
        data2.add(item2);
        data2.add(item3);
        SimpleAdapter simpleAdapter2 = new SimpleAdapter(this, data2,
                R.layout.infor_list2, new String[] { "img_b", "title_b","info_b" },
                new int[] { R.id.img_b, R.id.title_b , R.id.info_b});
        //步骤3：将List中的内容填充到listView里面去
        listView2.setAdapter(simpleAdapter2);



        tab_publish_img = (ImageButton) findViewById(R.id.tab_publish_img);
        add_activity = (LinearLayout) findViewById(R.id.add_activity);
        add_together = (LinearLayout) findViewById(R.id.add_together);

        add_activity.setVisibility(View.INVISIBLE);
        add_together.setVisibility(View.INVISIBLE);
        tab_publish_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible) {
                    add_activity.setVisibility(View.VISIBLE);
                    add_together.setVisibility(View.VISIBLE);
                    isVisible = false;
                } else {
                    add_activity.setVisibility(View.INVISIBLE);
                    add_together.setVisibility(View.INVISIBLE);
                    isVisible = true;
                }
            }
        });


        ImageButton bt3= (ImageButton) findViewById(R.id.addactivity);
        bt3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(information.this,publish.class);
                startActivity(intent);
            }
        });

        ImageButton bt6= (ImageButton) findViewById(R.id.addtogether);
        bt6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(information.this,Publish_toge.class);
                startActivity(intent);
            }
        });


        ImageButton bt1= (ImageButton) findViewById(R.id.tab_main_img);
        bt1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(information.this, mainDisplayActivity .class);
                startActivity(intent);
            }
        });

        ImageButton bt2= (ImageButton) findViewById(R.id.tab_together_img);
        bt2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(information.this,together_activity.class);
                startActivity(intent);
            }
        });

        ImageButton bt5= (ImageButton) findViewById(R.id.tab_person_img);
        bt5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(information.this, Center.class);
                startActivity(intent);
            }
        });



    }


}

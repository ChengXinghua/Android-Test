package com.example.xinghuacheng.howrelax.switchActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class together_activity extends mainDisplayActivity {
    private ImageButton tab_publish_img;
    private LinearLayout add_activity;
    private LinearLayout add_together;
    private boolean isVisible = true;

    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.together_activity);

        listView = (ListView) findViewById(R.id.list);
        //步骤1 一个列表项的内容，就是一个item
        Map<String, Object> item1 = new HashMap<String, Object>();
        item1.put("photo", R.drawable.test1);
        item1.put("name", "足球赛");
        item1.put("user_name", "珺珺");
        item1.put("publish_time", "2天前");
        item1.put("data", "2017.8.1");
        item1.put("place", "东区足球场");
        item1.put("info", "青少年杯足球赛");
        item1.put("talk", "对球赛挺感兴趣的，有北区的小伙伴吗，走起走起╭（′▽‵）╭（′▽‵）╭（′▽‵）╯");
        item1.put("comment_user","爆帅的二大爷");
        item1.put("thumbup_num","3");
        item1.put("comment_num","1");
        item1.put("comment_content","学姐我也去，约我啊");
        item1.put("playbill",R.drawable.timg);

        //步骤2：把这些Map放到List当中
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        data.add(item1);

        //注意：第四个参数和第五个参数要一一对应
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data,
                R.layout.toge_act_list, new String[] { "photo",
                "user_name",
                "publish_time",
                "talk",
                "thumbup_num",
                "comment_num",
                "comment_user",
                "comment_content",
                "playbill",
                "name",
                "data",
                "place",
                "info" },
                new int[] { R.id.photo,
                        R.id.user_name ,
                        R.id.publish_time,
                        R.id.talk,
                        R.id.thumbup_num,
                        R.id.comment_num,
                        R.id.comment_user,
                        R.id.comment_content,
                        R.id.playbill,
                        R.id.name,
                        R.id.data,
                        R.id.place,
                        R.id.info});

        //步骤3：将List中的内容填充到listView里面去
        listView.setAdapter(simpleAdapter);



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

        ImageButton bt1= (ImageButton) findViewById(R.id.tab_main_img);
        bt1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(together_activity.this, mainDisplayActivity.class);
                startActivity(intent);
            }
        });

        ImageButton bt3= (ImageButton) findViewById(R.id.addactivity);
        bt3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(together_activity.this,publish.class);
                startActivity(intent);
            }
        });

        ImageButton bt6= (ImageButton) findViewById(R.id.addtogether);
        bt6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(together_activity.this,Publish_toge.class);
                startActivity(intent);
            }
        });


        ImageButton bt4= (ImageButton) findViewById(R.id.tab_information_img);
        bt4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(together_activity.this,information.class);
                startActivity(intent);
            }
        });

        ImageButton bt5= (ImageButton) findViewById(R.id.tab_person_img);
        bt5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(together_activity.this, Center.class);
                startActivity(intent);
            }
        });


    }
}

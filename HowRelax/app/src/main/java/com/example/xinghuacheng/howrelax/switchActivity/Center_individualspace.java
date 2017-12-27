package com.example.xinghuacheng.howrelax.switchActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.xinghuacheng.howrelax.Activity.mainDisplayActivity;
import com.example.xinghuacheng.howrelax.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dingding on 2017/8/1.
 */

public class Center_individualspace extends mainDisplayActivity {

    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.center_individualspace);

        listView = (ListView) findViewById(R.id.list);
        //步骤1 一个列表项的内容，就是一个item
        Map<String, Object> item1 = new HashMap<String, Object>();

        item1.put("name", "足球赛");
        item1.put("data", "2017.8.1");
        item1.put("place", "东区足球场");
        item1.put("info", "青少年杯足球赛");
        item1.put("playbill",R.drawable.timg);

        //步骤2：把这些Map放到List当中
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        data.add(item1);

        //注意：第四个参数和第五个参数要一一对应
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data,
                R.layout.center_indsp_list, new String[] {
                "playbill",
                "name",
                "data",
                "place",
                "info" },
                new int[] {
                        R.id.playbill,
                        R.id.name,
                        R.id.data,
                        R.id.place,
                        R.id.info});

        //步骤3：将List中的内容填充到listView里面去
        listView.setAdapter(simpleAdapter);




        ImageButton ibt1= (ImageButton) findViewById(R.id.backButton);
        ibt1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Center_individualspace.this, Center.class);
                startActivity(intent);
            }
        });
    }
}

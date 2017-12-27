package com.example.xinghuacheng.howrelax.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.xinghuacheng.howrelax.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class HomepageActivity extends AppCompatActivity {

    private ImageButton tab_publish_img;
    private LinearLayout add_activity;
    private LinearLayout add_together;
    private boolean isVisible = true;

    private ListView listView;


    FragmentAdapter mAdapter;
    ViewPager mPager;

    TextView title;
    TextView text;

    Timer mTimer;
    TimerTask mTask;
    int pageIndex = 1;
    boolean isTaskRun;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.homepage);

        listView = (ListView) findViewById(R.id.list);
        //步骤1 一个列表项的内容，就是一个item
        Map<String, Object> item1 = new HashMap<String, Object>();
        item1.put("background", R.drawable.background);
        item1.put("name", "足球赛");
        item1.put("data", "2017.8.1");
        item1.put("place", "东区足球场");
        item1.put("info", "青少年杯足球赛");

        //步骤2：把这些Map放到List当中
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        data.add(item1);

        //注意：第四个参数和第五个参数要一一对应
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data,
                R.layout.homepage_list, new String[] { "background", "name","data","place","info" },
                new int[] { R.id.background, R.id.name , R.id.data, R.id.place, R.id.info});

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


        // 设置ViewPager
        mPager = (ViewPager) findViewById(R.id.viewpager1);
        mAdapter = new FragmentAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /* 更新手动滑动时的位置 */
            @Override
            public void onPageSelected(int position) {
                pageIndex = position;
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            /* state: 0空闲，1是滑行中，2加载完毕 */
            @Override
            public void onPageScrollStateChanged(int state) {
                // TODO Auto-generated method stub
                System.out.println("state:" + state);
                if (state == 0 && !isTaskRun) {
                    setCurrentItem();
                    startTask();
                } else if (state == 1 && isTaskRun)
                    stopTask();
            }
        });

    }


    /**
     * 开启定时任务
     */
    private void startTask() {
        // TODO Auto-generated method stub
        isTaskRun = true;
        mTimer = new Timer();
        mTask = new TimerTask() {
            @Override
            public void run() {
                pageIndex++;
                mHandler.sendEmptyMessage(0);
            }
        };
        mTimer.schedule(mTask, 2 * 1000, 2 * 1000);// 这里设置自动切换的时间，单位是毫秒，2*1000表示2秒
    }

    // 处理EmptyMessage(0)
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            setCurrentItem();
        }
    };

    /**
     * 处理Page的切换逻辑
     */
    private void setCurrentItem() {
        if (pageIndex == 0) {
            pageIndex = 3;
        } else if (pageIndex == 4) {
            pageIndex = 1;
        }
        mPager.setCurrentItem(pageIndex, false);// 取消动画
    }

    /**
     * 停止定时任务
     */
    private void stopTask() {
        // TODO Auto-generated method stub
        isTaskRun = false;
        mTimer.cancel();
    }

    public void onResume() {
        super.onResume();
        setCurrentItem();
        startTask();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopTask();
    }

    public  void processControls(){
        ImageButton bt2= (ImageButton) findViewById(R.id.tab_together_img);
        bt2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(HomepageActivity.this, com.example.xinghuacheng.howrelax.switchActivity.together_activity.class);
                startActivity(intent);
            }
        });

        ImageButton bt3= (ImageButton) findViewById(R.id.addactivity);
        bt3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(HomepageActivity.this,com.example.xinghuacheng.howrelax.switchActivity.publish.class);
                startActivity(intent);
            }
        });

        ImageButton ibt6= (ImageButton) findViewById(R.id.addtogether);
        ibt6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(HomepageActivity.this,com.example.xinghuacheng.howrelax.switchActivity.Publish_toge.class);
                startActivity(intent);
            }
        });



        ImageButton bt4= (ImageButton) findViewById(R.id.tab_information_img);
        bt4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(HomepageActivity.this,com.example.xinghuacheng.howrelax.switchActivity.information.class);
                startActivity(intent);
            }
        });

        ImageButton bt5= (ImageButton) findViewById(R.id.tab_person_img);
        bt5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(HomepageActivity.this, com.example.xinghuacheng.howrelax.switchActivity.Center.class);
                startActivity(intent);
            }
        });


    }


}

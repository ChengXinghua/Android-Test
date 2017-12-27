package com.example.xinghuacheng.howrelax.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.xinghuacheng.howrelax.R;
import com.example.xinghuacheng.howrelax.switchActivity.ItemAdapter;
import com.example.xinghuacheng.howrelax.switchActivity.secondItem;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Xinghua Cheng on 16/06/2017.
 */

public class mainDisplayActivity extends LoginActivityNew {

    private ListView item_list;

    Handler handler;

    private Bitmap myBitmap = null;

    // ListView使用的自定Adapter物件
    private ItemAdapter itemAdapter;

    ImageView imageView;

    private TextView show_app_name;

    private Bitmap bitmap = null;

    String picurl,thumburl;

    // 儲存所有記事本的List物件
    private List<secondItem> listItems;


    // 選單項目物件
    private MenuItem add_item, search_item, revert_item, share_item, delete_item;


    // 已選擇項目數量
    private int selectedCount = 0;


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

        super.onCreate(savedInstanceState);

        //锁定在个人中心
        setContentView(R.layout.maindisplay);

        processInitViews();


        // 加入範例資料
//        listItems = new ArrayList<>();
//
//        listItems.add(new thirdItem(1, new Date().getTime(), Colors.RED, "校新生杯篮球比赛", "Hello content","","", 0, 0, 0));
//
//        listItems.add(new thirdItem(2, new Date().getTime(), Colors.BLUE, "旧书回收", "Hello Content","","", 0, 0, 0));
//
//        listItems.add(new thirdItem(3, new Date().getTime(), Colors.GREEN, "校新生杯足球比赛！", "Hello content","","", 0, 0, 0));
//
//        listItems.add(new thirdItem(4, new Date().getTime(), Colors.GREEN, "电脑维修", "Hello content","","", 0, 0, 0));


        // 建立自定Adapter物件
//        itemAdapter = new thirdItemAdapter(this,R.layout.third_single_item,listItems);
//
//        item_list.setAdapter(itemAdapter);


        //多线程，handler查询
        handler = new Handler(){

            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                queryByRequire();
            }
        };

        //Toast.makeText(mainDisplayActivity.this,"hello world",Toast.LENGTH_SHORT).show();
        Message msg = new Message();

        msg.arg1 = 0;

        handler.sendMessage(msg);

//        setListViewHeightBasedOnChildren(item_list);

        processControls();

        onClickSingeItem();


        // 设置ViewPager
        mPager = (ViewPager) findViewById(R.id.viewpager);
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


    //获取控件
    public  void processInitViews(){

        item_list = (ListView)findViewById(R.id.headListView);
    }

    //点击控件触发事件
    public  void processControls(){

        ImageButton bt2= (ImageButton) findViewById(R.id.tab_together_img);
        bt2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(mainDisplayActivity.this, com.example.xinghuacheng.howrelax.switchActivity.together_activity.class);
                startActivity(intent);
            }
        });

        ImageButton bt3= (ImageButton) findViewById(R.id.tab_publish_img);
        bt3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(mainDisplayActivity.this,com.example.xinghuacheng.howrelax.switchActivity.publish.class);
                startActivity(intent);
            }
        });


        ImageButton bt4= (ImageButton) findViewById(R.id.tab_information_img);
        bt4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(mainDisplayActivity.this,com.example.xinghuacheng.howrelax.switchActivity.information.class);
                startActivity(intent);
            }
        });
//
        ImageButton bt5= (ImageButton) findViewById(R.id.tab_person_img);
        bt5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(mainDisplayActivity.this, com.example.xinghuacheng.howrelax.switchActivity.Center.class);
                startActivity(intent);
            }
        });

        Button bt= (Button) findViewById(R.id.search_pt1);
        bt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(mainDisplayActivity.this, com.example.xinghuacheng.howrelax.switchActivity.Search.class);
                startActivity(intent);
            }
        });

        ImageButton ibt_map= (ImageButton) findViewById(R.id.mapdisplay);
        ibt_map.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(mainDisplayActivity.this, ShowInMap.class);
                startActivity(intent);
            }
        });

    }


    public  void onClickSingeItem(){
        item_list.setOnItemClickListener(new ListView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

//                Toast.makeText(mainDisplayActivity.this,"hello world",Toast.LENGTH_SHORT).show();

                //获取所点击的项目
                secondItem item = itemAdapter.getItem(position);

                //显示单个项目对应的活动
                Intent itemIntent = new Intent(mainDisplayActivity.this, com.example.xinghuacheng.howrelax.switchActivity.thirdSingleActivity.class);

//                 intent.putExtra("position",position);
                itemIntent.putExtra("com.example.xinghuacheng.howrelax.switchActivity.secondItem", item);
//
////               startActivityForResult(intent,1);
//
                startActivity(itemIntent);
            }
        });


    }

    public void setListViewHeightBasedOnChildren(ListView listView){

        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);

    }

    //查询
    private void queryByRequire(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                    Toast.makeText(mainDisplayActivity.this,"Search", Toast.LENGTH_SHORT).show();

                //查询所有记录
                AVQuery<AVObject> avQuery =  new AVQuery<>("activityTable");

//                avQuery.whereContains("title","足球");

                //组合查询
                AVQuery<AVObject> query = AVQuery.or(Arrays.asList(avQuery));

                //查询到对应的图片
                query.include("activityPicture");

                //查询
                query.findInBackground(new FindCallback<AVObject>() {
                    @Override
                    public void done(List<AVObject> list, AVException e) {

                        listItems = getItem(list);

//                             Toast.makeText(Search.this,"hello world",Toast.LENGTH_SHORT).show();

                        if (listItems.size() == 0) {

                            Toast.makeText(getApplicationContext(), "没有查询结果", Toast.LENGTH_SHORT).show();

                        } else {

                            itemAdapter = new ItemAdapter(mainDisplayActivity.this,R.layout.singleitem,listItems);

                            item_list.setAdapter(itemAdapter);

                            setListViewHeightBasedOnChildren(item_list);
                        }
                    }
                });

            }
        }).start();

    }


    //从list中解析出数据库搜索结果
    public List<secondItem> getItem(List<AVObject> list){

        List<secondItem> result=new ArrayList<>();

        int index=0;

        while(index<list.size()){

            result.add(getRecord(list.get(index)));

            index++;
        }
        return result;
    }


    //解析AvObject
    public secondItem getRecord(AVObject listSingle){


        final secondItem result = new secondItem();

        //获取活动海报
        final AVFile avFile = listSingle.getAVFile("activityPicture");

        picurl = avFile.getUrl();
        result.setUrl(picurl);

        thumburl = avFile.getThumbnailUrl(true,64,64);
        result.setThumburl(thumburl);


        result.setObjectid(listSingle.getObjectId().toString());

        result.setBitmap(myBitmap);

        result.setActTitle(listSingle.get("title").toString());

        result.setActContent(listSingle.get("introduction").toString());

        result.setActDate(listSingle.get("date").toString());

        result.setActTime(listSingle.get("time").toString());

        result.setActTimeCount(listSingle.get("timeCount").toString());

        result.setActAddress(listSingle.get("address").toString());

        return result;
    }


    Runnable r2 = new Runnable() {
        @Override
        public void run() {
            try{
                URL url=new URL(thumburl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                // 设置请求方法，注意大写
                connection.setRequestMethod("GET");
                // 设置连接超时
                connection.setConnectTimeout(5000);
                // 设置读取超时
                connection.setReadTimeout(5000);
                // 5.发送请求与服务器建立连接
                connection.connect();
                // 如果状态码等于200，说明请求成功
                if (connection.getResponseCode() == 200) {
                    // 获取服务器相应头中的流，流中的数据就是客户端请求的数据
                    InputStream is = connection.getInputStream();
                    //读取出流里的数据，并构造成位图对象
                    Bitmap bm = BitmapFactory.decodeStream(is);
                    // ImageView iv = (ImageView) findViewById(R.id.iv);
                    // 将位图对象显示至imageView
                    // iv.setImageBitmap(bm);
                    // 将消息发送至主线程的消息队列
                    myBitmap = bm;
                    Message msg = new Message();
                    // 消息对象可以携带数据
                    msg.obj = bm;
                    msg.what = 1;
                    handler2.sendMessage(msg);

                }else {
                    Message msg = handler.obtainMessage();
                    msg.what = 2;
                    handler2.sendMessage(msg);
                }

            }
            catch (IOException e){

                Message msg = new Message();
                msg.what = 2;
                handler2.sendMessage(msg);
                e.printStackTrace();
            }
        }
    };

    //通知
    Handler handler2=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
//                    imageView.setImageBitmap((Bitmap) msg.obj);
//                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    break;
                case 2:
                    Toast.makeText(getApplication(),"No,No,No",Toast.LENGTH_SHORT).show();
            }
        }
    };

}

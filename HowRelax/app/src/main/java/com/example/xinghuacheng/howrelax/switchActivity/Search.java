package com.example.xinghuacheng.howrelax.switchActivity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.xinghuacheng.howrelax.Activity.mainDisplayActivity;
import com.example.xinghuacheng.howrelax.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.xinghuacheng.howrelax.R.id.search_edit;

/**
 * Created by Xinghua Cheng on 18/06/2017.
 */

public class Search extends AppCompatActivity {

    private ListView item_list;

    private TextView show_app_name;

   private Bitmap myBitmap = null;

    // ListView使用的自定Adapter物件
    private ItemAdapter itemAdapter;

    ImageView imageView;

    // 儲存所有記事本的List物件
    private List<secondItem> listItems;

    // 選單項目物件
    private MenuItem add_item, search_item, revert_item, share_item, delete_item;

    // 已選擇項目數量
    private int selectedCount = 0;

    private  Button okButton;

    String picurl,thumburl;

    Handler handler;

    private SearchView searchView;

    private String titleStr ="足球赛";

    private EditText editSearch;

    private String actPropertyStr;

    private String actTypeStr;

    private String actTCountStr;

    //菜单标题
    private String headers[] = {"类型", "标签", "时长"};

    private ListView listView1;
    private ListView listView2;
    private ListView listView3;

    private MenuListAdapter mMenuAdapter1;
    private MenuListAdapter mMenuAdapter2;
    private MenuListAdapter mMenuAdapter3;

    private DropDownMenu mDropDownMenu;

    private String types[] = {"不限", "活动", "同游"};

    private String labels[] = {"不限","展览","电影","舞会","毕业典礼","歌手大赛","话剧","球类","晚会","其他"};

    private String hours[] = {"不限","2小时以内","2-4小时","4小时以上"};

    private List<View> popupViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        //跳转页面
        ImageButton ibt= (ImageButton) findViewById(R.id.backButton);
        ibt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Search.this, mainDisplayActivity.class);
                startActivity(intent);
            }
        });

        initView();

         //多线程，handler查询
        handler = new Handler(){

            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                queryByRequire();
            }
        };

        processViews();

//        searchView =(SearchView)findViewById(R.id.searchView);
//
//        searchView.setOnQueryTextListener(onQuerySearchView);

        Message msg = new Message();

        msg.arg1 = 0;

        handler.sendMessage(msg);

        // 加入範例資料
//       listItems = new ArrayList<>();
//
//        listItems.add(new secondItem(1, new Date().getTime(), Colors.RED, "關於Android Tutorial的事情", "Hello content", "aa", "bb","cc","dd","ee","ff",myBitmap,0, 0, 0));
//
//        listItems.add(new secondItem(2, new Date().getTime(), Colors.BLUE, "一隻非常可愛的小狗狗!", "她的名字叫「大熱狗」，又叫\n作「奶嘴」，是一隻非常可愛\n的小狗。", "aa", "bb","cc","dd","ee","ff",myBitmap,0, 0, 0));
//
//        listItems.add(new secondItem(3, new Date().getTime(), Colors.GREEN, "一首非常好聽的音樂！", "Hello content", "aa", "bb","cc","dd","ee","ff",myBitmap,0, 0, 0));


      // 建立自定Adapter物件
//        itemAdapter = new ItemAdapter(this,R.layout.singleitem,listItems);
//
//        item_list.setAdapter(itemAdapter);


        //事件触发
        processControllers();

    }

//    private SearchView.OnQueryTextListener onQuerySearchView = new SearchView.OnQueryTextListener() {
//        @Override
//        public boolean onQueryTextSubmit(String s) {
//            //点击键盘的搜索之后才进行搜索
//            return false;
//        }
//
//        @Override
//        public boolean onQueryTextChange(String s) {
//
//
//
//            return false;
//        }
//
//    };



    public void processViews(){

        item_list = (ListView)findViewById(R.id.seek_list);

        okButton = (Button)findViewById(R.id.seek_button);

        editSearch = (EditText)findViewById(search_edit);


//      imageView = (ImageView)findViewById(R.id.id_bmapView);

//      Toast.makeText(Search.this,"hello",Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    /*
    ...



    */
    }


    //初始化显示界面
    private void initView() {

        mDropDownMenu = (DropDownMenu) findViewById(R.id.dropDownMenu);


        //init menu listview

        //这里是每个下拉菜单之后的布局,目前只是简单的listview作为展示
        listView1 = new ListView(Search.this);
        listView2 = new ListView(Search.this);
        listView3 = new ListView(Search.this);

        listView1.setDividerHeight(0);
        listView2.setDividerHeight(0);
        listView3.setDividerHeight(0);

        mMenuAdapter1 = new MenuListAdapter(Search.this, Arrays.asList(types));
        mMenuAdapter2 = new MenuListAdapter(Search.this, Arrays.asList(labels));
        mMenuAdapter3 = new MenuListAdapter(Search.this, Arrays.asList(hours));

        listView1.setAdapter(mMenuAdapter1);
        listView2.setAdapter(mMenuAdapter2);
        listView3.setAdapter(mMenuAdapter3);

        popupViews.add(listView1);
        popupViews.add(listView2);
        popupViews.add(listView3);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                mDropDownMenu.setTabText(types[position]);
                mDropDownMenu.closeMenu();
            }
        });

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                mDropDownMenu.setTabText(labels[position]);
                mDropDownMenu.closeMenu();
            }
        });

        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                mDropDownMenu.setTabText(hours[position]);
                mDropDownMenu.closeMenu();
            }
        });


        //这里添加 内容显示区域,可以是任何布局
        TextView contentView = new TextView(this);
//        contentView.setText("这里是内容区域");
//        contentView.setTextSize(20);
//        contentView.setGravity(Gravity.CENTER);

        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews,contentView );

        listItems = new ArrayList<>();
    }


   //点击控件，触发事件
    public  void processControllers(){

        //获取输入的活动标题
        titleStr = editSearch.getText().toString();

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.arg1 = 0;

                handler.sendMessage(msg);
            }
        });

         item_list.setOnItemClickListener(new ListView.OnItemClickListener(){

             @Override
             public void onItemClick(AdapterView<?> parent, View view,
                                     int position, long id) {


//               Toast.makeText(Search.this,"hello world",Toast.LENGTH_SHORT).show();


                 //获取所点击的项目
                 secondItem item = itemAdapter.getItem(position);

                 //显示单个项目对应的活动
                 Intent itemIntent = new Intent(Search.this, com.example.xinghuacheng.howrelax.switchActivity.singleActivity.class);

//                 intent.putExtra("position",position);
                itemIntent.putExtra("com.example.xinghuacheng.howrelax.switchActivity.secondItem", item);
//
////               startActivityForResult(intent,1);
//
                 startActivity(itemIntent);
             }
         });

    }

    //查询
    private void queryByRequire(){

             new Thread(new Runnable() {
                 @Override
                 public void run() {

//                     Toast.makeText(Search.this,"Search", Toast.LENGTH_SHORT).show();

                     //进行模糊查询
                     AVQuery<AVObject> avQuery =  new AVQuery<>("activityTable");
                     avQuery.whereContains("title",editSearch.getText().toString());


                     AVQuery<AVObject> avQuery1 = new AVQuery<>("activityTable");
                     avQuery1.whereContains("timeCount",editSearch.getText().toString());

                      //组合查询
                     AVQuery<AVObject> query = AVQuery.or(Arrays.asList(avQuery,avQuery1));

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

                                 itemAdapter = new ItemAdapter(Search.this,R.layout.singleitem,listItems);

                                 item_list.setAdapter(itemAdapter);
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

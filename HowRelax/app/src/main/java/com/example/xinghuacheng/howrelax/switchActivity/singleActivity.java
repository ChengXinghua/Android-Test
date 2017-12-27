package com.example.xinghuacheng.howrelax.switchActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xinghuacheng.howrelax.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class singleActivity extends AppCompatActivity {

    private TextView actTypeText,actTitText,actDateText,actTimeText,actTimeCoText,actAddressText,actContentText;

    private ImageView actImageView;

    private  secondItem item;


    String picurl,thumburl;

    Bitmap myBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_single);

        Intent intent = getIntent();

        //获取item
        item  = (secondItem) intent.getExtras().getSerializable("com.example.xinghuacheng.howrelax.switchActivity.secondItem");

        //设定图片
        //getUrl(item.getObjectid());
        picurl = item.getUrl();
        thumburl=item.getThumburl();
        Thread t2 = new Thread(r2);
        t2.start();

        processViews();


        processControllers();


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
                    handler.sendMessage(msg);

                }else {
                    Message msg = handler.obtainMessage();
                    msg.what = 2;
                    handler.sendMessage(msg);
                }

            }
            catch (IOException e){

                Message msg = new Message();
                msg.what = 2;
                handler.sendMessage(msg);
                e.printStackTrace();
            }
        }
    };

    //通知
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
//                    imageView.setImageBitmap((Bitmap) msg.obj);
//                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                    actImageView.setImageBitmap((Bitmap) msg.obj);

                    actImageView.setScaleType(ImageView.ScaleType.FIT_XY);

                    break;
                case 2:
                    Toast.makeText(getApplication(),"No,No,No",Toast.LENGTH_SHORT).show();
            }
        }
    };

    //解析每个Item,并对每个界面上的控件进行赋值

    //根据id获取控件
  public void processViews(){





      //海报
      actImageView = (ImageView)findViewById(R.id.actImage);

      //标题
      actTitText  =(TextView)findViewById(R.id.actTitle);

      //类型
      actTypeText = (TextView)findViewById(R.id.actType);

      //日期
      actDateText = (TextView)findViewById(R.id.actDate);

     //时间点
      actTimeText = (TextView)findViewById(R.id.actTime);

      //时长
      actTimeCoText = (TextView)findViewById(R.id.actTimeCount);

      //地址
      actAddressText = (TextView)findViewById(R.id.actAddress);

      //活动详情
      actContentText = (TextView)findViewById(R.id.actContent);

  }

  //返回到上一级界面
    public  void back(){
        ImageButton backButton = (ImageButton)findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(singleActivity.this,Search.class);
                startActivity(intent);
            }
        });

    }

   //控件事件触发
  public void processControllers(){

      back();

      //解析Item到活动详情界面
      actTitText.setText(item.getActTitle()); //标题

      actTimeCoText.setText(item.getActTimeCount());//时长

      actAddressText.setText(item.getActAddress());//地址

      actImageView.setImageBitmap(item.getBitmap());

      actImageView.setVisibility(View.VISIBLE);

      actDateText.setText(item.getActDate());//日期

      actContentText.setText(item.getActContent());//内容

      actTimeText.setText(item.getActTime());//时间点
      

  }

}

package com.example.xinghuacheng.howrelax;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.xinghuacheng.howrelax.Activity.LoginActivityNew;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        // 测试 SDK 是否正常工作的代码
//        AVObject testObject = new AVObject("TestObject");
//        testObject.put("words","Hello World!");
//        testObject.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(AVException e) {
//                if(e == null){
//                    Log.d("saved","success!");
//                }
//            }
//        });


        Button btn = (Button) findViewById(R.id.button1);

        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivityNew.class);
                startActivity(intent);

            }
        });


    }

//    public void onclick1(View view) //这个方法是弹出一个对话框
//    {
//        AlertDialog.Builder builder=new Builder(this);
//
//        builder.setTitle("若想进入活动平台，请注册");//设置对话框的标题
//        builder.setMessage("你确定要注册吗？");//设置对话框的内容
//        builder.setPositiveButton("确定", new OnClickListener() {  //这个是设置确定按钮
//
//            @Override
//            public void onClick(DialogInterface arg0, int arg1) {
//                Toast.makeText(MainActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        builder.setNegativeButton("取消", new OnClickListener() {  //取消按钮
//
//            @Override
//            public void onClick(DialogInterface arg0, int arg1) {
//                Toast.makeText(MainActivity.this, "取消注册",Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        AlertDialog b=builder.create();
//        b.show();  //必须show一下才能看到对话框，跟Toast一样的道理
//    }


}

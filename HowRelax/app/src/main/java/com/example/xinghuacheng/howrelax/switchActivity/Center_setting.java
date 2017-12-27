package com.example.xinghuacheng.howrelax.switchActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.xinghuacheng.howrelax.Activity.LoginActivityNew;
import com.example.xinghuacheng.howrelax.Activity.mainDisplayActivity;
import com.example.xinghuacheng.howrelax.R;

/**
 * Created by dingding on 2017/8/1.
 */

public class Center_setting extends mainDisplayActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.center_setting);

        //跳转页面
        ImageButton ibt1 = (ImageButton) findViewById(R.id.backButton);
        ibt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Center_setting.this, Center.class);
                startActivity(intent);
            }
        });

        Button signOutBut = (Button)findViewById(R.id.signout);
        signOutBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentV = new Intent(Center_setting.this, LoginActivityNew.class);
                startActivity(intentV);
            }
        });
    }
}

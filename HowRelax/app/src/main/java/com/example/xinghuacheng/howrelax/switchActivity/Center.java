package com.example.xinghuacheng.howrelax.switchActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.xinghuacheng.howrelax.Activity.mainDisplayActivity;
import com.example.xinghuacheng.howrelax.R;

/**
 * Created by Xinghua Cheng on 17/06/2017.
 */

public class Center extends mainDisplayActivity {
    private ImageButton tab_publish_img;
    private LinearLayout add_activity;
    private LinearLayout add_together;
    private boolean isVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.center_1);

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

        ImageButton ibt3= (ImageButton) findViewById(R.id.addactivity);
        ibt3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Center.this,publish.class);
                startActivity(intent);
            }
        });

        ImageButton ibt5= (ImageButton) findViewById(R.id.addtogether);
        ibt5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Center.this,Publish_toge.class);
                startActivity(intent);
            }
        });


        ImageButton ibt1= (ImageButton) findViewById(R.id.tab_main_img);
        ibt1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Center.this, mainDisplayActivity.class);
                startActivity(intent);
            }
        });

        ImageButton ibt4= (ImageButton) findViewById(R.id.tab_information_img);
        ibt4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Center.this,information.class);
                startActivity(intent);
            }
        });

        ImageButton ibt2= (ImageButton) findViewById(R.id.tab_together_img);
        ibt2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Center.this, together_activity.class);
                startActivity(intent);
            }
        });


        Button bt1= (Button) findViewById(R.id.individualspace);
        bt1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Center.this, Center_individualspace.class);
                startActivity(intent);
            }
        });

        Button bt2= (Button) findViewById(R.id.activitymanage);
        bt2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Center.this, Activity_manage_edit.class);
                startActivity(intent);
            }
        });

        Button bt3= (Button) findViewById(R.id.setting);
        bt3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Center.this, Center_setting.class);
                startActivity(intent);
            }
        });



    }

}

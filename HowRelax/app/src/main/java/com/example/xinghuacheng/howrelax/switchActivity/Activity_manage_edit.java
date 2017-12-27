package com.example.xinghuacheng.howrelax.switchActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import com.example.xinghuacheng.howrelax.R;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by dingding on 2017/8/11.
 */

public class Activity_manage_edit extends Center {


    private Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_manage_edit);

        bt = (Button) findViewById(R.id.delete);
        bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_manage_edit.this);
                dialog.setTitle("确定删除该活动吗？");//窗口名

                dialog.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                });
                dialog.show();
            }
        });



    }
}

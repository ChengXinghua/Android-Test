package com.example.xinghuacheng.howrelax.switchActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.xinghuacheng.howrelax.R;

/**
 * Created by Administrator on 2017/8/14.
 */

public class together_list extends together_activity{

    private ImageButton ibt1;
    private ImageButton ibt2;
    private int state = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toge_act_list);

        ibt1 = (ImageButton) findViewById(R.id.good);
        ibt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (state){
                    case 0:
                        ibt1.setBackgroundResource(R.drawable.good1);
                        state=1;
                        break;
                    case 1:
                        ibt1.setBackgroundResource(R.drawable.good2);
                        state=0;
                        break;
                }
            }
        });


        		/*2、带简单输入框的弹出对话框*/
        ibt2 = (ImageButton) findViewById(R.id.comment);
        ibt2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                final EditText edit = new EditText(together_list.this);
                final TextView tv=(TextView) findViewById(R.id.comment_content);
                AlertDialog.Builder dialog = new AlertDialog.Builder(together_list.this);
                dialog.setTitle("请输入评论");//窗口名
                dialog.setView(edit);

                dialog.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String avalue =edit.getText().toString();
                        tv.setText(avalue);
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

package com.example.xinghuacheng.howrelax.switchActivity;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.example.xinghuacheng.howrelax.Activity.mainDisplayActivity;
import com.example.xinghuacheng.howrelax.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import static com.example.xinghuacheng.howrelax.R.layout.publish_activity;


/**
 * Created by Xinghua Cheng on 17/06/2017.
 */

public class publish extends mainDisplayActivity {
    private static final String[] m={"展览","电影","舞会","毕业典礼","歌手大赛","话剧","球类","晚会","其他"};
    private static final String[] n={"2小时以内","2-4小时","4小时以上"};
    private Spinner spinner1;
    private Spinner spinner2;
    private ArrayAdapter<String> adapter;
    int mYear, mMonth, mDay;
    ImageButton ibt1;
    TextView dateDisplay;
    final int DATE_DIALOG = 1;
    public String typeStr;
    public String timeCountStr;
    boolean issueBool = true;
    Bitmap bitmap = null;
    // 啟動功能用的請求代碼
    private static final int START_CAMERA = 0;

    // 記事物件
    private Item item;

    // 檔案名稱
    private String fileName;

    // 照片
    Button takepic;
    private ImageView picture;

    // 寫入外部儲存設備授權請求代碼
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 100;
    // 錄音設備授權請求代碼
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(publish_activity);
        processViews();

        processControllers();

        //跳转页面
        ImageButton ibt= (ImageButton) findViewById(R.id.backButton);
        ibt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(publish.this, mainDisplayActivity.class);
                startActivity(intent);
            }
        });

        ImageButton ibt2= (ImageButton) findViewById(R.id.addmap);
        ibt2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(publish.this, AddMark.class);
                startActivity(intent);
            }
        });



        //分类下拉框
        spinner1 = (Spinner) findViewById(R.id.Spinner01);

        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);

        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //将adapter 添加到spinner中
        spinner1.setAdapter(adapter);

        //添加spinner事件监听
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                typeStr = spinner1.getSelectedItem().toString();

                //设置默认值
                spinner1.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        //时长下拉框
        spinner2 = (Spinner) findViewById(R.id.Spinner02);
        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,n);

        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //将adapter 添加到spinner中
        spinner2.setAdapter(adapter);

        //添加spinner事件监听
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                timeCountStr = spinner2.getSelectedItem().toString();

                //设置默认值
                spinner2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        //日期选择
        ibt1 = (ImageButton) findViewById(R.id.datechoose);
        dateDisplay = (TextView) findViewById(R.id.datedisplay);

        ibt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG);
            }
        });

        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);

        Button fabuBtn = (Button) findViewById(R.id.button2);

        //点击发布按钮将活动信息写入到数据库中
        fabuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                if (onClickPublish(v)==true)
                {
                    EditText editTtTitle =  (EditText) findViewById(R.id.editTextTitle);
                    //获取标题
                    String titleStr = editTtTitle.getText().toString();


                    EditText intro = (EditText)findViewById(R.id.editTextIntro);
                    //获取活动简介
                    String introStr = intro.getText().toString();

                    TextView dateTV = (TextView)findViewById(R.id.datedisplay);
                    //获取日期
                    String dateStr = dateTV.getText().toString();


                    EditText time = (EditText)findViewById(R.id.editTextTime);

                    //获取活动时间
                    String timeStr = time.getText().toString();

                    EditText address = (EditText)findViewById(R.id.editTextAddress);

                    //获取活动地址
                    String addressStr = address.getText().toString();


                    //将活动信息写入到数据库中
                    AVObject activityTab = new AVObject("activityTable");

                    //标题
                    activityTab.put("title",titleStr);

                    //类型
                    activityTab.put("type",typeStr);

                    //活动介绍
                    activityTab.put("introduction",introStr);

                    //活动日期
                    activityTab.put("date",dateStr);

                    //活动时间
                    activityTab.put("time",timeStr);

                    //活动时长
                    activityTab.put("timeCount", timeCountStr);

                    //活动地址
                    activityTab.put("address",addressStr);


                    activityTab.put("activityPicture",new AVFile(fileName,getBitmapByte(fileName)));


                    activityTab.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                // 存储成功
                                Log.d("saved","success!");

                                Toast.makeText(publish.this, "发布成功", Toast.LENGTH_SHORT).show();

                                //进入主界面
                                Intent intent = new Intent(publish.this, mainDisplayActivity.class);
                                startActivity(intent);

                            } else {
                                // 失败的话，请检查网络环境以及 SDK 配置是否正确
                                Toast.makeText(getBaseContext(),"发布活动失败！请检查网络环境以及 SDK 配置是否正确",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{
                    Toast.makeText(publish.this, "取消发布",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


    //点击拍照触发事件


    private void processViews() {

        // 取得顯示照片的ImageView元件
        takepic=(Button)findViewById(R.id.take_picture);
        picture = (ImageView) findViewById(R.id.picture);

    }

    private void processControllers(){
        takepic.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
//                Toast.makeText(publish.this,"dadf",Toast.LENGTH_SHORT).show();
                requestStoragePermission();
            }
        });
    }

    private void requestStoragePermission() {

        //装置版本为Android7.0(N)以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            // 取得授權狀態，參數是請求授權的名稱
            int hasPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            // 如果未授權
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                // 請求授權
                //     第一個參數是請求授權的名稱
                //     第二個參數是請求代碼
                requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION);
                return;
            }
        }

        takePicture();
    }


    //拍摄照片
    private void takePicture(){

        // 啟動相機元件用的Intent物件
        Intent intentCamera =
                new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 设定照片名称
        File pictureFile = configFileName("P", ".jpg",item);
        Uri uri = Uri.fromFile(pictureFile);


        // 設定檔案名稱
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        // 啟動相機元件
        startActivityForResult(intentCamera, START_CAMERA);

    }


    public  File configFileName(String prefix,String extension,Item item){
        if(item==null){
            fileName=FileUtil.getUniqueFileName();
        }
        else{
            fileName=item.getFileName();
        }
        /*if(item.getFileName()!=null && item.getFileName().length()>0){
            fileName=item.getFileName();
        }
        else{
            fileName=FileUtil.getUniqueFileName();
        }*/
        return new File(FileUtil.getExternalStorageDir(FileUtil.APP_DIR),
                prefix+fileName+extension);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        // 如果是寫入外部儲存設備授權請求
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION) {
            // 如果在授權請求選擇「允許」
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 拍攝照片
                takePicture();
            }
            // 如果在授權請求選擇「拒絕」
            else {
                // 顯示沒有授權的訊息
                Toast.makeText(this, "没有权限",
                        Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

      //发布活动之后，弹出对话框确定是否进入主界面
        public boolean onClickPublish(View view) //这个方法是弹出一个对话框
        {
          //默认是否发布 为“是”
          issueBool = true;

        AlertDialog.Builder builder=new Builder(this);

        builder.setTitle("活动发布");//设置对话框的标题
        builder.setMessage("你确定要发布该活动吗？");//设置对话框的内容
        builder.setPositiveButton("确定", new OnClickListener() {  //这个是设置确定按钮

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                issueBool = true;
                Toast.makeText(publish.this, "发布成功", Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNegativeButton("取消", new OnClickListener() {  //取消按钮

            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                issueBool = false;

                Toast.makeText(publish.this, "取消发布",Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog b=builder.create();
        b.show();  //必须show一下才能看到对话框，跟Toast一样的道理
          return issueBool;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                // 照像
                case START_CAMERA:
                    // 設定照片檔案名稱
                    item= new  Item();
                    item.setFileName(fileName);
                    break;
            }
        }
    }

    //set picture to imageview
    @Override
    public void onResume(){
        super.onResume();
        if(item==null){

        }
        else{
            if(item.getFileName()!=null && item.getFileName().length()>0){
                File file=configFileName("p",".jpg",item);
                if(file.exists()){

                    picture.setVisibility(View.VISIBLE);

                    FileUtil.fileToImageView(file.getAbsolutePath(),picture);
                }
            }
        }
    }



    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }



    //將圖片轉為二進制存儲到數據庫中
    public byte[] getBitmapByte(String fileName){
        File file=configFileName("p",".jpg",item);
        bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //参数1转换类型，参数2压缩质量，参数3字节流资源
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }



    /**
     * 设置日期 利用StringBuffer追加
     */
    public void display() {
        dateDisplay.setText(new StringBuffer().append(mMonth + 1).append("-").append(mDay).append("-").append(mYear).append(" "));
    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
        }

    };

}

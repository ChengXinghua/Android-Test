package com.example.xinghuacheng.howrelax.Activity;

import com.example.xinghuacheng.howrelax.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentOne extends Fragment{

    ImageView image;
    TextView text;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	// ��һ�����������Fragment��Ҫ��ʾ�Ľ��沼��,�ڶ������������Fragment������Activity,�����������Ǿ�����fragment�Ƿ�����Activity
    	View view=inflater.inflate(R.layout.fragment, container, false);
        image = (ImageView) view.findViewById(R.id.ads);
        image.setImageResource(R.drawable.a02);
        text = (TextView) view.findViewById(R.id.ads_title);
        text.setText("图书会");
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        System.out.println("FragmentOne onCreate");
    }
    
	public void onResume(){
        super.onResume();
        System.out.println("FragmentOne onResume");
    }
    
    @Override
    public void onPause(){
        super.onPause();
        System.out.println("FragmentOne onPause");
    }
    
    @Override
    public void onStop(){
        super.onStop();
        System.out.println("FragmentOne onStop");
    }
}

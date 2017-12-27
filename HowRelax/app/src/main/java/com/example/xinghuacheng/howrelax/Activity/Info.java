package com.example.xinghuacheng.howrelax.Activity;

import com.example.xinghuacheng.howrelax.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Info implements Serializable
{
	private static final long serialVersionUID = -758459502806858414L;
	/**
	 * 精度
	 */
	private double latitude;
	/**
	 * 纬度
	 */
	private double longitude;
	/**
	 * 图片ID，真实项目中可能是图片路径
	 */
	private int imgId;
	/**
	 * 商家名称
	 */
	private String name;
	/**
	 * 距离
	 */
	private String time;
	/**
	 * 赞数量
	 */
	private int zan;

	public static List<Info> infos = new ArrayList<Info>();

	static
	{
		infos.add(new Info(32.106067,118.914621, R.drawable.a01, "校园篮球赛",
				"2017.6.17 Sat 4:00pm", 1456));
		infos.add(new Info(32.110789,118.917564,R.drawable.a02, "一起读书吧",
				"2017.6.18 Sun 8:00am", 456));
		infos.add(new Info(32.121259,118.922521, R.drawable.a03, "走进地球",
				"2017.6.21 Tus 1:30pm", 1456));
		infos.add(new Info(32.111351,118.92103, R.drawable.a04, "就业招聘",
				"2017.6.22 Wen 9:00am", 1456));
	}

	public Info()
	{
	}

	public Info(double latitude, double longitude, int imgId, String name,
			String time, int zan)
	{
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.imgId = imgId;
		this.name = name;
		this.time = time;
		this.zan = zan;
	}

	public double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}

	public double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}

	public String getName()
	{
		return name;
	}

	public int getImgId()
	{
		return imgId;
	}

	public void setImgId(int imgId)
	{
		this.imgId = imgId;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDistance()
	{
		return time;
	}

	public void setDistance(String distance)
	{
		this.time = distance;
	}

	public int getZan()
	{
		return zan;
	}

	public void setZan(int zan)
	{
		this.zan = zan;
	}

}

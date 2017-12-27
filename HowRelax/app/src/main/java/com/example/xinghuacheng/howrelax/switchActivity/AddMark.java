package com.example.xinghuacheng.howrelax.switchActivity;

import android.os.Bundle;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.xinghuacheng.howrelax.Activity.mainDisplayActivity;
import com.example.xinghuacheng.howrelax.R;

/**
 * Created by dingding on 2017/6/19.
 */

public class AddMark extends mainDisplayActivity {

    /**
     * 定位SDK核心类
     */
    private LocationClient locationClient;

    /**
     * 百度地图控件
     */
    private MapView mapView;
    /**
     * 百度地图对象
     */
    private BaiduMap baiduMap;

    boolean isFirstLoc = true; // 是否首次定位

    //定义Maker坐标点
    LatLng point = null;
    LatLng finalpoint=null;

    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        SDKInitializer.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.addmark);
        /**
         * 地图初始化
         */
        //获取地图控件引用
        mapView = (MapView) findViewById(R.id.bmapView);
        //获取百度地图对象
        baiduMap=mapView.getMap();
        // 开启定位图层
        baiduMap.setMyLocationEnabled(true);
        /**
         * 定位初始化
         */
        locationClient=new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(myListener);
        //定位配置信息
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);//定位请求时间间隔
        locationClient.setLocOption(option);
        //开启定位
        locationClient.start();
        this.setLocatonOption();
        locationClient.start();
        setLocatonOption();
        locationClient.start();
    }

    /**
     * 定位监听
     */

    private BDLocationListener myListener=new BDLocationListener(){
        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            if (bdLocation==null||mapView==null)
                return;
            MyLocationData locDate=new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    .direction(100)
                    .latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude())
                    .build();
            baiduMap.setMyLocationData(locDate);

            //Maker坐标点
            point = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.icon_marka);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions options = new MarkerOptions()
                    .position(point)  //设置marker的位置
                    .icon(bitmap)  //设置marker图标
                    .zIndex(9)  //设置marker所在层级
                    .draggable(true);  //设置手势拖拽
            // 将marker添加到地图上
            final Marker marker = (Marker) (baiduMap.addOverlay(options));

            baiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
                public void onMarkerDrag(Marker marker1) {

                    //拖拽中
                }
                public void onMarkerDragEnd(Marker marker1) {
                    //拖拽结束
                }
                public void onMarkerDragStart(Marker marker1) {
                    //开始拖拽
                }
            });

            //最终位置的坐标
            finalpoint=marker.getPosition();
            latitude = finalpoint.latitude;
            longitude = finalpoint.longitude;




            if (isFirstLoc){
                isFirstLoc=false;

                LatLng latLng=new LatLng(bdLocation.getLatitude()
                        ,bdLocation.getLongitude());

                MapStatusUpdate update= MapStatusUpdateFactory.newLatLngZoom(latLng,16);
                baiduMap.animateMapStatus(update);
            }

        }
    };


    public  void  setLocatonOption(){
        LocationClientOption option=new LocationClientOption();
        option.setOpenGps(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        option.setAddrType("all");
        option.setCoorType("bd09ll");
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        option.setNeedDeviceDirect(true);
        locationClient.setLocOption(option);
    }

    protected  void onDestroy(){

        locationClient.stop();
        baiduMap.setMyLocationEnabled(false);
        super.onDestroy();
        mapView.onDestroy();
        mapView=null;
    }

    public void  onResume(){
        super.onResume();
        mapView.onResume();
    }
    public  void  onPause(){
        super.onPause();
        mapView.onPause();
    }




}

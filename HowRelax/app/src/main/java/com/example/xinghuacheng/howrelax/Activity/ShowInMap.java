package com.example.xinghuacheng.howrelax.Activity;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.xinghuacheng.howrelax.R;

import java.lang.reflect.Method;
import java.util.List;

public class ShowInMap extends Activity {

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

    // 初始化全局 bitmap 信息，不用时及时 recycle
    private BitmapDescriptor mIconMaker;
    /**
     * 详细信息的 布局
     */
    private RelativeLayout mMarkerInfoLy;

    //覆盖物相关
    private BitmapDescriptor mMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SDKInitializer.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_in_map);
        /**
         * 地图初始化
         */
        //获取地图控件引用
        mapView = (MapView) findViewById(R.id.bmapView);
        mMarkerInfoLy = (RelativeLayout) findViewById(R.id.id_marker_info);
        //获取百度地图对象
        baiduMap=mapView.getMap();
        mIconMaker = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
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
        initMarkerClickEvent();
        initMapClickEvent();
        initMarker();
        addOverlays(Info.infos);
    }

    //定位相关
    private BDLocationListener myListener=new BDLocationListener() {
        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            if (bdLocation == null || mapView == null)
                return;
            MyLocationData locDate = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    .direction(100)
                    .latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude())
                    .build();
            baiduMap.setMyLocationData(locDate);
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

    //添加覆盖物
    private void addOverlays(List<Info> infos) {
        baiduMap.clear();
        LatLng latLng = null;
        Marker marker = null;
        OverlayOptions options;
        for(Info info:infos)
        {
            //经纬度
            latLng = new LatLng(info.getLatitude(),info.getLongitude());
            //图标
            options = new MarkerOptions().position(latLng).icon(mMarker).zIndex(5);
            marker = (Marker) baiduMap.addOverlay(options);
            Bundle arg0 = new Bundle();
            arg0.putSerializable("info",info);
            marker.setExtraInfo(arg0);
        }
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        baiduMap.setMapStatus(msu);
    }

    private void initMarker() {
        mMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);


    }

    private void initMapClickEvent()
    {
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener()
        {
            @Override
            public boolean onMapPoiClick(MapPoi arg0)
            {
                return false;
            }

            @Override
            public void onMapClick(LatLng arg0)
            {
                mMarkerInfoLy.setVisibility(View.GONE);
                baiduMap.hideInfoWindow();
            }
        });
    }

    private void initMarkerClickEvent()
    {
        // 对Marker的点击
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(final Marker marker)
            {
                // 获得marker中的数据
                Info info = (Info) marker.getExtraInfo().get("info");
                InfoWindow mInfoWindow;
                // 生成一个TextView用户在地图中显示InfoWindow
                TextView location = new TextView(getApplicationContext());
                location.setBackgroundColor(0xAAFFFFFF);
                location.setPadding(30, 20, 30, 20);
                location.setText(info.getName());
                // 将marker所在的经纬度的信息转化成屏幕上的坐标
                final LatLng ll = marker.getPosition();
                Point p = baiduMap.getProjection().toScreenLocation(ll);
                p.y -= 47;
                LatLng llInfo = baiduMap.getProjection().fromScreenLocation(p);
                // 为弹出的InfoWindow添加点击事件
                mInfoWindow = new InfoWindow(location, llInfo,-47);
                new OnInfoWindowClickListener()
                {

                    @Override
                    public void onInfoWindowClick()
                    {
                        // 隐藏InfoWindow
                        baiduMap.hideInfoWindow();
                    }
                };
                // 显示InfoWindow
                baiduMap.showInfoWindow(mInfoWindow);
                // 设置详细信息布局为可见
                mMarkerInfoLy.setVisibility(View.VISIBLE);
                // 根据商家信息为详细信息布局设置信息
                popupInfo(mMarkerInfoLy, info);
                return true;
            }
        });
    }

    /**
     * 默认点击menu菜单，菜单项不现实图标，反射强制其显示
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu)
    {

        if (featureId == Window.FEATURE_OPTIONS_PANEL && menu != null)
        {
            if (menu.getClass().getSimpleName().equals("MenuBuilder"))
            {
                try
                {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e)
                {
                }
            }

        }
        return super.onMenuOpened(featureId, menu);
    }

    /**
     * 根据info为布局上的控件设置信息
     *
     *
     * @param info
     */
    protected void popupInfo(RelativeLayout mMarkerLy, Info info)
    {
        ViewHolder viewHolder = null;
        if (mMarkerLy.getTag() == null)
        {
            viewHolder = new ViewHolder();
            viewHolder.infoImg = (ImageView) mMarkerLy
                    .findViewById(R.id.info_img);
            viewHolder.infoName = (TextView) mMarkerLy
                    .findViewById(R.id.info_name);
            viewHolder.infoDistance = (TextView) mMarkerLy
                    .findViewById(R.id.info_distance);
            viewHolder.infoZan = (TextView) mMarkerLy
                    .findViewById(R.id.info_zan);

            mMarkerLy.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) mMarkerLy.getTag();
        viewHolder.infoImg.setImageResource(info.getImgId());
        viewHolder.infoDistance.setText(info.getDistance());
        viewHolder.infoName.setText(info.getName());
        viewHolder.infoZan.setText(info.getZan() + "");
    }


    /**
     * 复用弹出面板mMarkerLy的控件
     *
     * @author zhy
     *
     */
    private class ViewHolder
    {
        ImageView infoImg;
        TextView infoName;
        TextView infoDistance;
        TextView infoZan;
    }

    protected  void onDestroy(){

        locationClient.stop();
        baiduMap.setMyLocationEnabled(false);
        super.onDestroy();
        mapView.onDestroy();
        mapView=null;
    }

    protected void  onResume(){
        super.onResume();
        mapView.onResume();
    }
    protected  void  onPause(){
        super.onPause();
        mapView.onPause();
    }
}
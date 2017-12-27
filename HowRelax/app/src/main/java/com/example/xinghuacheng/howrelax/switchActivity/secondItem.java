package com.example.xinghuacheng.howrelax.switchActivity;

import android.graphics.Bitmap;

import java.util.Date;
import java.util.Locale;

/**
 * Created by Xinghua Cheng on 04/08/2017.
 */




public class secondItem implements java.io.Serializable {



    private long id;

    private long dateTime;

    private Colors color;

    private String actTitle;  //活动标题

    private String actContent; //活动内容

    private String actProperty; //活动性质

    private String actType; //活动类型

    private String actDate; //活动日期

    private String actTime;  //活动开始时间点

    private String actTimeCount; //活动时长

    private String actAddress; //活动地点

    private double latitude;
    private double longitude;
    private String address;

    private long lastModify;
    private boolean selected;

    //存储图片
    private String fileName;
    private Bitmap bitmap;
    private String objectid;
    private String url;
    private String thumburl;
    private String ownerid;


    public secondItem(){

        actTitle = "";

        actContent = "";

        color = Colors.LIGHTGREY;
    }

    public  secondItem(long id, long dateTime, Colors color,String actTitle,String actContent,
                 String actProperty,String actType,String actDate,String actTime,String actTimeCount,
                 String actAddress,Bitmap bitmap,double latitude, double longitude, long lastModify){

        this.id = id;
        this.dateTime = dateTime;
        this.color = color;
        this.actContent = actContent;
        this.actTitle = actTitle;
        this.actProperty = actProperty;
        this.actType =actType;
        this.actDate =actDate;
        this.actTime = actTime;
        this.actTimeCount =actTimeCount;
        this.actAddress =actAddress;
        this.bitmap = bitmap;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lastModify = lastModify;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDatetime() {
        return dateTime;
    }

    // 裝置區域的日期時間
    public String getLocaleDatetime() {
        return String.format(Locale.getDefault(), "%tF  %<tR", new Date(dateTime));
    }

    // 裝置區域的日期
    public String getLocaleDate() {
        return String.format(Locale.getDefault(), "%tF", new Date(dateTime));
    }

    // 裝置區域的時間
    public String getLocaleTime() {
        return String.format(Locale.getDefault(), "%tR", new Date(dateTime));
    }

    public void setDatetime(long datetime) {
        this.dateTime = datetime;
    }

    public Colors getColor() {
        return color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }


    public  String getActContent(){

        return actContent;
    }

    public  void setActContent(String actContent){

        this.actContent = actContent;

    }

    public String getActTitle() {
        return  actTitle;
    }

    public void setActTitle(String actTitle){

        this.actTitle = actTitle;
    }


    public String getActProperty() {
        return  actProperty;
    }

    public void setActProperty(String actProperty){

        this.actProperty = actProperty;
    }


    public String getActDate() {
        return  actDate;
    }

    public void setActDate(String actDate){
        this.actDate  = actDate;

    }

    public String getActTime() {

        return  actTime;
    }

    public void setActTime(String actTime){

        this.actTime = actTime;
    }


    public String getActTimeCount() {
        return  actTimeCount;
    }

    public void setActTimeCount(String actTimeCount){

        this.actTimeCount = actTimeCount;
    }


    public String getActAddress() {
        return  actAddress;
    }

    public void setActAddress(String actAddress){

        this.actAddress= actAddress;
    }


    //latitude
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    //longtitude
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address=address;
    }

    //lastmodify
    public long getLastModify() {
        return lastModify;
    }

    public void setLastModify(long lastModify) {
        this.lastModify = lastModify;
    }

    //iselected
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    //filname
    public String getFileName(){
        return fileName;
    }

    public void setFileName(String fileName){
        this.fileName=fileName;
    }

    //二进制
    public Bitmap getBitmap(){
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap=bitmap;
    }

    //数据库序列号
    public String getObjectid(){
        return objectid;
    }

    public void setObjectid(String objectid){
        this.objectid=objectid;
    }

    //封面url
    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url=url;
    }

    //缩略图url
    public String getThumburl(){
        return thumburl;
    }

    public void setThumburl(String thumburl){
        this.thumburl= thumburl;
    }

    //所有者信息,对应其objectid
    public String getOwnerid(){
        return ownerid;
    }

    public void setOwnerid(String ownerid){
        this.ownerid=ownerid;
    }




}
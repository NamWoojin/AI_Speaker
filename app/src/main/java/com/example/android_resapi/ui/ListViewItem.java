package com.example.android_resapi.ui;

import android.graphics.drawable.Drawable;

public class ListViewItem {
    private int type;


    private Drawable morningImage ;
    private Drawable afternoonImage;
    private Drawable nightImage;

    private String sleepTime;
    private String goBedTime;
    private String getUpTime;

    private String heartBeat;

    public void setType(int type) {
        this.type = type ;
    }
    public int getType(){
        return this.type;
    }

    public void setMorningImage(Drawable icon) {
        this.morningImage = icon ;
    }
    public void setAfternoonImage(Drawable icon) {
        this.afternoonImage = icon ;
    }
    public void setNightImage(Drawable icon) {
        this.nightImage = icon ;
    }
    public Drawable getMorningImage(){
        return this.morningImage;
    }
    public Drawable getAfternoonImage(){
        return this.afternoonImage;
    }
    public Drawable getNightImage(){
        return this.nightImage;
    }

    public void setSleepTime(String time){
        this.sleepTime = time;
    }
    public void setGoBedTime(String time){
        this.goBedTime = time;
    }
    public void setGetUpTime(String time){
        this.getUpTime = time;
    }
    public String getSleepTime(){
        return this.sleepTime;
    }
    public String getGoBedTime(){
        return this.goBedTime;
    }
    public String getGetUpTime(){
        return this.getUpTime;
    }


    public void setHeartBeat(String beat){
        this.heartBeat = beat;
    }
    public String getHeartBeat(){
        return this.heartBeat;
    }

}

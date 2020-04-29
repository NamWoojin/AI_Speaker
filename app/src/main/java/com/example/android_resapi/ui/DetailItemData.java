package com.example.android_resapi.ui;

public class DetailItemData {
    private String Date;
    private String goBedTime;
    private String wakeUpTime;

    public void setWakeUpTime(String wakeUpTime) {
        this.wakeUpTime = wakeUpTime;
    }

    public void setGoBedTime(String goBedTime) {
        this.goBedTime = goBedTime;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getWakeUpTime() {
        return wakeUpTime;
    }

    public String getGoBedTime() {
        return goBedTime;
    }

    public String getDate() {
        return Date;
    }
}

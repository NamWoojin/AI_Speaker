package com.example.android_resapi.ui;

public class DetailItemData {
    private String Date="";
    private String goBedTime="";
    private String wakeUpTime="";

    private String MealDate = "";
    private Boolean Breakfast = false;
    private Boolean Lunch = false;
    private Boolean Dinner = false;


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


    public void setMealDate(String mealDate) {
        MealDate = mealDate;
    }

    public void setBreakfast(Boolean breakfast) {
        Breakfast = breakfast;
    }

    public void setLunch(Boolean lunch) {
        Lunch = lunch;
    }

    public void setDinner(Boolean dinner) {
        Dinner = dinner;
    }

    public String getMealDate() {
        return MealDate;
    }

    public Boolean getBreakfast() {
        return Breakfast;
    }

    public Boolean getLunch() {
        return Lunch;
    }

    public Boolean getDinner() {
        return Dinner;
    }

}

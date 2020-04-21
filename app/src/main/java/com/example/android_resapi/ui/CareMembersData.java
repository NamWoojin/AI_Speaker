package com.example.android_resapi.ui;

public class CareMembersData {
    private String Name;
    private String Gender;
    private String Age;
    private String DeviceId;

    public void setName(String name) {
        this.Name = name;
    }
    public void setAge(String age) {
        this.Age = age;
    }
    public void setGender(String gender) {
        this.Gender = gender;
    }
    public void setDeviceId(String deviceId){this.DeviceId = deviceId;}
    public String getName(){
        return this.Name;
    }
    public String getAge(){
        return this.Age;
    }
    public String getGender(){
        return this.Gender;
    }
    public String getDeviceId(){
        return this.DeviceId;
    }
}
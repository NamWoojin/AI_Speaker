package com.example.android_resapi.ui;

import android.graphics.drawable.Drawable;

public class medicineListViewItem {

    private String medicineType="";
    private String medicineName="";
    private String medicineCycle="";


    public void setMedicineType(String medicineDay) {
        this.medicineType = medicineDay;
    }
    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }
    public void setMedicineCycle(String medicineTime) {
        this.medicineCycle = medicineTime;
    }
    public String getMedicineType() {
        return medicineType;
    }
    public String getMedicineName() {
        return medicineName;
    }
    public String getMedicineCycle() {
        return medicineCycle;
    }


}

package com.example.android_resapi.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.android_resapi.R;

public class medicineListLinearLayout extends LinearLayout {

    public medicineListLinearLayout(Context context){
        super(context);

        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.healthinfo_medicine_listview,this,true);
    }
}

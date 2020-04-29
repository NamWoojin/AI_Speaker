package com.example.android_resapi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.android_resapi.R;
import com.example.android_resapi.ui.apicall.GetLogSleepTime;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailScrollingActivity extends AppCompatActivity {
    int HealthInfoNum = 0;
    int DeviceId = 0;
    String MemberName = "";
    String urlbase = "";
    int loadNum = 0;

    LinearLayout medicineLinearLayout = (LinearLayout)findViewById(R.id.MedicineDetailLinearLayout_id);
    LinearLayout mealLinearLayout = (LinearLayout)findViewById(R.id.MealDetailLinearLayout_id);
    LinearLayout sleepLinearLayout = (LinearLayout)findViewById(R.id.SleepDetailLinearLayout_id);
    LinearLayout heartLinearLayout = (LinearLayout)findViewById(R.id.HeartDetailLinearLayout_id);

    LinearLayout[] LinearLayouts = {medicineLinearLayout,mealLinearLayout,sleepLinearLayout,heartLinearLayout};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent  = getIntent();
        HealthInfoNum = intent.getIntExtra("position",-1);
        DeviceId = intent.getIntExtra("DeviceId",-1);
        MemberName = intent.getStringExtra("CareMember");
        urlbase = intent.getStringExtra("url");

        for(int i = 0;i<LinearLayouts.length;i++){
            if(HealthInfoNum == i)
                LinearLayouts[i].setVisibility(View.VISIBLE);
            else
                LinearLayouts[i].setVisibility(View.GONE);
        }

        if(HealthInfoNum == 0){//복약주기 디테일

        }
        else if(HealthInfoNum == 1){//식사여부 디테일

        }
        else if(HealthInfoNum == 2){//수면주기 디테일

        }
        else if(HealthInfoNum == 3){//심박수 디테일

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadNum = 0;
        if(HealthInfoNum == 0){//복약주기 디테일

        }
        else if(HealthInfoNum == 1){//식사여부 디테일

        }
        else if(HealthInfoNum == 2){//수면주기 디테일
            GetSleepTime();
        }
        else if(HealthInfoNum == 3){//심박수 디테일

        }
    }

    void GetSleepTime(){
        long curDateMS = System.currentTimeMillis()-86400000*14*loadNum;
        long twoWeeksDateMS = curDateMS- 86400000*14*(loadNum+1);
        String curDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(curDateMS));
        String twoWeeksData = new SimpleDateFormat("yyyy-MM-dd").format(new Date(twoWeeksDateMS));

        String params = "?device_id="+DeviceId + "&from="+twoWeeksData+"%2000:00:00&to="+curDate+"%2023:59:59";  //전날 00시부터 오늘 현재시간까지의 기록 조회
        String sleepUrl = urlbase+params;
        new GetLogSleepTime(DetailScrollingActivity.this,sleepUrl,"detail",this).execute();
    }
}

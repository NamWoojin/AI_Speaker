package com.example.android_resapi.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android_resapi.R;
import com.example.android_resapi.ui.apicall.GetLogMeal;
import com.example.android_resapi.ui.apicall.GetLogSleepTime;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HealthInfoActivity extends AppCompatActivity {
    String SocialWorker;
    String MemberName;
    String DeviceId;
    String urlbase;
    String SleepTimeurlbase = "https://h7vyxc6832.execute-api.ap-northeast-2.amazonaws.com/SleepDB/SleepTime";
    String Mealurlbase = "https://ilpmvnc607.execute-api.ap-northeast-2.amazonaws.com/MealDB/Meal";

    Drawable morning_image;
    Drawable lunch_image;
    Drawable dinner_image;

    String wholeTime="";
    String sleepTime="";
    String wakeupTime="";

    SwipeRefreshLayout refreshLayout;
    ListViewAdapter adapter = new ListViewAdapter();
    ListView listView;

    @Override
    protected void onStart() {
        super.onStart();
        GetSleepTime();
        GetMeal();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_info);

        Intent intent = getIntent();
        SocialWorker = intent.getStringExtra("SocialWorker");
        MemberName = intent.getStringExtra("MemberName");
        DeviceId = intent.getStringExtra("DeviceId");
        urlbase = intent.getStringExtra("urlbase");

        morning_image=ContextCompat.getDrawable(this,R.drawable.meal_morning_not);
        lunch_image=ContextCompat.getDrawable(this,R.drawable.meal_afternoon_not);
        dinner_image=ContextCompat.getDrawable(this,R.drawable.meal_night_not);


        TextView CareMemberNameTextView = (TextView)findViewById(R.id.CareMemberNameTextView_id);
        CareMemberNameTextView.setText(MemberName+"님의 건강정보입니다");

        listView = (ListView)findViewById(R.id.HealthInfoListView_id);
        listView.setAdapter(adapter);
        adapter.addItem();
        adapter.addItem(morning_image, lunch_image, dinner_image);
        adapter.addItem(wholeTime,sleepTime,wakeupTime);
        adapter.addItem("70");


        FloatingActionButton sendMessegeButton = (FloatingActionButton)findViewById(R.id.sendMessagePopUpButton_id);
        sendMessegeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(HealthInfoActivity.this,SendMessagePopUpActivity.class);
                intent.putExtra("MemberName",MemberName);
                startActivity(intent);
            }
        });

        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetSleepTime();
                GetMeal();
                listView.setAdapter(adapter);
                refreshFinish();
            }

        });
    }

    void refreshFinish(){
        refreshLayout.setRefreshing(false);
    }

    void GetSleepTime(){
        long curDateMS = System.currentTimeMillis();
        long yesterDateMS = curDateMS- 86400000*2;
        String curDate = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").format(new Date(curDateMS));
        String yesterDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(yesterDateMS));

        String params = String.format("?device_id=%s&from=%s00:00:00&to=%s",DeviceId,yesterDate,curDate);  //전날 00시부터 오늘 현재시간까지의 기록 조회
        String sleepUrl = SleepTimeurlbase+params;
        new GetLogSleepTime(HealthInfoActivity.this,sleepUrl,"simple",this).execute();
    }

    void GetMeal(){
        long curDateMS = System.currentTimeMillis();
        long yesterDateMS = curDateMS- 86400000;
        String curDate = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").format(new Date(curDateMS));
        String yesterDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(yesterDateMS));

        String params = String.format("?device_id=%s&from=%s00:00:00&to=%s",DeviceId,yesterDate,curDate);  //전날 00시부터 오늘 현재시간까지의 기록 조회
        String mealUrl = Mealurlbase+params;
        new GetLogMeal(HealthInfoActivity.this,mealUrl,"simple",this).execute();
    }

    public void SetHealthInfoMeal(Boolean breakfast, Boolean lunch, Boolean dinner){
        morning_image = ContextCompat.getDrawable(this,R.drawable.meal_morning_not);
        lunch_image=ContextCompat.getDrawable(this,R.drawable.meal_afternoon_not);
        dinner_image=ContextCompat.getDrawable(this,R.drawable.meal_night_not);
        if(breakfast)
            morning_image = ContextCompat.getDrawable(this,R.drawable.meal_morning);
        if(lunch)
            lunch_image= ContextCompat.getDrawable(this,R.drawable.meal_afternoon);
        if(dinner)
            dinner_image = ContextCompat.getDrawable(this,R.drawable.meal_night);

        adapter.removeItem(1);
        adapter.addItem(morning_image, lunch_image, dinner_image);
        listView.setAdapter(adapter);
    }

    public void SetHealtInfoSleepTime(String wholeTime,String sleepTime,String wakeupTime){
        this.wholeTime = wholeTime;
        this.sleepTime = sleepTime;
        this.wakeupTime = wakeupTime;
        adapter.removeItem(2);
        adapter.addItem(wholeTime,sleepTime,wakeupTime);
        listView.setAdapter(adapter);
    }

}

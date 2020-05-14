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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android_resapi.R;
import com.example.android_resapi.ui.apicall.GetLogMeal;
import com.example.android_resapi.ui.apicall.GetLogMedicine;
import com.example.android_resapi.ui.apicall.GetLogSleepTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HealthInfoActivity extends AppCompatActivity {
    String SocialWorker;
    String MemberName;
    String DeviceId;
    String urlbase;
    String SleepTimeurlbase = "https://h7vyxc6832.execute-api.ap-northeast-2.amazonaws.com/SleepDB/SleepTime";
    String Mealurlbase = "https://ilpmvnc607.execute-api.ap-northeast-2.amazonaws.com/MealDB/Meal";
    String Medicineurlbase = "https://qfz80h42a4.execute-api.ap-northeast-2.amazonaws.com/MedicationDB/Medication";

    Drawable morning_image;
    Drawable lunch_image;
    Drawable dinner_image;

    String wholeTime="";
    String sleepTime="";
    String wakeupTime="";

    SwipeRefreshLayout refreshLayout;
    ListViewAdapter adapter = new ListViewAdapter();
    ListView listView;

    medicineListViewAdapter mLVA = new medicineListViewAdapter();
    ListView medicineListView;
    ArrayList<medicineListViewItem> mLVI = new ArrayList<>();

    @Override
    protected void onStart() {
        super.onStart();
        GetMeal();
        GetSleepTime();
        GetMedicine();
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

//        LinearLayout medicineSubtractButtonLinearLayout = (LinearLayout)findViewById(R.id.medicine_subtract_button_LinearLayout_id);
//        medicineSubtractButtonLinearLayout.setVisibility(View.GONE);

        adapter.addItem(mLVI);
        adapter.addItem(morning_image, lunch_image, dinner_image);
        adapter.addItem(wholeTime,sleepTime,wakeupTime);
        adapter.addItem("70");



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                if(position == 0){
                    intent = new Intent(HealthInfoActivity.this,MedicineEditActivity.class);
                }
                else{
                    intent = new Intent(HealthInfoActivity.this,DetailScrollingActivity.class);
                    intent.putExtra("position",position-1);
                    intent.putExtra("DeviceId",DeviceId);

                    if(position == 1){
                        intent.putExtra("urlbase",Mealurlbase);
                    }
                    else if(position == 2){
                        intent.putExtra("urlbase",SleepTimeurlbase);
                    }

                }

                startActivity(intent);

            }
        });

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
                GetMedicine();
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

        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String curDate = date.format(today);

        Calendar twoWeekBefore = Calendar.getInstance();
        twoWeekBefore.add(Calendar.DATE, -1);
        String yesterDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(twoWeekBefore.getTime());

        String params = "?device_id="+DeviceId + "&from="+yesterDate+"%2000:00:00&to="+curDate+"%2023:59:59";  //전날 00시부터 오늘 현재시간까지의 기록 조회
        String sleepUrl = SleepTimeurlbase+params;
        new GetLogSleepTime(HealthInfoActivity.this,sleepUrl,"simple",this).execute();
    }

    void GetMeal(){
        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String curDate = date.format(today);

        String params = "?device_id="+DeviceId + "&from="+curDate+"%2000:00:00&to="+curDate+"%2023:59:59";  //전날 00시부터 오늘 현재시간까지의 기록 조회
        String mealUrl = Mealurlbase+params;
        new GetLogMeal(HealthInfoActivity.this,mealUrl,"simple",this).execute();
    }

    void GetMedicine(){
        String medicineUrl = Medicineurlbase+ "?device_id="+DeviceId;
        new GetLogMedicine(HealthInfoActivity.this,medicineUrl,"simple",this).execute();
    }

    public void SetHealtInfoMedicine(ArrayList<medicineListViewItem> mLVI){
        adapter.removeItem(0);
        adapter.addItem(mLVI);
        listView.setAdapter(adapter);
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

package com.example.android_resapi.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android_resapi.R;

public class HealthInfoActivity extends AppCompatActivity {
    String SocialWorker;
    String MemberName;
    String urlbase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_info);

        Intent intent = getIntent();
        SocialWorker = intent.getStringExtra("SocialWorker");
        MemberName = intent.getStringExtra("MemberName");
        urlbase = intent.getStringExtra("urlbase");

        TextView CareMemberNameTextView = (TextView)findViewById(R.id.CareMemberNameTextView_id);
        CareMemberNameTextView.setText(MemberName+"님의 건강정보입니다");

        ListView listView;
        ListViewAdapter adapter;

        adapter = new ListViewAdapter();

        listView = (ListView)findViewById(R.id.HealthInfoListView_id);


        adapter.addItem();
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.meal_morning_not), ContextCompat.getDrawable(this,R.drawable.meal_afternoon_not),ContextCompat.getDrawable(this,R.drawable.meal_night_not)) ;
        adapter.addItem("6시간","11시","5시");
        adapter.addItem("102");

        listView.setAdapter(adapter);

        FloatingActionButton sendMessegeButton = (FloatingActionButton)findViewById(R.id.sendMessagePopUpButton_id);
        sendMessegeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(HealthInfoActivity.this,SendMessagePopUpActivity.class);
                intent.putExtra("MemberName",MemberName);
                startActivity(intent);
            }
        });
    }
}

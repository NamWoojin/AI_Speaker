package com.example.android_resapi.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android_resapi.R;
import com.example.android_resapi.ui.apicall.UpdateShadow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddCareMemberActivity extends AppCompatActivity {
    final static String TAG = "AndroidAPITest";
    String Gender = "남";
    String urlbase;
    String Worker_Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_care_member);

        Intent intent = getIntent();
        urlbase=intent.getStringExtra("urlbase");
        Worker_Name = intent.getStringExtra("Worker_Name");

        Button addButton = (Button)findViewById(R.id.addCareMemberButton_id);
        final Button maleButton = (Button)findViewById(R.id.MaleButton_id);
        final Button femaleButton = (Button)findViewById(R.id.FemaleButton_id);



        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText NameEditText = (EditText)findViewById(R.id.CMNameEditText_id) ;
                String CMName = NameEditText.getText().toString();
                EditText AgeEditText = (EditText)findViewById(R.id.CMAgeEditText_id);
                String CMAge = AgeEditText.getText().toString();
                EditText DeviceIdEditText = (EditText)findViewById(R.id.CMDeviceEditText_id);
                String CMDeviceId = DeviceIdEditText.getText().toString();
                SendInformation(CMName,Gender,CMAge,CMDeviceId);
                finish();
            }
        });
        maleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gender = "Male";
                maleButton.setBackgroundColor(Color.rgb(15,76,129));
                maleButton.setTextColor(Color.WHITE);
                femaleButton.setBackgroundColor(Color.TRANSPARENT);
                femaleButton.setTextColor(Color.BLACK);

            }
        });
        femaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gender = "Female";
                maleButton.setBackgroundColor(Color.TRANSPARENT);
                maleButton.setTextColor(Color.BLACK);
                femaleButton.setBackgroundColor(Color.rgb(15,76,129));
                femaleButton.setTextColor(Color.WHITE);
            }
        });


        maleButton.callOnClick();
    }

    void SendInformation(String name,String gender, String age,String deviceid){
        JSONObject payload = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();

            JSONObject tag0 = new JSONObject();
            tag0.put("tagName", "Device_Id");
            tag0.put("tagValue", deviceid);
            jsonArray.put(tag0);

            JSONObject tag1 = new JSONObject();
            tag1.put("tagName", "SocialWorkerName");
            tag1.put("tagValue", Worker_Name);
            jsonArray.put(tag1);

            JSONObject tag2 = new JSONObject();
            tag2.put("tagName", "MemberName");
            tag2.put("tagValue", name);
            jsonArray.put(tag2);

            JSONObject tag3 = new JSONObject();
            tag3.put("tagName", "MemberAge");
            tag3.put("tagValue", age);
            jsonArray.put(tag3);

            JSONObject tag4 = new JSONObject();
            tag4.put("tagName", "MemberGender");
            tag4.put("tagValue", gender);
            jsonArray.put(tag4);

            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));


            JSONObject tag5 = new JSONObject();
            tag5.put("tagName", "RegisterTime");
            tag5.put("tagValue", time);
            jsonArray.put(tag5);

            if (jsonArray.length() > 0)
                payload.put("tags", jsonArray);
        } catch (JSONException e) {
            Log.e(TAG, "JSONEXception");
        }
        Log.i(TAG,"payload="+payload);
        if (payload.length() >0 )
            new UpdateShadow(AddCareMemberActivity.this,urlbase).execute(payload);
        else
            Toast.makeText(AddCareMemberActivity.this,"변경할 상태 정보 입력이 필요합니다", Toast.LENGTH_SHORT).show();

    }
}

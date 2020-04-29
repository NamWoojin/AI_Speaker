package com.example.android_resapi.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android_resapi.R;
import com.example.android_resapi.ui.apicall.GetLogCareMembers;

public class CareMembersListActivity extends AppCompatActivity {
    String urlbase;
    String Worker_Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_members_list);

        Intent intent = getIntent();
        urlbase = intent.getStringExtra("urlbase");
        Worker_Name = intent.getStringExtra("Worker_Name");



        ListView CareMembersListView = (ListView) findViewById(R.id.CareMembersListView_id);
        CareMembersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(CareMembersListActivity.this,HealthInfoActivity.class);
                CareMembersData cmData = (CareMembersData)parent.getAdapter().getItem(position);
                intent.putExtra("SocialWorker",Worker_Name);
                intent.putExtra("MemberName",cmData.getName());
                intent.putExtra("DeviceId",cmData.getDeviceId());
                intent.putExtra("urlbase",urlbase);
                startActivity(intent);
            }
        });

        FloatingActionButton addMemberButton = (FloatingActionButton)findViewById(R.id.AddMemberButton_id);
        addMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CareMembersListActivity.this,AddCareMemberActivity.class);
                intent.putExtra("urlbase",urlbase);
                intent.putExtra("Worker_Name",Worker_Name);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        String params = String.format("?Worker_Name=%s",Worker_Name);
        String url = urlbase+params;
        new GetLogCareMembers(CareMembersListActivity.this,url,Worker_Name).execute();

    }



}

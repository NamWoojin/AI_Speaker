package com.example.android_resapi.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_resapi.R;
import com.example.android_resapi.ui.apicall.GetLog;

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
                intent.putExtra("urlbase",urlbase);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        String params = String.format("?Worker_Name=%s",Worker_Name);
        String url = urlbase+params;
        new GetLog(CareMembersListActivity.this,url).execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {

        switch(item.getItemId())
        {
            case R.id.AddCareMember:
                Intent intent = new Intent(CareMembersListActivity.this,AddCareMemberActivity.class);
                intent.putExtra("urlbase",urlbase);
                intent.putExtra("Worker_Name",Worker_Name);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}

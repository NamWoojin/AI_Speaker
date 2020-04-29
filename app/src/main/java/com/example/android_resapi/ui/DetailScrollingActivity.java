package com.example.android_resapi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.android_resapi.R;
import com.example.android_resapi.ui.apicall.GetLogSleepTime;

import java.util.ArrayList;
import java.util.Calendar;

public class DetailScrollingActivity extends AppCompatActivity implements DetailItemAdapter.OnLoadMoreListener{

    String urlbase = "";
    String DeviceId = "";
    int position = 0;
    int loadNum = 0;

    private DetailItemAdapter mAdapter;
    private ArrayList<DetailItemData> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_scrolling);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        itemList = new ArrayList<DetailItemData>();
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.Detail_Recycler_View);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new DetailItemAdapter(this);
        mAdapter.setLinearLayoutManager(mLayoutManager);
        mAdapter.setRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        Intent intent = getIntent();
        position = intent.getIntExtra("position",-1);
        DeviceId = intent.getStringExtra("DeviceId");
        urlbase = intent.getStringExtra("urlbase");

        if(position == 0){
            setTitle("식사 패턴");
        }
        else if(position == 1){
            setTitle("수면 패턴");
        }
        else if(position ==2){
            setTitle("심박수");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadNum = 0;
        GetSleepTime();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void GetSleepTime(){

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, loadNum*(-14));
        String curDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

        Calendar twoWeekBefore = Calendar.getInstance();
        cal.add(Calendar.DATE, (loadNum+1)*(-14));
        String yesterDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

        String params = "?device_id="+DeviceId + "&from="+yesterDate+"%2000:00:00&to="+curDate+"%2023:59:59";  //전날 00시부터 오늘 현재시간까지의 기록 조회
        String sleepUrl = urlbase+params;
        new GetLogSleepTime(DetailScrollingActivity.this,sleepUrl,"detail",this,loadNum).execute();
    }

    @Override
    public void onLoadMore() {
        mAdapter.setProgressMore(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                itemList.clear();
                mAdapter.setProgressMore(false);

                loadNum +=1;
                GetSleepTime();
                ///////이부분에을 자신의 프로젝트에 맞게 설정하면 됨
                //다음 페이지? 내용을 불러오는 부분

            }
        }, 2000);
    }

    public void AddData(ArrayList<DetailItemData> did){
        mAdapter.addAll(did);
    }
    public void AddMoreData (ArrayList<DetailItemData> did){

        mAdapter.addItemMore(did);
        mAdapter.setMoreLoading(false);
    }
}

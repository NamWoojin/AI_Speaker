package com.example.android_resapi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.android_resapi.R;

import java.util.ArrayList;

public class MedicineEditActivity extends AppCompatActivity {
    String urlbase = "";
    ArrayList<medicineListViewItem> mLVI;
    medicineRecyclerViewAdapter mRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_edit);

        Toast.makeText(this,"옆으로 밀어서 삭제하세요.", Toast.LENGTH_LONG).show();
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("복약 주기 수정");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mLVI = (ArrayList<medicineListViewItem>) intent.getSerializableExtra("mLVI");
        urlbase = intent.getStringExtra("urlbase");

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.medicine_edit_RecyclerView_id);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRVAdapter = new medicineRecyclerViewAdapter();
        mRVAdapter.addItem(mLVI);
        mRecyclerView.setAdapter(mRVAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        FloatingActionButton sendMessegeButton = (FloatingActionButton)findViewById(R.id.Add_Medicine_Button_id);
        sendMessegeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MedicineEditActivity.this,SendMessagePopUpActivity.class);
                //intent.putExtra("MemberName",MemberName);
                startActivity(intent);
            }
        });
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

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            mLVI.remove(position);
            mRVAdapter.notifyItemRemoved(position);
        }
    };


}



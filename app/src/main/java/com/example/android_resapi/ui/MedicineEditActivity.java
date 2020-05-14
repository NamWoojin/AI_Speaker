package com.example.android_resapi.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.android_resapi.R;

public class MedicineEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayout medicineSubtractButtonLinearLayout = (LinearLayout)findViewById(R.id.medicine_subtract_button_LinearLayout_id);
        medicineSubtractButtonLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_subtract, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.medicine_add:
                Toast.makeText(this, "add",Toast.LENGTH_SHORT).show();
                break;
            case R.id.medicine_subtract:
                Toast.makeText(this, "subtract",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

}

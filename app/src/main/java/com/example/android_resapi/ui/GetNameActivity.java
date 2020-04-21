package com.example.android_resapi.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android_resapi.R;

public class GetNameActivity extends AppCompatActivity {

    String urlbase = "https://n0x51433j3.execute-api.ap-northeast-2.amazonaws.com/DB/MemberLogging";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_name);

        Button  getNameButton = (Button)findViewById(R.id.GetNameButton_id);
        final EditText editText = (EditText)findViewById(R.id.EditName_id) ;
        getNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GetNameActivity.this,CareMembersListActivity.class);
                intent.putExtra("urlbase",urlbase);
                intent.putExtra("Worker_Name",editText.getText().toString());
                startActivity(intent);

            }
        });
    }
}

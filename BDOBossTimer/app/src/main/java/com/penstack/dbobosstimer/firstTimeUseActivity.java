package com.penstack.dbobosstimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class firstTimeUseActivity extends AppCompatActivity {

    Intent intentToMain;
    Button firstStart;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_use);

        firstStart =  (Button)findViewById(R.id.firstStart);
        intentToMain = new Intent(firstTimeUseActivity.this, MainActivity.class);
        firstStart.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                startActivity(intentToMain);
            }
        });
    }
}

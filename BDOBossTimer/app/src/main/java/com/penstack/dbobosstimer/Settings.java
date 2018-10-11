package com.penstack.dbobosstimer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.RadioButton;
import android.view.View;
import android.widget.RadioGroup;

public class Settings extends AppCompatActivity {
    RadioGroup radioGroup;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        radioGroup = (RadioGroup) findViewById(R.id.ServerGroup);

    }
}




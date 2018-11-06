package com.penstack.dbobosstimer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.ads.consent.ConsentStatus;


public class firstTimeUseActivity extends AppCompatActivity {

    Intent intentToMain;
    Button firstStart;
    final String PREFS_NAME = "BDO_TIMER_PREFS";
    final String EUSERVER = "EU";
    final String NASERVER = "NA";
    final int EUSERVER_CONSTANT = 1;
    final int NASERVER_CONSTANT = 2;
    final String PREF_SERVER_CONSTANT = "0";
    final int DOESNT_EXIST = -1;
    int currentServerSelection;
    SharedPreferences prefs;
    String GDPRCONSENT = "-1";
    final int NOCONSENTGIVEN = 0;
    final int CONSENTGIVEN = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_use);

        final GdprHelper gdprHelper = new GdprHelper(this);
        gdprHelper.initialise();

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        currentServerSelection = prefs.getInt(PREF_SERVER_CONSTANT,DOESNT_EXIST);

        TextView optOut = (TextView) findViewById(R.id.optOut);
        optOut.setMovementMethod(LinkMovementMethod.getInstance());

        TextView privacyPocicy = (TextView) findViewById(R.id.privacyPolicy);
        privacyPocicy.setMovementMethod(LinkMovementMethod.getInstance());

        firstStart =  (Button)findViewById(R.id.firstStart);
        intentToMain = new Intent(firstTimeUseActivity.this, MainActivity.class);
        firstStart.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if(  currentServerSelection != -1)
                {
                    startActivity(intentToMain);
                    int  conPass = gdprHelper.getCon();
                    Log.d("TAG", conPass + "");
                    switch (conPass){
                        case 0:
                            prefs.edit().putInt(GDPRCONSENT, NOCONSENTGIVEN).apply();
                            // Log.d("TAG1", conPass + "");
                        case 1:
                            prefs.edit().putInt(GDPRCONSENT, CONSENTGIVEN).apply();
                            // Log.d("TAG1", conPass + "");
                    }
                }
                else
                {

                }
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rbEU:
                if (checked)
                    prefs.edit().putInt(PREF_SERVER_CONSTANT, EUSERVER_CONSTANT).apply();
                    currentServerSelection = prefs.getInt(PREF_SERVER_CONSTANT,DOESNT_EXIST);
                break;
            case R.id.rbNA:
                if (checked)
                    prefs.edit().putInt(PREF_SERVER_CONSTANT, NASERVER_CONSTANT).apply();
                    currentServerSelection = prefs.getInt(PREF_SERVER_CONSTANT,DOESNT_EXIST);
                break;
        }
    }

}

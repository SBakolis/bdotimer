package com.penstack.dbobosstimer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.RadioButton;
import android.view.View;

public class Settings extends AppCompatActivity {

    final String PREFS_NAME = "BDO_TIMER_PREFS";
    //final String EUSERVER = "EU";
    //final String NASERVER = "NA";
    final int EUSERVER_CONSTANT = 1;
    final int NASERVER_CONSTANT = 2;
    final String PREF_SERVER_CONSTANT = "0";
    SharedPreferences prefs;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

         prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rbEU:
                if (checked)
                    prefs.edit().putInt(PREF_SERVER_CONSTANT, EUSERVER_CONSTANT).apply();
                    break;
            case R.id.rbNA:
                if (checked)
                    prefs.edit().putInt(PREF_SERVER_CONSTANT, NASERVER_CONSTANT).apply();
                    break;
        }
    }
}




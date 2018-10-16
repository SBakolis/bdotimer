package com.penstack.dbobosstimer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.CheckBox;
import android.widget.RadioButton;
import android.view.View;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Settings extends AppCompatActivity {

    final String PREFS_NAME = "BDO_TIMER_PREFS";
    //final String EUSERVER = "EU";
    //final String NASERVER = "NA";
    final int EUSERVER_CONSTANT = 1;
    final int NASERVER_CONSTANT = 2;
    final String PREF_SERVER_CONSTANT = "0";
    final ArrayList<String> NOTIFY_BOSS=new ArrayList<>();
    final Set<String> BossNotify=new HashSet<String>();
    final String PREF_NOTIFY="";
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

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();


        switch(view.getId()) {
            case R.id.checkKaranda:
                if (checked) {
                    NOTIFY_BOSS.add("Karanda");//prefs.edit().putStringSet("Karanda",BossNotify).apply();
                } else
                    NOTIFY_BOSS.add("");
                break;
            case R.id.checkKutum:
                if (checked) {

                    NOTIFY_BOSS.add("Kutum");// prefs.edit().putStringSet("Kutum",BossNotify).apply();
                } else
                    NOTIFY_BOSS.add("");
                break;
            case R.id.checkKzarka:
                if (checked) {

                    NOTIFY_BOSS.add("Kzarka");//prefs.edit().putStringSet("Kzarka",BossNotify).apply();
                } else
                    NOTIFY_BOSS.add("");
                break;
            case R.id.checkNouver:
                if (checked) {

                    NOTIFY_BOSS.add("Nouver");//prefs.edit().putStringSet("Nouver",BossNotify).apply();
                } else
                    NOTIFY_BOSS.add("");
                break;
            case R.id.checkQuint:
                if (checked) {

                    NOTIFY_BOSS.add("Quint");//prefs.edit().putStringSet("Quint",BossNotify).apply();
                } else
                    NOTIFY_BOSS.add("");
                break;
            case R.id.checkVell:
                if (checked) {

                    NOTIFY_BOSS.add("Vell");// prefs.edit().putString(NOTIFY_BOSS.get(5),"Vell").apply();
                } else
                    NOTIFY_BOSS.add("");
                break;
            case R.id.checkOffin:
                if (checked) {

                    NOTIFY_BOSS.add("Offin");// prefs.edit().putString(NOTIFY_BOSS.get(6),"Offin").apply();
                } else
                    NOTIFY_BOSS.add("");
                break;
        }
         BossNotify.addAll(NOTIFY_BOSS);
        prefs.edit().putStringSet(PREF_NOTIFY,BossNotify).apply();
    }
}




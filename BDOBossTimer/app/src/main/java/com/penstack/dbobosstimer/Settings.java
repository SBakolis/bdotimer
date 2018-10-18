package com.penstack.dbobosstimer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.CheckBox;
import android.widget.RadioButton;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
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
    final String PREF_NOTIFY="NotificationList";
    ArrayList<Boss>  BossEU=(ArrayList<Boss>) getIntent().getSerializableExtra("BossDayEUList");
    ArrayList<Boss>  BossNA=(ArrayList<Boss>) getIntent().getSerializableExtra("BossDayNAList");
    SharedPreferences prefs;
    RadioButton rbEU,rbNa;

    private PendingIntent pendingIntent;
    private AlarmManager manager;
    public Calendar calendar;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

         prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
       rbEU=(RadioButton)findViewById(R.id.rbEU);
        rbNa=(RadioButton) findViewById(R.id.rbNA);
       CheckBox CheckKaranda=(CheckBox) findViewById(R.id.checkKaranda);
       CheckBox CheckKutum=(CheckBox) findViewById(R.id.checkKutum);
       CheckBox CheckKzarka=(CheckBox) findViewById(R.id.checkKzarka);
       CheckBox CheckNouver=(CheckBox) findViewById(R.id.checkNouver);
       CheckBox CheckQuint=(CheckBox) findViewById(R.id.checkQuint);
       CheckBox CheckVell=(CheckBox) findViewById(R.id.checkVell);
       CheckBox CheckOffin=(CheckBox) findViewById(R.id.checkOffin);

       rbEU.setChecked(prefs.getBoolean("EU",false));
       rbNa.setChecked(prefs.getBoolean("NA",false));
       CheckKaranda.setChecked(prefs.getBoolean("Karanda",false));
       CheckKzarka.setChecked(prefs.getBoolean("Kzarka",false));
       CheckKutum.setChecked(prefs.getBoolean("Kutum",false));
       CheckNouver.setChecked(prefs.getBoolean("Nouver",false));
       CheckOffin.setChecked(prefs.getBoolean("Offin",false));
       CheckVell.setChecked(prefs.getBoolean("Vell",false));
       CheckQuint.setChecked(prefs.getBoolean("Quint",false));
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rbEU:
                if (checked) {
                    prefs.edit().putInt(PREF_SERVER_CONSTANT, EUSERVER_CONSTANT).apply();
                    prefs.edit().putBoolean("EU", checked).apply();// na apothikeuei kai to oti einai checkarismeno
                    prefs.edit().putBoolean("NA",false).apply();
                }
                else

                    prefs.edit().putBoolean("EU", checked).apply();// na to ksetickarei

                break;
            case R.id.rbNA:
                if (checked){

                    prefs.edit().putInt(PREF_SERVER_CONSTANT, NASERVER_CONSTANT).apply();
                    prefs.edit().putBoolean("NA", checked).apply();
                    prefs.edit().putBoolean("EU", false).apply();
                }
                else
                    prefs.edit().putBoolean("NA",checked).apply();

                break;
        }
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked2 = ((CheckBox) view).isChecked();


        switch(view.getId()) {
            case R.id.checkKaranda:
                if (checked2) {
                    NOTIFY_BOSS.add("Karanda");//prefs.edit().putStringSet("Karanda",BossNotify).apply();
                    prefs.edit().putBoolean("Karanda",checked2).apply();
                    if(rbEU.isChecked()){

                        for(int w=0;w<BossEU.size();w++){

                            BossEU.get(w).getBossDay();
                            BossEU.get(w).getBossHour();
                            BossEU.get(w).getBossMin();
                        }
                    }
                    else
                        for(int w=0;w<BossNA.size();w++){
                            BossNA.get(w).getBossDay();
                            BossNA.get(w).getBossHour();
                            BossNA.get(w).getBossMin();
                        }
                } else
                    //NOTIFY_BOSS.add("");
                    prefs.edit().putBoolean("Karanda",checked2).apply();
                break;
            case R.id.checkKutum:
                if (checked2) {

                    NOTIFY_BOSS.add("Kutum");// prefs.edit().putStringSet("Kutum",BossNotify).apply();
                    prefs.edit().putBoolean("Kutum",checked2).apply();
                    if(rbEU.isChecked()){

                        for(int w=0;w<BossEU.size();w++){
                            BossEU.get(w).getBossDay();
                            BossEU.get(w).getBossHour();
                            BossEU.get(w).getBossMin();
                        }
                    }
                    else
                        for(int w=0;w<BossNA.size();w++){

                            BossNA.get(w).getBossDay();
                            BossNA.get(w).getBossHour();
                            BossNA.get(w).getBossMin();
                        }
                }
                else {

                    //NOTIFY_BOSS.add("");
                    prefs.edit().putBoolean("Kutum", checked2).apply();
                }
                break;
            case R.id.checkKzarka:
                if (checked2) {

                    NOTIFY_BOSS.add("Kzarka");
                    prefs.edit().putBoolean("Kzarka",checked2).apply();
                    if(rbEU.isChecked()){

                        for(int w=0;w<BossEU.size();w++){
                            BossEU.get(w).getBossDay();
                            BossEU.get(w).getBossHour();
                            BossEU.get(w).getBossMin();
                        }
                    }
                    else
                        for(int w=0;w<BossNA.size();w++){
                            BossNA.get(w).getBossDay();
                            BossNA.get(w).getBossHour();
                            BossNA.get(w).getBossMin();
                        }
                }
                else {

                    //NOTIFY_BOSS.add("");
                    prefs.edit().putBoolean("Kzarka", checked2).apply();
                }
                break;
            case R.id.checkNouver:
                if (checked2) {

                    NOTIFY_BOSS.add("Nouver");
                    prefs.edit().putBoolean("Nouver",checked2).apply();

                }
                else {
                   // NOTIFY_BOSS.add("");
                    prefs.edit().putBoolean("Nouver", checked2).apply();
                }
                break;
            case R.id.checkQuint:
                if (checked2) {

                    NOTIFY_BOSS.add("Quint");//prefs.edit().putStringSet("Quint",BossNotify).apply();
                    prefs.edit().putBoolean("Quint",checked2).apply();
                }
                else {

                    //NOTIFY_BOSS.add("");
                    prefs.edit().putBoolean("Quint", checked2).apply();
                }
                break;
            case R.id.checkVell:
                if (checked2) {

                    NOTIFY_BOSS.add("Vell");// prefs.edit().putString(NOTIFY_BOSS.get(5),"Vell").apply();
                    prefs.edit().putBoolean("Vell",checked2).apply();
                }
                else {

                    //NOTIFY_BOSS.add("");
                    prefs.edit().putBoolean("Vell", checked2).apply();
                }
                break;
            case R.id.checkOffin:
                if (checked2) {

                    NOTIFY_BOSS.add("Offin");// prefs.edit().putString(NOTIFY_BOSS.get(6),"Offin").apply();
                    prefs.edit().putBoolean("Offin",checked2).apply();
                }
                else {

                   // NOTIFY_BOSS.add("");
                    prefs.edit().putBoolean("Offin",checked2).apply();
                }
                break;
        }
         BossNotify.addAll(NOTIFY_BOSS);
        prefs.edit().putStringSet(PREF_NOTIFY,BossNotify).apply();
    }

    public void startAlarm(View view, int dayOfTheWeek, int hourOfTheDay, int minutes) {
        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        int interval = 1000 * 60 * 60 * 24 * 7;
        calendar.set(Calendar.DAY_OF_WEEK, dayOfTheWeek);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfTheDay);
        calendar.set(Calendar.MINUTE, minutes);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
    }

    public void cancelAlarm(View view) {
        if (manager != null) {
            manager.cancel(pendingIntent);
            //Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
        }
    }
}



